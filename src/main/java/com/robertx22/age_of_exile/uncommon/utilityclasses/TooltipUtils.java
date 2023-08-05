package com.robertx22.age_of_exile.uncommon.utilityclasses;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.Rarity;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRequirement;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.wrappers.SText;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TooltipUtils {

    public static String CHECKMARK = ChatFormatting.GREEN + "\u2714";
    public static String X = ChatFormatting.RED + "\u2716";

    public static MutableComponent color(ChatFormatting format, MutableComponent comp) {
        return new TextComponent(format + "").append(comp);
    }

    public static void addRequirements(List<Component> tip, int lvl, StatRequirement req, EntityData data) {

        if (data.getLevel() >= lvl) {
            tip.add(new TextComponent(ChatFormatting.GREEN + "" + ChatFormatting.BOLD + StatRequirement.CHECK_YES_ICON + ChatFormatting.GRAY)
                    .append(ChatFormatting.GRAY + " Level Min: " + lvl + " "));

        } else {
            tip.add(new TextComponent(ChatFormatting.RED + "" + ChatFormatting.BOLD + StatRequirement.NO_ICON + ChatFormatting.GRAY)
                    .append(ChatFormatting.GRAY + " Level Min: " + lvl + " ")
            );
        }
        tip.addAll(req
                .GetTooltipString(lvl, data));
    }

    public static void addSocketNamesLine(List<Component> tip, GearItemData gear) {
        if (gear.sockets.sockets.size() > 0) {
            tip.add(new TextComponent("Gemmed").withStyle(ChatFormatting.LIGHT_PURPLE));
        }
    }

    public static void addEmpty(List<Component> tooltip) {
        tooltip.add(CLOC.blank(""));
    }

    public static List<String> compsToStrings(List<Component> list) {
        return list.stream()
                .map(x -> x.getContents())
                .collect(Collectors.toList());
    }

    public static MutableComponent level(int lvl) {
        return new TextComponent(ChatFormatting.YELLOW + "").append(Words.Level.locName())
                .append((": " + lvl))
                .withStyle(ChatFormatting.YELLOW);

    }

    public static List<Component> cutIfTooLong(MutableComponent comp) {
        List<String> stringList = cutIfTooLong(CLOC.translate(comp));
        return stringList.stream()
                .map(x -> new SText(x))
                .collect(Collectors.toList());

    }

    public static List<MutableComponent> cutIfTooLong(MutableComponent comp, ChatFormatting format) {
        List<String> stringList = cutIfTooLong(CLOC.translate(comp));
        return stringList.stream()
                .map(x -> new SText(x).withStyle(format))
                .collect(Collectors.toList());

    }

    // private static final Pattern PATTERN = Pattern.compile("(?)§[0-9A-FK-OR]");

    static Character CHAR = "§".charAt(0); // TODO WTF INTELIJ

    public static List<String> cutIfTooLong(String str) {

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
            } else if (i - start > 28 && c == ' ') {
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
                MutableComponent comp = new TextComponent(X + " ").append(Words.Broken.locName());
                return comp;
            }

        }

        return null;
    }

    public static List<Component> removeDoubleBlankLines(List<Component> list) {
        return removeDoubleBlankLines(list, 5000);
    }

    public static List<Component> removeDoubleBlankLines(List<Component> list, int minLinesCutAllBlanks) {

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

        return new TextComponent(rarity.textFormatting() + "")
                .append(rarity.locName())
                .withStyle(rarity.textFormatting());
    }

    public static MutableComponent rarityShort(Rarity rarity) {
        return (new TextComponent(rarity.textFormatting() + "").append(rarity.locName()));
    }

    public static MutableComponent tier(int tier) {
        return Words.Tier.locName()
                .append(": " + tier);

    }

    public static MutableComponent gearSlot(GearSlot slot) {
        return new TextComponent("Item Type: ").withStyle(ChatFormatting.WHITE)
                .append(slot.locName()
                        .withStyle(ChatFormatting.AQUA));
    }

    public static MutableComponent gearTier(int tier) {
        return new TextComponent("Item Tier: ").withStyle(ChatFormatting.WHITE)
                .append(new TextComponent(tier + "").withStyle(ChatFormatting.AQUA));
    }

    public static MutableComponent gearRarity(GearRarity rarity) {
        return new TextComponent("Rarity: ").withStyle(ChatFormatting.WHITE)
                .append(rarity.locName()
                        .withStyle(rarity.textFormatting()));
    }

    public static MutableComponent gearLevel(int lvl) {
        return new TextComponent("Level Req: ")
                .withStyle(ChatFormatting.WHITE)
                .append(new TextComponent(lvl + "")
                        .withStyle(ChatFormatting.YELLOW));
    }

    public static String STAR = "\u272B";

 
    public static MutableComponent dragOntoGearToUse() {
        return new TextComponent("[Drag onto gear to use]").withStyle(ChatFormatting.AQUA, ChatFormatting.BOLD);
    }
}
