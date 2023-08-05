package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.CurrencyItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.GemItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.RuneItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.data.HashCache;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.Item;
import net.minecraft.loot.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import net.minecraft.world.level.storage.loot.Deserializers;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.RandomValueBounds;
import net.minecraft.world.level.storage.loot.entries.LootItem;

public class LootTableGenerator {

    protected HashCache cache;

    public LootTableGenerator() {

        try {
            cache = new HashCache(FMLPaths.GAMEDIR.get(), "datagencache");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public void run() {
        generateAll(cache);
    }

    static Gson GSON = Deserializers.createLootTableSerializer()
        .setPrettyPrinting()
        .create();

    protected void generateAll(HashCache cache) {

        Path path = getBasePath();

        getLootTables().entrySet()
            .forEach(x -> {
                Path target = movePath(resolve(path, x.getKey()
                    .getPath()));

                try {
                    DataProvider.save(GSON, cache, GSON.toJsonTree(x.getValue()), target);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

    }

    public static ResourceLocation RUNE_SALVAGE_RECIPE = SlashRef.id("runes_salvage_recipe");
    public static ResourceLocation GEM_SALVAGE_RECIPE = SlashRef.id("gems_salvage_recipe");
    public static ResourceLocation CURRENCIES_SALVAGE_RECIPE = SlashRef.id("currencies_salvage_recipe");

    private HashMap<ResourceLocation, LootTable> getLootTables() {
        HashMap<ResourceLocation, LootTable> map = new HashMap<ResourceLocation, LootTable>();

        LootTable.Builder gems = LootTable.lootTable();
        LootPool.Builder gemloot = LootPool.lootPool();
        gemloot.setRolls(RandomValueBounds.between(1, 3));
        GemItems.ALL.forEach(x -> {
            gemloot.add(LootItem.lootTableItem(x.get())
                .setWeight(x.get().weight));
        });
        gems.withPool(gemloot);

        LootTable.Builder runes = LootTable.lootTable();
        LootPool.Builder runeloot = LootPool.lootPool();
        runeloot.setRolls(RandomValueBounds.between(1, 3));
        RuneItems.ALL.forEach(x -> {
            runeloot.add(LootItem.lootTableItem(x.get())
                .setWeight(x.get().weight));
        });
        runes.withPool(runeloot);

        LootTable.Builder currencies = LootTable.lootTable();
        LootPool.Builder curLoot = LootPool.lootPool();
        curLoot.setRolls(RandomValueBounds.between(1, 3));
        CurrencyItems.getAllCurrenciesFromRegistry()
            .forEach(x -> {
                curLoot.add(LootItem.lootTableItem(x)
                    .setWeight(x
                        .Weight()));
            });
        currencies.withPool(curLoot);

        map.put(RUNE_SALVAGE_RECIPE, runes.build());
        map.put(GEM_SALVAGE_RECIPE, gems.build());
        map.put(CURRENCIES_SALVAGE_RECIPE, currencies.build());

        return map;

    }

    private void addFarming(Block block, Item item, Item seed, int age, HashMap<ResourceLocation, LootTable> map) {

        LootItemCondition.Builder condition = LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
            .setProperties(StatePropertiesPredicate.Builder.properties()
                .hasProperty(CropBlock.AGE, age));

        LootTable.Builder b = LootTable.lootTable();

        LootPool.Builder loot = LootPool.lootPool();
        loot.when(condition);
        loot.setRolls(RandomValueBounds.between(1, 3));
        loot.add(LootItem.lootTableItem(item));
        b.withPool(loot);

        if (seed != null) {
            LootPool.Builder seedpool = LootPool.lootPool();
            seedpool.when(condition);
            seedpool.setRolls(RandomValueBounds.between(1, 2));
            seedpool.add(LootItem.lootTableItem(seed));
            b.withPool(seedpool);
        }

        map.put(block.getLootTable(), b.build());
    }

}
