package com.robertx22.mine_and_slash.maps;

import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

public class MapEvents {

    public static void init() {

        ForgeEvents.registerForgeEvent(EntityMobGriefingEvent.class, x -> {
            if (WorldUtils.isMapWorldClass(x.getEntity().level())) {
                x.setResult(Event.Result.DENY);
            }
        });

        ForgeEvents.registerForgeEvent(BlockEvent.BreakEvent.class, event -> {
            Level level = event.getPlayer().level();

            if (!level.isClientSide) {

                Player player = event.getPlayer();
                if (player.isCreative()) {
                    return;
                }
                if (WorldUtils.isMapWorldClass(level)) {
                    event.setCanceled(true);
                }
            }
        });


        ForgeEvents.registerForgeEvent(BlockEvent.EntityPlaceEvent.class, event -> {
            Level level = event.getEntity().level();
            if (!level.isClientSide) {
                if (event.getEntity() instanceof Player p && p.isCreative()) {
                    return;
                }
                if (WorldUtils.isMapWorldClass(level)) {
                    event.setCanceled(true);
                }
            }
        });


    }
}
