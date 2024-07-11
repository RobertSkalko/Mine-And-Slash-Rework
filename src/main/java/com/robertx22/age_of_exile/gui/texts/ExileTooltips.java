package com.robertx22.age_of_exile.gui.texts;

import com.robertx22.age_of_exile.gui.texts.textblocks.AbstractTextBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.RarityBlock;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ExileTooltips {
    public static Component EMPTY_LINE = Component.literal("");

    private final List<AbstractTextBlock> blockContainer = new ArrayList<>();
    //use this map to standardize the same blocks name in different tooltips.


    public ExileTooltips() {
    }

    public ExileTooltips accept(AbstractTextBlock block) {
        blockContainer.add(block);
        return this;
    }


    public List<Component> release() {
        IgnoreNullList<Component> list = new IgnoreNullList<>();

        Map<BlockCategories, List<AbstractTextBlock>> collect = blockContainer.stream().collect(Collectors.groupingBy(AbstractTextBlock::getCategory));


        //handle the req, stat. Should be noticed that the order of these blocks are fixed, and thats the point in order to maintain the style consistency of tooltips.
        Optional.ofNullable(collect.get(BlockCategories.NAME))
                //can't have multiple name so directly get(0)
                .map(x -> x.get(0))
                .map(AbstractTextBlock::getAvailableComponents)
                .ifPresent(x -> {
                    List<AbstractTextBlock> rarity = collect.get(BlockCategories.RARITY);
                    if (rarity != null) {
                        for (Component component : x) {
                            list.add(component.copy().withStyle(((RarityBlock) rarity.get(0)).rarity.textFormatting()));
                        }
                    } else {
                        for (Component component : x) {
                            list.add(component.copy().withStyle(ChatFormatting.WHITE));
                        }
                    }
                });

        list.add(EMPTY_LINE);

        Stream.of(
                        collect.get(BlockCategories.REQUIREMENT),
                        collect.get(BlockCategories.STAT),
                        collect.get(BlockCategories.USAGE),
                        collect.get(BlockCategories.LEVELED_ITEM)
                )
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .filter(x -> !x.getAvailableComponents().isEmpty())
                .forEachOrdered(x -> {
                    list.addAll(x.getAvailableComponents());
                    list.add(EMPTY_LINE);
                });

        //handle additional blocks, the order of aBs is the putting order.
        List<AbstractTextBlock> additions = collect.get(BlockCategories.ADDITIONAL);
        if (additions != null) {
            for (AbstractTextBlock abstractTextBlock : additions) {
                if (abstractTextBlock != null && !abstractTextBlock.getAvailableComponents().isEmpty()) {
                    list.addAll(abstractTextBlock.getAvailableComponents());
                    list.add(EMPTY_LINE);
                }
            }
        }


        Stream.of(
                        collect.get(BlockCategories.RARITY),
                        collect.get(BlockCategories.DURABILITY)
                )
                .filter(Objects::nonNull)
                .map(x -> x.get(0))
                .filter(x -> !x.getAvailableComponents().isEmpty())
                .forEachOrdered(x -> list.addAll(x.getAvailableComponents()));

        list.add(EMPTY_LINE);
        Optional.ofNullable(collect.get(BlockCategories.OPERATION))
                //also I don't think we need multiple operation blocks.
                .map(x -> x.get(0))
                .map(AbstractTextBlock::getAvailableComponents)
                .ifPresent(list::addAll);


        while (list.get(list.size() - 1).getString().isBlank()) {
            list.remove(list.size() - 1);
        }

        List<Component> postEditList = TooltipUtils.splitLongText(list);

        postEditList = TooltipUtils.removeDoubleBlankLines(postEditList);

        return postEditList;

    }

    public enum BlockCategories {
        NAME,
        RARITY,
        REQUIREMENT,
        STAT,
        DURABILITY,
        USAGE,
        LEVELED_ITEM,
        OPERATION,
        ADDITIONAL

    }

}
