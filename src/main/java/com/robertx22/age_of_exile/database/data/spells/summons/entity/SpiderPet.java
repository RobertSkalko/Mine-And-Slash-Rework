package com.robertx22.age_of_exile.database.data.spells.summons.entity;

import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SpiderPet extends SummonEntity {
    public SpiderPet(EntityType<? extends SummonEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public SummonType summonType() {
        return SummonType.UNDEAD;
    }
}
