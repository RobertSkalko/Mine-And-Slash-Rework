package com.robertx22.age_of_exile.database.data.stats.types.generated;

import com.robertx22.age_of_exile.database.data.stats.IUsableStat;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatGuiGroup;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.ElementalResistEffect;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.ElementalStat;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.wrappers.MapWrapper;
import net.minecraft.ChatFormatting;

import java.util.HashMap;
import java.util.List;

public class ElementalResist extends ElementalStat implements IUsableStat {

    public static MapWrapper<Elements, ElementalResist> MAP = new MapWrapper();

    @Override
    public List<Stat> generateAllPossibleStatVariations() {
        List<Stat> list = super.generateAllPossibleStatVariations();
        list.forEach(x -> MAP.put(x.getElement(), (ElementalResist) x));
        return list;
    }

    public ElementalResist(Elements element) {
        super(element);

        this.gui_group = StatGuiGroup.RESIST;

        this.min = -300;

        this.group = StatGroup.ELEMENTAL;
        this.is_perc = true;
        this.scaling = StatScaling.NONE;

        this.format = element.format.getName();
        this.icon = element.icon;
        this.statEffect = new ElementalResistEffect();

        this.max = 500;
    }

    @Override
    public ChatFormatting getStatGuiTooltipNumberColor(StatData data) {

        float val = data.getValue();

        if (val > 75) {
            return ChatFormatting.LIGHT_PURPLE;
        }
        if (val > 70) {
            return ChatFormatting.GREEN;
        }
        if (val > 25) {
            return ChatFormatting.YELLOW;
        }
        return ChatFormatting.RED;
    }

    static HashMap<Elements, MaxElementalResist> cached = new HashMap<>();

    MaxElementalResist getMaxStat() {
        return cached.computeIfAbsent(getElement(), x -> new MaxElementalResist(x));
    }

    @Override
    public float getAdditionalMax(Unit data) {
        return data.getCalculatedStat(getMaxStat()).getValue();
    }

    @Override
    public Stat newGeneratedInstance(Elements element) {
        return new ElementalResist(element);
    }

    @Override
    public String GUID() {
        return this.getElement().guidName + "_resist";
    }

    @Override
    public String locDescForLangFile() {
        return "Stops X percent damage of that element";
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".stat_desc." + "ele_resist";
    }

    @Override
    public String locNameForLangFile() {
        return this.getElement().dmgName + " Resistance";
    }


    @Override
    public float getMaxMulti() {
        return 0;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 0;
    }

    @Override
    public float getUsableValue(Unit unit, int value, int lvl) {
        float max = MathHelper.clamp(75 + this.getAdditionalMax(unit), min, 90);
        float min = this.min;
        return MathHelper.clamp(value, min, max) / 100F;
    }
}

