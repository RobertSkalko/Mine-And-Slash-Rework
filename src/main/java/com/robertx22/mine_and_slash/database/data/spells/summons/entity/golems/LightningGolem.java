package com.robertx22.mine_and_slash.database.data.spells.summons.entity.golems;

import com.robertx22.mine_and_slash.aoe_data.database.mob_affixes.MobAffixes;
import com.robertx22.mine_and_slash.aoe_data.database.spells.schools.WaterSpells;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;

public class LightningGolem extends GolemSummon {
    public LightningGolem(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public String affix() {
        return MobAffixes.FULL_LIGHTNING;
    }

    @Override
    public String aoeSpell() {
        return WaterSpells.FROST_NOVA_AOE; // todo
    }

    @Override
    public Elements ele() {
        return Elements.Nature;
    }
}
