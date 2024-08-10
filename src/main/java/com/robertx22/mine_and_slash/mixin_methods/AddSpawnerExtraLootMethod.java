package com.robertx22.mine_and_slash.mixin_methods;

import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.LootModifier;
import com.robertx22.mine_and_slash.loot.LootModifierEnum;
import com.robertx22.mine_and_slash.loot.MasterLootGen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class AddSpawnerExtraLootMethod {

    public static void hookLoot(LootContext context, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {
            if (!context.hasParam(LootContextParams.BLOCK_STATE)) {
                return;
            }
            if (!context.hasParam(LootContextParams.TOOL)) {
                return;
            }
            if (!context.hasParam(LootContextParams.ORIGIN)) {
                return;
            }
            if (!context.hasParam(LootContextParams.THIS_ENTITY)) {
                return;
            }
            if (context.getParamOrNull(LootContextParams.BLOCK_STATE)
                    .getBlock() != Blocks.SPAWNER) {
                return;
            }

            Entity en = context.getParamOrNull(LootContextParams.THIS_ENTITY);

            Player player = null;
            if (en instanceof Player) {
                player = (Player) en;
            }
            if (player == null) {
                return;
            }

            ItemStack stack = context.getParamOrNull(LootContextParams.TOOL);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) != 0) {
                return;
            }


            Vec3 p = context.getParamOrNull(LootContextParams.ORIGIN);
            BlockPos pos = new BlockPos((int) p.x, (int) p.y, (int) p.z);

            LootInfo info = LootInfo.ofSpawner(player, context.getLevel(), pos);
            info.lootMods.add(new LootModifier(LootModifierEnum.BREAK_SPAWNER, 2));
            List<ItemStack> list = MasterLootGen.generateLoot(info);

            ci.getReturnValue()
                    .addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
