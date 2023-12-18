package com.robertx22.age_of_exile.database.data.profession;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;

public class PlayerUTIL {
    public static boolean isFake(Player p) {
        if (p instanceof FakePlayer) {

            return true;
        }
        return false;
    }
}
