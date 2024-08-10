package com.robertx22.mine_and_slash.event_hooks.player;

import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.world.entity.player.Player;

public class StopCastingIfInteract {

    private static void stop(Player player) {
        if (player.level().isClientSide) {
            return;
        }
        if (player.isDeadOrDying()) {
            return;
        }
        var data = Load.player(player);

        if (data.spellCastingData.isCasting()) {
            data.spellCastingData.cancelCast(player);
            data.playerDataSync.setDirty();
        }
    }


    public static void interact(Player playerEntity) {
        stop(playerEntity);
    }
}

