package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.utils.Watch;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;

import java.util.Map;
import java.util.Set;

public class OnLogin {


    public static void onLoad(ServerPlayer player) {

        Watch total = null;
        if (MMORPG.RUN_DEV_TOOLS) {
            total = new Watch();
        }

        try {
            /*
            if (MMORPG.RUN_DEV_TOOLS_REMOVE_WHEN_DONE) {
                   var test = new UniqueGearTest(ExileDB.UniqueGears().random());
                ExileLog.get().log(test.generateDocumentation());
            }
             */

            if (ModList.get().isLoaded("majruszlibrary")) {
                player.sendSystemMessage(Component.literal("[WARNING] You have majruszlibrary mod installed, which currently has a bug and makes Mine and Slash professions not work! It's recommended to remove the mod (and all the mods that depend on that library), until the issue is fixed."));
            }
            if (ModList.get().isLoaded("enchantments_plus")) {
                player.sendSystemMessage(Component.literal("[WARNING] You have Mo' Enchantments mod installed, which currently has a bug and makes Mine and Slash NBT on items break!!! It's recommended to remove the mod, until the issue is fixed."));
            }


            if (!player.getServer()
                    .isCommandBlockEnabled()) {
                player.displayClientMessage(Chats.COMMAND_BLOCK_UNAVALIABLE.locName().withStyle(ChatFormatting.RED), false);
                player.displayClientMessage(Chats.HOW_TO_ENABLE_COMMAND_BLOCK.locName().withStyle(ChatFormatting.GREEN), false);
            }


            if (MMORPG.RUN_DEV_TOOLS) {
                player.displayClientMessage(Chats.Dev_tools_enabled_contact_the_author.locName(), false);
            }

            EntityData data = Load.Unit(player);

            data.onLogin(player);

            if (!JsonExileRegistry.NOT_LOADED_JSONS_MAP.isEmpty()) {
                int count = 0;
                String hovertext = "";
                for (Map.Entry<ExileRegistryType, Set<ResourceLocation>> en : JsonExileRegistry.NOT_LOADED_JSONS_MAP.entrySet()) {
                    for (ResourceLocation s : en.getValue()) {
                        hovertext += en.getKey().id + ": " + s.toString() + "\n";
                        count++;
                    }
                }

                var hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(hovertext));

                player.sendSystemMessage(Component.literal("Mine and Slash Datapack Error: " + count + " Jsons errored while loading.").withStyle(
                        Style.EMPTY.withHoverEvent(hover)
                ));

            }
            // idk if this one is ever called, but better be safe
            if (!JsonExileRegistry.INVALID_JSONS_MAP.isEmpty()) {
                int count = 0;

                String hovertext = "";
                for (Map.Entry<ExileRegistryType, Set<String>> en : JsonExileRegistry.INVALID_JSONS_MAP.entrySet()) {
                    for (String s : en.getValue()) {
                        hovertext += en.getKey().id + ": " + s + "\n";
                        count++;
                    }
                }

                var hover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal(hovertext));

                player.sendSystemMessage(Component.literal("Mine and Slash Datapack Error: " + count + " Jsons were marked as wrong with automatic error checking.").withStyle(
                        Style.EMPTY.withHoverEvent(hover).applyFormats(ChatFormatting.RED)
                ));
            }

            if (!JsonExileRegistry.INVALID_JSONS_MAP.isEmpty() || !JsonExileRegistry.NOT_LOADED_JSONS_MAP.isEmpty()) {

                player.sendSystemMessage(Component.literal("Check the log file for more info. Note, this is still an experimental error checking feature.")
                        .withStyle(ChatFormatting.YELLOW));
                player.sendSystemMessage(Component.literal("THIS MEANS YOUR MINE AND SLASH DATAPACKS ARE LIKELY BROKEN AND MIGHT BUG IN-GAME UNLESS FIXED")
                        .withStyle(ChatFormatting.LIGHT_PURPLE));

            }

            data.sync.setDirty();


            Load.player(player).playerDataSync.setDirty();


        } catch (
                Exception e) {
            e.printStackTrace();
        }

        if (MMORPG.RUN_DEV_TOOLS) {
            total.print("Total on login actions took ");
        }
    }

    public static void GiveStarterItems(Player player) {

        if (player.level().isClientSide) {
            return;
        }

        player.getInventory().add(new ItemStack(SlashItems.NEWBIE_GEAR_BAG.get()));

    }

}
