package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RarityItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.RarityStoneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class BackpackItem extends AutoItem implements IAutoLocName, IAutoModel, IShapelessRecipe {

    public BackpackItem() {
        super(new Properties());

    }


    public int getSlots() {
        return 6 * 9;
    }


    @Override
    public String locNameForLangFile() {
        return ChatFormatting.DARK_PURPLE + "Master Backpack";
    }

    @Override
    public String GUID() {
        return "we";
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player p, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide) {
            Load.backpacks(p).getBackpacks().openBackpack(Backpacks.BackpackType.GEARS, p, getSlots() / 9);
        }
        return InteractionResultHolder.success(p.getItemInHand(pUsedHand));

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {

        list.add(Component.literal(""));
        list.add(Component.literal("Right Click to open"));

    }


    @Override
    public ShapelessRecipeBuilder getRecipe() {
        RarityStoneItem item = RarityItems.RARITY_STONE.get(IRarity.MYTHIC_ID).get();
        Item middle = Items.CHEST;

        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this)
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(item, 8)
                .requires(middle, 1);
    }

}
