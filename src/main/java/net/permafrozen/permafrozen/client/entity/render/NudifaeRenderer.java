package net.permafrozen.permafrozen.client.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.permafrozen.permafrozen.client.entity.model.NudifaeModel;
import net.permafrozen.permafrozen.entity.Nudifae;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class NudifaeRenderer extends GeoEntityRenderer<Nudifae> {

    public NudifaeRenderer(EntityRendererFactory.Context context) {

        super(context, new NudifaeModel());
        this.shadowRadius = 0.3F;

    }

    public void render(Nudifae nudifae, float entityYaw, float partialTicks, MatrixStack stack, VertexConsumerProvider provider, int packedLightIn) {
        super.render(nudifae, entityYaw, partialTicks, stack, provider, packedLightIn);

    }


    public Identifier getTexture(Nudifae nudifae) {

        return this.getTextureLocation(nudifae);

    }

}