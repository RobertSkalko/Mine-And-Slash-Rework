package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.bases.EntityGears;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class OnEntityTick extends EventConsumer<ExileEvents.OnEntityTick> {

    @Override
    public void accept(ExileEvents.OnEntityTick onEntityTick) {
        LivingEntity entity = onEntityTick.entity;

        if (entity.level().isClientSide) {
            return;
        }
        try {

            if (Load.Unit(entity) == null) {
                return; // it shouldnt be though
            }

            var data = Load.Unit(entity);

            if (entity instanceof SummonEntity s) {
                data.summonedPetData.tick(s);
            }

            data.ailments.onTick(entity);

            data.getStatusEffectsData().tick(entity);

            data.getCooldowns().onTicksPass(1);

            if (entity.tickCount % 20 == 0) {
                data.leech.onSecondUseLeeches(data);
            }


            var boss = data.getBossData();
            if (boss != null) {
                boss.tick(entity);
            }
            // todo lets see if this works fine, no need to lag if mobs anyway recalculate stats when needed
            if (entity instanceof Player) {
                checkGearChanged(entity);
            }

            data.trySync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkGearChanged(LivingEntity entity) {

        if (entity.level().isClientSide) {
            return;
        }

        if (entity.isDeadOrDying()) {
            return;
        }

        EntityData data = Load.Unit(entity);

        EntityGears gears = data.getCurrentGears();

        boolean calc = false;

        for (EquipmentSlot s : EquipmentSlot.values()) {
            ItemStack now = entity.getItemBySlot(s);
            ItemStack before = gears.get(s);

            if (now != before) {
                calc = true;
            }
            gears.put(s, now);
        }

        if (calc) {
            on$change(entity);
        }

    }

    private static void on$change(LivingEntity entity) {
        if (entity != null) {

            EntityData data = Load.Unit(entity);
            data.setEquipsChanged(true);
            data.tryRecalculateStats();

            if (entity instanceof Player) {
                data.syncToClient((Player) entity);
            }
        }

    }

}
