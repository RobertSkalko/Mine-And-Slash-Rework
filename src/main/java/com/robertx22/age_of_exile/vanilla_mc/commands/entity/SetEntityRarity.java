package com.robertx22.age_of_exile.vanilla_mc.commands.entity;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

import java.util.Collection;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;


public class SetEntityRarity {

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(

                literal(CommandRefs.ID)
                        .then(literal("set").requires(e -> e.hasPermission(2))
                                .then(literal("entity")
                                        .then(literal("rarity")
                                                .requires(e -> e.hasPermission(2))
                                                .then(argument("target", EntityArgument.entities())
                                                        .then(argument("rarity", StringArgumentType.string())
                                                                .executes(e -> execute(e.getSource(), EntityArgument.getEntities(e, "target"), StringArgumentType
                                                                        .getString(e, "rarity")))))))));
    }

    private static int execute(CommandSourceStack commandSource, Collection<? extends Entity> entities, String rarity) {

        for (Entity en : entities) {
            EntityData data = Load.Unit(en);

            data.setRarity(rarity);
            data.setShouldSync();
        }


        return 0;
    }
}
