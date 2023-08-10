package com.robertx22.age_of_exile.entity.minions;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;

public class ThornyMinion extends Minion {
    public ThornyMinion(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick() {
        try {
            super.tick();
            if (!this.level().isClientSide) {

                if (!this.isDeadOrDying()) {
                    if (this.tickCount > (20 * 15)) {
                        this.discard();
                    }
                }

            }
        } catch (Exception e) {
            //throw new RuntimeException(e);
        }
    }
}
