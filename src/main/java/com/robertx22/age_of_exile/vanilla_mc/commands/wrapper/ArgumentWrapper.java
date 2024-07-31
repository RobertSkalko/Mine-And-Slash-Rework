package com.robertx22.age_of_exile.vanilla_mc.commands.wrapper;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSourceStack;

public abstract class ArgumentWrapper<T> {

    private boolean optional = false;
    private T opt = null;

    public abstract T getter(CommandContext<CommandSourceStack> ctx);


    public T getOptionalDefaultValue() {
        return opt;
    }

    public void setOptional(T opt) {
        this.opt = opt;
        this.optional = true;
    }

    public boolean isOptional() {
        return optional;
    }


    public final T get(CommandContext<CommandSourceStack> ctx) {
        if (this.isOptional()) {
            try {
                var val = getter(ctx);
                return val;
            } catch (Exception e) {
                return opt;
            }

        }
        return getter(ctx);
    }

    public abstract String id();

    public abstract RequiredArgumentBuilder getType();

    // public abstract T get(CommandContext<CommandSourceStack> ctx);

    public SuggestionProvider<CommandSourceStack> suggestions = null;
}
