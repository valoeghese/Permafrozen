package net.ladysnake.permafrozen.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.ItemTags;
import net.minecraft.world.World;
import net.ladysnake.permafrozen.Permafrozen;
import net.ladysnake.permafrozen.registry.PermafrozenBiomes;
import net.ladysnake.permafrozen.registry.PermafrozenSoundEvents;
import net.ladysnake.permafrozen.util.PlayerUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    private int ticks;
    private int temperature;
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo info) {
        PlayerEntity playerEntity = (PlayerEntity) (Object) this;
        if (!this.world.isClient && !playerEntity.isCreative() && !playerEntity.isSpectator() && playerEntity.isAlive() && this.getEntityWorld() == Objects.requireNonNull(this.getEntityWorld().getServer()).getWorld(Permafrozen.WORLD_KEY)) {
            ticks++;
            if (ticks >= 20) {
                if (PlayerUtil.isWarmBlockNearby(playerEntity) || playerEntity.isOnFire() && temperature < 36) {
                    temperature++;
                } else if(playerEntity.world.isSkyVisible(playerEntity.getCameraBlockPos())) {
                    temperature--;
                } else {
                    if (playerEntity.getRandom().nextInt(200) == 69) {
                        temperature--;
                    }
                }
                if(temperature < 2 && !this.getEquippedStack(EquipmentSlot.HEAD).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && !this.getEquippedStack(EquipmentSlot.CHEST).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && !this.getEquippedStack(EquipmentSlot.LEGS).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && !this.getEquippedStack(EquipmentSlot.FEET).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES)) {
                    playerEntity.setFrozenTicks(Math.min(this.getMinFreezeDamageTicks(), this.getFrozenTicks() + 1));
                }
                ticks = 0;
            }
        }
//        if(this.world.getBiome(this.getCameraBlockPos()) == PermafrozenBiomes.BOREAL_FOREST) {
//            float pitchModifier = 0.7f;
//            if (playerEntity.isSubmergedIn(FluidTags.WATER)) {
//                pitchModifier = 0.3f;
//            }
//            playerEntity.playSound(PermafrozenSoundEvents.BOREAL_AMBIENCE, SoundCategory.WEATHER, 0.5f, pitchModifier);
//        }



    }
    @Inject(method = "readCustomDataFromNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    public void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        temperature = nbt.getInt("temp");
    }

    @Inject(method = "writeCustomDataToNbt(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("TAIL"))
    public void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("temp", getTemperature());
    }
    public int getTemperature() {
        return temperature;
    }

}
