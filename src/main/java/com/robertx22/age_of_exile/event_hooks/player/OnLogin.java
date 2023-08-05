package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

public class OnLogin {

    public static void onLoad(ServerPlayer player) {

        Watch total = null;
        if (MMORPG.RUN_DEV_TOOLS) {
            total = new Watch();
        }

        try {

            if (!player.getServer()
                .isCommandBlockEnabled()) {
                player.displayClientMessage(new TextComponent("Command blocks are disabled, this will stop you from playing Age of Exile Dungeons!").withStyle(ChatFormatting.RED), false);
                player.displayClientMessage(new TextComponent("To enable go to your server.properties file and put enable-command-block as true.").withStyle(ChatFormatting.GREEN), false);
            }

            CapSyncUtil.syncAll(player);

            if (MMORPG.RUN_DEV_TOOLS) {
                player.displayClientMessage(Chats.Dev_tools_enabled_contact_the_author.locName(), false);
            }

            EntityData data = Load.Unit(player);

            data.onLogin(player);

            data.syncToClient(player);

        } catch (
            Exception e) {
            e.printStackTrace();
        }

        if (MMORPG.RUN_DEV_TOOLS) {
            total.print("Total on login actions took ");
        }
    }

    public static void GiveStarterItems(Player player) {

        if (player.level.isClientSide) {
            return;
        }

        player.inventory.add(new ItemStack(SlashItems.NEWBIE_GEAR_BAG.get()));

    }

}
