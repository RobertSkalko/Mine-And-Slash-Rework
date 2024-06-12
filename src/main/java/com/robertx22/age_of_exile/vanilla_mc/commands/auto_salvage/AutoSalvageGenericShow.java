package com.robertx22.age_of_exile.vanilla_mc.commands.auto_salvage;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.capability.player.data.PlayerConfigData;
import com.robertx22.age_of_exile.database.data.support_gem.SupportGem;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Objects;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class AutoSalvageGenericShow {

    ExileRegistryType registryType;

    public AutoSalvageGenericShow(ExileRegistryType registryType) {
        this.registryType = registryType;
    }
    public void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("auto_salvage")
                                        .then(literal("show")
                                                .then(literal(registryType.id)
                                                .executes(e -> execute(e.getSource(), e.getSource().getPlayerOrException(), null))
                                                .then(argument("search_query", StringArgumentType.word())
                                                .executes(e -> execute(e.getSource(), e.getSource().getPlayerOrException(), StringArgumentType.getString(e, "search_query")))
                                        ))))
        );
    }

    private int execute(CommandSourceStack commandSource, Player player, String searchQuery) {


        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 0;
            }
        }

        ToggleAutoSalvageRarity.SalvageType salvageType = registryType == ExileRegistryTypes.GEAR_SLOT ? ToggleAutoSalvageRarity.SalvageType.GEAR : ToggleAutoSalvageRarity.SalvageType.SPELL;
        PlayerConfigData playerConfigData = Load.player(player).config;

       HashMap<String, Boolean> configuredMap = playerConfigData.salvage.getConfiguredMapForSalvageType(salvageType);

       if(configuredMap.isEmpty()) {
           player.sendSystemMessage(Component.literal("There are no currently configured options for " + registryType.id + " items.").withStyle(ChatFormatting.GRAY));
           return 1;
       }

        if(searchQuery == null) {
            player.sendSystemMessage(Component.literal("--- Listing all configured " + registryType.id + " ids ---"));
        } else {
            player.sendSystemMessage(Component.literal("--- Listing configured " + registryType.id + " ids matching: " + searchQuery + " ---"));
        }

        var enabledTextComponent = Component.literal("ENABLED").withStyle(ChatFormatting.GREEN);
        var disabledTextComponent = Component.literal("DISABLED").withStyle(ChatFormatting.RED);

        for (String id : configuredMap.keySet()) {
            var enabled = configuredMap.get(id);
            if(registryType == ExileRegistryTypes.SUPPORT_GEM) {
                SupportGem gem = ExileDB.SupportGems().get(id);
                if(searchQuery == null || gem.id.toLowerCase().contains(searchQuery) || gem.translate().toLowerCase().contains(searchQuery)) {
                    player.sendSystemMessage(Component.literal("[" + gem.id + "] " + gem.translate() + " [").append(enabled ? enabledTextComponent : disabledTextComponent).append(Component.literal("]").withStyle(ChatFormatting.WHITE)));
                }
            } else {
                if (searchQuery == null || id.toLowerCase().contains(searchQuery)) {
                    player.sendSystemMessage(Component.literal(id + " [").append(enabled ? enabledTextComponent : disabledTextComponent).append(Component.literal("]").withStyle(ChatFormatting.WHITE)));
                }
            }
        }

        player.sendSystemMessage(Component.literal("---~---"));

        return 1;

    }

}
