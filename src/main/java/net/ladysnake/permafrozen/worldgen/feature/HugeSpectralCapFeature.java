package net.ladysnake.permafrozen.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.HugeMushroomFeature;
import net.minecraft.world.gen.feature.HugeMushroomFeatureConfig;

import java.util.Random;

public class HugeSpectralCapFeature extends HugeMushroomFeature {
    public HugeSpectralCapFeature(Codec<HugeMushroomFeatureConfig> codec) {
        super(codec);
    }

    protected void generateCap(WorldAccess world, Random random, BlockPos start, int y, BlockPos.Mutable mutable, HugeMushroomFeatureConfig config) {
        for(int i = y - 3; i <= y; ++i) {
            int j = i < y ? config.foliageRadius : config.foliageRadius - 1;
            int k = config.foliageRadius - 2;

            for(int l = -j; l <= j; ++l) {
                for(int m = -j; m <= j; ++m) {
                    boolean bl = l == -j;
                    boolean bl2 = l == j;
                    boolean bl3 = m == -j;
                    boolean bl4 = m == j;
                    boolean bl5 = bl || bl2;
                    boolean bl6 = bl3 || bl4;
                    if (i >= y || bl5 != bl6) {
                        mutable.set(start, l, i, m);
                        if (!world.getBlockState(mutable).isOpaqueFullCube(world, mutable)) {
                            BlockState blockState = config.capProvider.getBlockState(random, start);
                            if (blockState.contains(MushroomBlock.WEST) && blockState.contains(MushroomBlock.EAST) && blockState.contains(MushroomBlock.NORTH) && blockState.contains(MushroomBlock.SOUTH) && blockState.contains(MushroomBlock.UP)) {
                                blockState = (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)blockState.with(MushroomBlock.UP, i >= y - 1)).with(MushroomBlock.WEST, l < -k)).with(MushroomBlock.EAST, l > k)).with(MushroomBlock.NORTH, m < -k)).with(MushroomBlock.SOUTH, m > k);
                            }

                            this.setBlockState(world, mutable, blockState);
                        }
                    }
                }
            }
        }

    }

    protected int getCapSize(int i, int j, int capSize, int y) {
        int k = 0;
        if (y < j && y >= j - 3) {
            k = capSize;
        } else if (y == j) {
            k = capSize;
        }

        return k;
    }
}
