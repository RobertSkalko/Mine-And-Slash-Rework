package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_methods.AddSpawnerExtraLootMethod;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LootTable.class)
public class LootTableMixin {

    @Inject(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;", at = @At(value = "RETURN"))
    public void hookLoot(LootContext context, CallbackInfoReturnable<List<ItemStack>> ci) {
       
        try {
            LootTable lootTable = (LootTable) (Object) this;

            AddSpawnerExtraLootMethod.hookLoot(context, ci);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
