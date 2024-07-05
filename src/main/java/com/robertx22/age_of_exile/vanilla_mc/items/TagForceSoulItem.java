package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.gui.texts.ExileTooltips;
import com.robertx22.age_of_exile.gui.texts.textblocks.OperationTipBlock;
import com.robertx22.age_of_exile.gui.texts.textblocks.usableitemblocks.UsageBlock;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class TagForceSoulItem extends AutoItem implements IShapelessRecipe {
    public String tag;
    Supplier<Item> item;
    String tagname;

    public TagForceSoulItem(Supplier<Item> si, String tag, String tagname) {
        super(new Properties());
        this.tag = tag;
        this.tagname = tagname;
        this.item = si;
    }

    @Override
    public String locNameForLangFile() {
        return tagname + " Soul Modifier";
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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.addAll(Objects.requireNonNull(
                new ExileTooltips()
                        .accept(new UsageBlock(Collections.singletonList(Itemtips.SOUL_MODIFIER_TIP.locName().withStyle(ChatFormatting.AQUA))))
                        .accept(new OperationTipBlock().addDraggableTipAbove(OperationTipBlock.AvailableTarget.GEAR_SOUL))).release());
    }
}
