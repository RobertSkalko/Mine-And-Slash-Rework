package com.robertx22.age_of_exile.gui.texts.textblocks.mapblocks;

import com.google.common.collect.ImmutableMap;
import com.robertx22.age_of_exile.gui.texts.IgnoreNullList;
import com.robertx22.age_of_exile.gui.texts.textblocks.StatBlock;
import com.robertx22.age_of_exile.maps.AffectedEntities;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Stream;

import static com.robertx22.age_of_exile.gui.texts.ExileTooltips.EMPTY_LINE;

@RequiredArgsConstructor
public class MapStatBlock extends StatBlock {
    @Nonnull
    private final MapItemData mapItemData;

    private final ImmutableMap<AffectedEntities, MutableComponent> map = ImmutableMap.of(
            AffectedEntities.Mobs, Words.Mob_Affixes.locName(),
            AffectedEntities.Players, Words.Player_Affixes.locName(),
            AffectedEntities.All, Words.Affixes_Affecting_All.locName()
    );

    @Override
    public List<? extends Component> getAvailableComponents() {

        IgnoreNullList<Component> list = new IgnoreNullList<>();
        TooltipInfo info = new TooltipInfo();

        Stream.of(AffectedEntities.Mobs, AffectedEntities.Players, AffectedEntities.All).forEachOrdered(x -> getAffectedStatList(list, info, x));

        list.add(Itemtips.TIER_INFLUENCE.locName().withStyle(ChatFormatting.BLUE));
        mapItemData.getTierStats().forEach(exactStatData -> list.addAll(exactStatData.GetTooltipString(info)));

        return list;
    }

    private void getAffectedStatList(IgnoreNullList<Component> list, TooltipInfo info, AffectedEntities target) {
        List<MutableComponent> list1 = Optional.of(target)
                .map(mapItemData::getAllAffixesThatAffect)
                .filter(x -> !x.isEmpty())
                .stream()
                .flatMap(Collection::stream)
                .map(x -> x.getAffix().getStats(x.p, mapItemData.getLevel()))
                .flatMap(x -> x.stream()
                        .map(y -> y.GetTooltipString(info))
                        .flatMap(Collection::stream)
                )
                .sorted((s1, s2) -> {
                    Boolean s1IfLong = s1.getString().contains("\u25C6");
                    Boolean s2IfLong = s2.getString().contains("\u25C6");
                    return s1IfLong.compareTo(s2IfLong);
                })
                .filter(x -> Objects.nonNull(x) && !x.getString().isBlank())
                .toList();
        if (!list1.isEmpty()) {
            list.add(map.get(target).withStyle(ChatFormatting.BLUE));
            list.addAll(list1);
            list.add(EMPTY_LINE);
        }

    }
}
