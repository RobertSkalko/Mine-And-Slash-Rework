package com.robertx22.age_of_exile.vanilla_mc.items;

import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RuneItems;
import com.robertx22.age_of_exile.saveclasses.jewel.CraftedUniqueJewelData;
import com.robertx22.age_of_exile.saveclasses.jewel.JewelItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Itemtips;
import com.robertx22.age_of_exile.vanilla_mc.items.gemrunes.RuneType;
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

                data.uniq = new CraftedUniqueJewelData();
                data.uniq.id = CraftedUniqueJewelData.CRAFTED_UNIQUE_ID;

                data.saveToStack(stack);
            }

        }


        return InteractionResultHolder.pass(pPlayer.getItemInHand(pUsedHand));

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (!StackSaving.JEWEL.has(pStack)) {
            pTooltipComponents.add(Itemtips.UNIQUE_JEWEL_USE.locName());
        }
    }


    @Override
    public ShapelessRecipeBuilder getRecipe() {
        return ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, this)
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(rune(RuneType.ANO))
                .requires(rune(RuneType.DOS))
                .requires(rune(RuneType.TOQ))

                .requires(rune(RuneType.CEN))
                .requires(rune(RuneType.ENO))
                .requires(rune(RuneType.ITA))

                .requires(rune(RuneType.XER))
                .requires(rune(RuneType.WIR))
                .requires(rune(RuneType.HAR));
    }

    private Item rune(RuneType type) {
        return RuneItems.MAP.get(type.id).get();
    }
}
