package com.robertx22.mine_and_slash.saveclasses;

import com.google.gson.JsonObject;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatContainer;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class ExactStatData implements ISerializable<ExactStatData>, ITooltipList {

    public static ExactStatData EMPTY = new ExactStatData();

    private ExactStatData() {

    }

    public static ExactStatData copy(ExactStatData o) {
        ExactStatData data = new ExactStatData();
        data.stat = o.stat;
        data.v1 = o.v1;
        data.percentIncrease = o.percentIncrease;
        data.type = o.type;
        data.scaled = o.scaled;
        return data;
    }

    public static ExactStatData levelScaled(float first, Stat stat, ModType type, int lvl) {
        ExactStatData data = new ExactStatData();
        data.v1 = first;
        data.stat = stat.GUID();
        data.type = type;
        data.scaleToLevel(lvl);
        return data;
    }

    public static ExactStatData fromStatModifier(StatMod mod, int percent, float lvl) {
        ExactStatData data = new ExactStatData();

        data.v1 = (mod.min + (mod.max - mod.min) * percent / 100F);

        data.type = mod.getModType();
        data.stat = mod.stat;

        data.scaleToLevel(lvl);

        return data;
    }

    public static ExactStatData scaleTo(float v1, ModType type, String stat, int level) {
        ExactStatData data = new ExactStatData();

        data.v1 = v1;

        data.type = type;
        data.stat = stat;

        data.scaleToLevel(level);

        return data;
    }

    public void multiplyBy(float multi) {
        v1 *= multi;
    }

    public static ExactStatData noScaling(float v1, ModType type, String stat) {
        ExactStatData data = new ExactStatData();

        data.v1 = v1;

        data.type = type;
        data.stat = stat;

        data.scaled = true;

        return data;
    }

    private boolean scaled = false;

    private void scaleToLevel(float lvl) {
        if (!scaled) {
            this.v1 = getStat().scale(type, v1, lvl);
        }
    }

    private float v1 = 0;
    private ModType type = ModType.FLAT;
    private String stat = "";

    public transient float percentIncrease = 0;

    public String getStatId() {
        return stat;
    }

    public float getValue() {
        return v1;
    }

    public void add(ExactStatData other) {
        if (type == other.type) {
            v1 += other.v1;
        } else {
            MMORPG.LOGGER.log("error wrong types");
        }
    }

    public void increaseByAddedPercent() {

        v1 *= (1 + percentIncrease / 100F);

        percentIncrease = 0;
    }


    public float getFirstValue() {
        return v1;
    }

    public ModType getType() {
        return type;
    }

    public Stat getStat() {
        return ExileDB.Stats()
                .get(stat);
    }

    public void applyToStatInCalc(InCalcStatContainer calc) {
        calc.getStatInCalculation(stat).add(this);
    }

    @Override
    public List<MutableComponent> GetTooltipString() {

        Stat stat = getStat();
        TooltipStatInfo statInfo = new TooltipStatInfo(this, 100, new StatRangeInfo(ModRange.hide()));
        return new ArrayList<>(stat.getTooltipList(new TooltipStatWithContext(statInfo, null, null)));

    }


    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("v1", this.v1);
        json.addProperty("type", this.type.id);
        json.addProperty("stat", this.stat);

        return json;
    }

    @Override
    public ExactStatData fromJson(JsonObject json) {

        float first = json.get("v1")
                .getAsFloat();
        String stat = json.get("stat")
                .getAsString();

        ModType type = ModType.fromString(json.get("type")
                .getAsString());

        ExactStatData data = new ExactStatData();
        data.v1 = first;
        data.stat = stat;
        data.type = type;

        data.scaled = true;
        return data;
    }


}
