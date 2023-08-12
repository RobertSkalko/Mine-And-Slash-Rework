package com.robertx22.age_of_exile.mmorpg.registers.common.items;

import com.robertx22.age_of_exile.capability.player.BackpackItem;
import com.robertx22.age_of_exile.maps.MapItem;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.saveclasses.stat_soul.StatSoulItem;
import com.robertx22.age_of_exile.vanilla_mc.items.*;
import com.robertx22.age_of_exile.vanilla_mc.items.crates.gem_crate.LootCrateItem;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemNecklace;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.baubles.ItemRing;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.weapons.StaffWeapon;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.ItemNewbieGearBag;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.LootTableItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.ProjectileItem;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.AscClassResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.ResetStatsPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.SingleTalentResetPotion;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.reset_pots.TalentResetPotion;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class SlashItems {

    public static void init() {

    }

    public static RegObj<CommonGearProducerItem> COMMON_SOUL_PRODUCE = Def.item(() -> new CommonGearProducerItem(), "common_soul_produce");

    public static RegObj<StatSoulItem> STAT_SOUL = Def.item(() -> new StatSoulItem(), "stat_soul");
    public static RegObj<MapItem> MAP = Def.item(() -> new MapItem(), "map");
    public static RegObj<Item> MAP_SETTER = Def.item(() -> new Item(new Item.Properties()), "set_map");
    public static RegObj<Item> MAP_DEVICE = Def.item(() -> new BlockItem(SlashBlocks.MAP.get(), new Item.Properties()), "teleporter");
    public static RegObj<IdentifyTomeItem> IDENTIFY_TOME = Def.item(() -> new IdentifyTomeItem(), "identify_tome");
    public static RegObj<Item> TP_BACK = Def.item(() -> new TpBackItem(), "tp_back");
    public static RegObj<Item> INVISIBLE_ICON = Def.item(() -> new Item(new Item.Properties()), "invisible_item");
    public static RegObj<LootCrateItem> LOOT_CRATE = Def.item(() -> new LootCrateItem(), "loot_crate/default");
    public static RegObj<BackpackItem> BACKPACK = Def.item(() -> new BackpackItem(), "backpack");

    public static RegObj<ProjectileItem> FIREBALL = Def.item(() -> new ProjectileItem("fireball"), "projectile/" + "fireball");
    public static RegObj<ProjectileItem> SNOWBALL = Def.item(() -> new ProjectileItem("snowball"), "projectile/" + "snowball");
    public static RegObj<ProjectileItem> SLIMEBALL = Def.item(() -> new ProjectileItem("slimeball"), "projectile/" + "slimeball");

    // public static RegObj<EssencePaperItem> ESSENCE_PAPER = Def.item(() -> new EssencePaperItem(), "scroll/paper");


    public static RegObj<LootTableItem> LOOT_TABLE_ITEM = Def.item(() -> new LootTableItem(), "loot_table_chest");


    public static RegObj<Item> NEWBIE_GEAR_BAG = Def.item(() -> new ItemNewbieGearBag(), "newbie_gear_bag");
    public static RegObj<Item> SALVAGE_HAMMER = Def.item(() -> new SalvageHammerItem(), "salvage_hammer");
    public static RegObj<Item> SOCKET_EXTRACTOR = Def.item(() -> new SocketExtractorItem(), "socket_extractor");

    public static RegObj<Item> INFUSED_IRON = Def.item(() -> new SimpleMatItem(), "mat/infused_iron");
    public static RegObj<Item> CRYSTALLIZED_ESSENCE = Def.item(() -> new SimpleMatItem(), "mat/crystallized_essence");
    public static RegObj<Item> GOLDEN_ORB = Def.item(() -> new SimpleMatItem(), "mat/golden_orb");
    public static RegObj<Item> MYTHIC_ESSENCE = Def.item(() -> new SimpleMatItem(), "mat/mythic_essence");

    public static RegObj<TalentResetPotion> RESET_ALL_PERKS = Def.item(() -> new TalentResetPotion(), "potions/reset_all_perks");
    public static RegObj<SingleTalentResetPotion> ADD_RESET_PERK_POINTS = Def.item(() -> new SingleTalentResetPotion(), "potions/add_reset_perk_points");
    public static RegObj<AscClassResetPotion> RESET_SPELLS = Def.item(() -> new AscClassResetPotion(), "potions/reset_spells");
    public static RegObj<ResetStatsPotion> RESET_STATS = Def.item(() -> new ResetStatsPotion(), "potions/reset_stats");


    public static class GearItems {

        public static void init() {
        }

        public static HashMap<VanillaMaterial, RegObj<Item>> STAFFS = of("weapon/staff/",
                Arrays.asList(VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON, VanillaMaterial.WOOD),
                x -> new StaffWeapon(x));

        public static HashMap<VanillaMaterial, RegObj<Item>> RINGS = of("jewelry/ring/", Arrays.asList(
                VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON
        ), x -> new ItemRing(x));
        public static HashMap<VanillaMaterial, RegObj<Item>> NECKLACES = of("jewelry/necklace/", Arrays.asList(
                VanillaMaterial.DIAMOND, VanillaMaterial.GOLD, VanillaMaterial.IRON
        ), x -> new ItemNecklace(x));

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
