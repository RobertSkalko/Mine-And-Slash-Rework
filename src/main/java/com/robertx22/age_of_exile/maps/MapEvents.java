package com.robertx22.age_of_exile.maps;

import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;

public class MapEvents {

    public static void init() {
// todo

        ForgeEvents.registerForgeEvent(LivingEvent.LivingTickEvent.class, x -> {
            if (!x.getEntity().level().isClientSide) {
                if (x.getEntity() instanceof Player p && x.getEntity().level() instanceof ServerLevel sw) {
                    if (p.tickCount == 0 || p.tickCount == 1 || p.tickCount % 20 == 0) {
                        ProcessChunkBlocks.process(sw, x.getEntity().blockPosition());
                    }
                }
            }
        });
    }
}
