package com.robertx22.age_of_exile.database.data.spells.entities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class SimpleArrowEntity extends SimpleProjectileEntity {

    public SimpleArrowEntity(EntityType<? extends Entity> type, Level worldIn) {
        super(type, worldIn);
    }

}
