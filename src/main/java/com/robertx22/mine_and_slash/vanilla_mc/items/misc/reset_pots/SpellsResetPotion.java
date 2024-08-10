package com.robertx22.mine_and_slash.vanilla_mc.items.misc.reset_pots;

import com.robertx22.mine_and_slash.database.data.currency.base.IShapedRecipe;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.AutoItem;
import com.robertx22.mine_and_slash.vanilla_mc.items.misc.RarityStoneItem;
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

import java.util.HashMap;

public class SpellsResetPotion extends AutoItem implements IShapedRecipe {

    public SpellsResetPotion() {
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
            Load.player(p).ascClass.reset();
            Load.player(p).spellCastingData.hotbar = new HashMap<>();
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
                .define('t', RarityStoneItem.of(IRarity.UNCOMMON))
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
        return "Class Reset Potion";
    }
}
