package com.robertx22.age_of_exile.event_hooks.player;

import com.robertx22.age_of_exile.capability.bases.CapSyncUtil;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.spells.spell_fx.PositionEffect;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

        if (player.level().isClientSide) {
            return;
        }

        player.getInventory().add(new ItemStack(SlashItems.NEWBIE_GEAR_BAG.get()));

    }
    private static Map<UUID, Boolean> playerFXEnableMap = new HashMap<>();

    public static Map<UUID, PositionEffect> clientPlayerEntityFXHolder = new HashMap<>();


    public static void writeFXConfigValue(Player player){
        UUID UUID = player.getUUID();
        Boolean ifFXEnable = ClientConfigs.getConfig().ENABLE_PHOTON_FX.get();
        playerFXEnableMap.put(UUID, ifFXEnable);
    }

    public static void writeFXConfigValueFromPacket(Player player, Boolean b){
        UUID UUID = player.getUUID();
        playerFXEnableMap.put(UUID, b);
    }

    public static Boolean readFXConfigValue(Player player){
        UUID UUID = player.getUUID();
        return playerFXEnableMap.getOrDefault(UUID, false);
    }

}
