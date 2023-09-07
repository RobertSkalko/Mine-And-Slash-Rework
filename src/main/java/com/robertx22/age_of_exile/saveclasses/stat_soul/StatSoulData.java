package com.robertx22.age_of_exile.saveclasses.stat_soul;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gear_types.bases.SlotFamily;
import com.robertx22.age_of_exile.database.data.rarities.GearRarity;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.inv_gui.actions.auto_salvage.ToggleAutoSalvageRarity;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipContext;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ICommonDataItem;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.ISettableLevelTier;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.uncommon.utilityclasses.LevelUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StatSoulData implements ICommonDataItem<GearRarity>, ISettableLevelTier {


    public int tier = 1;

    public String slot = "";


    public String rar = "";

    public SlotFamily fam = SlotFamily.NONE;

    public String uniq = "";


    public boolean can_sal = true;


    public GearItemData gear = null;


    // todo how do i make the result accept nbt.
    // and how to make jei accept nbt

    // i COULD make each of these an item ? and have profession set the correct tier?

    public static StatSoulData ofFamily(GearRarity rar, SkillItemTier tier, SlotFamily fam) {
        StatSoulData data = new StatSoulData();
        data.tier = tier.tier;
        data.fam = fam;
        data.rar = rar.GUID();
        return data;
    }

    public boolean canBeOnAnySlot() {
        return slot.isEmpty();
    }

    public void setCanBeOnAnySlot() {
        this.slot = "";
    }

    public ItemStack toStack() {


        ItemStack stack = new ItemStack(SlashItems.STAT_SOUL.get());

        StackSaving.STAT_SOULS.saveTo(stack, this);

        if (!slot.isEmpty()) {
            stack.getOrCreateTag()
                    .putInt("CustomModelData", ExileDB.GearSlots()
                            .get(slot).model_num);
        }

        return stack;

    }

    public void insertAsUnidentifiedOn(ItemStack stack) {
        if (gear != null) {
            StackSaving.GEARS.saveTo(stack, gear);
        } else {
            StackSaving.GEARS.saveTo(stack, this.createGearData(stack));
            //LoadSave.Save(this, stack.getOrCreateTag(), StatSoulItem.TAG);
        }
    }

    public GearSlot getSlotFor(ItemStack stack) {
        GearSlot gearslot = ExileDB.GearSlots().random();

        if (!slot.isEmpty()) {
            gearslot = ExileDB.GearSlots().get(slot);
        }
        if (stack != null) {
            gearslot = GearSlot.getSlotOf(stack.getItem());
        }
        return gearslot;
    }

    public boolean canApplyTo(ItemStack stack) {
        GearSlot slot = GearSlot.getSlotOf(stack.getItem());

        if (canBeOnAnySlot()) {
            return slot != null;
        }
        if (fam != SlotFamily.NONE) {
            if (slot.fam == fam) {
                return true;
            }
        }
        if (!this.slot.isEmpty()) {
            return this.slot.equals(slot.GUID());
        }

        return false;
    }


    public GearItemData createGearData(@Nullable ItemStack stack) {

        int lvl = LevelUtils.tierToLevel(tier).getMinLevel();

        GearBlueprint b = new GearBlueprint(LootInfo.ofLevel(lvl));
        b.level.set(lvl);
        b.rarity.set(ExileDB.GearRarities()
                .get(rar));


        UniqueGear uniq = ExileDB.UniqueGears()
                .get(this.uniq);

        if (uniq != null) {
            b.uniquePart.set(uniq);
            b.rarity.set(uniq.getUniqueRarity());
        }

        GearSlot gearslot = getSlotFor(stack);
        String slotid = gearslot.GUID();

        b.gearItemSlot.set(ExileDB.GearTypes()
                .getFilterWrapped(x -> x.gear_slot.equals(slotid))
                .random());


        GearItemData gear = b.createData();

        gear.data.set(GearItemData.KEYS.SALVAGING_DISABLED, !this.can_sal);
        return gear;
    }

    public boolean canInsertIntoStack(ItemStack stack) {

        if (stack.isEmpty()) {
            return false;
        }

        if (StackSaving.GEARS.has(stack)) {
            return false;
        }

        if (this.gear != null) {
            return GearSlot.isItemOfThisSlot(gear.GetBaseGearType().getGearSlot(), stack.getItem());
        }

        return canApplyTo(stack);

    }

    @Override
    public String getRarityId() {
        return rar;
    }

    @Override
    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }


    @Override
    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public void BuildTooltip(TooltipContext ctx) {

    }

    @Override
    public int getLevel() {
        return LevelUtils.levelToTier(tier);
    }

    @Override
    public ItemstackDataSaver<? extends ICommonDataItem> getStackSaver() {
        return StackSaving.STAT_SOULS;
    }

    public List<Component> getTooltip(ItemStack stack) {

        List<Component> tooltip = new ArrayList<>();

        var data = this;

        if (data.gear != null) {
            data.gear.BuildTooltip(new TooltipContext(stack, tooltip, Load.Unit(ClientOnly.getPlayer())));
        } else {
            tooltip.add(TooltipUtils.gearTier(data.tier));
            if (data.canBeOnAnySlot()) {

            } else {
                if (data.fam != SlotFamily.NONE) {
                    tooltip.add(Component.literal("Item Type: ").withStyle(ChatFormatting.WHITE).append(data.fam.name()).withStyle(ChatFormatting.BLUE));
                } else {
                    tooltip.add(Component.literal("Item Type: ").withStyle(ChatFormatting.WHITE)
                            .append(ExileDB.GearSlots()
                                    .get(data.slot)
                                    .locName()
                                    .withStyle(ChatFormatting.BLUE)));
                }
            }
            tooltip.add(TooltipUtils.gearRarity(ExileDB.GearRarities()
                    .get(data.rar)));

        }
        tooltip.add(Component.literal(""));

        tooltip.add(Component.literal("Infuses stats into empty gear").withStyle(ChatFormatting.AQUA));
        tooltip.add(TooltipUtils.dragOntoGearToUse());
        tooltip.add(Component.literal("You can also right click to generate an item.").withStyle(ChatFormatting.AQUA));

        return tooltip;
    }

    @Override
    public void saveToStack(ItemStack stack) {
        StackSaving.STAT_SOULS.saveTo(stack, this);
    }

    @Override
    public List<ItemStack> getSalvageResult(ItemStack stack) {
        int amount = 1;
        return Arrays.asList(new ItemStack(RarityItems.RARITY_STONE.get(getRarity().GUID()).get(), amount));
    }


    @Override
    public ToggleAutoSalvageRarity.SalvageType getSalvageType() {
        return ToggleAutoSalvageRarity.SalvageType.GEAR;
    }
}
