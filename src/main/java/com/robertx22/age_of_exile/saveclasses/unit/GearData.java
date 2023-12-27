package com.robertx22.age_of_exile.saveclasses.unit;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.gear_types.bases.BaseGearType;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.utilityclasses.RepairUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class GearData {

    public ItemStack stack;
    public GearItemData gear;
    public EquipmentSlot slot;
    public int percentStatUtilization = 100; // todo if stats change stat utilization, they need special handling..

    public GearData(ItemStack stack, EquipmentSlot slot, EntityData data) {
        this.stack = stack;
        if (stack != null) {
            this.gear = StackSaving.GEARS.loadFrom(stack);
        }
        this.slot = slot;


        calcStatUtilization(data);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GearData == false) {
            return false;
        }
        GearData other = (GearData) obj;

        return (ItemStack.matches(stack, other.stack));
    }

    private void calcStatUtilization(EntityData data) {
        if (slot == EquipmentSlot.OFFHAND) {
            if (gear != null) {
                if (gear.GetBaseGearType().getTags().contains(BaseGearType.SlotTag.offhand_family)) {
                    percentStatUtilization = 100;
                }
                if (gear.GetBaseGearType().weapon_type.can_dual_wield) {
                    if (gear.GetBaseGearType().getTags().contains(BaseGearType.SlotTag.weapon_family)) {
                        percentStatUtilization = ServerContainer.get().PERC_OFFHAND_WEP_STAT.get();
                    }
                }
            }
        }
    }

    public boolean isUsableBy(EntityData data) {
        if (stack == null) {
            return false;
        }
        if (gear == null) {
            return false;
        }

        if (stack.isDamageableItem()) {
            if (RepairUtils.isItemBroken(stack)) {
                return false;
            }
        }
        if (!gear.isValidItem()) {
            return false;
        }

        if (data.getLevel() < gear.lvl) {
            return false;
        }

        BaseGearType type = gear.GetBaseGearType();

        if (type.isWeapon()) {
            if (type.weapon_type.can_dual_wield) {
                if (slot == EquipmentSlot.OFFHAND) {
                    return true;
                }
            }

            return slot == EquipmentSlot.MAINHAND; // ranged weapon
        }
        if (type.tags.contains(BaseGearType.SlotTag.chest)) {
            return slot == EquipmentSlot.CHEST;
        }
        if (type.tags.contains(BaseGearType.SlotTag.pants)) {
            return slot == EquipmentSlot.LEGS;
        }
        if (type.tags.contains(BaseGearType.SlotTag.boots)) {
            return slot == EquipmentSlot.FEET;
        }
        if (type.tags.contains(BaseGearType.SlotTag.helmet)) {
            return slot == EquipmentSlot.HEAD;
        }
        if (type.tags.contains(BaseGearType.SlotTag.jewelry_family)) {
            return slot == null;
        }
        if (type.tags.contains(BaseGearType.SlotTag.offhand_family)) {
            return slot == EquipmentSlot.OFFHAND;
        }

        return false;
    }
}
