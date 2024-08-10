package com.robertx22.mine_and_slash.vanilla_mc.commands.stats;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;


public class RemoveStat {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("stat").requires(e -> e.hasPermission(2))
                                .then(literal("remove")
                                        .requires(e -> e.hasPermission(2))
                                        .then(argument("target", EntityArgument.entity())
                                                .then(argument("scaling", StringArgumentType.string())
                                                        .suggests(new GiveStat.ModOrExact())
                                                        .then(argument("GUID", StringArgumentType.string())
                                                                .executes(ctx -> {
                                                                    return run(EntityArgument.getPlayer(ctx, "target"), StringArgumentType
                                                                            .getString(ctx, "scaling"), StringArgumentType
                                                                            .getString(ctx, "GUID"));
                                                                })))))));
    }

    private static int run(Entity en, String scaling, String GUID) {

        try {

            if (en instanceof LivingEntity) {

                EntityData data = Load.Unit(en);

                if (scaling.equals("exact")) {
                    data.getCustomExactStats()
                            .removeExactStat(GUID);
                } else {
                    data.getCustomExactStats()
                            .removeMod(GUID);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 1;
    }
}
