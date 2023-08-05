package com.robertx22.age_of_exile.vanilla_mc.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class CommandSuggestions implements SuggestionProvider<CommandSourceStack> {

    public abstract List<String> suggestions();

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context,

                                                         SuggestionsBuilder builder) {
        SharedSuggestionProvider.suggest(this.suggestions(), builder);
        return builder.buildFuture();
    }
}

