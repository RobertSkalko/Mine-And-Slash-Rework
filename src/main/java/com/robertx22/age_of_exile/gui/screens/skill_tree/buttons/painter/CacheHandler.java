package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.painter;

import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CacheHandler {
    @SubscribeEvent
    public static void outputCache(PlayerEvent.PlayerLoggedOutEvent event) {
        PaintingTransformer.exportCacheOnLogOut();
    }

    @SubscribeEvent
    public static void gatherCache(PlayerEvent.PlayerLoggedInEvent event) {
        PaintingTransformer.readCacheOnLogIn();
    }
}
