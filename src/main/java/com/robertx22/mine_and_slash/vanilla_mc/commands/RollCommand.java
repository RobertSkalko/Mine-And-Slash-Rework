package com.robertx22.mine_and_slash.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TeamUtils;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import static net.minecraft.commands.Commands.literal;


public class RollCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(

                literal(CommandRefs.ID)
                        .then(literal("roll").executes(x -> {

                            Player player = x.getSource()
                                    .getPlayerOrException();

                            int roll = RandomUtils.RandomRange(0, 100);
                            TeamUtils.getOnlineMembers(player)
                                    .forEach(p -> {
                                        p.displayClientMessage(Component.empty().append(player.getDisplayName())
                                                .append(" rolled a " + roll), false);
                                    });

                            return 0;
                        }))
        );
    }

}
