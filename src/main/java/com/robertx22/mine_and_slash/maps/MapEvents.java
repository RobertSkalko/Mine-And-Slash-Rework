package com.robertx22.mine_and_slash.maps;

import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DispenserMenu;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.Event;

public class MapEvents {

    public static void init() {

        ForgeEvents.registerForgeEvent(PlayerContainerEvent.Open.class, event -> {
            try {
                Player p = event.getEntity();
                if (WorldUtils.isMapWorldClass(p.level())) {
                    if (event.getContainer() instanceof DispenserMenu) {
                        p.closeContainer();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        ForgeEvents.registerForgeEvent(PlayerInteractEvent.RightClickItem.class, x -> {
            if (WorldUtils.isMapWorldClass(x.getEntity().level())) {
                if (ServerContainer.get().isItemBanned(x.getItemStack().getItem())) {
                    x.getEntity().sendSystemMessage(Component.literal("This item is banned in Adventure Maps: ")
                            .append(x.getItemStack().getDisplayName()).withStyle(ChatFormatting.BOLD));
                    x.setCanceled(true);
                }
            }
        });
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
