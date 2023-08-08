package com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts;

import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.library_of_exile.registry.FilterListWrap;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.joml.Math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class AffixData implements IRerollable, IGearPartTooltip, IStatsContainer {


    // perc
    public Integer p = -1;
    public String id;
    // tier
    public int t;
    public Affix.Type ty;

    public void setTier(int t) {
        this.t = Math.clamp(t, 0, 10); // todo if i rework tiers
    }


    @Override
    public MinMax getMinMax(GearItemData gear) {
        return new MinMax((t * 10) - 10, t * 10); // todo temporary
    }

    public AffixData(Affix.Type type) {
        this.ty = type;
    }


    private AffixData() {
    }

    public boolean isEmpty() {
        return p < 0;
    }

    public Affix.Type getAffixType() {
        return ty;
    }

    @Override
    public List<Component> GetTooltipString(TooltipInfo info, GearItemData gear) {
        List<Component> list = new ArrayList<Component>();
        getAllStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));
        return list;
    }

    public Affix getAffix() {
        return ExileDB.Affixes()
                .get(this.id);
    }

    @Override
    public IGearPart.Part getPart() {
        return IGearPart.Part.AFFIX;
    }

    @Override
    public void RerollNumbers(GearItemData gear) {
        p = getMinMax(gear).random();
    }


    public int getAffixTier() {
        return t;
    }

    public final Affix BaseAffix() {
        return ExileDB.Affixes()
                .get(id);
    }

    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, TooltipInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.BaseAffix()
                .getStats()
                .forEach(x -> {
                    ExactStatData exact = x.ToExactStat(p, gear.getLevel());
                    TooltipStatInfo confo = new TooltipStatInfo(exact, p, info);
                    confo.affix_tier = getAffixTier();
                    list.add(new TooltipStatWithContext(confo, x, (int) gear.getLevel()));
                });
        return list;
    }

    public boolean isValid() {
        if (!ExileDB.Affixes()
                .isRegistered(this.id)) {
            return false;
        }
        if (this.isEmpty()) {
            return false;
        }

        return true;
    }

    @Override
    public List<ExactStatData> GetAllStats(GearItemData gear) {

        if (!isValid()) {
            return Arrays.asList();
        }

        return this.BaseAffix()
                .getStats()
                .stream()
                .map(x -> x.ToExactStat(p, gear.getLevel()))
                .collect(Collectors.toList());

    }

    public void create(GearItemData gear, Affix suffix) {
        id = suffix.GUID();
        RerollNumbers(gear);
    }

    @Override
    public void RerollFully(GearItemData gear) {

        Affix affix = null;
        try {

            FilterListWrap<Affix> list = ExileDB.Affixes()
                    .getFilterWrapped(x -> x.type == getAffixType() && gear.canGetAffix(x));

            if (list.list.isEmpty()) {
                System.out.print("Gear Type: " + gear.gtype + " affixtype: " + this.ty.name());
            }

            affix = list
                    .random();

            this.randomizeTier(gear);

        } catch (Exception e) {
            System.out.print("Gear Type: " + gear.gtype + " affixtype: " + this.ty.name());
            e.printStackTrace();
        }

        this.create(gear, affix);

    }


    // this is kinda simplified.. but might be fine

    public void randomizeTier(GearItemData gear) {

        int num = (int) (gear.getLevel() / (float) GameBalanceConfig.get().MAX_LEVEL * 10);

        num = Mth.clamp(num, 1, 10);

        this.t = RandomUtils.RandomRange(1, num);

    }
}
