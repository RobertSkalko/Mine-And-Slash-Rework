package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.stat_compat.StatCompat;
import com.robertx22.age_of_exile.maps.ProcessChunkBlocks;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.GameType;

public class OnServerTick {


    public static void onEndTick(ServerPlayer player) {
        try {
            if (player == null || player.isDeadOrDying()) {
                return;
            }
            EntityData unitdata = Load.Unit(player);
            PlayerData playerData = Load.player(player);


            if (player.level() instanceof ServerLevel sw) {
                if (WorldUtils.isMapWorldClass(sw)) {
                    if (player.gameMode.isSurvival()) {
                        player.setGameMode(GameType.ADVENTURE);
                    }
                } else {
                    if (player.gameMode.getGameModeForPlayer() == GameType.ADVENTURE) {
                        player.setGameMode(GameType.SURVIVAL);
                    }
                }
                if (!unitdata.getCooldowns().isOnCooldown("stop_map_gen")) {
                    ProcessChunkBlocks.process(sw, player.blockPosition());
                } else {
                    return;
                }
            }

            playerData.spellCastingData.onTimePass(player);
            unitdata.didStatCalcThisTickForPlayer = false;

            int age = player.tickCount;

            if (age % 20 == 0) {
                StatCompat.onTick(player);
            }

            if (age % (20 * 3) == 0) {
                unitdata.setEquipsChanged();
                playerData.playerDataSync.setDirty();
            }

            if (age % 5 == 0) {
                var tickrate = 5;

                if (player.isBlocking()) {
                    if (playerData.spellCastingData.isCasting()) {
                        playerData.spellCastingData.cancelCast(player);
                    }
                }
                playerData.buff.onTick(player, tickrate);
                unitdata.getResources().onTickBlock(player, tickrate);

            }

            if (true || age % (20 * 30) == 0) {
                //     SummonPetAction.despawnIfExceededMaximumSummons(player, (int) unitdata.getUnit().getStatInCalculation(Stats.MAX_SUMMON_CAPACITY.get()).getValue());
            }

            if (age % 20 == 0) {

                playerData.favor.onSecond(player);

                if (unitdata
                        .getResources()
                        .getEnergy() < unitdata
                        .getUnit()
                        .energyData()
                        .getValue() / 10) {
                    if (ServerContainer.get().ENERGY_PENALTY.get()) {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 3, 2));
                        player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 3, 2));
                    }
                }

                UnequipGear.onTick(player);

                if (player.tickCount % 60 == 0) {
                    playerData.getSkillGemInventory().removeSupportGemsIfTooMany(player);
                    playerData.getJewels().checkRemoveJewels(player);
                }

                playerData.spellCastingData.charges.onTicks(player, 20);


                if (!ResourceType.mana.isFull(unitdata)) {
                    RestoreResourceEvent mana = EventBuilder.ofRestore(player, player, ResourceType.mana, RestoreType.regen, 0)
                            .build();
                    mana.Activate();
                }

                if (!player.isBlocking()) {
                    if (!ResourceType.energy.isFull(unitdata)) {
                        RestoreResourceEvent energy = EventBuilder.ofRestore(player, player, ResourceType.energy, RestoreType.regen, 0)
                                .build();
                        energy.Activate();
                    }
                } else {
                    if (unitdata.getResources().getEnergy() < 1) {
                        player.getCooldowns().addCooldown(player.getOffhandItem().getItem(), 20 * 3);
                        player.getCooldowns().addCooldown(player.getMainHandItem().getItem(), 20 * 3);
                        player.stopUsingItem();
                    }
                }

                if (!ResourceType.magic_shield.isFull(unitdata)) {
                    RestoreResourceEvent msevent = EventBuilder.ofRestore(player, player, ResourceType.magic_shield, RestoreType.regen, 0)
                            .build();
                    msevent.Activate();
                }

                boolean canHeal = player.getFoodData().getFoodLevel() >= 1;

                if (canHeal) {
                    if (true) { //if (player.getHealth() < player.getMaxHealth()) {
                        RestoreResourceEvent hpevent = EventBuilder.ofRestore(player, player, ResourceType.health, RestoreType.regen, 0)
                                .build();
                        hpevent.Activate();

                    }
                }


            }

            playerData.playerDataSync.onTickTrySync(player);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
