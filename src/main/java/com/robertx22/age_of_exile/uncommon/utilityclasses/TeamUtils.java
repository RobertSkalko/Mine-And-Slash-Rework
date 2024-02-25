package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.player.data.PlayerConfigData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TeamUtils {

    public static void forEachMember(Level world, BlockPos pos, Consumer<Player> action) {

        Player player = PlayerUtils.nearestPlayer((ServerLevel) world, pos);

        if (player != null) {
            TeamUtils.getOnlineMembers(player)
                    .forEach(x -> action.accept(x));
        }

    }

    public static List<Player> getOnlineTeamMembersInRange(Player player, double range) {
        return getOnlineMembers(player).stream()
                .filter(x -> player.distanceTo(x) < range)
                .collect(Collectors.toList());

    }

    public static List<Player> getOnlineTeamMembersInRange(Player player) {

        return getOnlineTeamMembersInRange(player, ServerContainer.get().PARTY_RADIUS.get());

    }

    public static List<Player> getOnlineMembers(Player player) {
        List<Player> players = new ArrayList<>();

        try {
            player.level()
                    .players()
                    .forEach(x -> {
                        if (areOnSameTeam(player, x)) {
                            players.add(x);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (players.isEmpty()) {
            players.add(player);
        }

        return players;
    }

    public static boolean areOnSameTeam(Player p1, Player p2) {
        if (p1 == p2) {
            return true;
        }
        if (p1.distanceTo(p2) > ServerContainer.get().MAX_TEAM_DISTANCE.get()) {
            return false;
        }

        if (Load.player(p1).config.isConfigEnabled(PlayerConfigData.AUTO_PVE) && Load.player(p2).config.isConfigEnabled(PlayerConfigData.AUTO_PVE)) {
            return true;
        }
        if (Load.player(p1).team.isOnSameTeam(p2)) {
            return true;
        }

        return false;

    }

}
