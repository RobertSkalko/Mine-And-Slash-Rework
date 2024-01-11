package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.PlayerData;
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


    }


    public static void onEndTick(ServerPlayer player) {
        try {
            if (player == null || player.isDeadOrDying()) {
                return;
            }
            Load.player(player).spellCastingData.onTimePass(player);
            Load.Unit(player).didStatCalcThisTickForPlayer = false;

            int age = player.tickCount;

            if (age % 200 == 0) {
                Load.Unit(player).setEquipsChanged();
            }

            if (age % 5 == 0) {
                var tickrate = 5;

                if (player.isBlocking()) {
                    if (Load.player(player).spellCastingData.isCasting()) {
                        Load.player(player).spellCastingData.cancelCast(player);
                    }
                }
                Load.player(player).buff.onTick(player, tickrate);
                Load.Unit(player).getResources().onTickBlock(player, tickrate);

            }

            if (age % 20 == 0) {
                EntityData unitdata = Load.Unit(player);
                PlayerData playerData = Load.player(player);

                Load.player(player).favor.onSecond(player);

                if (unitdata
                        .getResources()
                        .getEnergy() < Load.Unit(player)
                        .getUnit()
                        .energyData()
                        .getValue() / 10) {
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 3, 2));
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 3, 2));
                }

                UnequipGear.onTick(player);

                if (player.tickCount % 60 == 0) {
                    playerData.getSkillGemInventory().removeSupportGemsIfTooMany(player);
                    playerData.getJewels().checkRemoveJewels(player);
                }

                playerData.spellCastingData.charges.onTicks(player, 20);


                unitdata.syncedRecently = false;
                playerData.syncedRecently = false;

                unitdata.tryRecalculateStats();

                RestoreResourceEvent mana = EventBuilder.ofRestore(player, player, ResourceType.mana, RestoreType.regen, 0)
                        .build();
                mana.Activate();

                //if (!player.isSprinting()) {
                if (!player.isBlocking()) {
                    RestoreResourceEvent energy = EventBuilder.ofRestore(player, player, ResourceType.energy, RestoreType.regen, 0)
                            .build();
                    energy.Activate();
                } else {
                    if (unitdata.getResources().getEnergy() < 1) {
                        player.getCooldowns().addCooldown(player.getOffhandItem().getItem(), 20 * 3);
                        player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 20 * 3);
                        player.stopUsingItem();
                    }
                }
                // }

                RestoreResourceEvent msevent = EventBuilder.ofRestore(player, player, ResourceType.magic_shield, RestoreType.regen, 0)
                        .build();
                msevent.Activate();


                boolean canHeal = player.getFoodData().getFoodLevel() >= 1;

                if (canHeal) {
                    if (player.getHealth() < player.getMaxHealth()) {
                        RestoreResourceEvent hpevent = EventBuilder.ofRestore(player, player, ResourceType.health, RestoreType.regen, 0)
                                .build();
                        hpevent.Activate();

                    }
                }

                CapSyncUtil.syncPerSecond(player);

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
