package com.robertx22.mine_and_slash.aoe_data.datapacks.generators;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.util.concurrent.CompletableFuture;

public class DataGenHook implements DataProvider {

    public DataGenHook() {

    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {


        new LootTableGenerator().generateAll(pOutput);
        new RecipeGenerator().generateAll(pOutput);


        for (ExileRegistryType type : ExileRegistryType.getAllInRegisterOrder()) {
            type.getDatapackGenerator().run(pOutput);
        }

        //DataProvider.saveStable(pOutput, x.serializeRecipe(), target);

        return CompletableFuture.completedFuture(null); // todo this is bad, but would it work?
    }


    @Override
    public String getName() {
        return "hookdata";
    }
}
