package net.ladysnake.permafrozen.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.entity.living.NudifaeEntity;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class NudifaeEntityModel extends AnimatedGeoModel<NudifaeEntity> {
	private static Identifier[] TEXTURE_IDENTIFIERS;
	private static final Identifier MODEL_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "geo/nudifae.geo.json");
	private static final Identifier ANIMATION_IDENTIFIER = new Identifier(Permafrozen.MOD_ID, "animations/nudifae.animation.json");
	
	@Override
	public Identifier getTextureLocation(NudifaeEntity entity) {
		if (TEXTURE_IDENTIFIERS == null) {
			TEXTURE_IDENTIFIERS = new Identifier[NudifaeEntity.getTypes()];
			for (int i = 0; i < NudifaeEntity.getTypes(); i++) {
				TEXTURE_IDENTIFIERS[i] = new Identifier(Permafrozen.MOD_ID, "textures/entity/nudifae_" + i + ".png");
			}
		}
		return TEXTURE_IDENTIFIERS[entity.getDataTracker().get(NudifaeEntity.TYPE)];
	}
	
	@Override
	public Identifier getModelLocation(NudifaeEntity entity) {
		return MODEL_IDENTIFIER;
	}
	
	@Override
	public Identifier getAnimationFileLocation(NudifaeEntity entity) {
		return ANIMATION_IDENTIFIER;
	}
	
	@Override
	public void setLivingAnimations(NudifaeEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");
		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		head.setRotationX((extraData.headPitch + 30) * ((float) Math.PI / 360F));
		head.setRotationY((extraData.netHeadYaw) * ((float) Math.PI / 500F));
		if (entity.isBaby()) {
			IBone root = this.getAnimationProcessor().getBone("body");
			if (root != null) {
				root.setScaleX(0.5f);
				root.setScaleY(0.5f);
				root.setScaleZ(0.5f);
			}
		}
	}
}
