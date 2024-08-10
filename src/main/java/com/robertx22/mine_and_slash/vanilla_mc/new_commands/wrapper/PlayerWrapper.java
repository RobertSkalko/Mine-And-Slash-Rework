package com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper;

import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import static net.minecraft.commands.Commands.argument;

public class PlayerWrapper extends ArgumentWrapper<Player> {
    public PlayerWrapper() {
        super("player");
    }

    @Override
    public String id() {
        return "player";
    }

    @Override
    public RequiredArgumentBuilder getType() {
        return argument(argName, EntityArgument.player());
    }

    @Override
    public Player getter(CommandContext<CommandSourceStack> ctx) {
        Player en = null;
        try {
            en = EntityArgument.getPlayer(ctx, argName);
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        if (en == null) {
            en = ctx.getSource().getPlayer();
        }

        return en;
    }


}
