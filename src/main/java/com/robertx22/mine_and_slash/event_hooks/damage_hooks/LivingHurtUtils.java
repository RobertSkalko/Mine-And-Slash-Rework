package com.robertx22.mine_and_slash.event_hooks.damage_hooks;

import com.robertx22.mine_and_slash.a_libraries.curios.MyCurioUtils;
import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.DmgSourceUtils;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.OnScreenMessageUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class LivingHurtUtils {


    public static int getItemDamage(float dmg) {
        return 1; // todo lets reduce the dmg jewelry takes(int) Mth.clamp(dmg / 10F, 1, 4);
    }

    public static void damageCurioItems(LivingEntity en, float dmg) {

        if (en instanceof Player) {

            Player player = (Player) en;

            List<ItemStack> curios = MyCurioUtils.getAllSlots(player);

            curios.forEach(x -> x.hurtAndBreak(getItemDamage(dmg), player, (entity) -> {
                entity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            }));

        }
    }

    public static void tryAttack(AttackInformation event) {

        LivingEntity target = event.getTargetEntity();


        if (target.level().isClientSide) {
            return;
        }

        if (event.getSource() != null) {
            if (DmgSourceUtils.isMyDmgSource(event.getSource())) {
                return;
            }
            if (!RangedDamageUtil.isValidAttack(event)) {
                event.setAmount(0);
                event.setCanceled(true);
                return;
            }
            if (event.getSource().getEntity() instanceof LivingEntity) {
                onAttack(event);
            }
        }

    }

    private static void onAttack(AttackInformation data) {

        try {

            if (data.getTargetEntity().isAlive() == false) {
                return; // stops attacking dead mobs
            }

            GearItemData weapondata = data.weaponData;

            if (data.getAttackerEntity() instanceof Player p) {

                if (weapondata == null || !weapondata.GetBaseGearType().weaponType().damage_validity_check.isValid(data.getSource())) {
                    Load.Unit(p).unarmedAttack(data);
                    return;
                }

                if (!weapondata.canPlayerWear(data.getAttackerEntityData())) {
                    OnScreenMessageUtils.sendMessage((ServerPlayer) data.getAttackerEntity(), Component.literal(""), Component.literal("Weapon requirements not met"));
                    Load.Unit(p).unarmedAttack(data);
                    return;
                }

                if (weapondata != null && weapondata.isWeapon()) {
                    if (data.getAttackerEntityData().canUseWeapon(weapondata)) {
                        data.getAttackerEntityData().attackWithWeapon(data);
                    }
                } else {
                    Load.Unit(p).unarmedAttack(data);
                }

            } else { // if its a mob
                data.getAttackerEntityData().mobBasicAttack(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isEnviromentalDmg(DamageSource source) {
        return source.getEntity() instanceof LivingEntity == false;
    }

}
