package com.robertx22.mine_and_slash.vanilla_mc.commands.auto_salvage;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.robertx22.mine_and_slash.vanilla_mc.commands.CommandRefs;
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
                                .then(literal("help")
                                        .executes(e -> execute(e.getSource(), e.getSource().getPlayerOrException()))
                                ))
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

        player.sendSystemMessage(Component.literal("/mine_and_slash auto_salvage").withStyle(ChatFormatting.GREEN).append(Component.literal(" commands are used to configure more advanced auto-salvaging.").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.literal("Sometimes you want to auto salvage all common support gems except a particular type you want in any rarity. Maybe you don't use shields, so you want to auto salvage all shields, regardless of rarity. These commands help you do that.").withStyle(ChatFormatting.WHITE));
        player.sendSystemMessage(Component.empty());
        player.sendSystemMessage(Component.literal("/mine_and_slash auto_salvage list <support_gems | gear_slot> <search_query> (optional)").withStyle(ChatFormatting.GREEN).append(Component.literal(" will show you a list of all possible types for gear slots or support gems. You can use an optional search query as a final parameter to search for a specific id.").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.empty());
        player.sendSystemMessage(Component.literal("/mine_and_slash auto_salvage show <support_gems | gear_slot> <search_query> (optional)").withStyle(ChatFormatting.GREEN).append(Component.literal(" will show you a list of all your currently configured options for gear slots or support gems. You can use an optional search query as a final parameter to search for a specific id.").withStyle(ChatFormatting.WHITE)));
        player.sendSystemMessage(Component.empty());
        player.sendSystemMessage(Component.literal("/mine_and_slash auto_salvage config <support_gems | gear_slot> <id> <enable | disable | clear>").withStyle(ChatFormatting.GREEN).append(Component.literal(" lets you actually configure advanced auto salvaging. Advanced auto salvaging configs take precedence over the rarity config you can configure on the Salvaging configuration screen.").withStyle(ChatFormatting.WHITE)));


        return 1;

    }

}
