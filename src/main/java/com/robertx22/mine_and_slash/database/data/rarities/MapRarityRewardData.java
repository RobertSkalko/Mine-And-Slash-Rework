package com.robertx22.mine_and_slash.database.data.rarities;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.screens.map.MapSyncData;
import com.robertx22.mine_and_slash.maps.MapData;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TeamUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MapCompletePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class MapRarityRewardData {

    public int perc_to_unlock = 0;
    public String loot_table = "";
    public int reward_chests = 0;
    public float loot_multi = 1;

    public MapRarityRewardData(int perc_to_unlock, ResourceLocation loot_table, int reward_chests, float loot_multi) {
        this.perc_to_unlock = perc_to_unlock;
        this.loot_table = loot_table.toString();
        this.reward_chests = reward_chests;
        this.loot_multi = loot_multi;
    }


    public static void updateMapCompletionRarity(ServerPlayer player, MapData map) {

        int perc = map.rooms.getMapCompletePercent();

        if (!map.gave_boss_tp && map.canTeleportToArena(player).can) {
            map.gave_boss_tp = true;

            for (Player p : TeamUtils.getOnlineMembers(player)) {
                p.sendSystemMessage(Chats.GIVEN_BOSS_ARENA_ITEM.locName()
                        .setStyle(Style.EMPTY.withClickEvent(
                                new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mine_and_slash open map_gui"))).withStyle(ChatFormatting.RED));
            }
        }

        var rar = ExileDB.GearRarities().get(map.completion_rarity);

        if (rar.hasHigherRarity()) {
            var higher = rar.getHigherRarity();
            if (higher.map_reward != null && perc >= higher.map_reward.perc_to_unlock) {
                map.completion_rarity = higher.GUID();

                for (Player x : player.level().players()) {
                    x.sendSystemMessage(Chats.MAP_COMPLETE_RARITY_UPGRADE.locName(ExileDB.GearRarities().get(map.completion_rarity).coloredName()).withStyle(ChatFormatting.LIGHT_PURPLE));
                    Packets.sendToClient(player, new MapCompletePacket(new MapSyncData(map)));
                }
            }
        }

        // Packets.sendToClient(player, new MapCompletePacket(new MapSyncData(map)));
    }
}
