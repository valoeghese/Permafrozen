package net.ladysnake.permafrozen.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.util.GridPoint;
import net.ladysnake.permafrozen.util.JitteredGrid;
import net.ladysnake.permafrozen.util.SimpleIntCache;
import net.ladysnake.permafrozen.worldgen.terrain.TerrainSampler;
import net.ladysnake.permafrozen.worldgen.terrain.Terrain;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.dynamic.RegistryLookupCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeCoords;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CarvingMask;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.chunk.AquiferSampler;
import net.minecraft.world.gen.chunk.Blender;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.random.AbstractRandom;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;
import net.minecraft.world.gen.random.RandomSeed;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

// TODO noise caves
public class PermafrozenChunkGenerator extends ChunkGenerator {
	public PermafrozenChunkGenerator(Registry<Biome> biomeRegistry, BiomeSource biomeSource, long seed, Supplier<ChunkGeneratorSettings> settings) {
		super(biomeSource, biomeSource, settings.get().getStructuresConfig(), seed);

		this.biomeRegistry = biomeRegistry;
		this.seed = seed;
		this.voronoiSeed = (int) seed;
		this.settings = settings;
		this.terrainSampler = biomeSource instanceof PermafrozenBiomeSource pbs ? pbs.terrainSampler : new TerrainSampler(biomeRegistry, seed);
		this.terrainHeightSampler = new SimpleIntCache(512, this::calculateTerrainHeight);
		this.fakeNoiseCGForCarving = new NoiseChunkGenerator(BuiltinRegistries.NOISE_PARAMETERS, biomeSource, 0, settings);
	}

	private final Registry<Biome> biomeRegistry;
	private final long seed;
	private final int voronoiSeed;
	private final Supplier<ChunkGeneratorSettings> settings;
	private final TerrainSampler terrainSampler;
	private final SimpleIntCache terrainHeightSampler;
	private final NoiseChunkGenerator fakeNoiseCGForCarving; // because 1.18's carvers depend on this

	@Override
	protected Codec<? extends ChunkGenerator> getCodec() {
		return CODEC;
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		return new PermafrozenChunkGenerator(this.biomeRegistry, this.populationSource.withSeed(seed), seed, this.settings);
	}

	@Override
	public void buildSurface(ChunkRegion region, StructureAccessor structures, Chunk chunk) {
		ChunkPos chunkPos = chunk.getPos();
		int chunkX = chunkPos.x;
		int chunkZ = chunkPos.z;
		AbstractRandom rand = new ChunkRandom(new AtomicSimpleRandom(region.getSeed())).createRandomDeriver().createRandom(chunkX, 0, chunkZ);

		int startX = chunkPos.getStartX();
		int startZ = chunkPos.getStartZ();

		for(int xo = 0; xo < 16; ++xo) {
			for(int zo = 0; zo < 16; ++zo) {
				int x = startX + xo;
				int z = startZ + zo;
				int height = this.terrainHeightSampler.sample(x, z);

				this.terrainSampler.sample(startX, startZ + zo).buildSurface(chunk, rand, x, z, height, this.getSeaLevel());
			}
		}

		this.buildBedrock(chunk);
	}

