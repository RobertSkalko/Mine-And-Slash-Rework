package com.robertx22.age_of_exile.vanilla_mc.commands.open_gui;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenGuiPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;

import static net.minecraft.commands.Commands.literal;


public class OpenHub {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("open").requires(e -> e.hasPermission(0))
                                .then(literal("hub")
                                        .executes(ctx -> run(ctx.getSource())))));
    }

    public static final String COMMAND = "slash open hub";

    private static int run(CommandSourceStack source) {

        try {

            if (source.getEntity() instanceof ServerPlayer) {
                Packets.sendToClient(source.getPlayerOrException(),
                        new OpenGuiPacket(OpenGuiPacket.GuiType.MAIN_HUB));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}

