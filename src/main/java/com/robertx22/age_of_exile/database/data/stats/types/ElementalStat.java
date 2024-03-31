package com.robertx22.age_of_exile.database.data.stats.types;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.age_of_exile.saveclasses.unit.InCalcStatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IElementalGenerated;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ElementalStat extends Stat implements IElementalGenerated<Stat>, ITransferToOtherStats {

    public Elements element;

    public ElementalStat(Elements element) {
        this.element = element;

        this.show_in_gui = element != Elements.Elemental;

        if (getElement() != null) {
            this.format = getElement().format.getName();
            this.icon = getElement().icon;
        }
    }

    @Override
    public Elements getElement() {
        return this.element;
    }

    public abstract Stat newGeneratedInstance(Elements element);

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = new ArrayList<>();
        Arrays.stream(Elements.values()).forEach(x -> list.add(newGeneratedInstance(x)));
        return list;

    }

    @Override
    public void transferStats(Unit unit, InCalcStatData thisstat) {
        if (this.element == Elements.Elemental) {
            for (Elements ele : Elements.getAllSingle()) {
                thisstat.addFullyTo(unit.getStatInCalculation(newGeneratedInstance(ele)));
            }
            thisstat.clear();
        }
    }

}
