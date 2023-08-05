package com.robertx22.age_of_exile.event_hooks.ontick;

import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.server.level.ServerLevel;

public class OnTickDungeonWorld {


    public static void onEndTick(ServerLevel world) {

        if (WorldUtils.isMapWorldClass(world)) {
            //   Load.dungeonData(world).data.onTick(world);
        }
    }
}
