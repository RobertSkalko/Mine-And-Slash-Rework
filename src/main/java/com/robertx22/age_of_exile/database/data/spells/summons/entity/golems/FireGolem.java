package com.robertx22.age_of_exile.database.data.spells.summons.entity.golems;

import com.robertx22.age_of_exile.aoe_data.database.mob_affixes.MobAffixes;
import com.robertx22.age_of_exile.aoe_data.database.spells.schools.FireSpells;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;

public class FireGolem extends GolemSummon {
    public FireGolem(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public String affix() {
        return MobAffixes.FULL_FIRE;
    }

    @Override
    public String aoeSpell() {
        return FireSpells.FIRE_NOVA_ID;
    }

    @Override
    public Elements ele() {
        return Elements.Fire;
    }
}
