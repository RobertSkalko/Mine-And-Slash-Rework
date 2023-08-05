package com.robertx22.age_of_exile.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import static net.minecraft.command.Commands.argument;
import staticnet.minecraft.commands.Commandss.literal;

public class ClearStats {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
            literal(CommandRefs.ID)
                .then(literal("stat").requires(e -> e.hasPermission(2))
                    .then(literal("clear")
                        .requires(e -> e.hasPermission(2))
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

            if (en instanceof LivingEntity) {
                EntityData data = Load.Unit(en);

                if (type.equals("exact")) {
                    data.getCustomExactStats().stats.clear();
                } else {
                    data.getCustomExactStats().mods.clear();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
