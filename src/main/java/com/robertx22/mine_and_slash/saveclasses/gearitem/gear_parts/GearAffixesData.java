package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts;

import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.saveclasses.item_classes.tooltips.TooltipStatWithContext;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;


public class GearAffixesData implements IGearPartTooltip {


    public List<AffixData> suf = new ArrayList<>();

    public List<AffixData> pre = new ArrayList<>();

    public List<AffixData> cor = new ArrayList<>();

    public boolean isCorrupted() {
        return !cor.isEmpty();
    }


    public List<TooltipStatWithContext> getAllStatsWithCtx(GearItemData gear, StatRangeInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.suf.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear.getLevel(), gear.getRarity())));
        this.pre.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear.getLevel(), gear.getRarity())));
        this.cor.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear.getLevel(), gear.getRarity())));
        return list;
    }

    public List<TooltipStatWithContext> getSufStatsWithCtx(GearItemData gear, StatRangeInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.suf.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear.getLevel(), gear.getRarity())));
        return list;
    }

    public List<TooltipStatWithContext> getPreStatsWithCtx(GearItemData gear, StatRangeInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.pre.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear.getLevel(), gear.getRarity())));
        return list;
    }

    public List<TooltipStatWithContext> getCorStatsWithCtx(GearItemData gear, StatRangeInfo info) {
        List<TooltipStatWithContext> list = new ArrayList<>();
        this.cor.forEach(x -> list.addAll(x.getAllStatsWithCtx(gear.getLevel(), gear.getRarity())));
        return list;
    }

    @Override
    public List<Component> GetTooltipString(StatRangeInfo info, GearItemData gear) {
        List<Component> list = new ArrayList<Component>();


        if (!getCorStatsWithCtx(gear, info).isEmpty()) {
            TooltipUtils.addEmpty(list);
            list.add(Itemtips.COR_STATS.locName().withStyle(ChatFormatting.RED));
            getCorStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString()));
            TooltipUtils.addEmpty(list);
        }


        var color = ChatFormatting.BLUE;
        if (!getPreStatsWithCtx(gear, info).isEmpty()) {
            TooltipUtils.addEmpty(list);
            list.add(Itemtips.PREFIX_STATS.locName().withStyle(color));
            getPreStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString()));
            TooltipUtils.addEmpty(list);
        }


        if (!getSufStatsWithCtx(gear, info).isEmpty()) {
            TooltipUtils.addEmpty(list);
            list.add(Itemtips.SUFFIX_STATS.locName().withStyle(color));
            getSufStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString()));
            TooltipUtils.addEmpty(list);
        }


        /*

        if (!pre.isEmpty() || !suf.isEmpty()) {
            TooltipUtils.addEmpty(list);
            var color = ChatFormatting.GREEN;
            list.add(Itemtips.AFFIX_STATS.locName().withStyle(color));

            if (!getPreStatsWithCtx(gear, info).isEmpty()) {
                getPreStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));

            }
            if (!getSufStatsWithCtx(gear, info).isEmpty()) {
                getSufStatsWithCtx(gear, info).forEach(x -> list.addAll(x.GetTooltipString(info)));
            }
            TooltipUtils.addEmpty(list);
        }

         */

        return list;
    }

    public int getMaxAffixesPerType(GearItemData gear) {
        int affixes = gear.getRarity()
                .maximumOfOneAffixType();

        return affixes;
    }

    public void add(AffixData affix) {
        if (affix.ty.isSuffix()) {
            suf.add(affix);
        } else {
            pre.add(affix);
        }
    }

    public boolean canGetMore(Affix.Type type, GearItemData gear) {


        int current;
        if (type == Affix.Type.prefix) {
            current = pre
                    .size();
        } else {
            current = suf.size();
        }

        return current < getMaxAffixesPerType(gear);

    }

    public int getNumberOfPrefixes() {
        return pre.size();
    }

    public int getNumberOfSuffixes() {
        return suf.size();
    }

    public void randomize(GearItemData gear) {

        this.pre.clear();
        this.suf.clear();

        GearRarity rar = gear.getRarity();

        for (int i = 0; i < rar.maximumOfOneAffixType(); i++) {

            AffixData suffix = new AffixData(Affix.Type.suffix);
            suffix.RerollFully(gear);
            suf.add(suffix);

            AffixData prefix = new AffixData(Affix.Type.prefix);
            prefix.RerollFully(gear);
            pre.add(prefix);

        }

        int minaffixes = rar.min_affixes;
        int affixesToGen = minaffixes - (this.getNumberOfAffixes());

        while (affixesToGen > 0) {
            addOneRandomAffix(gear);
            affixesToGen--;

        }
    }

    public void addOneRandomAffix(GearItemData gear) {
        if (getNumberOfPrefixes() > getNumberOfSuffixes()) {
            AffixData suffix = new AffixData(Affix.Type.suffix);
            suffix.RerollFully(gear);
            suf.add(suffix);
        } else {
            AffixData prefix = new AffixData(Affix.Type.prefix);
            prefix.RerollFully(gear);
            pre.add(prefix);
        }
    }

    public boolean hasSuffix() {
        return getNumberOfSuffixes() > 0;
    }

    public boolean hasPrefix() {
        return getNumberOfPrefixes() > 0;
    }

    public List<AffixData> getAllAffixesAndSockets() {
        List<AffixData> list = new ArrayList<>();

        list.addAll(pre);
        list.addAll(suf);
        list.addAll(cor);

        return list;
    }

    public List<AffixData> getPrefixesAndSuffixes() {
        List<AffixData> list = new ArrayList<>();

        list.addAll(pre);
        list.addAll(suf);

        return list;
    }

    public boolean containsAffix(Affix affix) {
        return containsAffix(affix.GUID());
    }

    public boolean containsAffix(String id) {
        return getAllAffixesAndSockets().stream()
                .anyMatch(x -> x.id.equals(id));
    }

    public int getNumberOfAffixes() {
        return getNumberOfPrefixes() + getNumberOfSuffixes();
    }

    @Override
    public Part getPart() {
        return Part.AFFIX;
    }
}
