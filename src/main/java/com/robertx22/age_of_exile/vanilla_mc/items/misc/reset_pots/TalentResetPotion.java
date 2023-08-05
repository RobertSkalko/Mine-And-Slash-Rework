package com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots;

import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.AutoItem;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class TalentResetPotion extends AutoItem implements IShapedRecipe {

    public TalentResetPotion() {
        super(new Properties().tab(CreativeTabs.MyModTab)
                .stacksTo(10));
    }

    @Override
    public String GUID() {
        return "potions/reset_all_perks";
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity player) {

        stack.shrink(1);

        if (player instanceof Player) {
            Player p = (Player) player;
            Load.playerRPGData(p).talents
                    .clearAllTalents();
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
                .define('t', SlashItems.T0_DUST())
                .define('v', Items.GOLD_INGOT)
                .define('b', Items.GLASS_BOTTLE)
                .define('c', Items.DIAMOND)
                .pattern("cvc")
                .pattern("vtv")
                .pattern("cbc")
                .unlockedBy("player_level", trigger());
    }

    @Override
    public String locNameForLangFile() {
        return "Talent Reset Potion";
    }
}
