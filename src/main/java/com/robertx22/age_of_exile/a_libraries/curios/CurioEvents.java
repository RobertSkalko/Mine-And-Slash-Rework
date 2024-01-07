package com.robertx22.age_of_exile.a_libraries.curios;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class CurioEvents {


    public static void reg() {
        ForgeEvents.registerForgeEvent(CurioChangeEvent.class, event -> {

            LivingEntity entity = event.getEntity();
            if (entity != null) {
                if (!entity.level().isClientSide) {
                    EntityData data = Load.Unit(entity);
                    if (data != null) {
                        data.setEquipsChanged();
                        data.tryRecalculateStats();

                        if (entity instanceof ServerPlayer) {
                            data.syncToClient((ServerPlayer) entity);
                        }
                    }
                }
            }

        });
    }
}
