package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class FunnyDeathPotion extends AutoItem {

    static class MySource extends DamageSource {

        protected MySource(String name) {
            super(name);
            this.bypassArmor();
            this.bypassInvul();
        }
    }

    public FunnyDeathPotion() {
        super(new Item.Properties().tab(CreativeTabs.MyModTab)
                .stacksTo(64));
    }

    public static final DamageSource DMG_SOURCE = (new MySource("death_potion"));

    @Override
    public String GUID() {
        return "potions/death_potion";
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity en) {
        if (en instanceof Player) {
            en.hurt(DMG_SOURCE, Float.MAX_VALUE);
            stack.shrink(1);
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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.literal("Are you sure it's a good idea to drink this?"));
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
    public String locNameForLangFile() {
        return "Death Potion";
    }

}

