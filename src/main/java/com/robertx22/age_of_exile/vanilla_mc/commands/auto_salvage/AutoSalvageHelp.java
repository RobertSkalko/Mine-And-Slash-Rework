package com.robertx22.age_of_exile.vanilla_mc.commands.auto_salvage;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.age_of_exile.vanilla_mc.commands.CommandRefs;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

import static net.minecraft.commands.Commands.literal;

public class AutoSalvageHelp {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(
                literal(CommandRefs.ID)
                        .then(literal("auto_salvage")
                                .then(literal("support_gems")
                                        .then(literal("help")
                                                .executes(e -> execute(e.getSource(), e.getSource().getPlayerOrException()))
                                        )))
        );
    }

    private static int execute(CommandSourceStack commandSource, Player player) {

        if (Objects.isNull(player)) {
            try {
                player = commandSource.getPlayerOrException();
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
                return 0;
            }
        }

        player.sendSystemMessage(Component.literal("/mine_and_slash auto_salvage support_gems").withStyle(ChatFormatting.GREEN).append(Component.literal(" commands are used to configure more advanced auto-salvaging of Support Gems.").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("Sometimes you might want to configure all common gems to be auto-salvaged except certain rare ones that you care about. That's what these commands are for.").withStyle(ChatFormatting.WHITE));
        player.sendSystemMessage(Component.empty());
        player.sendSystemMessage(Component.literal("/mine_and_slash auto_salvage support_gems list").withStyle(ChatFormatting.GREEN).append(Component.literal(" will show you a list of all possible gems, important for configuring specific gems. You can use an optional search query as a final parameter to search for a specific gem's id.").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.empty());
        player.sendSystemMessage(Component.literal("/mine_and_slash auto_salvage support_gems config <type> <rarity> <enabled>").withStyle(ChatFormatting.GREEN).append(Component.literal(" lets you actually configure your auto salvaging for your Support Gems. There are two special parameters: all_types and all_rarities which do what you'd hope, and configure either all gem types or all rarities at once. They can be combined to configure all gems to be broadly enabled or disabled.").withStyle(ChatFormatting.WHITE)));


        return 1;

    }

}
