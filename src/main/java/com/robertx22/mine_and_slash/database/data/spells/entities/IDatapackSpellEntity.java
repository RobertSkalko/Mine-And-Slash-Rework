package com.robertx22.mine_and_slash.database.data.spells.entities;

import com.robertx22.mine_and_slash.database.data.spells.components.MapHolder;
import net.minecraft.world.entity.LivingEntity;

public interface IDatapackSpellEntity {

    public void init(LivingEntity caster, CalculatedSpellData data, MapHolder holder);

}
