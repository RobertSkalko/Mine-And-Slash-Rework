package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.capability.player.data.PlayerBuffData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class OnPlayerDeath extends EventConsumer<ExileEvents.OnPlayerDeath> {

    @Override
    public void accept(ExileEvents.OnPlayerDeath event) {
        try {

            Player player = event.player;
            var cd = Load.Unit(player).getCooldowns();

            if (!cd.isOnCooldown("death_event")) {
                cd.setOnCooldown("death_event", 100);

                Load.Unit(player).setEquipsChanged();

                PlayerData data = Load.player(player);

                data.prophecy.OnDeath(player);

                if (Load.Unit(player).getLevel() > ServerContainer.get().DEATH_PENALTY_START_LEVEL.get()) {
                    Load.Unit(player).onDeathDoPenalty();
                    data.rested_xp.onDeath();
                    data.favor.onDeath(player);
                }
                
                data.deathStats.died = true;
                data.buff = new PlayerBuffData(); // we delete all the buff foods and potions on death, if in the future i want some buffs to persist across death, change this
                data.playerDataSync.setDirty();

                var map = Load.mapAt(player.level(), player.blockPosition());

                if (map != null) {
                    map.reduceLives(player);
                    player.sendSystemMessage(Chats.MAP_DEATH_LIVES_LOSS.locName(map.getLives(player)).withStyle(ChatFormatting.RED));
                    List<Component> reqDifference = map.map.getStatReq().getReqDifference(map.map.lvl, Load.Unit(player));
                    if (!reqDifference.isEmpty()) {
                        player.sendSystemMessage(Chats.NOT_MEET_MAP_REQ_FIRST_LINE.locName());
                        reqDifference.forEach(player::sendSystemMessage);
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

