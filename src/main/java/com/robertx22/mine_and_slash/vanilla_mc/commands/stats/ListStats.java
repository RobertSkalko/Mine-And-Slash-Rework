package com.robertx22.mine_and_slash.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import joptsimple.internal.Strings;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.stream.Collectors;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;


public class ListStats {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("stat").requires(e -> e.hasPermission(2))
                                .then(literal("list")
                                        .requires(e -> e.hasPermission(0))
                                        .then(argument("target", EntityArgument.entity())
                                                .then(argument("scaling", StringArgumentType.string())
                                                        .suggests(new GiveStat.ModOrExact())
                                                        .executes(ctx -> {
                                                            return run(EntityArgument.getPlayer(ctx, "target"), StringArgumentType
                                                                    .getString(ctx, "scaling"));

                                                        }))))));
    }

    private static int run(Entity en, String type) {

        try {

            if (en instanceof Player) {
                EntityData data = Load.Unit(en);
                Player player = (Player) en;

                String str = "";

                if (type.equals("exact")) {
                    str = Strings.join(data.getCustomExactStats().stats.values()
                            .stream()
                            .map(x -> x.getStatId())
                            .collect(Collectors.toList()), ",");
                } else {
                    str = Strings.join(data.getCustomExactStats().mods.values()
                            .stream()
                            .map(x -> x.stat)
                            .collect(Collectors.toList()), ",");
                }
                player.displayClientMessage(Component.literal(str), false);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
