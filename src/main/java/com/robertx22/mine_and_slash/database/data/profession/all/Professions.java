package com.robertx22.mine_and_slash.database.data.profession.all;

import com.robertx22.mine_and_slash.database.data.profession.CraftedItemPower;
import com.robertx22.mine_and_slash.database.data.profession.ExpSources;
import com.robertx22.mine_and_slash.database.data.profession.Profession;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.mine_and_slash.tags.all.SlotTags;
import com.robertx22.mine_and_slash.tags.imp.SlotTag;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class Professions {

    // gathering
    public static String FARMING = "farming";
    public static String MINING = "mining";
    public static String HUSBANDRY = "husbandry";
    public static String FISHING = "fishing";

    // crafting
    public static String COOKING = "cooking";
    public static String ALCHEMY = "alchemy";
    public static String ENCHANTING = "enchanting";
    public static String GEAR_CRAFTING = "gear_crafting";

    // misc
    public static String SALVAGING = "salvaging";


    public static List<String> STATION_PROFESSIONS = Arrays.asList(
            COOKING,
            SALVAGING,
            ALCHEMY,
            GEAR_CRAFTING,
            ENCHANTING
    );

    public static List<String> TOOL_PROFESSIONS = Arrays.asList(
            FARMING,
            MINING,
            HUSBANDRY,
            FISHING
    );


    public static List<String> DROP_PROFESSIONS = Arrays.asList(
            FARMING,
            MINING,
            HUSBANDRY,
            SALVAGING,
            FISHING
    );


    public static List<String> ALL = Arrays.asList(
            FARMING,
            MINING,
            FISHING,
            SALVAGING,
            HUSBANDRY,
            COOKING,
            ALCHEMY,
            ENCHANTING
    );


    public static void init() {

        // todo think about whether wheat should have tier/level needed to farm or not

        // maybe blocks have tier, to get best xp/loot, you need to farm that tier
        // or maybe blocks only have tiers, but you get less xp loot the more you farm them, so if you just farm wheat you will get less and less etc.
        // this would be a pain to save

        Builder.of(SALVAGING, "Salvaging", "Salvaging is the process of turning junk loot into useful materials in the Salvaging Station. You can only salvage Gear with Mine and Slash souls and other Mine and Slash rarity items like Support gems, jewels.")
                .sometimesDrop(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.SALVAGING), 20)
                .build();

        var FARM_EXP = 30;
        var ORE_EXP = 100;

        Builder.of(FARMING, "Farming", "Farming gives you a chance to obtain extra custom materials by chopping wheat, potatoes and so on. You can see what gives experience in the Mine and Slash HUB > Library screen.")
                .tool(SlotTags.farming_tool)

                .crop(SkillItemTier.TIER0, FARM_EXP, Items.WHEAT, ExpSources.REQ_GROWTH_STAGE)
                .crop(SkillItemTier.TIER0, FARM_EXP, Items.POTATO, ExpSources.REQ_GROWTH_STAGE)
                .crop(SkillItemTier.TIER1, FARM_EXP, Items.MELON_SLICE)
                .crop(SkillItemTier.TIER2, 50, Items.BEETROOT, ExpSources.REQ_GROWTH_STAGE)
                .crop(SkillItemTier.TIER3, 60, Items.CARROT, ExpSources.REQ_GROWTH_STAGE)
                .crop(SkillItemTier.TIER4, 75, Items.NETHER_WART, ExpSources.REQ_GROWTH_STAGE)
                .crop(SkillItemTier.TIER5, 100, Items.COCOA_BEANS, ExpSources.REQ_GROWTH_STAGE)

                .dropTiered(ProfessionMatItems.TIERED_MAIN_MATS.get(FARMING), 1)

                .sometimesDrop(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FARMING), 5)

                .build();

        Builder.of(FISHING, "Fishing", "Fishing gives you a chance to obtain extra custom materials by fishing.")
                .tool(SlotTags.fishing_tool)

                // todo locked crates

                .dropTiered(ProfessionMatItems.TIERED_MAIN_MATS.get(FISHING), 3)
                .sometimesDrop(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.FISHING), 15)

                .build();

        Builder.of(HUSBANDRY, "Animal Breeding", "Animal Breeding gives you a chance to obtain extra custom materials by breeding animals.")
                .tool(SlotTags.husbandry_tool)

                .dropTiered(ProfessionMatItems.TIERED_MAIN_MATS.get(HUSBANDRY), 1)

                .sometimesDrop(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.HUSBANDRY), 10)

                .build();

        // todo each should really be separate and have chance for tag..
        Builder.of(MINING, "Mining", "Mining gives you chance to obtain extra custom materials by mining ores. You can see which ores give experience in the Mine and Slash HUB > Library screen.")
                .tool(SlotTags.mining_tool)

                .blockTag(SkillItemTier.TIER0, 15, BlockTags.COAL_ORES)
                .blockTag(SkillItemTier.TIER1, 20, BlockTags.COPPER_ORES)
                .blockTag(SkillItemTier.TIER1, 15, BlockTags.REDSTONE_ORES)
                .blockTag(SkillItemTier.TIER1, 25, BlockTags.LAPIS_ORES)
                .blockTag(SkillItemTier.TIER2, 50, BlockTags.IRON_ORES)
                .blockTag(SkillItemTier.TIER3, 75, BlockTags.GOLD_ORES)
                .blockTag(SkillItemTier.TIER4, 100, BlockTags.EMERALD_ORES)
                .blockTag(SkillItemTier.TIER5, 200, BlockTags.DIAMOND_ORES)

                .dropTiered(ProfessionMatItems.TIERED_MAIN_MATS.get(MINING), 1)

                .sometimesDrop(ProfessionMatItems.POWERED_RARE_MATS.get(Professions.MINING), 10)

                .build();

        Builder.of(COOKING, "Cooking", "Cooking is a crafting profession that turns materials from gathering professions into foods that can provide stat buffs.")

                .build();


        Builder.of(ALCHEMY, "Alchemy", "Alchemy is a crafting profession that turns materials from gathering professions into Potions.")

                .build();

        Builder.of(ENCHANTING, "Infusing", "Infusing is a crafting profession that turns materials from gathering professions into scrolls that can infuse extra power into your gear.")

                .build();

        Builder.of(GEAR_CRAFTING, "Gear Crafting", "Gear Crafting is a crafting profession that turns materials from gathering professions into new pieces of gear.")

                .build();


    }


    private static class Builder {
        Profession p = new Profession();

        public static Builder of(String id, String locname, String desc) {
            Builder b = new Builder();
            b.p.id = id;
            b.p.desc = desc;
            b.p.locname = locname;
            return b;
        }

        public Builder tool(SlotTag tag) {
            this.p.tool_tag = tag.GUID();
            return this;
        }

        public Builder crop(SkillItemTier tier, int exp, Item item, String... tags) {
            this.p.exp_sources.add(tier, ExpSources.Type.FARM_ITEM, VanillaUTIL.REGISTRY.items().getKey(item).toString(), exp, tags);

            return this;
        }

        public Builder item(SkillItemTier tier, int exp, Item... items) {
            for (Item block : items) {
                this.p.exp_sources.add(tier, ExpSources.Type.FARM_ITEM, VanillaUTIL.REGISTRY.items().getKey(block).toString(), exp);
            }
            return this;
        }

        public Builder block(SkillItemTier tier, int exp, Block... blocks) {
            for (Block block : blocks) {
                this.p.exp_sources.add(tier, ExpSources.Type.BLOCK, VanillaUTIL.REGISTRY.blocks().getKey(block).toString(), exp);
            }
            return this;
        }

        public Builder blockTag(SkillItemTier tier, int exp, TagKey<Block>... blocks) {
            for (TagKey<Block> block : blocks) {
                this.p.exp_sources.add(tier, ExpSources.Type.BLOCK_TAG, block.location().toString(), exp);
            }
            return this;
        }

        public Builder dropTiered(HashMap<SkillItemTier, RegObj<Item>> map, float chancemulti) {
            for (Map.Entry<SkillItemTier, RegObj<Item>> en : map.entrySet()) {
                Item item = en.getValue().get();

                var id = VanillaUTIL.REGISTRY.items().getKey(item).toString();
                var drop = new Profession.ProfessionDrop(id, 1, 1000, en.getKey().lvl_req);

                if (!p.tiered_drops.containsKey(en.getKey())) {
                    p.tiered_drops.put(en.getKey(), new ArrayList<>());
                }
                this.p.tiered_drops.get(en.getKey()).add(new Profession.ChancedDrop(Arrays.asList(drop), Profession.DropCategory.MAIN, 10F * chancemulti));

            }
            return this;
        }

        public Builder sometimesDrop(HashMap<CraftedItemPower, RegObj<Item>> map, float chance) {
            List<Profession.ProfessionDrop> drops = new ArrayList<>();

            for (Map.Entry<CraftedItemPower, RegObj<Item>> en : map.entrySet()) {
                Item item = en.getValue().get();
                var id = VanillaUTIL.REGISTRY.items().getKey(item).toString();
                var drop = new Profession.ProfessionDrop(id, 1, 1000, 0); // all use separate loot tables
                drops.add(drop);

                this.p.chance_drops.add(new Profession.ChancedDrop(drops, en.getKey().category, chance * (en.getKey().weight / 1000F)));
            }
            return this;
        }

        public void build() {
            p.addToSerializables();
        }
    }
}
