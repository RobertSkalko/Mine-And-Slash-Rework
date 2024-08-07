package com.robertx22.age_of_exile.vanilla_mc.new_commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.vanilla_mc.new_commands.wrapper.CommandBuilder;
import com.robertx22.age_of_exile.vanilla_mc.new_commands.wrapper.PermWrapper;
import com.robertx22.age_of_exile.vanilla_mc.new_commands.wrapper.PlayerWrapper;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

public class DevCommands {


    public static void init(CommandDispatcher dis) {

        CommandBuilder.of(dis, x -> {
            PlayerWrapper enarg = new PlayerWrapper();

            x.addLiteral("dev", PermWrapper.OP);
            x.addLiteral("generate_wiki", PermWrapper.OP);
            x.addLiteral("commands", PermWrapper.OP);

            x.addArg(enarg);

            x.action(e -> {
                Player p = enarg.get(e);
                String wiki = "List of All Mine and Slash Commands: \n\n";
                for (CommandBuilder c : CommandBuilder.ALL) {
                    wiki += c.getWikiString() + "\n\n";
                }
                p.sendSystemMessage(Component.literal("Click to copy commands wiki")
                        .withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, wiki))));
            });

        }, "Generates a wiki section for all commands using the new CommandBuilder wrapper, their args and descriptions.");


    }
}
