package com.robertx22.age_of_exile.aoe_data.datapacks.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.currency.base.IShapedRecipe;
import com.robertx22.age_of_exile.database.data.currency.base.IShapelessRecipe;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import joptsimple.internal.Strings;
import net.minecraft.advancements.critereon.*;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.function.Consumer;

public class RecipeGenerator {

    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting()
            .create();

    public RecipeGenerator() {


    }

    protected Path getBasePath() {
        return FMLPaths.GAMEDIR.get();
    }

    protected Path movePath(Path target) {
        String movedpath = target.toString();
        movedpath = movedpath.replace("run", "src/generated/resources");
        return Paths.get(movedpath);
    }

    private Path resolve(Path path, String id) {

        return path.resolve(
                "data/" + SlashRef.MODID + "/recipes/" + id
                        + ".json");
    }


    protected void generateAll(CachedOutput cache) {

        Path path = getBasePath();

        generate(x -> {

            Path target = movePath(resolve(path, x.getId()
                    .getPath()));

            DataProvider.saveStable(cache, x.serializeRecipe(), target);

        });


    }

    private void generate(Consumer<FinishedRecipe> consumer) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof IShapedRecipe) {
                IShapedRecipe ir = (IShapedRecipe) item;
                ShapedRecipeBuilder rec = ir.getRecipe();
                if (rec != null) {
                    rec.save(consumer);
                }

            }
            if (item instanceof IShapelessRecipe) {
                IShapelessRecipe sr = (IShapelessRecipe) item;
                ShapelessRecipeBuilder srec = sr.getRecipe();
                if (srec != null) {
                    try {
                        srec.save(consumer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

        harvest(consumer);

        gearRecipe(consumer, SlashItems.GearItems.NECKLACES, GearSlots.NECKLACE);
        gearRecipe(consumer, SlashItems.GearItems.RINGS, GearSlots.RING);
        gearRecipe(consumer, SlashItems.GearItems.STAFFS, GearSlots.STAFF);


    }

    static void harvest(Consumer<FinishedRecipe> con) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HarvestItems.BLUE_KEY.get())
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(HarvestItems.BLUE_INGOT.get(), 9).save(con);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HarvestItems.GREEN_KEY.get())
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(HarvestItems.GREEN_INGOT.get(), 9).save(con);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, HarvestItems.PURPLE_KEY.get())
                .unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem())
                .requires(HarvestItems.PURPLE_INGOT.get(), 9).save(con);

    }

    public static void gearRecipe(Consumer<FinishedRecipe> cons, HashMap<VanillaMaterial, RegObj<Item>> map, String slot) {

        map.entrySet()
                .forEach(x -> {

                    ShapedRecipeBuilder fac = ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, x.getValue()
                            .get(), 1);

                    String[] pattern = getRecipePattern(ExileDB.GearSlots()
                            .get(slot));

                    String all = Strings.join(pattern, "");

                    if (all.contains("M")) {
                        if (x.getKey().mat.tag != null) {
                            fac.define('M', x.getKey().mat.tag);
                        } else {
                            fac.define('M', x.getKey().mat.item);
                        }
                    }
                    if (all.contains("S")) {
                        fac.define('S', Items.STICK);
                    }
                    if (all.contains("B")) {
                        fac.define('B', Items.STRING);
                    }

                    for (String pat : pattern) {
                        try {
                            fac.pattern(pat);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    fac.unlockedBy("player_level", EnchantedItemTrigger.TriggerInstance.enchantedItem());

                    fac.save(cons);
                });
    }

    public static String[] getRecipePattern(GearSlot type) {

        String id = type.id;

        if (id.equals(GearSlots.SWORD)) {
            return new String[]{
                    " M ",
                    " M ",
                    " S "
            };
        }


        if (id.equals(GearSlots.STAFF)) {
            return new String[]{
                    "  M",
                    "SM ",
                    "SS "
            };
        }

        if (id.equals(GearSlots.BOW)) {
            return new String[]{
                    " MB",
                    "M B",
                    " MB"
            };
        }
        if (id.equals(GearSlots.CROSBOW)) {
            return new String[]{
                    "MSM",
                    "S S",
                    " S "
            };
        }

        if (id.equals(GearSlots.CHEST)) {
            return new String[]{
                    "M M",
                    "MMM",
                    "MMM"
            };
        }
        if (id.equals(GearSlots.BOW)) {
            return new String[]{
                    "M M",
                    "M M"
            };
        }
        if (id.equals(GearSlots.PANTS)) {
            return new String[]{
                    "MMM",
                    "M M",
                    "M M"
            };
        }
        if (id.equals(GearSlots.HELMET)) {
            return new String[]{
                    "MMM",
                    "M M"
            };
        }

        if (id.equals(GearSlots.NECKLACE)) {
            return new String[]{
                    "MMM",
                    "M M",
                    "MMM"
            };
        }
        if (id.equals(GearSlots.RING)) {
            return new String[]{
                    " M ",
                    "M M",
                    " M "
            };
        }

        System.out.print("NO RECIPE FOR TAG ");

        return null;
    }

    static InventoryChangeTrigger.TriggerInstance conditionsFromItem(ItemLike itemConvertible) {
        return conditionsFromItemPredicates(ItemPredicate.Builder.item()
                .of(itemConvertible)
                .build());
    }

    private static InventoryChangeTrigger.TriggerInstance conditionsFromItemPredicates(ItemPredicate... itemPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, itemPredicates);
    }

}
