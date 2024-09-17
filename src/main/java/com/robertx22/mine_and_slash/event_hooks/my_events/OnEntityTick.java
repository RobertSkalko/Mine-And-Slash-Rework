package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.mine_and_slash.capability.bases.EntityGears;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.characters.PlayerStats;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public class OnEntityTick {

    
    public static void onTick(LivingEntity entity) {

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
                    for (Map.Entry<String, ResourceLocation> set : PlayerStats.REGISTERED_STATS.entrySet()) {
                        int max = Math.round(data.getUnit().getCalculatedStat(set.getKey()).getValue());
                        p.resetStat(Stats.CUSTOM.get(PlayerStats.REGISTERED_STATS.get(set.getKey())));
                        p.awardStat(Stats.CUSTOM.get(PlayerStats.REGISTERED_STATS.get(set.getKey())), max);
                    }
                }
            }

            data.equipmentCache.onTick();

            if (entity instanceof Player p) {
                Load.player(p).cachedStats.tick();
            }
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

        boolean gearChanged = false;
        boolean weaponchanged = false;

        for (EquipmentSlot s : EquipmentSlot.values()) {
            ItemStack now = entity.getItemBySlot(s);
            ItemStack before = gears.get(s);

            if (now != before) {
                if (s == EquipmentSlot.MAINHAND) {
                    weaponchanged = true;
                } else {
                    gearChanged = true;
                }
            }
            gears.put(s, now);
        }

        if (gearChanged) {
            data.equipmentCache.GEAR.setDirty();
        }
        if (weaponchanged) {
            data.equipmentCache.WEAPON.setDirty();
        }

    }

}
