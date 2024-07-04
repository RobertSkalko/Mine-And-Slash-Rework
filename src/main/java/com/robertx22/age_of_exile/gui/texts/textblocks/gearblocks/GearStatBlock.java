package com.robertx22.age_of_exile.gui.texts.textblocks.gearblocks;

import com.robertx22.age_of_exile.gui.texts.IgnoreNullList;
import com.robertx22.age_of_exile.gui.texts.StatCategory;
import com.robertx22.age_of_exile.gui.texts.textblocks.StatBlock;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IGearPartTooltip;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_parts.*;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.MergedStats;
import com.robertx22.age_of_exile.saveclasses.item_classes.tooltips.TooltipStatInfo;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.library_of_exile.wrappers.ExileText;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
public class GearStatBlock extends StatBlock {

    @Nonnull
    private final GearItemData gearItemData;
    @Nonnull
    private final TooltipInfo info;

    public BaseStatsData baseStatsData;

    public ImplicitStatsData implicitStatsData;

    public GearAffixesData gearAffixesData;

    @Nullable
    public UniqueStatsData uniqueStatsData;

    public GearSocketsData gearSocketsData;

    @Nullable
    public GearEnchantData gearEnchantData;

    private static int getPriority(String s, Map<String, Integer> priorityMap) {
        return priorityMap.entrySet().stream()
                .filter(entry -> s.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .min(Integer::compare)
                .orElse(Integer.MAX_VALUE);
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        IgnoreNullList<Component> list = new IgnoreNullList<>();
        MutableComponent emptyLine = ExileText.emptyLine().get();

        this.implicitStatsData = gearItemData.imp;
        this.baseStatsData = gearItemData.baseStats;
        this.gearAffixesData = gearItemData.affixes;
        this.gearSocketsData = gearItemData.sockets;
        this.gearEnchantData = gearItemData.ench;
        this.uniqueStatsData = gearItemData.uniqueStats;

        if (baseStatsData != null) {
            list.addAll(baseStatsData.GetTooltipString(info, gearItemData));
            list.add(emptyLine);
        }

        boolean showMerge = !info.useInDepthStats();
        if (showMerge) {

            List<ExactStatData> stats = new ArrayList<>();

            stats.addAll(implicitStatsData.GetAllStats(gearItemData));

            gearAffixesData.getAllAffixesAndSockets().forEach(x -> stats.addAll(x.GetAllStats(gearItemData)));

            if (uniqueStatsData != null) {
                stats.addAll(uniqueStatsData.GetAllStats(gearItemData));
            }

            Map<Boolean, List<ExactStatData>> map = stats.stream().collect(Collectors.groupingBy(x -> x.getStat().is_long));

            MergedStats merged = new MergedStats(new ArrayList<>(), info);
            if (map.get(false) != null) {
                merged = new MergedStats(map.get(false), info);
            }

            HashMap<String, Integer> DPMap = new HashMap<>();
            DPMap.put("damage", 1);
            DPMap.put("dmg", 1);
            DPMap.put("penetration", 2);

            Comparator<TooltipStatInfo> damageAndPenetration = (s1, s2) -> {
                int p1 = getPriority(s1.stat.GUID(), DPMap);
                int p2 = getPriority(s2.stat.GUID(), DPMap);
                return Integer.compare(p1, p2);
            };


            Map<String, Integer> priorityMap = new HashMap<>();
            Arrays.asList(Elements.values()).forEach(x -> {
                priorityMap.put(x.guidName, x.ordinal());
            });


            Comparator<TooltipStatInfo> eleOrder = (s1, s2) -> {
                int p1 = getPriority(s1.stat.GUID(), priorityMap);
                int p2 = getPriority(s2.stat.GUID(), priorityMap);
                return Integer.compare(p1, p2);
            };
            //handle and sort the tt.
            //this map is used to store the TooltipStatInfos in order of StatCategory.
            LinkedHashMap<String, ArrayList<TooltipStatInfo>> orderStatMap = new LinkedHashMap<>();
            for (StatCategory category : StatCategory.values()) {
                orderStatMap.put(category.name(), new ArrayList<>());
            }
            orderStatMap.put("other", new ArrayList<>());
            //insert and sort the TooltipStatInfo into map.

            for (TooltipStatInfo tooltipStatInfo : merged.mergedList) {
                StatCategory.distributeStat(tooltipStatInfo, orderStatMap);
            }


            //get each TooltipStatInfo List, and turn it to tt.
            for (ArrayList<TooltipStatInfo> tooltipStatInfos : orderStatMap.values()) {
                tooltipStatInfos.sort(Comparator.comparing(o -> o.stat.GUID()));
                tooltipStatInfos.sort(Comparator.comparing(o -> o.stat.GUID().length()));
                tooltipStatInfos.sort(eleOrder);
                tooltipStatInfos.sort(damageAndPenetration);
                for (TooltipStatInfo tooltipStatInfo : tooltipStatInfos) {
                    list.addAll(tooltipStatInfo.GetTooltipString(info));
                }
                list.add(emptyLine);
            }

            Optional.ofNullable(map.get(true))
                    .map(x -> x.stream()
                            .flatMap(y -> y.GetTooltipString(info).stream())
                            .toList()
                    ).ifPresent(list::addAll);

            list.add(emptyLine);

        } else {
            List<Component> impComps;
            List<Component> uniComps = new ArrayList<>();
            List<Component> prefixComps = new ArrayList<>();
            List<Component> corComps = new ArrayList<>();
            List<Component> suffixComps = new ArrayList<>();
            impComps = implicitStatsData.GetTooltipString(info, gearItemData);
            if (uniqueStatsData != null) uniComps = uniqueStatsData.GetTooltipString(info, gearItemData);
            var color = ChatFormatting.BLUE;
            if (!gearAffixesData.getPreStatsWithCtx(gearItemData, info).isEmpty()) {
                prefixComps.add(Itemtips.PREFIX_STATS.locName().withStyle(color));
                prefixComps.addAll(gearAffixesData.getPreStatsWithCtx(gearItemData, info)
                        .stream()
                        .flatMap(x -> x.GetTooltipString(info).stream())
                        .map(x -> (Component) x)
                        .toList());
            }

            if (!gearAffixesData.getCorStatsWithCtx(gearItemData, info).isEmpty()) {
                corComps.add(Itemtips.COR_STATS.locName().withStyle(ChatFormatting.RED));
                corComps.addAll(gearAffixesData.getCorStatsWithCtx(gearItemData, info)
                        .stream()
                        .flatMap(x -> x.GetTooltipString(info).stream())
                        .map(x -> (Component) x)
                        .toList());
            }

            if (!gearAffixesData.getSufStatsWithCtx(gearItemData, info).isEmpty()) {
                suffixComps.add(Itemtips.SUFFIX_STATS.locName().withStyle(color));
                suffixComps.addAll(gearAffixesData.getSufStatsWithCtx(gearItemData, info)
                        .stream()
                        .flatMap(x -> x.GetTooltipString(info).stream())
                        .map(x -> (Component) x)
                        .toList());
            }


            Stream<List<Component>> addOrder = Stream.of(uniComps, prefixComps, impComps, suffixComps, corComps);
            addOrder.forEachOrdered(x -> {
                x.sort(Comparator.comparing(component -> component.getString().contains("\u25C6")));
                list.addAll(x);
                list.add(emptyLine);

            });

        }
        //handle sockets and enchantment
        IgnoreNullList<IGearPartTooltip> list3 = IgnoreNullList.of(gearSocketsData, gearEnchantData);
        for (IGearPartTooltip iGearPartTooltip : list3) {
            list.addAll(iGearPartTooltip.GetTooltipString(info, gearItemData));
        }


        return list;
    }
}
