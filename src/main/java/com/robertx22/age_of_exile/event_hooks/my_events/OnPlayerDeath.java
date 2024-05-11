package com.robertx22.age_of_exile.event_hooks.my_events;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.capability.player.data.PlayerBuffData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.ChatFormatting;

public class OnPlayerDeath extends EventConsumer<ExileEvents.OnPlayerDeath> {

    @Override
    public void accept(ExileEvents.OnPlayerDeath event) {
        try {


            var cd = Load.Unit(event.player).getCooldowns();

            if (!cd.isOnCooldown("death_event")) {
                cd.setOnCooldown("death_event", 100);

                Load.Unit(event.player).setEquipsChanged();

                PlayerData data = Load.player(event.player);

                data.prophecy.OnDeath(event.player);

                if (Load.Unit(event.player).getLevel() > ServerContainer.get().DEATH_PENALTY_START_LEVEL.get()) {
                    Load.Unit(event.player).onDeathDoPenalty();
                    data.rested_xp.onDeath();
                    data.favor.onDeath(event.player);
                }
                
                data.deathStats.died = true;
                data.buff = new PlayerBuffData(); // we delete all the buff foods and potions on death, if in the future i want some buffs to persist across death, change this
                data.playerDataSync.setDirty();

                var map = Load.mapAt(event.player.level(), event.player.blockPosition());

                if (map != null) {
                    map.reduceLives(event.player);
                    event.player.sendSystemMessage(Chats.MAP_DEATH_LIVES_LOSS.locName(map.getLives(event.player)).withStyle(ChatFormatting.RED));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

