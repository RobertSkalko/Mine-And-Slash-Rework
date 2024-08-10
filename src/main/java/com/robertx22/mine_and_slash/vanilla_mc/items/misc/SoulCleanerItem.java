package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapelessRecipe;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
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

import java.util.List;

import static com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils.splitLongText;

public class SoulCleanerItem extends AutoItem implements IShapelessRecipe {


    public SoulCleanerItem() {
        super(new Item.Properties().stacksTo(64));
    }


    @Override
    public String locNameForLangFile() {
        return "Gear Soul Cleaner";
    }

    @Override
    public String GUID() {
        return "soul_cleaner";
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.addAll(splitLongText(Itemtips.SOUL_CLEANER_USAGE_AND_WARNING.locName().withStyle(ChatFormatting.RED)));
    }

    @Override
    public ShapelessRecipeBuilder getRecipe() {
        Item middle = Items.RAW_IRON;
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this)
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(Items.STONE_BRICKS, 3)
                .requires(middle, 1);
    }
}
