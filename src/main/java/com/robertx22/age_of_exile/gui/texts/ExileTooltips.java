package com.robertx22.age_of_exile.gui.texts;

import com.google.common.collect.ImmutableMap;
import com.robertx22.age_of_exile.gui.texts.textblocks.*;
import com.robertx22.age_of_exile.gui.texts.textblocks.gearblocks.DurabilityBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.gearblocks.GearStatBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.gearblocks.LeveledItemLevelBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.mapblocks.MapStatBlock;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.wrappers.ExileText;
import lombok.NoArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@NoArgsConstructor
public class ExileTooltips {

    public static Component EMPTY_LINE = Component.literal("");
    public final String aBlockPrefix = "additional_";
    private final HashMap<String, AbstractTextBlock> blockContainer = new HashMap<>();
    //use this map to standardize the same blocks name in different tooltips.
    private final ImmutableMap<Class<? extends AbstractTextBlock>, String> blockNameMap = ImmutableMap.of(
            NameBlock.class, "name",
            RarityBlock.class, "rarity",
            RequirementBlock.class, "requirement",
            StatBlock.class, "stat",
            GearStatBlock.class, "stat",
            MapStatBlock.class, "stat",
            DurabilityBlock.class, "durability",
            LeveledItemLevelBlock.class, "leveled_item_level",
            InformationBlock.class, "information"
    );
    private int additionalBlockCount;

    public ExileTooltips accept(AbstractTextBlock block) {
        if (blockNameMap.containsKey(block.getClass())) {
            blockContainer.put(blockNameMap.get(block.getClass()), block);
        } else {
            // handle AdditionalBlock in this part
            if (block instanceof AdditionalBlock additionalBlock) {
                blockContainer.put(aBlockPrefix + additionalBlockCount, additionalBlock);
                additionalBlockCount++;
            }
        }
        return this;
    }

    public List<Component> release() {
        IgnoreNullList<Component> list = new IgnoreNullList<>();

        MutableComponent emptyLine = ExileText.emptyLine().get();


        //handle the req, stat. Should be noticed that the order of these blocks are fixed, and thats the point in order to maintain the style consistency of tooltips.
        Optional.ofNullable(blockContainer.get("name"))
                .map(AbstractTextBlock::getAvailableComponents)
                .ifPresent(x -> {
                    if (blockContainer.get("rarity") != null) {
                        for (Component component : x) {
                            list.add(component.copy().withStyle(((RarityBlock) blockContainer.get("rarity")).rarity.textFormatting()));
                        }
                    } else {
                        for (Component component : x) {
                            list.add(component.copy().withStyle(ChatFormatting.WHITE));
                        }
                    }
                });

        list.add(emptyLine);

        Stream.of(
                        blockContainer.get("requirement"),
                        blockContainer.get("stat"),
                        blockContainer.get("leveled_item_level")
                ).filter(x -> Objects.nonNull(x) && !x.getAvailableComponents().isEmpty())
                .forEachOrdered(x -> {
                    list.addAll(x.getAvailableComponents());
                    list.add(emptyLine);
                });

        //handle additional blocks, the order of aBs is the putting order.

        for (int i = 0; i < additionalBlockCount; i++) {
            AbstractTextBlock aBlock = blockContainer.get(aBlockPrefix + i);
            if (aBlock != null && !aBlock.getAvailableComponents().isEmpty()) {
                list.addAll(aBlock.getAvailableComponents());
                list.add(emptyLine);
            }

        }

        Stream.of(
                        blockContainer.get("rarity"),
                        blockContainer.get("durability")
                ).filter(x -> Objects.nonNull(x) && !x.getAvailableComponents().isEmpty())
                .forEachOrdered(x -> {
                    list.addAll(x.getAvailableComponents());
                });

        list.add(emptyLine);
        Optional.ofNullable(blockContainer.get("information"))
                .map(AbstractTextBlock::getAvailableComponents)
                .ifPresent(list::addAll);


        if (list.get(list.size() - 1).getString().isBlank()){
            list.remove(list.size() - 1);
        }

        List<Component> postEditList = TooltipUtils.splitLongText(list);

        return postEditList;

    }

}
