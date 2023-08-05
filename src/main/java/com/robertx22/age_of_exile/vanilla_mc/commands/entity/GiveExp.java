package com.robertx22.age_of_exile.vanilla_mc.commands.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import static net.minecraft.command.Commands.argument;
import staticnet.minecraft.commands.Commandss.literal;

public class GiveExp {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("give").requires(e -> e.hasPermission(2))
                    .then(literal("exp")
                        .requires(e -> e.hasPermission(2))
                        .then(argument("target", EntityArgument.player())
                            .then(argument("exp", IntegerArgumentType.integer())
                                .executes(ctx -> run(EntityArgument.getPlayer(ctx, "target"), IntegerArgumentType
                                    .getInteger(ctx, "exp"))))))));
    }

    private static int run(Player player, int exp) {

        try {
            Load.Unit(player)
                .GiveExp(player, exp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}