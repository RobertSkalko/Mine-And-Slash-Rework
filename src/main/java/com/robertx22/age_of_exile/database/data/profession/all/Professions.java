package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.profession.ExpSources;
import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.library_of_exile.registry.IWeighted;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.*;
import java.util.stream.Collectors;

public class Professions {

    // gathering
    public static String FARMING = "farming";
    public static String MINING = "mining";
    public static String HUSBANDRY = "husbandry";


    // crafting
    public static String COOKING = "cooking";
    public static String ALCHEMY = "alchemy";
    public static String ENCHANTING = "enchanting";

    // misc
    public static String SALVAGING = "salvaging";


    public static List<String> STATION_PROFESSIONS = Arrays.asList(
            COOKING,
            SALVAGING,
            ALCHEMY,
            ENCHANTING
    );


    public static List<String> ALL = Arrays.asList(
            FARMING,
            MINING,
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

        Builder.of(SALVAGING)
                .sometimesDrop(ProfessionMatItems.Essence.ALL_NORMAL.stream().map(x -> x.get()).collect(Collectors.toList()), 5)
                .sometimesDrop(ProfessionMatItems.Essence.Rare.ALL_RARES.stream().map(x -> x.get()).collect(Collectors.toList()), 0.5F)
                .build();

        var FARM_EXP = 30;
        var ORE_EXP = 100;

        Builder.of(FARMING)
                .item(SkillItemTier.TIER0, FARM_EXP, Items.WHEAT, Items.POTATO)
                .item(SkillItemTier.TIER1, FARM_EXP, Items.MELON)
                .item(SkillItemTier.TIER2, FARM_EXP, Items.BEETROOT)
                .item(SkillItemTier.TIER3, FARM_EXP, Items.CARROT)
                .item(SkillItemTier.TIER4, FARM_EXP, Items.NETHER_WART)
                .item(SkillItemTier.TIER5, FARM_EXP, Items.COCOA_BEANS)

                .dropTiered(ProfessionMatItems.TIERED_MAIN_MATS.get(FARMING))

                .build();

        Builder.of(HUSBANDRY)

                .dropTiered(ProfessionMatItems.TIERED_MAIN_MATS.get(HUSBANDRY))

                .build();


        // todo each should really be separate and have chance for tag..
        Builder.of(MINING)
                .blockTag(SkillItemTier.TIER0, ORE_EXP, BlockTags.COAL_ORES)
                .blockTag(SkillItemTier.TIER1, ORE_EXP, BlockTags.COAL_ORES)
                .blockTag(SkillItemTier.TIER2, ORE_EXP, BlockTags.IRON_ORES)
                .blockTag(SkillItemTier.TIER3, ORE_EXP, BlockTags.GOLD_ORES)
                .blockTag(SkillItemTier.TIER4, ORE_EXP, BlockTags.EMERALD_ORES)
                .blockTag(SkillItemTier.TIER5, ORE_EXP, BlockTags.DIAMOND_ORES)

                .dropTiered(ProfessionMatItems.TIERED_MAIN_MATS.get(MINING))
                .build();

        Builder.of(COOKING)

                .build();


        Builder.of(ALCHEMY)

                .build();

        Builder.of(ENCHANTING)

                .build();


    }


    private static class Builder {
        Profession p = new Profession();

        public static Builder of(String id) {
            Builder b = new Builder();
            b.p.id = id;
            return b;
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

        public Builder dropTiered(HashMap<SkillItemTier, RegObj<Item>> map) {
            for (Map.Entry<SkillItemTier, RegObj<Item>> en : map.entrySet()) {
                Item item = en.getValue().get();

                var id = VanillaUTIL.REGISTRY.items().getKey(item).toString();
                var drop = new Profession.ProfessionDrop(id, 1, 1000, en.getKey().lvl_req);

                if (!p.tiered_drops.containsKey(en.getKey())) {
                    p.tiered_drops.put(en.getKey(), new ArrayList<>());
                }
                this.p.tiered_drops.get(en.getKey()).add(new Profession.ChancedDrop(Arrays.asList(drop), 5F));

            }
            return this;
        }

        public Builder sometimesDrop(List<Item> items, float chance) {
            List<Profession.ProfessionDrop> drops = new ArrayList<>();

            for (Item item : items) {
                var weight = 1000;
                if (item instanceof IWeighted w) {
                    weight = w.Weight();
                }
                var id = VanillaUTIL.REGISTRY.items().getKey(item).toString();
                var drop = new Profession.ProfessionDrop(id, 1, weight, 0);
                drops.add(drop);

            }
            this.p.chance_drops.add(new Profession.ChancedDrop(drops, chance));
            return this;
        }

        public void build() {
            p.addToSerializables();
        }
    }
}
