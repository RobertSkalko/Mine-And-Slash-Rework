package com.robertx22.age_of_exile.event_hooks.damage_hooks;

import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class RangedDamageUtil {

    // private static List<String> VALID_PROJECTILE_NAMES = Arrays.asList("arrow", "bolt", "ammo", "bullet", "dart", "missile");

    public static boolean isValidAttack(AttackInformation event) {
        if (!(event.getSource().getEntity() instanceof Player)) {
            return true;
        }

        LivingEntity en = (LivingEntity) event.getSource().getEntity();
        DamageSource source = event.getSource();
        GearItemData gear = StackSaving.GEARS.loadFrom(en.getMainHandItem());

        if (source.is(DamageTypes.PLAYER_ATTACK)) {
            return true;
        } else {

            if (gear != null) {
                var type = gear.GetBaseGearType();
                return type.weaponType().damage_validity_check.isValid(source);
            }
            return false;
        }

    }
}
