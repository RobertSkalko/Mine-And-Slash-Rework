package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
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

        ALL_PLAYERS_ARE_TEAMED_PVE_MODE = b.define("all_players_are_allied", true);
        GET_STARTER_ITEMS = b.define("start_items", true);
        ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER = b.define("scale_mob_to_nearby_player_lvl", true);
        ENABLE_LOOT_ANNOUNCEMENTS = b.define("loot_announcements", true);
        REQUIRE_TEAM_FOR_TEAM_DUNGEONS = b.define("require_team_for_dungeons", true);
        DONT_SYNC_DATA_OF_AMBIENT_MOBS = b.define("dont_sync_ambient_mob_data", true);
        MAPS_DONT_DROP_IN_MAPS = b.define("maps_dont_drop_in_maps", true);
        LOG_ERRORS = b.define("log_errors", true);
        STOP_ERROR_SPAM = b.define("stop_error_spam", true);
        MIN_LEVEL_MAP_DROPS = b.defineInRange("min_level_map_drops", 10, 0, Integer.MAX_VALUE);
        LEVEL_DISTANCE_PENALTY_LEEWAY = b.defineInRange("LEVEL_DISTANCE_PENALTY_LEEWAY", 2, 0, Integer.MAX_VALUE);
        PERC_OFFHAND_WEP_STAT = b.defineInRange("PERC_OFFHAND_WEP_STAT", 25, 0, 100);


        REGEN_HUNGER_COST = b.defineInRange("regen_hunger_cost", 10D, 0, 1000);
        EXP_LOSS_ON_DEATH = b.defineInRange("death_exp_penalty", 0.1D, 0, 1);
        EXP_DEBT_ON_DEATH = b.defineInRange("death_exp_debt", 0.1D, 0, 1);
        MAX_EXP_DEBT_MULTI = b.defineInRange("max_death_exp_debt_multi", 1F, 0, 100);
        EXP_GAIN_MULTI = b.defineInRange("exp_gain_multi", 1D, 0, 1000);
        PARTY_RADIUS = b.defineInRange("party_radius", 200D, 0, 1000);
        LEVEL_DISTANCE_PENALTY_PER_LVL = b.defineInRange("lvl_distance_penalty_per_level", 0.1D, 0, 1D);
        LEVEL_DISTANCE_PENALTY_MIN_MULTI = b.defineInRange("min_loot_chance", 0.2D, 0, 1);
        EXTRA_MOB_STATS_PER_LEVEL = b.defineInRange("extra_mob_stats_per_lvl", 0.02D, 0, 1000);
        VANILLA_MOB_DMG_AS_EXILE_DMG = b.defineInRange("vanilla_mob_dmg_as_exile_dmg", 1D, 0, 1000);
        PVP_DMG_MULTI = b.defineInRange("pvp_dmg_multi", 1D, 0, 1000);
        MAX_TEAM_DISTANCE = b.defineInRange("max_team_distance", 75D, 0, 100000);
        IN_COMBAT_REGEN_MULTI = b.defineInRange("in_combat_regen_multi", 0.5, 0, 10);
        COMBAT_TO_PROFESSION_RESTED_XP_GENERATION = b.defineInRange("COMBAT_TO_PROFESSION_RESTED_XP_GENERATION", 0.25, 0, 1);
        PROFESSION_TO_COMBAT_RESTED_XP_GENERATION = b.defineInRange("PROFESSION_TO_COMBAT_RESTED_XP_GENERATION", 0.1, 0, 1);
        RESTED_XP_DEATH_PENALTY = b.defineInRange("RESTED_XP_DEATH_PENALTY", 0.5F, 0, 1);

        FAVOR_DEATH_LOSS = b.defineInRange("favor_death_loss", 50D, 0, 10000);
        FAVOR_CHEST_GAIN = b.defineInRange("favor_chest_gain", 1D, 0, 10000);
        FAVOR_GAIN_PER_SECOND = b.defineInRange("favor_per_second", 1D / 60D, 0, 10000);

        GEAR_DROPRATE = b.defineInRange("gear_drop_rate", 7D, 0, 1000);
        SOUl_DROPRATE = b.defineInRange("soul_drop_rate", 0.3D, 0, 1000);
        MAP_DROPRATE = b.defineInRange("map_drop_rate", 0.5D, 0, 1000);
        GEM_DROPRATE = b.defineInRange("gem_drop_rate", 1D, 0, 1000);
        SKILL_GEM_DROPRATE = b.defineInRange("skill_gem_drop_rate", 3D, 0, 1000);
        SUPP_GEM_DROPRATE = b.defineInRange("support_gem_drop_rate", 2D, 0, 1000);
        AURA_GEM_DROPRATE = b.defineInRange("aura_gem_drop_rate", 1D, 0, 1000);
        RUNE_DROPRATE = b.defineInRange("rune_drop_rate", 0.5D, 0, 1000);
        CURRENCY_DROPRATE = b.defineInRange("currency_drop_rate", 0.5D, 0, 1000);
        JEWEL_DROPRATE = b.defineInRange("jewel_drop_rate", 0.25D, 0, 1000);
        LOOT_CHEST_DROPRATE = b.defineInRange("loot_chest_drop_rate", 0.1D, 0, 1000);

        MAP_MECHANIC_CHANCE = b.defineInRange("MAP_MECHANIC_CHANCE", 33D, 0, 100D);


        BLOCK_COST = b.defineInRange("block_cost", 0.25D, 0, 1000);

        PACK_MOB_MIN = b.defineInRange("pack_mob_min", 3, 0, 20);
        PACK_MOB_MAX = b.defineInRange("pack_mob_max", 6, 0, 20);

        MIN_MAP_ROOMS = b.defineInRange("MIN_MAP_ROOMS", 12, 1, 100);
        MAX_MAP_ROOMS = b.defineInRange("MAX_MAP_ROOMS", 20, 1, 100);


        MOB_MIN = b.defineInRange("mob_min", 1, 0, 20);
        MOB_MAX = b.defineInRange("mob_max", 2, 0, 20);
        MAX_ADDITIONAL_TALENT_POINTS = b.defineInRange("MAX_ADDITIONAL_TALENT_POINTS", 30, 0, 10000);
        MAX_CHARACTERS = b.defineInRange("MAX_CHARACTERS", 7, 1, 7);

        UNARMED_ENERGY_COST = b.defineInRange("UNARMED_ENERGY_COST", 5D, 0D, 100D);

        List<String> list = new ArrayList<>();

        list.add("minecraft:iron_sword:sword");

        GEAR_COMPATS = b.comment("This is for modded gear that can't be automatically recognized by the mod." +
                        " If there's say a weapon like a staff in another mod, but this mod doesn't recognize it. " +
                        "Put it here. The usage is: modid:path:gear_slot_id. Example: minecraft:diamond_sword:sword")
                .defineList("gear_compatibility", list, x -> {
                    String str = (String) x;
                    return str.split(":").length == 3;
                });


        List<String> items = new ArrayList<>();
        items.add(VanillaUTIL.REGISTRY.items().getKey(Items.ENDER_PEARL).toString());
        items.add(VanillaUTIL.REGISTRY.items().getKey(Items.CHORUS_FRUIT).toString());
        BANNED_ITEMS = b.comment("Stops items from being used in maps/adventuremaps. This is used for items that allow cheesing mechanics like teleporation items mostly.")
                .defineList("disabled_items_in_maps", items, x -> {
                    String str = (String) x;
                    return true;
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
                            Item item = ForgeRegistries.ITEMS.getValue(id);

                            if (item != Items.AIR && item != null) {
                                if (!ExileDB.GearSlots().isRegistered(array[2])) {
                                    cachedCompatMap.put(item, null); // if invalid, make the item have no gear type, this was requested as a way to blacklist
                                } else {
                                    GearSlot slot = ExileDB.GearSlots().get(array[2]);
                                    if (slot != null && !slot.GUID().isEmpty()) {
                                        cachedCompatMap.put(item, slot);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

        }

        return cachedCompatMap;

    }

    public boolean isItemBanned(Item item) {
        String id = VanillaUTIL.REGISTRY.items().getKey(item).toString();
        return BANNED_ITEMS.get().stream().anyMatch(x -> x.equals(id));
    }

    public ForgeConfigSpec.ConfigValue<List<? extends String>> GEAR_COMPATS;
    public ForgeConfigSpec.ConfigValue<List<? extends String>> BANNED_ITEMS;

    public ForgeConfigSpec.BooleanValue ALL_PLAYERS_ARE_TEAMED_PVE_MODE;
    public ForgeConfigSpec.BooleanValue GET_STARTER_ITEMS;
    public ForgeConfigSpec.BooleanValue ALWAYS_SCALE_MOB_LEVEL_TO_PLAYER;
    public ForgeConfigSpec.BooleanValue ENABLE_LOOT_ANNOUNCEMENTS;
    public ForgeConfigSpec.BooleanValue REQUIRE_TEAM_FOR_TEAM_DUNGEONS;
    public ForgeConfigSpec.BooleanValue DONT_SYNC_DATA_OF_AMBIENT_MOBS;
    public ForgeConfigSpec.BooleanValue MAPS_DONT_DROP_IN_MAPS;
    public ForgeConfigSpec.BooleanValue LOG_ERRORS;
    public ForgeConfigSpec.BooleanValue STOP_ERROR_SPAM;
    public ForgeConfigSpec.IntValue MIN_LEVEL_MAP_DROPS;

    public ForgeConfigSpec.IntValue LEVEL_DISTANCE_PENALTY_LEEWAY;
    public ForgeConfigSpec.IntValue PERC_OFFHAND_WEP_STAT;


    public ForgeConfigSpec.DoubleValue REGEN_HUNGER_COST;
    public ForgeConfigSpec.DoubleValue EXP_LOSS_ON_DEATH;
    public ForgeConfigSpec.DoubleValue EXP_DEBT_ON_DEATH;
    public ForgeConfigSpec.DoubleValue MAX_EXP_DEBT_MULTI;
    public ForgeConfigSpec.DoubleValue EXP_GAIN_MULTI;
    public ForgeConfigSpec.DoubleValue PARTY_RADIUS;
    public ForgeConfigSpec.DoubleValue LEVEL_DISTANCE_PENALTY_PER_LVL;
    public ForgeConfigSpec.DoubleValue LEVEL_DISTANCE_PENALTY_MIN_MULTI;
    public ForgeConfigSpec.DoubleValue EXTRA_MOB_STATS_PER_LEVEL;
    public ForgeConfigSpec.DoubleValue VANILLA_MOB_DMG_AS_EXILE_DMG;
    public ForgeConfigSpec.DoubleValue PVP_DMG_MULTI;
    public ForgeConfigSpec.DoubleValue MAX_TEAM_DISTANCE;
    public ForgeConfigSpec.DoubleValue IN_COMBAT_REGEN_MULTI;
    public ForgeConfigSpec.DoubleValue COMBAT_TO_PROFESSION_RESTED_XP_GENERATION;
    public ForgeConfigSpec.DoubleValue PROFESSION_TO_COMBAT_RESTED_XP_GENERATION;
    public ForgeConfigSpec.DoubleValue RESTED_XP_DEATH_PENALTY;

    public ForgeConfigSpec.DoubleValue UNARMED_ENERGY_COST;

    public ForgeConfigSpec.DoubleValue FAVOR_DEATH_LOSS;
    public ForgeConfigSpec.DoubleValue FAVOR_CHEST_GAIN;
    public ForgeConfigSpec.DoubleValue FAVOR_GAIN_PER_SECOND;

    public ForgeConfigSpec.DoubleValue GEAR_DROPRATE;
    public ForgeConfigSpec.DoubleValue SOUl_DROPRATE;
    public ForgeConfigSpec.DoubleValue MAP_DROPRATE;
    public ForgeConfigSpec.DoubleValue GEM_DROPRATE;
    public ForgeConfigSpec.DoubleValue SKILL_GEM_DROPRATE;
    public ForgeConfigSpec.DoubleValue LOOT_CHEST_DROPRATE;
    public ForgeConfigSpec.DoubleValue SUPP_GEM_DROPRATE;
    public ForgeConfigSpec.DoubleValue AURA_GEM_DROPRATE;
    public ForgeConfigSpec.DoubleValue RUNE_DROPRATE;
    public ForgeConfigSpec.DoubleValue CURRENCY_DROPRATE;
    public ForgeConfigSpec.DoubleValue JEWEL_DROPRATE;
    public ForgeConfigSpec.DoubleValue MAP_MECHANIC_CHANCE;

    public ForgeConfigSpec.DoubleValue BLOCK_COST;

    public ForgeConfigSpec.IntValue PACK_MOB_MIN;
    public ForgeConfigSpec.IntValue PACK_MOB_MAX;

    public ForgeConfigSpec.IntValue MIN_MAP_ROOMS;
    public ForgeConfigSpec.IntValue MAX_MAP_ROOMS;

    public ForgeConfigSpec.IntValue MOB_MIN;
    public ForgeConfigSpec.IntValue MOB_MAX;
    public ForgeConfigSpec.IntValue MAX_ADDITIONAL_TALENT_POINTS;
    public ForgeConfigSpec.IntValue MAX_CHARACTERS;

}
