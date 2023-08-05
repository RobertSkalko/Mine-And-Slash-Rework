package com.robertx22.age_of_exile.vanilla_mc.items.misc;

import com.robertx22.age_of_exile.aoe_data.datapacks.models.IAutoModel;
import com.robertx22.age_of_exile.aoe_data.datapacks.models.ItemModelManager;
import com.robertx22.age_of_exile.database.base.CreativeTabs;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import net.minecraft.world.item.Item.Properties;

public class LootTableItem extends Item implements IAutoModel {

    public LootTableItem() {
        super(new Properties().stacksTo(1)
            .tab(CreativeTabs.MyModTab));
    }

    @Override
    public void generateModel(ItemModelManager manager) {
        manager.generated(this);
    }

    public static ItemStack of(ResourceLocation loottable) {

        ItemStack stack = new ItemStack(SlashItems.LOOT_TABLE_ITEM.get());
        stack.setTag(new CompoundTag());
        stack.getTag()
            .putString("loot_table", loottable.toString());
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        if (!world.isClientSide) {
            try {

                ItemStack stack = player.getItemInHand(hand);
                stack.shrink(1);

                ResourceLocation loottableId = new ResourceLocation(stack.getTag()
                    .getString("loot_table"));

                LootContext lootContext = new LootContext.Builder((ServerLevel) world)
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
                    .withParameter(LootContextParams.BLOCK_STATE, Blocks.AIR.defaultBlockState())
                    .create(LootContextParamSets.BLOCK);
                ServerLevel serverWorld = lootContext.getLevel();
                LootTable lootTable = serverWorld.getServer()
                    .getLootTables()
                    .get(loottableId);

                List<ItemStack> drops = lootTable.getRandomItems(lootContext);

                spawnEffects(world, player);

                drops.forEach(x -> player.drop(x, false));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new InteractionResultHolder<ItemStack>(InteractionResult.PASS, player.getItemInHand(hand));
    }

    private void spawnEffects(Level world, LivingEntity en) {
        FireworkRocketEntity firework = new FireworkRocketEntity(world, en.getX(), en.getY(), en.getZ(), ItemStack.EMPTY);
        firework.setPosRaw(en.getX(), en.getY(), en.getZ());
        WorldUtils.spawnEntity(world, firework);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        try {
            //LootTableManager

            tooltip.add(Words.ClickToOpen.locName()
                .withStyle(ChatFormatting.RED));

        } catch (Exception e) {
            // e.printStackTrace();
        }
    }

}



