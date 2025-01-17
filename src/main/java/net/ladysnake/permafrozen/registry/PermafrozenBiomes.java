package net.ladysnake.permafrozen.registry;

public class PermafrozenBiomes {
//	public static final Biome BOREAL_FOREST = createBorealForest();
//	public static final Biome BOREAL_FOREST_GLADE = createBorealForestGlade();
//	public static final Biome GLACIAL_OCEAN = createGlacialOcean();
//	public static final Biome GLACIAL_OCEAN_SHORES = createGlacialOceanShores();
//	public static final RegistryKey<Biome> BOREAL_FOREST_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Permafrozen.MOD_ID, "boreal_forest"));
//	public static final RegistryKey<Biome> BOREAL_FOREST_GLADE_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Permafrozen.MOD_ID, "boreal_forest_glade"));
//	public static final RegistryKey<Biome> GLACIAL_OCEAN_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Permafrozen.MOD_ID, "glacial_ocean"));
//	public static final RegistryKey<Biome> GLACIAL_OCEAN_SHORES_KEY = RegistryKey.of(Registry.BIOME_KEY, new Identifier(Permafrozen.MOD_ID, "glacial_ocean_shores"));
//
//	private static Biome createBorealForest() {
//		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
//		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.AURORA_FAE, 1, 0, 2)).spawnCost(PermafrozenEntities.AURORA_FAE, 1.0D, 0.12D);
//		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
//		generationSettings.surfaceBuilder(PermafrozenSurfaceBuilders.PERMAFROST);
//		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
//		DefaultBiomeFeatures.addMossyRocks(generationSettings);
//		PermafrozenBiomeFeatures.addFirTrees(generationSettings);
//		PermafrozenBiomeFeatures.addGlaucaGrass(generationSettings);
//		PermafrozenBiomeFeatures.addSpectralCaps(generationSettings);
//		PermafrozenBiomeFeatures.addWaterLakes(generationSettings);
//		DefaultBiomeFeatures.addSprings(generationSettings);
//		return new Biome.Builder().precipitation(Biome.Precipitation.SNOW).category(Biome.Category.TAIGA).depth(0.1F).scale(0.2F).temperature(0.0F).downfall(0.6F).effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(0.0F)).loopSound(PermafrozenSoundEvents.BOREAL_AMBIENCE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
//	}
//	private static Biome createBorealForestGlade() {
//		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
//		spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.AURORA_FAE, 1, 0, 2)).spawnCost(PermafrozenEntities.AURORA_FAE, 1.0D, 0.12D);
//		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
//		generationSettings.surfaceBuilder(PermafrozenSurfaceBuilders.PERMAFROST);
//		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
//		PermafrozenBiomeFeatures.addFirTreesSparse(generationSettings);
//		PermafrozenBiomeFeatures.addGlaucaGrass(generationSettings);
//		PermafrozenBiomeFeatures.addSpectralCaps(generationSettings);
//		PermafrozenBiomeFeatures.addWaterLakes(generationSettings);
//		DefaultBiomeFeatures.addSprings(generationSettings);
//		return new Biome.Builder().precipitation(Biome.Precipitation.SNOW).category(Biome.Category.TAIGA).depth(0.1F).scale(0.2F).temperature(0.0F).downfall(0.6F).effects(new BiomeEffects.Builder().waterColor(4159204).waterFogColor(329011).fogColor(12638463).skyColor(getSkyColor(0.0F)).loopSound(PermafrozenSoundEvents.BOREAL_AMBIENCE).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
//	}
//	private static Biome createGlacialOcean() {
//		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
//		spawnSettings.spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.NUDIFAE, 2, 1, 2)).spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.FATFISH, 6, 0, 4)).spawn(SpawnGroup.WATER_CREATURE, new SpawnSettings.SpawnEntry(PermafrozenEntities.LUNAR_KOI, 2, 0, 4)).spawnCost(PermafrozenEntities.LUNAR_KOI, 1.0D, 0.2D).spawnCost(PermafrozenEntities.FATFISH, 1.0D, 0.2D).spawnCost(PermafrozenEntities.NUDIFAE, 1.0D, 0.64D);
//		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
//		generationSettings.surfaceBuilder(PermafrozenSurfaceBuilders.GLACIAL_OCEAN);
//		PermafrozenBiomeFeatures.addPrismarineCrystals(generationSettings);
//		PermafrozenBiomeFeatures.addAuroraCoral(generationSettings);
//		DefaultBiomeFeatures.addOceanCarvers(generationSettings);
//		DefaultBiomeFeatures.addBlueIce(generationSettings);
//		DefaultBiomeFeatures.addIcebergs(generationSettings);
//		PermafrozenBiomeFeatures.addWaterLakes(generationSettings);
//
//		DefaultBiomeFeatures.addSprings(generationSettings);
//		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
//		return new Biome.Builder().precipitation(Biome.Precipitation.SNOW).category(Biome.Category.OCEAN).depth(-1.8F).scale(0.2F).temperature(0.0F).downfall(0.6F).effects(new BiomeEffects.Builder().waterColor(0x45ADF2).waterFogColor(0x041633).fogColor(12638463).skyColor(getSkyColor(-0.2F)).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
//	}
//	private static Biome createGlacialOceanShores() {
//		SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
//		GenerationSettings.Builder generationSettings = new GenerationSettings.Builder();
//		generationSettings.surfaceBuilder(PermafrozenSurfaceBuilders.GLACIAL_OCEAN);
//		DefaultBiomeFeatures.addOceanCarvers(generationSettings);
//		DefaultBiomeFeatures.addBlueIce(generationSettings);
//		DefaultBiomeFeatures.addIcebergs(generationSettings);
//		PermafrozenBiomeFeatures.addWaterLakes(generationSettings);
//		DefaultBiomeFeatures.addSprings(generationSettings);
//		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
//		DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
//		return new Biome.Builder().precipitation(Biome.Precipitation.SNOW).category(Biome.Category.OCEAN).depth(-1.8F).scale(0.2F).temperature(-0.2F).downfall(0.6F).effects(new BiomeEffects.Builder().waterColor(0x45ADF2).waterFogColor(0x041633).fogColor(12638463).skyColor(getSkyColor(-0.2F)).build()).spawnSettings(spawnSettings.build()).generationSettings(generationSettings.build()).build();
//	}
//
//	private static int getSkyColor(float temperature) {
//		float f = temperature / 3.0F;
//		f = MathHelper.clamp(f, -1.0F, 1.0F);
//		return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
//	}
//
//	public static void init() {
//		Registry.register(BuiltinRegistries.BIOME, BOREAL_FOREST_KEY.getValue(), BOREAL_FOREST);
//		Registry.register(BuiltinRegistries.BIOME, GLACIAL_OCEAN_KEY.getValue(), GLACIAL_OCEAN);
//		Registry.register(BuiltinRegistries.BIOME, BOREAL_FOREST_GLADE_KEY.getValue(), BOREAL_FOREST_GLADE);
//		Registry.register(BuiltinRegistries.BIOME, GLACIAL_OCEAN_SHORES_KEY.getValue(), GLACIAL_OCEAN_SHORES);
//		Registry.register(Registry.BIOME_SOURCE, new Identifier(Permafrozen.MOD_ID, "biome_source"), PermafrozenBiomeSource.CODEC);
//	}
}
