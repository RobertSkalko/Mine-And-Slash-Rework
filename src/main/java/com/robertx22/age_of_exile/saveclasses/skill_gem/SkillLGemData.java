package com.robertx22.age_of_exile.saveclasses.skill_gem;

import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SkillGemsItems;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import net.minecraft.world.item.Item;

public class SkillLGemData {

    public String id = "";
    public SkillGemType type = SkillGemType.SKILL;
    public int perc = 0;
    public String rar = IRarity.COMMON_ID;

    public enum SkillGemType {
        SKILL, SUPPORT, AURA
    }


    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }

    public int getStatPercent() {
        return perc;
    }

    public Spell getSpell() {
        if (type == SkillGemType.SKILL) {
            return ExileDB.Spells().get(id);
        }
        return null;
    }

    public Aura getAura() {
        if (type == SkillGemType.AURA) {
            return ExileDB.auras().get(id);
        }
        return null;
    }

    public Support getSupport() {
        if (type == SkillGemType.SUPPORT) {
            return ExileDB.supports().get(id);
        }
        return null;
    }

    public PlayStyle getStyle() {
        return
    }


    public Item getItem() {
        return SkillGemsItems.get(this);
    }

}
