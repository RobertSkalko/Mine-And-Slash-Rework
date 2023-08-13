package com.robertx22.age_of_exile.damage_hooks;

import com.robertx22.age_of_exile.a_libraries.curios.MyCurioUtils;
import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.damage_hooks.util.DmgSourceUtils;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
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

            if (!RangedDamageUtil.isValidAttack(event)) {
                return;
            }
            if (DmgSourceUtils.isMyDmgSource(event.getSource())) {
                return;
            }
            if (event.getSource()
                    .getEntity() instanceof LivingEntity) {
                onAttack(event);
            }

        }

    }

    private static void onAttack(AttackInformation data) {

        try {

            if (data.getTargetEntity()
                    .isAlive() == false) {
                return; // stops attacking dead mobs
            }

            GearItemData weapondata = data.weaponData;

            data.getTargetEntityData()
                    .tryRecalculateStats();
            data.getAttackerEntityData()
                    .tryRecalculateStats();

            if (data.getAttackerEntity() instanceof Player) {

                if (weapondata == null) {
                    return;
                }

                if (!weapondata.canPlayerWear(data.getAttackerEntityData())) {
                    // todo    OnScreenMessageUtils.sendMessage((ServerPlayer) data.getAttackerEntity(), Component.literal("Weapon requirements not met"), ClientboundSetTitlesPacket.Type.ACTIONBAR);
                    return;
                }

                if (weapondata != null && weapondata.isWeapon()) {
                    if (data.getAttackerEntityData()
                            .canUseWeapon(weapondata)) {
                        data.getAttackerEntityData()
                                .attackWithWeapon(data);
                    }

                } else {
                    // data.getAttackerEntityData()
                    //   .unarmedAttack(data);
                }

            } else { // if its a mob
                data.getAttackerEntityData()
                        .mobBasicAttack(data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean isEnviromentalDmg(DamageSource source) {
        return source.getEntity() instanceof LivingEntity == false;
    }

}
