package com.robertx22.age_of_exile.capability.player.data;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.world.entity.player.Player;

import java.util.UUID;


public class TeamData {

    public String team_id = "";

    public String invitedToTeam = "";

    public boolean isLeader = false;

    public void joinTeamOf(Player other) {
        this.team_id = Load.playerRPGData(other).team.team_id;
    }

    public void leaveTeam() {
        this.team_id = "";
    }

    public boolean isOnTeam() {
        return !team_id.isEmpty();
    }

    public boolean isOnSameTeam(Player other) {

        if (team_id.isEmpty() || Load.playerRPGData(other).team.team_id.isEmpty()) {
            return false;
        }

        return team_id.equals(Load.playerRPGData(other).team.team_id);
    }

    public void createTeam() {
        this.team_id = UUID.randomUUID()
                .toString();
        this.isLeader = true;
    }

}
