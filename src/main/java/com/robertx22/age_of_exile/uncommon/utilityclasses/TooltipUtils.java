package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;


public class TooltipUtils {

    public static String CHECKMARK = ChatFormatting.GREEN + "\u2714";
    public static String X = ChatFormatting.RED + "\u2716";

    public static MutableComponent color(ChatFormatting format, MutableComponent comp) {
        return Component.literal(format + "").append(comp);
    }

    public static void addRequirements(List<Component> tip, int lvl, StatRequirement req, EntityData data) {
/*
        if (data.getLevel() >= lvl) {
            tip.add(Component.literal(ChatFormatting.GREEN + "" + ChatFormatting.BOLD + StatRequirement.CHECK_YES_ICON + ChatFormatting.GRAY)
                    .append(ChatFormatting.GRAY + " Level: " + lvl + " "));

        } else {
            tip.add(Component.literal(ChatFormatting.RED + "" + ChatFormatting.BOLD + StatRequirement.NO_ICON + ChatFormatting.GRAY)
                    .append(ChatFormatting.GRAY + " Level: " + lvl + " ")
            );
        }

 */
        tip.addAll(req.GetTooltipString(lvl, data));
    }


    public static void addEmpty(List<Component> tooltip) {
        tooltip.add(CLOC.blank(""));
    }

    public static List<String> compsToStrings(List<Component> list) {
        return list.stream()
                .map(x -> x.getString()) // todo does this work ?
                .collect(Collectors.toList());
    }

    public static MutableComponent level(int lvl) {
        return Component.literal(ChatFormatting.YELLOW + "").append(Itemtips.LEVEL_TIP.locName())
                .append(lvl + "")
                .withStyle(ChatFormatting.YELLOW);

    }

    public static List<Component> cutIfTooLong(MutableComponent comp) {
        List<String> stringList = cutIfTooLong(CLOC.translate(comp));
        return stringList.stream()
                .map(x -> ExileText.ofText(x).get())
                .collect(Collectors.toList());

    }

    public static List<MutableComponent> cutIfTooLong(MutableComponent comp, ChatFormatting format) {
        List<String> stringList = cutIfTooLong(CLOC.translate(comp));
        return stringList.stream()
                .map(x -> ExileText.ofText(x).format(format).get())
                .collect(Collectors.toList());

    }

    // private static final Pattern PATTERN = Pattern.compile("(?)§[0-9A-FK-OR]");

    static Character CHAR = "§".charAt(0); // TODO WTF INTELIJ

    public static List<String> cutIfTooLong(String str) {
        if (true) {
            return Arrays.asList(str);
        }

        List<String> list = new ArrayList<>();

        ChatFormatting format = null;

        char[] array = str.toCharArray();

        int start = 0;
        int i = 0;

        ChatFormatting formattouse = null;

        for (Character c : array) {

            if (c.equals(CHAR)) {
                format = ChatFormatting.getByCode(array[i + 1]);
            }

            if (i == str.length() - 1) {
                String cut = str.substring(start);
                if (cut.startsWith(" ")) {
                    cut = cut.substring(1);
                }
                if (formattouse != null) {
                    cut = formattouse + cut;
                    format = null;
                    formattouse = null;
                }
                list.add(cut);
            } else if (i - start > 35 && c == ' ') {
                String cut = str.substring(start, i);
                if (start > 0) {
                    cut = cut.substring(1);
                }

                if (format != null) {
                    formattouse = format;
                }

                list.add(cut);

                start = i;
            }
            i++;
        }

        return list;
    }

    public static MutableComponent itemBrokenText(ItemStack stack, ICommonDataItem data) {

        if (data != null) {

            if (RepairUtils.isItemBroken(stack)) {
                MutableComponent comp = Component.literal(X + " ").append(Words.Broken.locName());
                return comp;
            }

        }

        return null;
    }

    public static List<Component> mutableToComp(List<MutableComponent> list) {
        return new ArrayList<Component>(list);
    }

    public static List<Component> removeDoubleBlankLines(List<Component> list) {
        return removeDoubleBlankLines(list, ClientConfigs.getConfig().REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES.get());
    }

