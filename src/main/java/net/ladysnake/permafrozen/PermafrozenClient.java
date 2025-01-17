package net.ladysnake.permafrozen;

import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import ladysnake.satin.api.managed.ManagedCoreShader;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.ladysnake.permafrozen.client.PermafrozenFx;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.ladysnake.permafrozen.client.entity.render.*;
import net.ladysnake.permafrozen.client.particle.aurora.AuroraParticle;
import net.ladysnake.permafrozen.client.particle.aurora.AuroraParticleEffect;
import net.ladysnake.permafrozen.registry.PermafrozenBlocks;
import net.ladysnake.permafrozen.registry.PermafrozenEntities;
import net.ladysnake.permafrozen.registry.PermafrozenItems;
import net.ladysnake.permafrozen.util.ShaderHandler;

public class PermafrozenClient implements ClientModInitializer {
	public final PermafrozenFx permafrozenFx;
	public static ParticleType<AuroraParticleEffect> AURORA_SMOL;
	public static final ManagedCoreShader AURORA = ShaderEffectManager.getInstance().manageCoreShader(new Identifier(Permafrozen.MOD_ID, "aurora"), VertexFormats.POSITION_TEXTURE);

	public PermafrozenClient() {
		this.permafrozenFx = new PermafrozenFx();
	}
	public PermafrozenFx fxRenderer() {
		return permafrozenFx;
	}

	@Override
	public void onInitializeClient() {
		TerraformBoatClientHelper.registerModelLayer(new Identifier(Permafrozen.MOD_ID, "deadwood"));
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.NUDIFAE, NudifaeEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LUNAR_KOI, LunarKoiEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.FATFISH, FatfishEntityRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.AURORA_FAE, AuroraFaeRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.PUFFBOO, PuffbooRenderer::new);
		EntityRendererRegistry.INSTANCE.register(PermafrozenEntities.LESSER_FIDDLESNOUT, LesserFiddlesnoutEntityRenderer::new);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), PermafrozenBlocks.PRISMATIC_CORAL, PermafrozenBlocks.DEAD_PRISMATIC_CORAL, PermafrozenBlocks.SPECTRAL_CAP, PermafrozenBlocks.PRISMARINE_CLUSTER, PermafrozenBlocks.SMALL_PRISMARINE_BUD, PermafrozenBlocks.MEDIUM_PRISMARINE_BUD, PermafrozenBlocks.LARGE_PRISMARINE_BUD, PermafrozenBlocks.DEADWOOD_TRAPDOOR, PermafrozenBlocks.DEADWOOD_DOOR, PermafrozenBlocks.GLAUCA_GRASS, PermafrozenBlocks.DEADWOOD_THORN);
		SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, PermafrozenBlocks.DEADWOOD_SIGN.getTexture()));
		BlockEntityRendererRegistry.INSTANCE.register(PermafrozenEntities.SPECTRAL_CAP_TYPE, (BlockEntityRendererFactory.Context rendererDispatcherIn) -> new SpectralCapRenderer());
		ShaderHandler.init();
		initParticles();
		this.permafrozenFx.registerCallbacks();

		FabricModelPredicateProviderRegistry.register(PermafrozenItems.NUDIFAE_BUCKET, new Identifier(Permafrozen.MOD_ID + ":type"), (itemStack, world, livingEntity, seed) -> itemStack.getOrCreateNbt().getInt("type"));
	}

	private void initParticles() {
		AURORA_SMOL = Registry.register(Registry.PARTICLE_TYPE, "permafrozen:aurora", new ParticleType<AuroraParticleEffect>(true, AuroraParticleEffect.PARAMETERS_FACTORY) {
			@Override
			public Codec<AuroraParticleEffect> getCodec() {
				return AuroraParticleEffect.CODEC;
			}
		});
		ParticleFactoryRegistry.getInstance().register(PermafrozenClient.AURORA_SMOL, AuroraParticle.Factory::new);
	}




}
