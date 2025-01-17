package net.ladysnake.permafrozen.worldgen.feature;

import net.ladysnake.permafrozen.Permafrozen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.BasaltPillarFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PermafrozenFeatures {
    public static final Feature<DefaultFeatureConfig> PRISMARINE_SPIKE = register("prismarine_spike", new PrismarineSpikeFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> AURORA_CORAL = register("aurora_coral", new AuroraCoralFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SPIRESHROOM = register("spireshroom", new SpireshroomFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> DEADWOOD_TREE = register("deadwood_tree", new DeadwoodTreeFeature(DefaultFeatureConfig.CODEC));

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return (F) Registry.register(Registry.FEATURE, new Identifier(Permafrozen.MOD_ID, name), feature);
    }
}