	private void buildBedrock(Chunk chunk) {
		BlockPos.Mutable mutable = new BlockPos.Mutable();
		int minY;

		if (chunk.getBottomY() == (minY = this.getMinimumY())) { // cubic chunks compat. the rest of the code has it so may as well
			for(int xo = 0; xo < 16; ++xo) {
				for (int zo = 0; zo < 16; ++zo) {
					chunk.setBlockState(mutable.set(xo, minY, zo), Blocks.BEDROCK.getDefaultState(), false);
				}
			}
		}
	}
	@Override
	public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, StructureAccessor accessor, Chunk chunk) {
		ChunkPos pos = chunk.getPos();
		int seaLevel = this.getSeaLevel();
		BlockPos.Mutable setPos = new BlockPos.Mutable();
		Heightmap oceanFloor = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
		Heightmap surface = chunk.getHeightmap(Heightmap.Type.WORLD_SURFACE_WG);
		final int startX = pos.getStartX();
		final int startZ = pos.getStartZ();

		// CC Compat
		int[] chunkHeights = new int[17 * 17];
		int[] terrainHeights = new int[16 * 16];

		for (int x = 0; x < 17; ++x) {
			int totalX = startX + x;

			for (int z = 0; z < 17; ++z) {
				int totalZ = startZ + z;
				int sample = this.terrainHeightSampler.sample(totalX, totalZ);

				if (z < 16 && x < 16) {
					terrainHeights[(x * 16) + z] = sample;
				}

				chunkHeights[(x * 17) + z] = Math.min(chunk.getTopY() - 1, sample);
			}
		}

		for (int x = 0; x < 16; ++x) {
			int totalX = startX + x;
			setPos.setX(x);

			for (int z = 0; z < 16; ++z) {
				int totalZ = startZ + z;
				setPos.setZ(z);

				int height = chunkHeights[(x * 17) + z];
				int deepslateHeight = MathHelper.floor(3 * MathHelper.sin(totalX * 0.022f) + 3 * MathHelper.sin(totalZ * 0.022f));
				BlockState state;

				for (int y = chunk.getBottomY(); y < height; ++y) {
					try {
						state = y < deepslateHeight ? DEEPSLATE : SHIVERSLATE;
						setPos.setY(y);
						chunk.setBlockState(setPos, state, false);
					} catch (RuntimeException e) {
						System.out.println("=========== DEBUG INFO ================");
						System.out.println("worldHeight: " + this.getWorldHeight());
						System.out.println("minY: " + this.getMinimumY());
						System.out.println("chunkMinY: " + chunk.getBottomY());
						throw e;
					}
				}

				oceanFloor.trackUpdate(x, height - 1, z, SHIVERSLATE);
				surface.trackUpdate(x, height - 1, z, SHIVERSLATE);

				int trueHeight = terrainHeights[(x * 16) + z];

				if (trueHeight < seaLevel && height < chunk.getTopY()) { // Second Check: CC Compat
					int cap = Math.min(seaLevel, chunk.getTopY()); // CC Compat

					for (int y = height; y < cap; ++y) {
						setPos.setY(y);
						chunk.setBlockState(setPos, ICE, false);
					}
				}

				oceanFloor.trackUpdate(x, seaLevel - 1, z, ICE); // for oceanFloor probably not necessary
				surface.trackUpdate(x, seaLevel - 1, z, ICE);
			}
		}

		return CompletableFuture.completedFuture(chunk);
	}

	private int calculateTerrainHeight(int x, int z) {
		double height = 0.0;
		double totalWeight = 0.0;
		final double maxSquareRadius = 9.0; // 3.0 * 3.0;

		// Sample Relevant jittered points around the player for smoothing

		int calcX = (x >> 4);
		int calcZ = (z >> 4);
		GridPoint cell = new GridPoint((double) x * 0.0625, (double) z * 0.0625);

		int sampleX;
		int sampleZ;

		// 9x9 sample because that is the possible range of the circle
		for (int xo = -4; xo <= 4; ++xo) {
			sampleX = xo + calcX;

			for (int zo = -4; zo <= 4; ++zo) {
				sampleZ = zo + calcZ;

				GridPoint point = JitteredGrid.sampleJitteredGrid(sampleX, sampleZ, this.voronoiSeed, 0.1);
				double sqrDist = point.centreSqrDist(cell);
				double weight = maxSquareRadius - sqrDist;

				// this is kept square-weighted because sqrt is a trash cringe operation and is slower than the hare from aesop's fables
				if (weight > 0) { // firstly minimise samples
					Terrain type = this.terrainSampler.sample(MathHelper.floor(point.getX() * 16.0), MathHelper.floor(point.getY() * 16.0));
					weight = type.modifyWeight(weight, maxSquareRadius);

					if (weight > 0) { // minimise samples again
						totalWeight += weight;
						height += weight * type.sampleHeight(x, z);
					}
				}
			}
		}

		// Complete the average
		return (int) (height / totalWeight);
	}

	@Override
	public int getHeight(int x, int z, Type heightmap, HeightLimitView world) {
		// Lazy Implementation from Naturverbunden. It works fine from experience.
		int height = this.terrainHeightSampler.sample(x, z);
		int seaLevel = this.getSeaLevel();

		if (height < seaLevel && heightmap.getBlockPredicate().test(ICE)) {
			return seaLevel - 1;
		}

		return height;
	}

	@Override
	public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {

		BlockState[] states = new BlockState[world.getHeight()];
		int height = this.terrainHeightSampler.sample(x, z);

		int i = 0;
		int y;

		for (y = world.getBottomY(); y < height; ++y) {
			states[i++] = SHIVERSLATE;
		}

		int seaLevel = this.getSeaLevel();

		while (y++ < seaLevel) {
			states[i++] = ICE;
		}

		while (i < states.length) {
			states[i++] = AIR;
		}

		return new VerticalBlockSample(world.getBottomY(), states);
	}

	@Override
	public void populateEntities(ChunkRegion region) {
		ChunkPos chunkPos = region.getCenterPos();
		Biome biome = region.getBiome(chunkPos.getStartPos());
		ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(region.getSeed()));
		chunkRandom.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
		SpawnHelper.populateEntities(region, biome, chunkPos, chunkRandom);
	}

	@Override
	public void carve(ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver generationStep) {
		// From NoiseChunkGenerator, cleaned up a bit.
		BiomeAccess sourcedBiomeAccess = biomeAccess.withSource((x, y, z) -> this.populationSource.getBiome(x, y, z, this.getMultiNoiseSampler()));
		final int seaLevel = this.getSeaLevel();

		ChunkRandom rand = new ChunkRandom(new AtomicSimpleRandom(RandomSeed.getSeed()));
		int radius = 8;
		ChunkPos chunkPos = chunk.getPos();

		CarverContext carverContext = new PermafrozenCarverContext(
				this,
				this.fakeNoiseCGForCarving,
				chunkRegion.getRegistryManager(), chunk.getHeightLimitView());
		CarvingMask carvingMask = ((ProtoChunk) chunk).getOrCreateCarvingMask(generationStep);

		for (int xOffset = -radius; xOffset <= radius; ++xOffset) {
			for (int zOffset = -radius; zOffset <= radius; ++zOffset) {
				ChunkPos carverChunkPos = new ChunkPos(chunkPos.x + xOffset, chunkPos.z + zOffset);
				Chunk carverChunk = chunkRegion.getChunk(carverChunkPos.x, carverChunkPos.z);
				GenerationSettings settings = carverChunk.setBiomeIfAbsent(() -> this.populationSource.getBiome(BiomeCoords.fromBlock(carverChunkPos.getStartX()), 0, BiomeCoords.fromBlock(carverChunkPos.getStartZ()), this.getMultiNoiseSampler())).getGenerationSettings();
				List<Supplier<ConfiguredCarver<?>>> list = settings.getCarversForStep(generationStep);
				ListIterator listIterator = list.listIterator();
				// TODO height level aquifers -- "fluid" as ice, or water? should the carvers carve air in ice in this dimension?
				// This controls aquifers. Currently this will basically ignore them
				AquiferSampler caveFloodLevelSampler = AquiferSampler.seaLevel((x, y, z) -> new AquiferSampler.FluidLevel(this.getMinimumY(), ICE));

				while (listIterator.hasNext()) {
					int l = listIterator.nextIndex();
					ConfiguredCarver<?> configuredCarver = (ConfiguredCarver) ((Supplier) listIterator.next()).get();
					rand.setCarverSeed(seed + (long) l, carverChunkPos.x, carverChunkPos.z);
					if (configuredCarver.shouldCarve(rand)) {
						Objects.requireNonNull(sourcedBiomeAccess);
						configuredCarver.carve(
								carverContext,
								chunk,
								sourcedBiomeAccess::getBiome,
								rand,
								caveFloodLevelSampler,
								carverChunkPos,
								carvingMask);
					}
				}
			}
		}
	}

	@Override
	public int getWorldHeight() {
		return this.settings.get().getGenerationShapeConfig().height();
	}

	@Override
	public int getSeaLevel() {
		return this.settings.get().getSeaLevel();
	}

	@Override
	public int getMinimumY() {
		return this.settings.get().getGenerationShapeConfig().minimumY();
	}

	@Override
	public MultiNoiseUtil.MultiNoiseSampler getMultiNoiseSampler() {
		return (i, j, k) -> NO_MULTI_NOISE;
	}

	public static long trueWorldSeed; // hack for json dimensions

	private static final MultiNoiseUtil.NoiseValuePoint NO_MULTI_NOISE = MultiNoiseUtil.createNoiseValuePoint(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);

	public static final Codec<PermafrozenChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
			instance.group(
					RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter(chunkGenerator -> chunkGenerator.biomeRegistry),
					BiomeSource.CODEC.fieldOf("biome_source").forGetter(chunkGenerator -> chunkGenerator.populationSource),
					Codec.LONG.fieldOf("seed").orElseGet(() -> trueWorldSeed).stable().forGetter(chunkGenerator -> chunkGenerator.seed),
					ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter(chunkGenerator -> chunkGenerator.settings))
				.apply(instance, PermafrozenChunkGenerator::new));

	public static final BlockState DEEPSLATE = Blocks.DEEPSLATE.getDefaultState();
	public static final BlockState SHIVERSLATE = PermafrozenBlocks.SHIVERSLATE.getDefaultState();
	public static final BlockState AIR = Blocks.AIR.getDefaultState();
	public static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
	public static final BlockState ICE = Blocks.ICE.getDefaultState();
}
