package com.robertx22.age_of_exile.database.data.value_calc;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import info.loenwind.autosave.annotations.Factory;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;

public class ScalingCalc {

    public String stat;
    public LeveledValue multi;

    public Stat getStat() {
        return ExileDB.Stats()
                .get(stat);
    }

    @Factory
    public ScalingCalc() {

    }

    public ScalingCalc(Stat stat, LeveledValue multi) {
        super();
        this.stat = stat.GUID();
        this.multi = multi;
    }

    public LeveledValue getMulti() {
        return multi;
    }

    public int getMultiAsPercent(LevelProvider provider) {
        return (int) (multi.getValue(provider) * 100);
    }

    public Component GetTooltipString(LevelProvider provider) {
        return new TextComponent("(" + getMultiAsPercent(provider) + "% of " + getStat().getIconNameFormat() + ")");
    }

    public List<Component> getTooltipFor(float multi, float value, MutableComponent statname, Elements el) {
        List<Component> list = new ArrayList<>();
        String eleStr = "";

        if (el != null) {
            eleStr = el.format + el.icon;
        }
        

        if (statname != null) {
            list.add(new TextComponent(
                    ChatFormatting.RED + "Scales with " + (int) (multi * 100F) + "% " + eleStr + " ").append(
                            statname)
                    .append(" (" + value + ")"));
        }

        return list;
    }

    public int getCalculatedValue(LevelProvider provider) {

        return (int) (getMulti().getValue(provider) * provider.getCasterData()
                .getUnit()
                .getCalculatedStat(stat)
                .getValue());

    }
}
