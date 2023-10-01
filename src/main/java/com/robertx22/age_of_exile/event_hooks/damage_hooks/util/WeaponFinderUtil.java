package com.robertx22.age_of_exile.event_hooks.damage_hooks.util;

import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class WeaponFinderUtil {

    public static ItemStack getWeapon(LivingEntity source, Entity sourceEntity) {

        if (source instanceof LivingEntity == false) {
            return ItemStack.EMPTY;
        }

        ItemStack stack = source.getMainHandItem();
        GearItemData gear = StackSaving.GEARS.loadFrom(stack);

        /*
        if (gear == null) {
            if (source instanceof Player p) {
                for (int i = 0; i < 9; i++) {
                    if (Inventory.isHotbarSlot(i)) { // just in case it changes
                        ItemStack attempt = p.getInventory().getItem(i);

                        if (StackSaving.GEARS.has(attempt)) {
                            gear = StackSaving.GEARS.loadFrom(attempt);
                            if (gear != null) {
                                if (gear.GetBaseGearType().isWeapon()) {
                                    if (Load.Unit(source).getLevel() >= gear.getLevel()) {
                                        return attempt;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
         */

        if (gear == null) {
            if (sourceEntity != null) {
                ItemStack wep = getWeaponStackFromThrownEntity(sourceEntity);
                if (wep != null) {
                    gear = StackSaving.GEARS.loadFrom(wep);
                }
            }
        }

        if (gear != null) {
            return stack;
        } else {
            return ItemStack.EMPTY;
        }
    }


    private static ItemStack getWeaponStackFromThrownEntity(Entity en) {
        try {
            for (SynchedEntityData.DataValue<?> entry : en.getEntityData().getNonDefaultValues()
            ) {
                if (entry.value() instanceof ItemStack) {
                    GearItemData gear = StackSaving.GEARS.loadFrom((ItemStack) entry.value());
                    if (gear != null) {
                        return (ItemStack) entry.value();
                    }
                }
            }
        } catch (Exception e) {
        }

        try {
            CompoundTag nbt = new CompoundTag();
            en.saveWithoutId(nbt);

            ItemStack stack = ItemStack.EMPTY;

            for (String key : nbt.getAllKeys()) {
                if (stack == null || stack.isEmpty()) {
                    try {
                        if (nbt.get(key) instanceof CompoundTag) {
                            ItemStack s = tryGetStackFromNbt(nbt.get(key));

                            if (!s.isEmpty() && StackSaving.GEARS.has(s)) {
                                return s;
                            }

                        } else {

                            CompoundTag nbt2 = (CompoundTag) nbt.get(key);

                            for (String key2 : nbt2.getAllKeys()) {
                                if (nbt.get(key) instanceof CompoundTag) {
                                    ItemStack s2 = tryGetStackFromNbt(nbt2.get(key2));
                                    if (!s2.isEmpty() && StackSaving.GEARS.has(s2)) {
                                        return s2;
                                    }

                                }
                            }

                        }
                    } catch (Exception e) {
                    }
                }

            }

            ItemStack tryWholeNbt = ItemStack.of(nbt);

            if (tryWholeNbt != null) {
                GearItemData gear = StackSaving.GEARS.loadFrom(tryWholeNbt);
                if (gear != null) {
                    return tryWholeNbt;
                }
            }

            if (stack == null) {
                stack = ItemStack.EMPTY;
            }

            return stack;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ItemStack.EMPTY;
    }

    private static ItemStack tryGetStackFromNbt(Tag nbt) {
        if (nbt instanceof CompoundTag) {
            ItemStack s = ItemStack.of((CompoundTag) nbt);
            if (s != null && !s.isEmpty()) {
                return s;

            }
        }

        return ItemStack.EMPTY;
    }


}
