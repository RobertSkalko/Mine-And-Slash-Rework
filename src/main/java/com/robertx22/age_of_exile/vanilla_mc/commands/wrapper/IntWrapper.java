package com.robertx22.age_of_exile.vanilla_mc.commands.wrapper;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;

import static net.minecraft.commands.Commands.argument;

public class IntWrapper extends ArgumentWrapper<Integer> {
    @Override
    public Integer getter(CommandContext<CommandSourceStack> ctx) {
        return IntegerArgumentType.getInteger(ctx, id());
    }

    @Override
    public String id() {
        return "number";
    }

    @Override
    public RequiredArgumentBuilder getType() {
        return argument(id(), IntegerArgumentType.integer());
    }

   
}
