package com.robertx22.age_of_exile.vanilla_mc.commands.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class SetProphecyFavor {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("set").requires(e -> e.hasPermission(2))
                                .then(literal("prophecy_favor")
                                        .requires(e -> e.hasPermission(2))
                                        .then(argument("target", EntityArgument.player())
                                                .then(argument("number", IntegerArgumentType.integer())
                                                        .executes(e -> execute(e.getSource(), EntityArgument.getPlayer(e, "target"), IntegerArgumentType
                                                                .getInteger(e, "number"))))))));
    }

    private static int execute(CommandSourceStack commandSource, Player player,
                               int num) {
        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 1;
            }
        }

        Load.player(player).prophecy.forceSetCurrency(num);
        Load.player(player).playerDataSync.setDirty();

        return 0;
    }
}