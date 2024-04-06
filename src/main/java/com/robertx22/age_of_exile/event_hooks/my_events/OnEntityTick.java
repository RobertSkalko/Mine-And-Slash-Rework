package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.bases.EntityGears;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.characters.PlayerStats;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.Set;

public class OnEntityTick extends EventConsumer<ExileEvents.OnEntityTick> {

    @Override
    public void accept(ExileEvents.OnEntityTick onEntityTick) {
        LivingEntity entity = onEntityTick.entity;


        try {

            if (entity.level().isClientSide) {
                return;
            }

            var data = Load.Unit(entity);

            if (data == null) {
                return;
            }

            if (data.isSummon()) {
                data.summonedPetData.tick(entity);
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

                if (entity.tickCount % 100 == 0) {
                    Player p = (Player) entity;
                    for(Map.Entry<String, ResourceLocation> set :  PlayerStats.REGISTERED_STATS.entrySet()){
                        int max = Math.round(data.getUnit().getCalculatedStat(set.getKey()).getValue());
                        p.resetStat(Stats.CUSTOM.get(PlayerStats.REGISTERED_STATS.get(set.getKey())));
                        p.awardStat(Stats.CUSTOM.get(PlayerStats.REGISTERED_STATS.get(set.getKey())), max);
                    }
                }
            }


            data.gear.onTickTrySync(entity);
            data.sync.onTickTrySync(entity);

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
            data.setEquipsChanged();

        }

    }

}
