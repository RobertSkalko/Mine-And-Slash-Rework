package com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots;

import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.RarityStoneItem;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SpellResetPotion extends AutoItem implements IShapedRecipe {

    public SpellResetPotion() {
        super(new Item.Properties()
                .stacksTo(10));
    }

    @Override
    public String GUID() {
        return "potions/reset_spells";
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity player) {

        stack.shrink(1);

        if (player instanceof Player) {
            Player p = (Player) player;
            Load.spells(p)
                    .reset();
            p.addItem(new ItemStack(Items.GLASS_BOTTLE));
        }

        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player player, InteractionHand handIn) {
        ItemStack itemStack = player.getItemInHand(handIn);
        player.startUsingItem(handIn);
        return InteractionResultHolder.success(itemStack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 30;
    }

    @Override
    public ShapedRecipeBuilder getRecipe() {
        return shaped(this)
                .define('t', RarityStoneItem.of(IRarity.COMMON_ID))
                .define('v', Items.IRON_AXE)
                .define('b', Items.GLASS_BOTTLE)
                .define('c', Items.GOLD_INGOT)
                .pattern("cvc")
                .pattern("vtv")
                .pattern("cbc")
                .unlockedBy("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return "Spells Reset Potion";
    }
}
