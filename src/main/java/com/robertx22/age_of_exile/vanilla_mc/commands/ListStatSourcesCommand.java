package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandSuggestions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

// todo, save and add stats from different points of stat calculation?

public class ListStatSourcesCommand {
    public static class Sugg extends CommandSuggestions {
        @Override
        public List<String> suggestions() {
            return ExileDB.Stats().getAll().values().stream().map(x -> x.GUID()).collect(Collectors.toList());
        }
    }

    public static void register(CommandDispatcher<CommandSourceStack> dis) {
        dis.register(
                literal(CommandRefs.ID)
                        .then(literal("list_stat_sources").requires(e -> e.hasPermission(2))
                                .then(argument("type", StringArgumentType.word()).suggests(new Sugg())
                                        .then(argument("target", EntityArgument.player())
                                                .executes(ctx -> run(EntityArgument.getPlayer(ctx, "target"), StringArgumentType.getString(ctx, "type")))))));
    }

    private static int run(Player en, String id) {

        try {
            en.sendSystemMessage(Component.literal("Sources of ").withStyle(ChatFormatting.YELLOW).append(ExileDB.Stats().get(id).locName()));

            for (SimpleStatCtx ctx : Load.player(en).ctxs.list) {
                for (ExactStatData stat : ctx.stats) {
                    if (stat.getStat().GUID().equals(id)) {

                        String s = ctx.type.name() + ": ";
                        if (!ctx.gear_slot.isEmpty()) {
                            s += ctx.gear_slot + ". ";
                        }
                        s += stat.getValue() + " " + stat.getType().name() + "";

                        en.sendSystemMessage(Component.literal(s).withStyle(ChatFormatting.YELLOW));
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