    private static List<Component> removeDoubleBlankLines(List<Component> list, int minLinesCutAllBlanks) {

        List<Component> newt = new ArrayList();

        boolean lastIsEmpty = false;

        boolean alwaysRemoveEmpty = list.size() > minLinesCutAllBlanks;

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i)
                    .getString()
                    .length() > 2) {
                lastIsEmpty = false;
                newt.add(list.get(i));
            } else {

                if ((lastIsEmpty || alwaysRemoveEmpty)) {

                } else {
                    newt.add(list.get(i));
                }

                lastIsEmpty = true;

            }
        }

        list.clear();

        list.addAll(newt);

        return newt;
    }

    public static MutableComponent rarity(Rarity rarity) {
        return Itemtips.RARITY_TIP.locName().withStyle(ChatFormatting.WHITE)
                .append(rarity.locName().withStyle(rarity.textFormatting()));
    }

    public static MutableComponent rarityShort(Rarity rarity) {
        return (Component.literal(rarity.textFormatting() + "").append(rarity.locName().withStyle(rarity.textFormatting())));
    }

    public static MutableComponent tier(int tier) {
        return Itemtips.TIER_TIP.locName().append(tier + "");

    }

    public static MutableComponent gearSlot(GearSlot slot) {
        return Itemtips.ITEM_TYPE.locName().withStyle(ChatFormatting.WHITE)
                .append(slot.locName()
                        .withStyle(ChatFormatting.AQUA));
    }

    public static MutableComponent gearTier(int tier) {
        return Itemtips.ITEM_TIER_TIP.locName().withStyle(ChatFormatting.WHITE)
                .append(Component.literal(tier + "").withStyle(ChatFormatting.AQUA));
    }

    public static MutableComponent gearRarity(GearRarity rarity) {
        return Itemtips.RARITY_TIP.locName().withStyle(ChatFormatting.WHITE)
                .append(rarity.locName()
                        .withStyle(rarity.textFormatting()));
    }

    public static MutableComponent gearLevel(int lvl, int playerlvl) {

        ChatFormatting color = ChatFormatting.YELLOW;
        if (lvl > playerlvl) {
            color = ChatFormatting.RED;
        }
        return Itemtips.LEVEL_TIP.locName()
                .withStyle(color)
                .append(Component.literal(lvl + "")
                        .withStyle(color));
    }


    public static MutableComponent dragOntoGearToUse() {
        return Itemtips.USE_TIP.locName().withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD);
    }

    public static List<MutableComponent> splitLongText(MutableComponent comp) {
        List<MutableComponent> componentList = new ArrayList<>();
        Style format = comp.getStyle();
        String[] originalList = comp.getString().split("\n");

        for (String comp1 : originalList) {
            componentList.add(Component.literal(comp1).withStyle(format));
        }
        return componentList;
    }


    public static List<Integer> incrementArray(List<Integer> inputList) {
        // Find the maximum value in the input list
        int max = inputList.stream().max(Integer::compareTo).orElse(0);

        // Create a list to store the count of increments for each element
        List<Integer> countList = new ArrayList<>(inputList.size());

        // Initialize the countList with zeros
        for (int i = 0; i < inputList.size(); i++) {
            countList.add(0);
        }

        // Create a new list to operate on (a copy of the input list)
        List<Integer> result = new ArrayList<>(inputList);

        // Iterate over each element in the result list
        for (int i = 0; i < result.size(); i++) {
            // Increment the element until it reaches or exceeds the maximum value
            while (result.get(i) < max) {
                result.set(i, result.get(i) + 2); // Increment the element by 2, is the "."'s width
                countList.set(i, countList.get(i) + 1); // Increment the count
            }
        }

        // Return the list containing the count of increments for each element
        return countList;

    }

    public static MutableComponent getMutabletags(Iterator<?> iterator, MutableComponent separator) {
        if (separator == null) {
            separator = Component.literal("");
        }

        ExileText Etext = ExileText.emptyLine();
        while (iterator.hasNext()) {
            Etext.append((MutableComponent) iterator.next());
            if (iterator.hasNext()) {
                Etext.append(separator);
            }
        }
        return Etext.get();
    }
}
