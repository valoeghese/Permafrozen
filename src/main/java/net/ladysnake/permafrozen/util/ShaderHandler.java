package net.ladysnake.permafrozen.util;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import ladysnake.satin.api.managed.uniform.Uniform3f;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Identifier;
import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.registry.PermafrozenStatusEffects;

public class ShaderHandler {
    public static final ManagedShaderEffect SPECTRAL_DAZE = ShaderEffectManager.getInstance()
            .manage(new Identifier(Permafrozen.MOD_ID, "shaders/post/spectral_daze.json"));
    public static final ManagedShaderEffect SPECTRAL_DAZE_DOS = ShaderEffectManager.getInstance()
            .manage(new Identifier(Permafrozen.MOD_ID, "shaders/post/spectral_daze_2.json"));
    public static final ManagedShaderEffect SPECTRAL_DAZE_TRES = ShaderEffectManager.getInstance()
            .manage(new Identifier(Permafrozen.MOD_ID, "shaders/post/spectral_daze_wobble.json"));
    private static final Uniform3f SPECTR_PHOSPHOR = SPECTRAL_DAZE.findUniform3f("Phosphor");

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(ShaderHandler::onEndTick);
        ShaderEffectRenderCallback.EVENT.register(ShaderHandler::renderShaderEffects);
    }

    private static void renderShaderEffects(float v) {
        if (MinecraftClient.getInstance().player != null) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            if (player.hasStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE)) {
                SPECTRAL_DAZE.render(v);
                SPECTRAL_DAZE_DOS.render(v);
                if(player.getStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE).getAmplifier() > 0) {
                    SPECTRAL_DAZE_TRES.render(v);
                }
            }
        }
    }

    private static void onEndTick(MinecraftClient client) {
        if (client.player != null) {
            if (client.player.hasStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE)
                    && client.player.getStatusEffect(PermafrozenStatusEffects.SPECTRAL_DAZE).getAmplifier() > 0) {
                SPECTR_PHOSPHOR.set(0.7F, 0.7F, 0.9F);
            } else {
                SPECTR_PHOSPHOR.set(0.6F, 0.6F, 0.8F);
            }

        }
    }
}
