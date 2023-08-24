package com.robertx22.age_of_exile.vanilla_mc.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TeamUtils;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.player.Player;

import java.util.List;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;


public class TeamCommand {

    public static void listMembers(Player player) {
        List<Player> players = TeamUtils.getOnlineMembers(player);

        player.displayClientMessage(ExileText.ofText("Team members:").get(), false);

        players.forEach(e -> {
            player.displayClientMessage(e.getDisplayName(), false);
        });
    }


    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(

                literal(CommandRefs.ID)
                        .then(literal("teams").requires(e -> e.hasPermission(0))

                                .then(literal("create").executes(x -> {
                                    Load.player(x.getSource()
                                                    .getPlayerOrException()).team
                                            .createTeam();
                                    return 0;
                                }))
                                .then(literal("leave").executes(x -> {
                                    Load.player(x.getSource()
                                                    .getPlayerOrException()).team
                                            .leaveTeam();
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

                                                    if (!Load.player(player).team
                                                            .isOnTeam()) {
                                                        player.displayClientMessage(ExileText.ofText("You are not in a team.").get(), false);
                                                        return 0;
                                                    }

                                                    Player other = EntityArgument.getPlayer(x, "player");

                                                    other.displayClientMessage(ExileText.emptyLine().get().append(player.getDisplayName())
                                                            .append(" has invited you to a team."), false);
                                                    other.displayClientMessage(ExileText.ofText("Do /mine_and_slash teams join ").get().append(player.getDisplayName())
                                                            .append(" to accept"), false);

                                                    Load.player(other).team.invitedToTeam = Load.player(player).team.team_id;

                                                    player.displayClientMessage(ExileText.ofText("You have invited ").get().append(other.getDisplayName())
                                                            .append(ExileText.ofText(" to join your team.").get()), false);

                                                    return 0;
                                                })

                                        ))
                                .then(literal("make_leader")
                                        .then(argument("player", EntityArgument.player())
                                                .executes(x -> {
                                                    Player player = x.getSource()
                                                            .getPlayerOrException();

                                                    Player other = EntityArgument.getPlayer(x, "player");

                                                    if (!Load.player(player).team
                                                            .isOnSameTeam(other)) {
                                                        player.displayClientMessage(ExileText.ofText("They aren't on your team").get(), false);
                                                        return 0;
                                                    }

                                                    player.displayClientMessage(ExileText.emptyLine().get().append(player.getDisplayName())
                                                            .append(" is now a team leader."), false);

                                                    player.displayClientMessage(ExileText.ofText("You have been made a team leader.").get(), false);

                                                    Load.player(other).team.isLeader = true;

                                                    player.displayClientMessage(ExileText.ofText("You have invited ").get().append(other.getDisplayName())
                                                            .append(ExileText.ofText(" to join your team.").get()), false);

                                                    return 0;
                                                })

                                        ))
                                .then(literal("kick")
                                        .then(argument("player", EntityArgument.player())
                                                .executes(x -> {
                                                    Player player = x.getSource()
                                                            .getPlayerOrException();

                                                    Player other = EntityArgument.getPlayer(x, "player");

                                                    if (!Load.player(player).team.isLeader) {
                                                        player.
                                                                displayClientMessage(ExileText.ofText("You can't kick members because you aren't the leader.").get(), false);
                                                        return 0;
                                                    }
                                                    if (!Load.player(player).team
                                                            .isOnSameTeam(other)) {
                                                        player.displayClientMessage(ExileText.ofText("They aren't on your team").get(), false);
                                                        return 0;
                                                    }

                                                    Load.player(other).team.team_id = "";

                                                    other.displayClientMessage(ExileText.ofText("You have been kicked from your team.").get(), false);

                                                    player.displayClientMessage(ExileText.ofText("You have kicked ").get().append(other.getDisplayName())
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

                                                    if (Load.player(other).team
                                                            .isOnTeam()) {

                                                        if (Load.player(player).team.invitedToTeam.equals(Load.player(other).team.team_id)) {
                                                            Load.player(x.getSource()
                                                                            .getPlayerOrException()).team
                                                                    .joinTeamOf(other);

                                                            player.displayClientMessage(ExileText.ofText("You have joined ").get().append(other.getDisplayName())
                                                                    .append(ExileText.ofText("'s team.").get()), false);
                                                        } else {
                                                            player.displayClientMessage(ExileText.ofText("You aren't invited to their team").get(), false);
                                                        }

                                                    } else {
                                                        x.getSource()
                                                                .getPlayerOrException()
                                                                .displayClientMessage(ExileText.ofText("Player isn't in a team").get(), false);
                                                    }
                                                    return 0;
                                                })

                                        ))));
    }

}
