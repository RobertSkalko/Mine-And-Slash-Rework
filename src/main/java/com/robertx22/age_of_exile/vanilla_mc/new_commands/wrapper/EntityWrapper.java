package com.robertx22.age_of_exile.vanilla_mc.new_commands.wrapper;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

import static net.minecraft.commands.Commands.argument;

public class EntityWrapper extends ArgumentWrapper<Entity> {


    public EntityWrapper() {
        super("entity");
    }

    @Override
    public Entity getter(CommandContext<CommandSourceStack> ctx) {
        Entity en = null;
        try {
            en = EntityArgument.getEntity(ctx, argName);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        if (en == null) {
            en = ctx.getSource().getEntity();
        }

        return en;
    }

    @Override
    public String id() {
        return "entity";
    }

    @Override
    public RequiredArgumentBuilder getType() {
        return argument(argName, EntityArgument.entity());
    }

}
