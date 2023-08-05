package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.capability.PlayerDamageChart;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import java.util.List;

import static net.minecraft.command.Commands.argument;
import staticnet.minecraft.commands.Commandss.literal;

public class TeamCommand {

    public static void listMembers(Player player) {
        List<Player> players = TeamUtils.getOnlineMembers(player);

        player.displayClientMessage(new TextComponent("Team members:"), false);

        players.forEach(e -> {
            player.displayClientMessage(e.getDisplayName(), false);
        });
    }

    public static void sendDpsCharts(Player player) {
        List<Player> members = TeamUtils.getOnlineMembers(player);

        player.displayClientMessage(new TextComponent("Damage Charts:"), false);

        members.forEach(e -> {
            Component text = new TextComponent("").append(e.getDisplayName())
                .append(": " + (int) PlayerDamageChart.getDamage(e));

            player.displayClientMessage(text, false);

        });

    }

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(

            literal(CommandRefs.ID)
                .then(literal("teams").requires(e -> e.hasPermission(0))

                    .then(literal("create").executes(x -> {
                        Load.playerRPGData(x.getSource()
                                .getPlayerOrException()).team
                            .createTeam();
                        return 0;
                    }))
                    .then(literal("leave").executes(x -> {
                        Load.playerRPGData(x.getSource()
                                .getPlayerOrException()).team
                            .leaveTeam();
                        return 0;
                    }))
                    .then(literal("dps_chart").executes(x -> {

                        Player player = x.getSource()
                            .getPlayerOrException();

                        sendDpsCharts(player);
                        return 0;
                    }))
                    .then(literal("list_members").executes(x -> {

                        Player player = x.getSource()
                            .getPlayerOrException();
                        listMembers(player);

                        return 0;
                    }))
                    .then(literal("invite")
                        .then(argument("player", EntityArgument.player())
                            .executes(x -> {
                                Player player = x.getSource()
                                    .getPlayerOrException();

                                if (!Load.playerRPGData(player).team
                                    .isOnTeam()) {
                                    player.displayClientMessage(new TextComponent("You are not in a team."), false);
                                    return 0;
                                }

                                Player other = EntityArgument.getPlayer(x, "player");

                                other.displayClientMessage(new TextComponent("").append(player.getDisplayName())
                                    .append(" has invited you to a team."), false);
                                other.displayClientMessage(new TextComponent("Do /age_of_exile teams join ").append(player.getDisplayName())
                                    .append(" to accept"), false);

                                Load.playerRPGData(other).team.invitedToTeam = Load.playerRPGData(player).team.team_id;

                                player.displayClientMessage(new TextComponent("You have invited ").append(other.getDisplayName())
                                    .append(new TextComponent(" to join your team.")), false);

                                return 0;
                            })

                        ))
                    .then(literal("make_leader")
                        .then(argument("player", EntityArgument.player())
                            .executes(x -> {
                                Player player = x.getSource()
                                    .getPlayerOrException();

                                Player other = EntityArgument.getPlayer(x, "player");

                                if (!Load.playerRPGData(player).team
                                    .isOnSameTeam(other)) {
                                    player.displayClientMessage(new TextComponent("They aren't on your team"), false);
                                    return 0;
                                }

                                player.displayClientMessage(new TextComponent("").append(player.getDisplayName())
                                    .append(" is now a team leader."), false);

                                player.displayClientMessage(new TextComponent("You have been made a team leader."), false);

                                Load.playerRPGData(other).team.isLeader = true;

                                player.displayClientMessage(new TextComponent("You have invited ").append(other.getDisplayName())
                                    .append(new TextComponent(" to join your team.")), false);

                                return 0;
                            })

                        ))
                    .then(literal("kick")
                        .then(argument("player", EntityArgument.player())
                            .executes(x -> {
                                Player player = x.getSource()
                                    .getPlayerOrException();

                                Player other = EntityArgument.getPlayer(x, "player");

                                if (!Load.playerRPGData(player).team.isLeader) {
                                    player.displayClientMessage(new TextComponent("You can't kick members because you aren't the leader."), false);
                                    return 0;
                                }
                                if (!Load.playerRPGData(player).team
                                    .isOnSameTeam(other)) {
                                    player.displayClientMessage(new TextComponent("They aren't on your team"), false);
                                    return 0;
                                }

                                Load.playerRPGData(other).team.team_id = "";

                                other.displayClientMessage(new TextComponent("You have been kicked from your team."), false);

                                player.displayClientMessage(new TextComponent("You have kicked ").append(other.getDisplayName())
                                    .append(" from your team."), false);

                                return 0;
                            })

                        ))
                    .then(literal("join")
                        .then(argument("player", EntityArgument.player())
                            .executes(x -> {
                                Player player = x.getSource()
                                    .getPlayerOrException();

                                Player other = EntityArgument.getPlayer(x, "player");

                                if (Load.playerRPGData(other).team
                                    .isOnTeam()) {

                                    if (Load.playerRPGData(player).team.invitedToTeam.equals(Load.playerRPGData(other).team.team_id)) {
                                        Load.playerRPGData(x.getSource()
                                                .getPlayerOrException()).team
                                            .joinTeamOf(other);

                                        player.displayClientMessage(new TextComponent("You have joined ").append(other.getDisplayName())
                                            .append(new TextComponent("'s team.")), false);
                                    } else {
                                        player.displayClientMessage(new TextComponent("You aren't invited to their team"), false);
                                    }

                                } else {
                                    x.getSource()
                                        .getPlayerOrException()
                                        .displayClientMessage(new TextComponent("Player isn't in a team"), false);
                                }
                                return 0;
                            })

                        ))));
    }

}
