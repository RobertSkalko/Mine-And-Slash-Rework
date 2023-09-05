package com.robertx22.age_of_exile.database.data.profession.all;

import com.robertx22.age_of_exile.database.data.profession.Profession;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.temp.SkillItemTier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Builder.of(FARMING)
                .one(Blocks.WHEAT, Blocks.POTATOES)
                .two(Blocks.MELON)
                .three(Blocks.BEETROOTS)
                .four(Blocks.CARROTS)
                .five(Blocks.NETHER_WART, Blocks.COCOA)
                .build();

        Builder.of(HUSBANDRY)

                .build();

        Builder.of(MINING)
                .dropTiered(ProfessionMatItems.TIERED_MAIN_MATS.get(MINING))
                .build();

        Builder.of(COOKING)

                .build();

        Builder.of(SALVAGING)

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

        public Builder one(Block... blocks) {
            for (Block block : blocks) {
                this.p.tier_1.put(VanillaUTIL.REGISTRY.blocks().getKey(block).toString(), 10);
            }
            return this;
        }

        public Builder two(Block... blocks) {
            for (Block block : blocks) {
                this.p.tier_2.put(VanillaUTIL.REGISTRY.blocks().getKey(block).toString(), 12);
            }
            return this;
        }

        public Builder three(Block... blocks) {
            for (Block block : blocks) {
                this.p.tier_3.put(VanillaUTIL.REGISTRY.blocks().getKey(block).toString(), 15);
            }
            return this;
        }

        public Builder four(Block... blocks) {
            for (Block block : blocks) {
                this.p.tier_4.put(VanillaUTIL.REGISTRY.blocks().getKey(block).toString(), 17);
            }
            return this;
        }

        public Builder five(Block... blocks) {
            for (Block block : blocks) {
                this.p.tier_5.put(VanillaUTIL.REGISTRY.blocks().getKey(block).toString(), 20);
            }
            return this;
        }

        public Builder drop(Item item, int weight, SkillItemTier tier) {
            var id = VanillaUTIL.REGISTRY.items().getKey(item).toString();

            var drop = new Profession.ProfessionDrop(id, 1, weight, tier.lvl_req);

            this.p.drops.add(drop);
            return this;
        }

        public Builder dropTiered(HashMap<SkillItemTier, RegObj<Item>> map) {
            for (Map.Entry<SkillItemTier, RegObj<Item>> en : map.entrySet()) {
                drop(en.getValue().get(), 1000, en.getKey());
            }
            return this;
        }

        public Builder drop(Item item, SkillItemTier tier) {
            return drop(item, 1000, tier);
        }

        public void build() {
            p.addToSerializables();
        }
    }
}
