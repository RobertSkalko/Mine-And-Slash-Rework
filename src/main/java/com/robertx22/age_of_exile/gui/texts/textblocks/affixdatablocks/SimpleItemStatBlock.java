package com.robertx22.age_of_exile.gui.texts.textblocks.affixdatablocks;

import com.google.common.collect.ImmutableMap;
import com.robertx22.age_of_exile.gui.texts.textblocks.StatBlock;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ITooltipList;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static com.robertx22.age_of_exile.gui.texts.ExileTooltips.EMPTY_LINE;

//It's impossible to sort this stat block.
public class SimpleItemStatBlock extends StatBlock {

    private final Map<Component, List<? extends ITooltipList>> map = new LinkedHashMap<>();

    @Nonnull
    private final TooltipInfo info;
    private final ImmutableMap<String, ChatFormatting> colorMap = ImmutableMap.of(
            Itemtips.COR_STATS.locName().getString(), ChatFormatting.RED,
            Itemtips.UNIQUE_STATS.locName().getString(), ChatFormatting.YELLOW
    );

    public SimpleItemStatBlock(@Nonnull TooltipInfo info) {
        this.info = info;
    }

    public SimpleItemStatBlock accept(Component title, List<? extends ITooltipList> dataList) {
        this.map.put(title, dataList);
        return this;
    }

    public SimpleItemStatBlock acceptIf(Component title, List<? extends ITooltipList> dataList, boolean condition) {
        if (condition) {
            this.map.put(title, dataList);
        }
        return this;
    }

    @Override
    public List<? extends Component> getAvailableComponents() {
        ArrayList<Component> components = new ArrayList<>();
        map.forEach((key, value) -> {
            List<MutableComponent> componentList = value.stream()
                    .flatMap(x -> x.GetTooltipString(info).stream())
                    .filter(component -> !component.getString().isBlank())
                    .toList();

            if (!componentList.isEmpty()) {
                ChatFormatting chatFormatting = colorMap.get(key.getString());
                components.add(chatFormatting != null ? key.copy().withStyle(chatFormatting) : key.copy().withStyle(ChatFormatting.BLUE));
                components.addAll(componentList);
                components.add(EMPTY_LINE);
            }
        });
        if (!components.isEmpty()) {
            components.remove(components.size() - 1);
        }
        return components;
    }
}
