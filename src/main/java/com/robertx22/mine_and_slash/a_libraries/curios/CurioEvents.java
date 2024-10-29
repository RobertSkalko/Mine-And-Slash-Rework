package com.robertx22.mine_and_slash.a_libraries.curios;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.mmorpg.ForgeEvents;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
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
                    }
                }
            }

        });
    }
}
