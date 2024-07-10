package com.robertx22.age_of_exile.vanilla_mc.commands.wrapper;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

import static net.minecraft.commands.Commands.argument;

public class EntityWrapper extends ArgumentWrapper<Entity> {
    @Override
    public String id() {
        return "entity";
    }

    @Override
    public RequiredArgumentBuilder getType() {
        return argument(id(), EntityArgument.entity());
    }

    @Override
    public Entity get(CommandContext<CommandSourceStack> ctx) {
        Entity en = null;
        try {
            en = EntityArgument.getEntity(ctx, id());
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        if (en == null) {
            en = ctx.getSource().getEntity();
        }

        return en;
    }
}
