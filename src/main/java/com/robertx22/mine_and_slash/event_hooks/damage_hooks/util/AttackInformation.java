package com.robertx22.mine_and_slash.event_hooks.damage_hooks.util;

import com.google.common.base.Preconditions;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class AttackInformation {

    private ExileEvents.OnDamageEntity event;
    Mitigation mitigation;
    boolean canceled = false;
    LivingEntity targetEntity;
    LivingEntity attackerEntity;
    DamageSource damageSource;
    float amount;

    public ItemStack weapon;
    public GearItemData weaponData;

    public AttackInformation(ExileEvents.OnDamageEntity event, Mitigation miti, LivingEntity target, DamageSource source, float amount) {
        this.targetEntity = target;
        this.damageSource = source;
        this.amount = amount;
        this.mitigation = miti;
        this.event = event;
        this.weapon = WeaponFinderUtil.getWeapon((LivingEntity) source.getEntity(), source.getDirectEntity());
        this.weaponData = StackSaving.GEARS.loadFrom(weapon);


        Preconditions.checkArgument(source.getEntity() instanceof LivingEntity);
        this.attackerEntity = (LivingEntity) source.getEntity();

    }

    public DamageSource getSource() {
        return damageSource;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float f) {
        amount = f;
        if (event != null) {
            this.event.damage = f;

            if (f > 0) {
                event.canceled = false;
            } else {
                event.canceled = true;
            }
        }
    }

    public LivingEntity getTargetEntity() {
        return targetEntity;
    }

    public LivingEntity getAttackerEntity() {
        return attackerEntity;
    }

    public EntityData getAttackerEntityData() {
        return Load.Unit(attackerEntity);
    }

    public EntityData getTargetEntityData() {
        return Load.Unit(targetEntity);
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
        if (event != null) {
            this.event.canceled = canceled;
        }
    }

    public enum Mitigation {
        PRE, POST;
    }
}
