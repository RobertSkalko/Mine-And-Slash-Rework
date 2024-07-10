package com.robertx22.age_of_exile.vanilla_mc.commands.wrapper;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;

public abstract class ArgumentWrapper<T> {

    public abstract String id();

    public abstract RequiredArgumentBuilder getType();

    public abstract T get(CommandContext<CommandSourceStack> ctx);

    public SuggestionProvider<CommandSourceStack> suggestions = null;
}
