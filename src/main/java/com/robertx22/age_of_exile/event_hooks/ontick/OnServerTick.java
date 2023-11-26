package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class OnServerTick {

    static List<PlayerTickAction> TICK_ACTIONS = new ArrayList<>();

    static {


        TICK_ACTIONS.add(new PlayerTickAction("update_caps", 20, (player) -> {
            if (player.isAlive()) {
                CapSyncUtil.syncPerSecond(player);
                //Packets.sendToClient(player, new SyncAreaLevelPacket(LevelUtils.determineLevel(player.level(), player.blockPosition(), player).level));
            }
        }));

        TICK_ACTIONS.add(new
                PlayerTickAction("second_pass", 20, (player) ->
        {
            if (player.isAlive()) {

                Load.player(player).favor.onSecond(player);

             
                if (Load.Unit(player)
                        .getResources()
                        .getEnergy() < Load.Unit(player)
                        .getUnit()
                        .energyData()
                        .getValue() / 10) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 3, 1));
                }

                UnequipGear.onTick(player);

                if (player.tickCount % 60 == 0) {
                    Load.player(player).getSkillGemInventory().removeSupportGemsIfTooMany(player);
                    Load.player(player).getJewels().checkRemoveJewels(player);
                }

                Load.player(player).spellCastingData.charges.onTicks(player, 20);
            }
        }));

        TICK_ACTIONS.add(new

                PlayerTickAction("regen", 20, (player) ->

        {

            if (player.isAlive()) {


                EntityData unitdata = Load.Unit(player);


                unitdata.tryRecalculateStats();

                RestoreResourceEvent mana = EventBuilder.ofRestore(player, player, ResourceType.mana, RestoreType.regen, 0)
                        .build();
                mana.Activate();

                //if (!player.isSprinting()) {
                RestoreResourceEvent energy = EventBuilder.ofRestore(player, player, ResourceType.energy, RestoreType.regen, 0)
                        .build();
                energy.Activate();
                // }

                RestoreResourceEvent msevent = EventBuilder.ofRestore(player, player, ResourceType.magic_shield, RestoreType.regen, 0)
                        .build();
                msevent.Activate();

                boolean restored = false;

                boolean canHeal = player.getFoodData()
                        .getFoodLevel() >= 1;

                if (canHeal) {
                    if (player.getHealth() < player.getMaxHealth()) {
                        restored = true;
                    }

                    RestoreResourceEvent hpevent = EventBuilder.ofRestore(player, player, ResourceType.health, RestoreType.regen, 0)
                            .build();
                    hpevent.Activate();


                    if (restored) {
                        unitdata.syncToClient(player);
                    }
                }

            }
        }));


        var tickrate = 5;
        TICK_ACTIONS.add(new
                PlayerTickAction("every_5_ticks", tickrate, (player) ->
        {
            if (player == null || player.isDeadOrDying()) {
                return;
            }

            if (player.isBlocking()) {
                if (Load.player(player).spellCastingData.isCasting()) {
                    Load.player(player).spellCastingData.cancelCast(player);
                }
            }

            Load.player(player).buff.onTick(player, tickrate);

            Load.Unit(player).getResources().onTickBlock(player, tickrate);

        }));

        TICK_ACTIONS.add(new
                PlayerTickAction("every_tick", 1, (player) ->
        {
            if (player == null || player.isDeadOrDying()) {
                return;
            }
            Load.player(player).spellCastingData.onTimePass(player);
        }));


    }


    public static void onEndTick(ServerPlayer player) {
        try {

            if (player.isAlive()) {
                TICK_ACTIONS.forEach(x -> {
                    x.tick(player);
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static class PlayerTickAction {

        public final String name;
        public final int ticksNeeded;
        private final Consumer<ServerPlayer> action;

        public PlayerTickAction(String name, int ticksNeeded, Consumer<ServerPlayer> action) {
            this.ticksNeeded = ticksNeeded;
            this.name = name;
            this.action = action;
        }

        public void tick(ServerPlayer player) {
            int ticks = player.tickCount;
            if (ticks % ticksNeeded == 0) {
                action.accept(player);
            }
        }

    }

}
