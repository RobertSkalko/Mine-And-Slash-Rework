package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ServerContainer {

    public static final ForgeConfigSpec spec;
    public static final ServerContainer CONTAINER;

    public static ServerContainer get() {
        return CONTAINER;
    }

    static {
        final Pair<ServerContainer, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ServerContainer::new);
        spec = specPair.getRight();
        CONTAINER = specPair.getLeft();
    }

    ServerContainer(ForgeConfigSpec.Builder b) {
        b.comment("General Configs")
                .push("general");

        ALL_PLAYERS_ARE_TEAMED_PVE_MODE = b.define("all_players_are_allied", false);
        TURN_REQ_ILVL_INTO_RECCOMENDATION = b.define("TURN_REQ_ILVL_INTO_RECCOMENDATION", false);
        GET_STARTER_ITEMS = b.define("start_items", true);
        ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER = b.define("scale_mob_to_nearby_player_lvl", false);
        ENABLE_LOOT_ANNOUNCEMENTS = b.define("loot_announcements", true);
        REQUIRE_TEAM_FOR_TEAM_DUNGEONS = b.define("require_team_for_dungeons", true);
        DONT_SYNC_DATA_OF_AMBIENT_MOBS = b.define("dont_sync_ambient_mob_data", true);


        HP_VALUE_NEEDED_FOR_MAX_ENVIRO_DMG_PROTECTION = b.defineInRange("HP_VALUE_NEEDED_FOR_MAX_ENVIRO_DMG_PROTECTION", 125, 0, 100000);
        MAX_ENVIRO_DMG_PROTECTION_FROM_HP_MULTI = b.defineInRange("MAX_ENVIRO_DMG_PROTECTION_FROM_HP_MULTI", 0.75F, 0, 1);


        REGEN_HUNGER_COST = b.defineInRange("regen_hunger_cost", 10D, 0, 1000);
        EXP_LOSS_ON_DEATH = b.defineInRange("death_exp_penalty", 0.1D, 0, 1);
        EXP_GAIN_MULTI = b.defineInRange("exp_gain_multi", 1D, 0, 1000);
        PARTY_RADIUS = b.defineInRange("party_radius", 200D, 0, 1000);
        LEVEL_DISTANCE_PENALTY_PER_LVL = b.defineInRange("lvl_distance_penalty_per_level", 0.05D, 0, 1D);
        LEVEL_DISTANCE_PENALTY_MIN_MULTI = b.defineInRange("min_loot_chance", 0.25D, 0, 1);
        EXTRA_MOB_STATS_PER_LEVEL = b.defineInRange("extra_mob_stats_per_lvl", 0.02D, 0, 1000);
        VANILLA_MOB_DMG_AS_EXILE_DMG = b.defineInRange("vanilla_mob_dmg_as_exile_dmg", 1D, 0, 1000);
        VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL = b.defineInRange("vanilla_mob_dmg_as_exile_dmg_at_max_lvl", 1D, 0, 1000);
        PLAYER_VANILLA_DMG_MULTI = b.defineInRange("PLAYER_VANILLA_DMG_MULTI", 0D, 0, 1000);
        PVP_DMG_MULTI = b.defineInRange("pvp_dmg_multi", 1D, 0, 1000);

        GEAR_DROPRATE = b.defineInRange("gear_drop_rate", 7D, 0, 1000);
        GEM_DROPRATE = b.defineInRange("gem_drop_rate", 0.5D, 0, 1000);
        RUNE_DROPRATE = b.defineInRange("rune_drop_rate", 0.05D, 0, 1000);
        CURRENCY_DROPRATE = b.defineInRange("currency_drop_rate", 0.2D, 0, 1000);

        DIFFICULTY_PER_SECOND = b.defineInRange("DIFFICULTY_PER_SECOND", 0.0005D, -1000, 1000);
        DIFFICULTY_PER_HOSTILE_KILL = b.defineInRange("DIFFICULTY_PER_HOSTILE_KILL", 0.005D, -1000, 1000);
        DIFFICULTY_PER_DEATH = b.defineInRange("DIFFICULTY_PER_DEATH", -1D, -1000, 1000);
        DIFFICULTY_PER_INCREASE_ITEM = b.defineInRange("DIFFICULTY_PER_INCREASE_ITEM", 10D, -1000, 1000);
        DIFFICULTY_PER_DECREASE_ITEM = b.defineInRange("DIFFICULTY_PER_DECREASE_ITEM", 10D, -1000, 1000);
        MAX_DIFFICULTY = b.defineInRange("MAX_DIFFICULTY", 100D, -1000, 1000);
        MAX_MOB_LVL_HIGHER_THAN_PLAYER_FOR_DIFF = b.defineInRange("MAX_MOB_LVL_HIGHER_THAN_PLAYER_FOR_DIFF", 0, -1000, 1000);
        DEATH_DIFFICULTY_COOLDOWN = b.defineInRange("DEATH_DIFFICULTY_COOLDOWN", 20 * 60 * 60D, 0, 100000);
        MOB_HP_MULTI_PER_DIFFICULTY = b.defineInRange("MOB_HP_MULTI_PER_DIFFICULTY", 0.01D, 0, 100000);
        MOB_DMG_MULTI_PER_DIFFICULTY = b.defineInRange("MOB_DMG_MULTI_PER_DIFFICULTY", 0.01D, 0, 100000);
        MOB_LOOT_MULTI_PER_DIFFICULTY = b.defineInRange("MOB_LOOT_MULTI_PER_DIFFICULTY", 0.01D, 0, 100000);

        List<String> list = new ArrayList<>();

        list.add("minecraft:iron_sword:sword");

        GEAR_COMPATS = b.comment("This is for modded gear that can't be automatically recognized by the mod." +
                        " If there's say a weapon like a staff in another mod, but this mod doesn't recognize it. " +
                        "Put it here. The usage is: modid:path:gear_slot_id. Example: minecraft:diamond_sword:sword")
                .defineList("gear_compatibility", list, x -> {
                    String str = (String) x;
                    return str.split(":").length == 3;
                });
        b.pop();
    }

    private static HashMap<Item, GearSlot> cachedCompatMap = new HashMap<>();

    public HashMap<Item, GearSlot> getCompatMap() {
        if (cachedCompatMap.isEmpty()) {

            GEAR_COMPATS.get()
                    .forEach(x -> {
                        try {
                            String[] array = x.split(":");
                            ResourceLocation id = new ResourceLocation(array[0], array[1]);
                            GearSlot slot = ExileDB.GearSlots()
                                    .get(array[2]);
                            Item item = ForgeRegistries.ITEMS.getValue(id);
                            if (item != Items.AIR && item != null) {
                                if (slot != null && !slot.GUID()
                                        .isEmpty()) {
                                    cachedCompatMap.put(item, slot);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

        }

        return cachedCompatMap;

    }

    public ForgeConfigSpec.DoubleValue DIFFICULTY_PER_SECOND;
    public ForgeConfigSpec.DoubleValue DIFFICULTY_PER_HOSTILE_KILL;
    public ForgeConfigSpec.DoubleValue DIFFICULTY_PER_DEATH;
    public ForgeConfigSpec.DoubleValue DEATH_DIFFICULTY_COOLDOWN;
    public ForgeConfigSpec.DoubleValue DIFFICULTY_PER_INCREASE_ITEM;
    public ForgeConfigSpec.DoubleValue DIFFICULTY_PER_DECREASE_ITEM;
    public ForgeConfigSpec.DoubleValue MAX_DIFFICULTY;
    public ForgeConfigSpec.IntValue MAX_MOB_LVL_HIGHER_THAN_PLAYER_FOR_DIFF;

    public ForgeConfigSpec.IntValue HP_VALUE_NEEDED_FOR_MAX_ENVIRO_DMG_PROTECTION;
    public ForgeConfigSpec.DoubleValue MAX_ENVIRO_DMG_PROTECTION_FROM_HP_MULTI;

    public ForgeConfigSpec.DoubleValue MOB_HP_MULTI_PER_DIFFICULTY;
    public ForgeConfigSpec.DoubleValue MOB_DMG_MULTI_PER_DIFFICULTY;
    public ForgeConfigSpec.DoubleValue MOB_LOOT_MULTI_PER_DIFFICULTY;

    // at max difficulty, lvl 1 mobs are lvl 50. And lvl 50 mobs are increased in strength

    public ForgeConfigSpec.ConfigValue<List<? extends String>> GEAR_COMPATS;

    public ForgeConfigSpec.BooleanValue ALL_PLAYERS_ARE_TEAMED_PVE_MODE;
    public ForgeConfigSpec.BooleanValue GET_STARTER_ITEMS;
    public ForgeConfigSpec.BooleanValue ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER;
    public ForgeConfigSpec.BooleanValue ENABLE_LOOT_ANNOUNCEMENTS;
    public ForgeConfigSpec.BooleanValue REQUIRE_TEAM_FOR_TEAM_DUNGEONS;
    public ForgeConfigSpec.BooleanValue DONT_SYNC_DATA_OF_AMBIENT_MOBS;
    public ForgeConfigSpec.BooleanValue TURN_REQ_ILVL_INTO_RECCOMENDATION;


    public ForgeConfigSpec.DoubleValue REGEN_HUNGER_COST;
    public ForgeConfigSpec.DoubleValue EXP_LOSS_ON_DEATH;
    public ForgeConfigSpec.DoubleValue EXP_GAIN_MULTI;
    public ForgeConfigSpec.DoubleValue PARTY_RADIUS;
    public ForgeConfigSpec.DoubleValue LEVEL_DISTANCE_PENALTY_PER_LVL;
    public ForgeConfigSpec.DoubleValue LEVEL_DISTANCE_PENALTY_MIN_MULTI;
    public ForgeConfigSpec.DoubleValue EXTRA_MOB_STATS_PER_LEVEL;
    public ForgeConfigSpec.DoubleValue VANILLA_MOB_DMG_AS_EXILE_DMG;
    public ForgeConfigSpec.DoubleValue VANILLA_MOB_DMG_AS_EXILE_DMG_AT_MAX_LVL;
    public ForgeConfigSpec.DoubleValue PVP_DMG_MULTI;
    public ForgeConfigSpec.DoubleValue PLAYER_VANILLA_DMG_MULTI;

    public ForgeConfigSpec.DoubleValue GEAR_DROPRATE;
    public ForgeConfigSpec.DoubleValue GEM_DROPRATE;
    public ForgeConfigSpec.DoubleValue RUNE_DROPRATE;
    public ForgeConfigSpec.DoubleValue CURRENCY_DROPRATE;

    public List<String> BLACKLIST_SPELLS_IN_DIMENSIONS = Arrays.asList("modid:testdim");

}
