package com.robertx22.age_of_exile.team;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ClientTeamPlayerData {

    public List<String> schools = new ArrayList<>();
    public int lvl = 1;

    private String uuid = "";
    private transient UUID id = null;

    public Player getPlayer(Level world) {
        return world.getPlayerByUUID(getUUID());
    }

    public UUID getUUID() {
        if (id == null) {
            id = UUID.fromString(uuid);
        }
        return id;
    }

}
