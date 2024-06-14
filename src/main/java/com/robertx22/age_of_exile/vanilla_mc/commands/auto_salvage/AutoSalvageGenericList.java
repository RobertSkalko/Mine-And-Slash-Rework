package com.robertx22.age_of_exile.vanilla_mc.commands.auto_salvage;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.database.data.support_gem.SupportGem;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.DatabaseSuggestions;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Objects;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class AutoSalvageGenericList {

    ExileRegistryType registryType;

    public AutoSalvageGenericList(ExileRegistryType registryType) {
        this.registryType = registryType;
    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("auto_salvage")
                                        .then(literal("list")
                                                .then(literal(registryType.id)
                                                .executes(e -> execute(e.getSource(), e.getSource().getPlayerOrException(), null))
                                                .then(argument("search_query", StringArgumentType.word())
                                                .executes(e -> execute(e.getSource(), e.getSource().getPlayerOrException(), StringArgumentType.getString(e, "search_query")))
                                        ))))
        );
    }

    private int execute(CommandSourceStack commandSource, Player player, String searchQuery) {

        List<String> allIdsSorted = new DatabaseSuggestions(registryType, null).suggestions().stream().sorted().toList();

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 0;
            }
        }

        if(searchQuery == null) {
            player.sendSystemMessage(Component.literal("--- Listing All " + registryType.id + " ids ---"));
        } else {
            player.sendSystemMessage(Component.literal("--- Listing " + registryType.id + "ids matching: " + searchQuery + " ---"));
        }

        for (String id : allIdsSorted) {
            if(registryType == ExileRegistryTypes.SUPPORT_GEM) {
                SupportGem gem = ExileDB.SupportGems().get(id);
                if(searchQuery == null || gem.id.toLowerCase().contains(searchQuery) || gem.translate().toLowerCase().contains(searchQuery)) {
                    player.sendSystemMessage(Component.literal("[" + gem.id + "] " + gem.translate()));
                }
            } else {
                if (searchQuery == null || id.toLowerCase().contains(searchQuery)) {
                    player.sendSystemMessage(Component.literal(id));
                }
            }
        }

        player.sendSystemMessage(Component.literal("---~---"));

        return 1;

    }

}
