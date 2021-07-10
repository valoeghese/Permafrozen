package net.permafrozen.permafrozen;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.block.PermafrozenBlocks;
import net.permafrozen.permafrozen.entity.PermafrozenEntities;
import net.permafrozen.permafrozen.item.PermafrozenItems;
import net.permafrozen.permafrozen.mob_effect.PermafrozenEffects;
import net.permafrozen.permafrozen.worldgen.biome.PermafrozenBiomes;
import net.permafrozen.permafrozen.worldgen.feature.PermafrozenConfiguredFeatures;
import software.bernie.geckolib3.GeckoLib;

import java.util.function.Supplier;


public class Permafrozen implements ModInitializer {
	public static final String MOD_ID = "permafrozen";
	
	public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, MOD_ID), () -> new ItemStack(PermafrozenItems.LUNAR_KOI));
	
	@Override
	public void onInitialize() {
		PermafrozenItems.innit();
		GeckoLib.initialize();
		PermafrozenEntities.init();
		PermafrozenBlocks.innit();
		PermafrozenConfiguredFeatures.commenceForth();
		PermafrozenBiomes.innit();
		PermafrozenEffects.innit();
	}

}
