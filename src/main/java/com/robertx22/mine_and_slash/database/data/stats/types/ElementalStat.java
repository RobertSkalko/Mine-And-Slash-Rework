package com.robertx22.mine_and_slash.database.data.stats.types;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.types.core_stats.base.ITransferToOtherStats;
import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.interfaces.IElementalGenerated;

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

    // todo make this its own stat?
    @Override
    public void transferStats(InCalcStatContainer unit, InCalcStatData thisstat) {
        if (this.element == Elements.Elemental) {
            for (Elements ele : Elements.getAllSingleElemental()) {
                thisstat.addFullyTo(unit.getStatInCalculation(newGeneratedInstance(ele)));
            }
            thisstat.clear();
        }
    }

}
