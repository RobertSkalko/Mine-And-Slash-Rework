package com.robertx22.mine_and_slash.mixins;

import com.robertx22.mine_and_slash.capability.player.data.PlayerConfigData;
import com.robertx22.mine_and_slash.mixin_ducks.DamageSourceDuck;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CombatTracker.class)
public class DamageConflictCheckMixin {
    @Inject(method = "recordDamage", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(DamageSource source, float num, CallbackInfo ci) {

        try {
            if (source instanceof DamageSourceDuck duck) {
                if (duck.hasMnsDamageOverride() && duck.getMnsDamage() != num) {
                    if (source.getEntity() instanceof Player p) {
                        if (Load.player(p).config.isConfigEnabled(PlayerConfigData.Config.DAMAGE_CONFLICT_MSG)) {
                            if (!Load.Unit(p).getCooldowns().isOnCooldown("dmg_conflict")) {
                                Load.Unit(p).getCooldowns().setOnCooldown("dmg_conflict", 20);
                                p.sendSystemMessage(Component.literal("Some mod has modified Mine and Slash damage!"));
                                p.sendSystemMessage(Component.literal("The intended Mine and Slash damage was  " + duck.getMnsDamage() + " but it was modified to be " + num));
                                p.sendSystemMessage(Component.literal("You can disable this message in the Mns Main Hub > Options menu"));
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
