package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class OnLogin {


    public static void onLoad(ServerPlayer player) {

        Watch total = null;
        if (MMORPG.RUN_DEV_TOOLS) {
            total = new Watch();
        }

        try {

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

            data.sync.setDirty();

            Load.player(player).prophecy.onLoginRegenIfEmpty();

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
