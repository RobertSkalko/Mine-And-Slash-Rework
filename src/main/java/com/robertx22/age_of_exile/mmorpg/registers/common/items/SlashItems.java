package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.capability.player.BackpackItem;
import com.robertx22.age_of_exile.content.ubers.UberBossMapItem;
import com.robertx22.age_of_exile.content.ubers.UberBossTier;
import com.robertx22.age_of_exile.content.ubers.UberEnum;
import com.robertx22.age_of_exile.content.ubers.UberFragmentItem;
import com.robertx22.age_of_exile.database.data.loot_chest.base.LootChestItem;
import com.robertx22.age_of_exile.database.data.profession.all.Professions;
import com.robertx22.age_of_exile.database.data.profession.items.DestroyOutputMegaExpItem;
import com.robertx22.age_of_exile.database.data.profession.items.StationBlockItem;
import com.robertx22.age_of_exile.maps.MapItem;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.tags.all.SlotTags;
import com.robertx22.age_of_exile.vanilla_mc.items.*;
import com.robertx22.age_of_exile.vanilla_mc.items.crates.gem_crate.LootCrateItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.DodgeOffhandItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.bases.TomeItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.StaffWeapon;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.ItemNewbieGearBag;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.LootTableItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.ProjectileItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.SoulCleanerItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.ResetStatsPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.SingleTalentResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.SpellsResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.TalentResetPotion;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class SlashItems {

    public static void init() {

        station(Professions.COOKING, () -> Items.BREAD);
        station(Professions.SALVAGING, () -> Items.IRON_INGOT);
        station(Professions.GEAR_CRAFTING, () -> Items.GOLD_INGOT);
        station(Professions.ALCHEMY, () -> Items.IRON_NUGGET);
        station(Professions.ENCHANTING, () -> Items.PAPER);

        for (EffectCtx eff : ModEffects.ALL) {
            EFFECT_DISPLAY.put(eff.GUID(), Def.item(() -> new EffectDisplayItem(), "mob_effects/" + eff.GUID()));
        }


        for (UberBossTier tier : UberBossTier.map.values()) {
            for (UberEnum uber : UberEnum.values()) {

                if (!UBER_MAPS.containsKey(uber)) {
                    UBER_MAPS.put(uber, new HashMap<>());
                }
                var maps = UBER_MAPS.get(uber);
                maps.put(tier.tier, Def.item(() -> new UberBossMapItem(tier.tier, uber), "uber/map_" + uber.id + "_tier" + tier.tier));

                if (!UBER_FRAGS.containsKey(uber)) {
                    UBER_FRAGS.put(uber, new HashMap<>());
                }
                var frags = UBER_FRAGS.get(uber);
                frags.put(tier.tier, Def.item(() -> new UberFragmentItem(tier.tier, uber), "uber/frag_" + uber.id + "_tier" + tier.tier));
            }
        }

    }

    private static void station(String pro, Supplier<Item> sup) {
        STATIONS.put(pro, Def.item(pro + "_station", () -> new StationBlockItem(SlashBlocks.STATIONS.get(pro).get(), new Item.Properties(), sup)));

    }

    public static HashMap<UberEnum, HashMap<Integer, RegObj<UberBossMapItem>>> UBER_MAPS = new HashMap<>();
    public static HashMap<UberEnum, HashMap<Integer, RegObj<UberFragmentItem>>> UBER_FRAGS = new HashMap<>();


    public static RegObj<CommonGearProducerItem> COMMON_SOUL_PRODUCE = Def.item(() -> new CommonGearProducerItem(), "common_soul_produce");

    public static RegObj<StatSoulItem> STAT_SOUL = Def.item(() -> new StatSoulItem(), "stat_soul");
    public static RegObj<MapItem> MAP = Def.item(() -> new MapItem(), "map");
    public static RegObj<Item> MAP_SETTER = Def.item(() -> new Item(new Item.Properties()), "set_map");
    public static RegObj<Item> MAP_DEVICE = Def.item(() -> new BlockItem(SlashBlocks.MAP.get(), new Item.Properties()), "teleporter");
    public static RegObj<Item> TP_BACK = Def.item(() -> new TpBackItem(), "tp_back");
    public static RegObj<Item> INVISIBLE_ICON = Def.item(() -> new Item(new Item.Properties()), "invisible_item");
    public static RegObj<Item> TEST_GEN = Def.item(() -> new Item(new Item.Properties()), "test_gen");
    public static RegObj<Item> MASTER_BAG = Def.item(() -> new BackpackItem(), "master_bag");
    public static RegObj<LootCrateItem> LOOT_CRATE = Def.item(() -> new LootCrateItem(), "loot_crate/default");

    public static RegObj<ProjectileItem> FIREBALL = Def.item(() -> new ProjectileItem("fireball"), "projectile/" + "fireball");
    public static RegObj<ProjectileItem> SNOWBALL = Def.item(() -> new ProjectileItem("snowball"), "projectile/" + "snowball");
    public static RegObj<ProjectileItem> SLIMEBALL = Def.item(() -> new ProjectileItem("slimeball"), "projectile/" + "slimeball");
    public static RegObj<ProjectileItem> LIGHTNING = Def.item(() -> new ProjectileItem("lightning"), "projectile/" + "lightning");
    public static RegObj<ProjectileItem> BOOMERANG = Def.item(() -> new ProjectileItem("boomerang"), "projectile/" + "boomerang");
    public static RegObj<ProjectileItem> HOLYBALL = Def.item(() -> new ProjectileItem("holy_ball"), "projectile/" + "holy_ball");


    public static RegObj<LootTableItem> LOOT_TABLE_ITEM = Def.item(() -> new LootTableItem(), "loot_table_chest");

    public static RegObj<Item> NEWBIE_GEAR_BAG = Def.item(() -> new ItemNewbieGearBag(), "newbie_gear_bag");
    public static RegObj<Item> DESTROY_OUTPUT = Def.item(() -> new DestroyOutputMegaExpItem("Disassembler's Learning Method"), "destroy_output_exp");
    //   public static RegObj<Item> SALVAGE_HAMMER = Def.item(() -> new SalvageHammerItem(), "salvage_hammer");
    public static RegObj<Item> SOCKET_EXTRACTOR = Def.item(() -> new SocketExtractorItem(), "socket_extractor");
    public static RegObj<Item> SOUL_CLEANER = Def.item(() -> new SoulCleanerItem(), "soul_cleaner");


    public static RegObj<Item> INFUSED_IRON = Def.item(() -> new SimpleMatItem(), "mat/infused_iron");
    public static RegObj<Item> CRYSTALLIZED_ESSENCE = Def.item(() -> new SimpleMatItem(), "mat/crystallized_essence");
    public static RegObj<Item> GOLDEN_ORB = Def.item(() -> new SimpleMatItem(), "mat/golden_orb");
    public static RegObj<Item> MYTHIC_ESSENCE = Def.item(() -> new SimpleMatItem(), "mat/mythic_essence");

    public static RegObj<TalentResetPotion> RESET_ALL_PERKS = Def.item(() -> new TalentResetPotion(), "potions/reset_all_perks");
    public static RegObj<SingleTalentResetPotion> ADD_RESET_PERK_POINTS = Def.item(() -> new SingleTalentResetPotion(), "potions/add_reset_perk_points");
    public static RegObj<SpellsResetPotion> RESET_SPELLS = Def.item(() -> new SpellsResetPotion(), "potions/reset_spells");
    public static RegObj<ResetStatsPotion> RESET_STATS = Def.item(() -> new ResetStatsPotion(), "potions/reset_stats");

    public static RegObj<LootChestItem> CURRENCY_CHEST = Def.item(() -> new LootChestItem("Currency"), "chest/currency");

    public static RegObj<Item> DEX_JEWEL = Def.item(() -> new JewelItem(new Item.Properties().stacksTo(1)), "jewel/dex");
    public static RegObj<Item> STR_JEWEL = Def.item(() -> new JewelItem(new Item.Properties().stacksTo(1)), "jewel/str");
    public static RegObj<Item> INT_JEWEL = Def.item(() -> new JewelItem(new Item.Properties().stacksTo(1)), "jewel/int");
    public static RegObj<Item> WATCHER_EYE_JEWEL = Def.item(() -> new JewelItem(new Item.Properties().stacksTo(1)), "jewel/watcher_eye");
    public static RegObj<Item> CRAFTED_UNIQUE_JEWEL = Def.item(() -> new CraftedUniqueJewelItem(), "jewel/unique_crafted");


    public static HashMap<String, RegObj<Item>> STATIONS = new HashMap<>();
    public static HashMap<String, RegObj<Item>> EFFECT_DISPLAY = new HashMap<>();

    public static RegObj<Item> CLOTH_SET = Def.item(() -> new TagForceSoulItem(() -> Items.PAPER, TagForceSoulItem.AvailableTags.CLOTH), "cloth_set");
    public static RegObj<Item> LEATHER_SET = Def.item(() -> new TagForceSoulItem(() -> Items.LEATHER, TagForceSoulItem.AvailableTags.LEATHER), "leather_set");
    public static RegObj<Item> PLATE_SET = Def.item(() -> new TagForceSoulItem(() -> Items.COPPER_INGOT, TagForceSoulItem.AvailableTags.PLATE), "plate_set");


    public static class GearItems {

        public static void init() {
        }

        public static HashMap<VanillaMaterial, RegObj<Item>> STAFFS = of("weapon/staff/",
                Arrays.asList(VanillaMaterial.DIAMOND, VanillaMaterial.IRON, VanillaMaterial.WOOD),
                x -> new StaffWeapon(x));

        public static HashMap<VanillaMaterial, RegObj<Item>> RINGS = of("jewelry/ring/", Arrays.asList(
                VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON
        ), x -> new ItemRing(x));

        public static HashMap<VanillaMaterial, RegObj<Item>> NECKLACES = of("jewelry/necklace/", Arrays.asList(
                VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON
        ), x -> new ItemNecklace(x));

        public static HashMap<VanillaMaterial, RegObj<Item>> TOMES = of("offhand/tome/", Arrays.asList(
                VanillaMaterial.DIAMOND, VanillaMaterial.WOOD, VanillaMaterial.IRON
        ), x -> new TomeItem());

        public static HashMap<VanillaMaterial, RegObj<Item>> ENERGY_DODGE_OFFHAND = of("offhand/dodge/", Arrays.asList(
                VanillaMaterial.DIAMOND, VanillaMaterial.WOOD, VanillaMaterial.IRON
        ), x -> new DodgeOffhandItem());

        private static HashMap<VanillaMaterial, RegObj<Item>> of(String idprefix, List<VanillaMaterial> list, Function<VanillaMaterial, Item> item) {
            HashMap<VanillaMaterial, RegObj<Item>> map = new HashMap<VanillaMaterial, RegObj<Item>>();
            list
                    .forEach(x -> {
                        map.put(x, Def.item(idprefix + x.id, () -> item.apply(x)));
                    });
            return map;
        }


    }

}
