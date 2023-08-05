package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.protocol.game.ClientboundSetTitlesPacket;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

public class OnScreenMessageUtils {

    public static void sendLevelUpMessage(Player player, MutableComponent levelType, int before, int now) {
        levelType.withStyle(ChatFormatting.GREEN)
            .withStyle(ChatFormatting.BOLD);

        ServerPlayer p = (ServerPlayer) player;
        p.connection.send(new ClientboundSetTitlesPacket(ClientboundSetTitlesPacket.Type.TITLE, Component.literal(ChatFormatting.YELLOW + "" + ChatFormatting.BOLD + "Leveled Up!"), 5, 15, 8));
        p.connection.send(new ClientboundSetTitlesPacket(ClientboundSetTitlesPacket.Type.SUBTITLE, levelType.append(Component.literal(ChatFormatting.GREEN + "" + ChatFormatting.BOLD + " Level: " + before + " > " + now + "!")), 5, 15, 8));

        SoundUtils.ding(player.level, player.blockPosition());
    }

    public static void sendMessage(ServerPlayer p, MutableComponent title, MutableComponent sub) {
        p.connection.send(new ClientboundSetTitlesPacket(ClientboundSetTitlesPacket.Type.TITLE, title
            , 5, 15, 8));
        p.connection.send(new ClientboundSetTitlesPacket(ClientboundSetTitlesPacket.Type.SUBTITLE, sub, 5, 15, 8));

    }

    public static void sendMessage(ServerPlayer p, MutableComponent title, ClientboundSetTitlesPacket.Type act) {
        p.connection.send(new ClientboundSetTitlesPacket(act, title
            , 5, 15, 8));
    }

}
