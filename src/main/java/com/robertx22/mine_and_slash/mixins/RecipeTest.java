package com.robertx22.mine_and_slash.mixins;

import net.minecraft.stats.RecipeBook;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RecipeBook.class)
public class RecipeTest {

    // todo2, turns out mods like this already exist, try them first. Only do it myself if the mods are lacking


    // todo this seems to work, try in JEI and if JEI works too make this a separate mod
    // NO IDEA how dumb this is, could break things but if not, it might be solve the big nbt problem

    /*

    @Inject(method = "add(Lnet/minecraft/world/item/crafting/Recipe;)V", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisableFire(Recipe<?> pRecipe, CallbackInfo ci) {
        try {
            ci.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "add(Lnet/minecraft/resources/ResourceLocation;)V", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisableFire(ResourceLocation pRecipeId, CallbackInfo ci) {
        try {
            ci.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "addHighlight(Lnet/minecraft/world/item/crafting/Recipe;)V", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisdsableFire(Recipe<?> pRecipe, CallbackInfo ci) {
        try {
            ci.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "addHighlight(Lnet/minecraft/resources/ResourceLocation;)V", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisdsableFire(ResourceLocation pRecipeId, CallbackInfo ci) {
        try {
            ci.cancel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "contains(Lnet/minecraft/world/item/crafting/Recipe;)Z", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisableFire(Recipe<?> pRecipe, CallbackInfoReturnable<Boolean> cir) {
        try {
            cir.setReturnValue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "contains(Lnet/minecraft/resources/ResourceLocation;)Z", cancellable = true, at = @At(value = "HEAD"))
    public void hookDisableFire(ResourceLocation pRecipeId, CallbackInfoReturnable<Boolean> cir) {
        try {
            cir.setReturnValue(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     */
}
