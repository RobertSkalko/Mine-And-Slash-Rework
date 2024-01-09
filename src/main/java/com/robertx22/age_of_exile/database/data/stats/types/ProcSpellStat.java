package com.robertx22.age_of_exile.database.data.stats.types;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class ProcSpellStat extends Stat {
    public Spell spell;

    public ProcSpellStat(Spell spell) {
        this.spell = spell;
        this.show_in_gui = false;
    }

    @Override
    public List<MutableComponent> getTooltipList(TooltipStatWithContext info) {
        var list = info.statinfo.tooltipInfo.statTooltipType.impl.getTooltipList(null, info);
        for (Component co : spell.GetTooltipString(info.statinfo.tooltipInfo)) {
            list.add((MutableComponent) co); // todo will this break? damn components
        }
        return list;
    }

    
    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public String locDescForLangFile() {
        return "";
    }

    @Override
    public String locNameForLangFile() {
        return "Chance to Cast " + spell.locNameForLangFile();
    }

    @Override
    public String GUID() {
        return "proc_spell_" + spell.GUID();
    }
}
