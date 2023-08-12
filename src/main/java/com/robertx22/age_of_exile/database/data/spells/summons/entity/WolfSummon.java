package com.robertx22.age_of_exile.database.data.spells.summons.entity;

import com.robertx22.age_of_exile.aoe_data.database.spells.SummonType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class WolfSummon extends SummonEntity {


    public WolfSummon(EntityType<? extends WolfSummon> pEntityType, Level pLevel) {

        super(pEntityType, pLevel);
    }

    @Override
    public SummonType summonType() {
        return SummonType.BEAST;
    }
}
