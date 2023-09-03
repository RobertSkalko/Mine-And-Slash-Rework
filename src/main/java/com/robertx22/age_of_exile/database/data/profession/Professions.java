package com.robertx22.age_of_exile.database.data.profession;

import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class Professions {

    public static String FARMING = "farming";
    public static String COOKING = "cooking";
    public static String ALCHEMY = "alchemy";
    public static String ENCHANTING = "enchanting";

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

        public void build() {
            p.addToSerializables();
        }
    }
}
