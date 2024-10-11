package com.robertx22.mine_and_slash.gui.texts.textblocks;

import com.robertx22.mine_and_slash.database.data.gear_slots.GearSlot;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.gui.texts.ExileTooltips;
import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.localization.Gui;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;


// todo rework currencies to have localized requirements on tooltip so like: works on gear: - must be lvl 5 - must be common rarity .. etc

public class WorksOnBlock extends AbstractTextBlock {


    RarityListData rarities = null;
    MutableComponent name;
    List<ItemType> items = new ArrayList<>();

    public static WorksOnBlock usableOn(ItemType type) {
        return new WorksOnBlock(Type.USABLE_ON).itemTypes(Arrays.asList(type));
    }

    public static WorksOnBlock usableOn(List<ItemType> type) {
        return new WorksOnBlock(Type.USABLE_ON).itemTypes(type);
    }

    public static WorksOnBlock possibleDrops(List<GearRarity> rar) {
        return new WorksOnBlock(Type.POSSIBLE_GEAR_DROPS).rarities(rar);
    }

    private WorksOnBlock(Type name) {
        this.name = name.name.locName();
    }


    public enum Type {
        USABLE_ON(Words.USABLE_ON),
        POSSIBLE_GEAR_DROPS(Words.POSSIBLE_DROPS),
        ;

        Type(Words name) {
            this.name = name;
        }

        public Words name;
    }

    public WorksOnBlock rarities(List<GearRarity> rar) {
        this.rarities = new RarityListData(rar);
        return this;
    }

    private WorksOnBlock itemTypes(List<ItemType> item) {
        items.addAll(item);
        return this;
    }

    List<MutableComponent> itemTypesTooltip() {
        List<MutableComponent> all = new ArrayList<>();

        var types = Component.literal(UNICODE.STAR + " ").append(Words.ITEM_TYPES.locName()).withStyle(ChatFormatting.GREEN);

        if (Screen.hasShiftDown()) {
            all.add(types);

            for (ItemType type : items) {
                all.add(Component.literal(UNICODE.ROTATED_CUBE + " ").append(type.name.locName()).withStyle(ChatFormatting.YELLOW));
                var desc = Component.literal("[").append(type.desc.locName().withStyle(ChatFormatting.BLUE)).append("]");
                all.add(desc);
            }
        } else {
            MutableComponent c = Component.literal(" " + UNICODE.ROTATED_CUBE + " ").append(TooltipUtils.joinMutableComps(items.stream().map(x -> x.name.locName()).iterator(), Gui.COMMA_SEPARATOR.locName())).withStyle(ChatFormatting.YELLOW);
            //all.add(c);

            all.add(types.append(c));

        }


        return all;
    }


    @Override
    public List<? extends Component> getAvailableComponents() {
        List<MutableComponent> all = new ArrayList<>();

        all.add(name.withStyle(ChatFormatting.AQUA));

        if (!items.isEmpty()) {
            for (MutableComponent m : itemTypesTooltip()) {
                all.add(Component.literal(" ").append(m));
            }
        }
        if (this.rarities != null) {
            for (MutableComponent m : rarities.getTooltip()) {
                all.add(Component.literal(" ").append(m));
            }
        }

        // todo maybe some items won't be drag and droppable?
        if (true) {
            all.add(Itemtips.DRAG_AND_DROP_TO_USE.locName().withStyle(ChatFormatting.BLUE));

            if (Screen.hasShiftDown()) {
                all.add(Itemtips.DRAG_AND_DROP_TO_USE_DESC.locName().withStyle(ChatFormatting.AQUA));
            }
            if (ClientOnly.getPlayer().isCreative()) {
                all.add(Words.DRAG_NO_WORK_CREATIVE.locName().withStyle(ChatFormatting.RED, ChatFormatting.BOLD));
            }
        }
        return all;
    }

    @Override
    public ExileTooltips.BlockCategories getCategory() {
        return ExileTooltips.BlockCategories.OPERATION;
    }


    public enum ItemType {
        GEAR(Words.Gear, Words.Gear_DESC, x -> StackSaving.GEARS.has(x)),
        TOOL(Words.TOOL, Words.TOOL_DESC, x -> StackSaving.TOOL.has(x)),
        JEWEL(Words.Jewel, Words.Jewel_DESC, x -> StackSaving.JEWEL.has(x)),
        MAP(Words.Map, Words.MapDESC, x -> StackSaving.MAP.has(x)),
        SOUL(Words.Soul, Words.Gear_DESC, x -> StackSaving.STAT_SOULS.has(x)),
        SOULLESS_GEAR(Words.SOULLESS_Gear, Words.SOULLESS_Gear_DESC, x -> !StackSaving.GEARS.has(x) && GearSlot.getSlotOf(x.getItem()) != null);

        public Words name;
        public Words desc;

        public Function<ItemStack, Boolean> worksOn;

        ItemType(Words name, Words desc, Function<ItemStack, Boolean> worksOn) {
            this.name = name;
            this.desc = desc;
            this.worksOn = worksOn;
        }
    }
}
