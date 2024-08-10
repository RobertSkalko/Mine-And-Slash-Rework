package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class StopMobDropWornGearInMapsMixin {
    @Inject(method = "dropAllDeathLoot", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(DamageSource pDamageSource, CallbackInfo ci) {

        try {
            LivingEntity en = (LivingEntity) (Object) this;
            if (WorldUtils.isMapWorldClass(en.level())) {
                if (en instanceof Mob mob) {
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        mob.setDropChance(slot, 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
