package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.profession.StationPacket;
import com.robertx22.age_of_exile.database.data.profession.StationSyncData;
import com.robertx22.age_of_exile.database.data.profession.screen.CraftingStationMenu;
import com.robertx22.age_of_exile.database.data.stat_compat.StatCompat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.ProcessChunkBlocks;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.EventBuilder;
import com.robertx22.age_of_exile.uncommon.effectdatas.RestoreResourceEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.TenSecondPlayerTickEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.RestoreType;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
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

                    var map = Load.mapAt(player.level(), player.blockPosition());

                    var pro = Load.player(player).prophecy;

                    if (!pro.mapid.equals(map.map.uuid)) {
                        pro.clearIfNewMap(map.map);
                    }

                    if (player.tickCount % (20 * 5) == 0) {
                        //  var map = Load.mapAt(player.level(), player.blockPosition());

                        if (!map.dungeonid.isEmpty()) {
                            if (Load.player(player).map.sendMapTpMsg) {
                                Load.player(player).map.sendMapTpMsg = false;

                                var info = TextUTIL.mergeList(map.map.getTooltipOnServer(player));

                                var event = new HoverEvent(HoverEvent.Action.SHOW_TEXT, info);
                                player.sendSystemMessage(Chats.TP_TO_DUNGEON_MAPNAME.locName(ExileDB.Dungeons().get(map.dungeonid).locName()
                                                .withStyle(ChatFormatting.DARK_PURPLE))
                                        .withStyle(ChatFormatting.LIGHT_PURPLE).withStyle(Style.EMPTY.withHoverEvent(event)));
                            }
                        }


                        if (map.getLives(player) < 1) {
                            player.sendSystemMessage(Chats.NO_MORE_LIVES_REMAINING.locName());
                            Load.player(player).map.teleportBack(player);
                        }

                    }
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
            if (age % 200 == 0) {
                TenSecondPlayerTickEvent event = new TenSecondPlayerTickEvent(player, player);
                event.Activate();
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

            if (player.containerMenu instanceof CraftingStationMenu men) {
                if (player.tickCount % 5 == 0) {
                    men.professionBlockEntity.onTickWhenPlayerWatching(player);
                    Packets.sendToClient(player, new StationPacket(new StationSyncData(men.professionBlockEntity)));
                }
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
