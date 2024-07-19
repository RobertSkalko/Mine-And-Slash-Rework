package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.OperationTipBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class TagForceSoulItem extends AutoItem implements IShapelessRecipe {

    public AvailableTags tag;
    Supplier<Item> item;

    public TagForceSoulItem(Supplier<Item> item, AvailableTags tag) {
        super(new Properties());
        this.tag = tag;
        this.item = item;
    }

    @Override
    public String locNameForLangFile() {
        return tag.tagName + " Soul Modifier";
    }

    @Override
    public String GUID() {
        return null;
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        Item middle = item.get();


        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this, 3)
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(Items.IRON_INGOT, 1)
                .requires(middle, 1);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {

        pTooltipComponents.addAll(
                new ExileTooltips()
                        .accept(new UsageBlock(Collections.singletonList(Itemtips.SOUL_MODIFIER_TIP.locName().withStyle(ChatFormatting.AQUA))))
                        .accept(new OperationTipBlock().addDraggableTipAbove(OperationTipBlock.AvailableTarget.GEAR_SOUL)).release());
    }

    public enum AvailableTags{
        PLATE(SlotTags.armor_stat.GUID(), "Plate", Words.GEAR_LOCKED_TYPE_PLATE.locName()),
        LEATHER(SlotTags.dodge_stat.GUID(), "Leather", Words.GEAR_LOCKED_TYPE_LEATHER.locName()),
        CLOTH(SlotTags.magic_shield_stat.GUID(), "Cloth", Words.GEAR_LOCKED_TYPE_CLOTH.locName());

        public final String tag;
        public final String tagName;
        public final MutableComponent translation;

        AvailableTags(String tag, String tagName, MutableComponent translation) {
            this.tag = tag;
            this.tagName = tagName;
            this.translation = translation;
        }
    }
}
