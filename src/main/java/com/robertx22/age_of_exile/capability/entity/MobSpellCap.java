package com.robertx22.age_of_exile.capability.entity;

import com.robertx22.age_of_exile.capability.player.EntitySpellCap;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.spells.SpellsData;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class MobSpellCap extends EntitySpellCap.SpellCap {
    public MobSpellCap(LivingEntity entity) {
        super(entity);
    }

    List<Spell> list;

    @Override
    public List<Spell> getLearnedSpells() {
        if (list.isEmpty()) {
            list = ExileDB.Spells().getList();
        }
        return list;
    }


    @Override
    public SpellsData getSpellsData() {
        return null;
    }


    @Override
    public int getLevelOf(String id) {
        int lvl = super.getLevelOf(id);
        if (lvl < 1) {
            return 1;
        }
        return lvl;
    }


}
