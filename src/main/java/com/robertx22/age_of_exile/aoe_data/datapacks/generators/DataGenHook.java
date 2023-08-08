package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public class DataGenHook implements DataProvider {
    PackOutput pack;

    public DataGenHook(PackOutput pack) {
        this.pack = pack;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {

        new LootTableGenerator().generateAll(pOutput);
        new RecipeGenerator().generateAll(pOutput);

        //DataProvider.saveStable(pOutput, x.serializeRecipe(), target);

        return CompletableFuture.completedFuture(null); // todo this is bad, but would it work?
    }

    @Override
    public String getName() {
        return "hookdata";
    }
}
