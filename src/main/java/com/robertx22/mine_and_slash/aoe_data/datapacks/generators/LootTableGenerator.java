package com.robertx22.mine_and_slash.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.robertx22.mine_and_slash.database.data.currency.reworked.ExileCurrency;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.GemItems;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.RuneItems;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class LootTableGenerator {


    public LootTableGenerator() {


    }

    protected Path getBasePath() {
        return FMLPaths.GAMEDIR.get();
    }

    protected Path movePath(Path target) {
        String movedpath = target.toString();
        movedpath = movedpath.replace("run/", "src/generated/resources/");
        movedpath = movedpath.replace("run\\", "src/generated/resources/");
        return Paths.get(movedpath);
    }

    private Path resolve(Path path, String id) {

        return path.resolve(
                "data/" + SlashRef.MODID + "/loot_tables/" + id
                        + ".json");
    }


    static Gson GSON = Deserializers.createLootTableSerializer()
            .setPrettyPrinting()
            .create();

    protected void generateAll(CachedOutput cache) {

        Path path = getBasePath();

        getLootTables().entrySet()
                .forEach(x -> {
                    Path target = movePath(resolve(path, x.getKey()
                            .getPath()));
                    DataProvider.saveStable(cache, GSON.toJsonTree(x.getValue()), target);
                });

    }

    public static ResourceLocation RUNE_SALVAGE_RECIPE = SlashRef.id("runes_salvage_recipe");
    public static ResourceLocation GEM_SALVAGE_RECIPE = SlashRef.id("gems_salvage_recipe");
    public static ResourceLocation CURRENCIES_SALVAGE_RECIPE = SlashRef.id("currencies_salvage_recipe");

    private HashMap<ResourceLocation, LootTable> getLootTables() {
        HashMap<ResourceLocation, LootTable> map = new HashMap<ResourceLocation, LootTable>();

        LootTable.Builder gems = LootTable.lootTable();
        LootPool.Builder gemloot = LootPool.lootPool();
        gemloot.setRolls(UniformGenerator.between(1, 3));
        GemItems.ALL.forEach(x -> {
            gemloot.add(LootItem.lootTableItem(x.get())
                    .setWeight(x.get().weight));
        });
        gems.withPool(gemloot);

        LootTable.Builder runes = LootTable.lootTable();
        LootPool.Builder runeloot = LootPool.lootPool();
        runeloot.setRolls(UniformGenerator.between(1, 3));
        RuneItems.ALL.forEach(x -> {
            runeloot.add(LootItem.lootTableItem(x.get())
                    .setWeight(x.get().weight));
        });
        runes.withPool(runeloot);

        LootTable.Builder currencies = LootTable.lootTable();
        LootPool.Builder curLoot = LootPool.lootPool();
        curLoot.setRolls(UniformGenerator.between(1, 3));

        for (ExileCurrency x : ExileDB.Currency().getList()) {
            curLoot.add(LootItem.lootTableItem(x.getItem()).setWeight(x.Weight()));
        }
        currencies.withPool(curLoot);

        // todo what are these loot tables for?
        map.put(RUNE_SALVAGE_RECIPE, runes.build());
        map.put(GEM_SALVAGE_RECIPE, gems.build());
        map.put(CURRENCIES_SALVAGE_RECIPE, currencies.build());

        return map;

    }

}
