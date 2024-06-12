package com.robertx22.age_of_exile.vanilla_mc.commands.auto_salvage;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.capability.player.data.PlayerConfigData;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.CommandSuggestions;
import com.robertx22.age_of_exile.vanilla_mc.commands.suggestions.DatabaseSuggestions;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Objects;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public class AutoSalvageConfigure {

    private static final List<String> allSupportGems = new DatabaseSuggestions(ExileRegistryTypes.SUPPORT_GEM, null).suggestions();
    private static final List<String> allRarities = new DatabaseSuggestions(ExileRegistryTypes.GEAR_RARITY, null).suggestions();

    private static class EnableDisableSuggestions extends CommandSuggestions {
        @Override
        public List<String> suggestions() {
            return List.of("enable", "disable");
        }
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("auto_salvage")
                                .then(literal("support_gems")
                                        .then(literal("config")
                                                .then(argument("gem_type", StringArgumentType.word())
                                                        .suggests(new DatabaseSuggestions(ExileRegistryTypes.SUPPORT_GEM, "all_types"))
                                                        .then(argument("rarity", StringArgumentType.word())
                                                                .suggests(new DatabaseSuggestions(ExileRegistryTypes.GEAR_RARITY, "all_rarities"))
                                                                .then(argument("enable_auto_salvage", StringArgumentType.word())
                                                                        .suggests(new EnableDisableSuggestions())
                                                                        .executes(e -> execute(e.getSource(),
                                                                                e.getSource().getPlayerOrException(),
                                                                                StringArgumentType.getString(e, "gem_type"),
                                                                                StringArgumentType.getString(e, "rarity"),
                                                                                StringArgumentType.getString(e, "enable_auto_salvage"))
                                                                        )))))))
        );
    }

    private static int execute(CommandSourceStack commandSource, Player player, String gemType, String rarity, String enableOrDisable) {

        if (!allSupportGems.contains(gemType) && !gemType.equalsIgnoreCase("all_types")) {
            player.sendSystemMessage(Component.literal("The gem type provided: " + gemType + " -- is not a valid gem type.").withStyle(ChatFormatting.RED));
            return 0;
        }

        if (!allRarities.contains(rarity) && !rarity.equalsIgnoreCase("all_rarities")) {
            player.sendSystemMessage(Component.literal("The rarity provided: " + rarity + " -- is not a valid rarity.").withStyle(ChatFormatting.RED));
            return 0;
        }

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 0;
            }
        }

        PlayerConfigData playerConfigData = Load.player(player).config;

        boolean setEnabled = enableOrDisable.equals("enable");

        if (gemType.equals("all_types")) {
            new DatabaseSuggestions(ExileRegistryTypes.SUPPORT_GEM, null).suggestions().forEach(multi_type -> {
                if (rarity.equals("all_rarities")) {
                    new DatabaseSuggestions(ExileRegistryTypes.GEAR_RARITY, null).suggestions().forEach(multi_rarity -> {
                        playerConfigData.salvage.setAutoSalvageForSupportGem(multi_type, multi_rarity, setEnabled);
                    });
                } else {
                    playerConfigData.salvage.setAutoSalvageForSupportGem(multi_type, rarity, setEnabled);
                }
            });
        } else {
            if (rarity.equals("all_rarities")) {
                new DatabaseSuggestions(ExileRegistryTypes.GEAR_RARITY, null).suggestions().forEach(multi_rarity -> {
                    playerConfigData.salvage.setAutoSalvageForSupportGem(gemType, multi_rarity, setEnabled);
                });
            } else {
                playerConfigData.salvage.setAutoSalvageForSupportGem(gemType, rarity, setEnabled);
            }
        }

        player.sendSystemMessage(Component.literal("Successfully updated auto_salvage settings for " + gemType + " " + rarity + " to " + enableOrDisable + "d").withStyle(ChatFormatting.GREEN));

        return 1;

    }

}
