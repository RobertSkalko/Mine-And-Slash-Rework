package com.robertx22.age_of_exile.uncommon.localization;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.IBaseAutoLoc;

import java.util.Locale;

public enum Command implements IAutoLocName {
    SOMEBODY_JOINED_YOUR_TEAM("%1$s has joined your team!"),
    CLICK_TO_LEAVE_AND_CREATE("Click Here to leave and create a new team."),
    CREATE_WHEN_IN_A_TEAM("You are already in a team, do you still want to create a new team? "),
    CANT_MAKE_LEADER("You are already the team leader."),
    LEAVE_TEAM("You have leaved the team."),
    AUTO_PVE_TEAMMATE(" (Auto PVE)"),
    NOT_IN_SAME_TEAM_DUE_TO_DISTANCE(" (Too Far)"),
    CANT_INVITE_YOURSELF("you can't invite yourself!"),
    NOT_IN_A_TEAM_CURRENTLY("Player isn't in a team"),
    NOT_INVITED("You aren't invited to their team"),
    JOIN_TEAM("You have joined %1$s's team."),
    KICK_INFO("You have kicked %1$s from your team."),
    BEEN_KICKED("You have been kicked from your team."),
    YOU_ARE_NOT_LEADER("you aren't the leader."),
    YOU_ARE_LEADER("You have been made a team leader."),
    TRANSFER_LEADERSHIP("%1$s is now a team leader."),
    PLAYER_NOT_IN_TEAM("They aren't on your team"),
    CLICK_TO_CREATE("You can click here to create a team!"),
    INVITE_INFO("You have invited %1$s to join your team."),
    CLICK_TO_JOIN("You can also click here to join!"),
    JOIN_TIP("Do /mine_and_slash teams join %1$s to accept"),
    BEEN_INVITED("%1$s has invited you to a team."),
    NOT_IN_TEAM("You are not in a team."),
    TEAM_CREATED("Team Created, you can now invite other players."),
    TEAM_MEMBER("Team members: ");

    private String localization = "";

    Command(String str) {
        this.localization = str;

    }

    @Override
    public IBaseAutoLoc.AutoLocGroup locNameGroup() {
        return AutoLocGroup.Command;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".command." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return localization;
    }

    @Override
    public String GUID() {
        return this.name()
                .toLowerCase(Locale.ROOT);
    }

}
