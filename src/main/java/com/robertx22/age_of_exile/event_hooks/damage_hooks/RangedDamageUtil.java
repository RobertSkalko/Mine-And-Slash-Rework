package com.robertx22.age_of_exile.event_hooks.damage_hooks;

import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.List;

public class RangedDamageUtil {

    private static List<String> VALID_PROJECTILE_NAMES = Arrays.asList("arrow", "bolt", "ammo", "bullet", "dart", "missile");

    public static boolean isValidAttack(AttackInformation event) {

        if (!(event.getSource()
                .getEntity() instanceof Player)) {
            return true;
        }
        LivingEntity en = (LivingEntity) event.getSource()
                .getEntity();
        DamageSource source = event.getSource();
        Item item = en.getMainHandItem()
                .getItem();
        GearItemData gear = StackSaving.GEARS.loadFrom(en.getMainHandItem());


        if (gear != null && gear.GetBaseGearType()
                .weaponType().isProjectile) {
            if (!VALID_PROJECTILE_NAMES.stream()
                    .anyMatch(x -> source.getMsgId().contains(x))) {
                return false; // if a ranged weapon's damage entity isn't an arrow or similar, don't do damage
            }
        } else {

            // todo what
            /*
            if (source instanceof IndirectEntityDamageSource) {
                if (source.getDirectEntity() instanceof ThrownTrident) {
                    return true;
                }
                return false;
            }

             */
        }
        return true;
    }
}
