package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class OnScreenMessageUtils {

    public static void sendLevelUpMessage(Player player, MutableComponent levelType, int before, int now) {
        levelType.withStyle(ChatFormatting.GREEN)
                .withStyle(ChatFormatting.BOLD);

        ServerPlayer p = (ServerPlayer) player;
        p.connection.send(new ClientboundSetTitleTextPacket(Chats.LEVEL_UP_MESSAGE_UP.locName().withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD)));
        p.connection.send(new ClientboundSetSubtitleTextPacket(Chats.LEVEL_UP_MESSAGE_DOWN.locName(levelType, before, now).withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD)));

        SoundUtils.ding(player.level(), player.blockPosition());
    }

    public static void sendMessage(ServerPlayer p, MutableComponent title, MutableComponent sub) {
        p.connection.send(new ClientboundSetTitleTextPacket(title));
        p.connection.send(new ClientboundSetTitleTextPacket(sub));

    }

    public static void title(ServerPlayer p, MutableComponent title) {
        p.connection.send(new ClientboundSetTitleTextPacket(title));

    }

    public static void actionBar(ServerPlayer p, MutableComponent title) {
        p.connection.send(new ClientboundSetActionBarTextPacket(title));

    }

    /*
    public static void sendMessage(ServerPlayer p, MutableComponent title, ClientboundSetTitlesPacket.Type act) {
        p.connection.send(new ClientboundSetTitlesPacket(act, title
                , 5, 15, 8));
    }
    
     */

}
