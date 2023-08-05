package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.uncommon.testing.CommandTests;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandsSuggestions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TextComponent;

import static net.minecraft.command.Commands.argument;
import staticnet.minecraft.commands.Commandss.literal;

public class RunTestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("runtest").requires(e -> e.hasPermission(2))
                    .then(argument("test", StringArgumentType.string()).suggests(new CommandsSuggestions())
                        .executes(
                            ctx -> run(ctx.getSource()
                                .getPlayerOrException(), StringArgumentType.getString(ctx, "test"))))));
    }

    private static int run(ServerPlayer player, String test) {

        CommandTests.run(test, player);

        player.displayClientMessage(new TextComponent("Test completed."), false);

        return 1;
    }
}
