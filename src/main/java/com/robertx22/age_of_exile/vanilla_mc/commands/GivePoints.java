package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandSuggestions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class GivePoints {

    public static class Sugg extends CommandSuggestions {
        @Override
        public List<String> suggestions() {
            return Arrays.asList("talent");
        }
    }

    public static void giveCommand(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("points").requires(e -> e.hasPermission(2))
                                .then(literal("give")
                                        .then(argument("target", EntityArgument.entity())
                                                .then(argument("type", StringArgumentType.string())
                                                        .suggests(new Sugg())
                                                        .then(argument("amount", IntegerArgumentType.integer())
                                                                .executes(ctx -> {
                                                                    return giveRun(EntityArgument.getPlayer(ctx, "target"),
                                                                            IntegerArgumentType.getInteger(ctx, "amount"),
                                                                            StringArgumentType.getString(ctx, "type"));
                                                                })))))));
    }

    private static int giveRun(Entity en, int num, String type) {

        try {

            if (en instanceof Player) {
                EntityData data = Load.Unit(en);
                Player player = (Player) en;


                if (type.equals("talent")) {

                    Load.player(player).bonusTalents += num;
                    player.sendSystemMessage(Chats.AWARDED_TALENTS.locName(num));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }

    ///
    public static void resetCommand(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("points").requires(e -> e.hasPermission(2))
                                .then(literal("reset")
                                        .then(argument("target", EntityArgument.entity())
                                                .then(argument("type", StringArgumentType.string())
                                                        .suggests(new Sugg())
                                                        .executes(ctx -> {
                                                            return resetrun(EntityArgument.getPlayer(ctx, "target"),
                                                                    StringArgumentType.getString(ctx, "type"));
                                                        }))))));
    }

    private static int resetrun(Entity en, String type) {

        try {

            if (en instanceof Player) {
                EntityData data = Load.Unit(en);
                Player player = (Player) en;

                if (type.equals("talent")) {
                    Load.player(player).bonusTalents = 0;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
