package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RuneItems;
import com.robertx22.age_of_exile.saveclasses.jewel.JewelItemData;
import com.robertx22.age_of_exile.saveclasses.jewel.UniqueJewelData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneItem;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class CraftedUniqueJewelItem extends Item implements IShapelessRecipe {
    public CraftedUniqueJewelItem() {
        super(new Properties().stacksTo(1));

    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);


        if (!pLevel.isClientSide) {

            var data = StackSaving.JEWEL.loadFrom(stack);

            if (data == null) {

                data = new JewelItemData();


                data.rar = IRarity.RUNEWORD_ID;

                data.uniq = new UniqueJewelData();
                data.uniq.id = UniqueJewelData.CRAFTED_UNIQUE_ID;

                data.saveToStack(stack);
            }

        }


        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (!StackSaving.JEWEL.has(pStack)) {
            pTooltipComponents.add(Component.literal("Right click to create"));
        }
    }


    @Override
    public ShapelessRecipeBuilder getRecipe() {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this)
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(rune(RuneItem.RuneType.ANO))
                .requires(rune(RuneItem.RuneType.DOS))
                .requires(rune(RuneItem.RuneType.TOQ))

                .requires(rune(RuneItem.RuneType.CEN))
                .requires(rune(RuneItem.RuneType.ENO))
                .requires(rune(RuneItem.RuneType.ITA))

                .requires(rune(RuneItem.RuneType.XER))
                .requires(rune(RuneItem.RuneType.WIR))
                .requires(rune(RuneItem.RuneType.HAR));
    }

    private Item rune(RuneItem.RuneType type) {
        return RuneItems.MAP.get(type.id).get();
    }
}
