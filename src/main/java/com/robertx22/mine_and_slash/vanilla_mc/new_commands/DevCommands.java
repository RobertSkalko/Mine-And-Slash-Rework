package com.robertx22.mine_and_slash.vanilla_mc.new_commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.library_of_exile.utils.Watch;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper.CommandBuilder;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper.PermWrapper;
import com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper.PlayerWrapper;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

public class DevCommands {


    public static void init(CommandDispatcher dis) {


        // we don't want players having these commands lol
        if (MMORPG.RUN_DEV_TOOLS) {


            CommandBuilder.of(dis, x -> {
                PlayerWrapper enarg = new PlayerWrapper();

                x.addLiteral("builder_tool_warning", PermWrapper.OP);
                x.addLiteral("test_stat_calc_perf", PermWrapper.OP);

                x.addArg(enarg);

                x.action(e -> {
                    Player p = enarg.get(e);

                    Watch w = new Watch();
                    for (int i = 0; i < 1; i++) {
                        Load.Unit(p).setEquipsChanged();
                        Load.Unit(p).didStatCalcThisTickForPlayer = false;
                        Load.Unit(p).equipmentCache.onTick();
                        Load.player(p).cachedStats.tick();
                        Load.Unit(p).recalcStats_DONT_CALL();
                    }

                    p.sendSystemMessage(Component.literal("calc all " + w.getPrint()));

                    Watch w2 = new Watch();
                    for (int i = 0; i < 1; i++) {
                        Load.Unit(p).equipmentCache.WEAPON.setDirty();
                        Load.Unit(p).didStatCalcThisTickForPlayer = false;
                        Load.Unit(p).equipmentCache.onTick();
                        Load.player(p).cachedStats.tick();
                        Load.Unit(p).recalcStats_DONT_CALL();
                    }
                    p.sendSystemMessage(Component.literal("calc only weapon " + w2.getPrint()));

                    p.sendSystemMessage(Component.literal("Done"));
                });

            }, "");


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
}
