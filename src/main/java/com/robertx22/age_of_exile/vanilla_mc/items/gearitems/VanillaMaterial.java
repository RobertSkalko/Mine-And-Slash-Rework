package com.robertx22.age_of_exile.vanilla_mc.items.gearitems;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;

public enum VanillaMaterial {

    WOOD("wood", new ItemOrTag(ItemTags.PLANKS), ArmorMaterials.LEATHER, Tiers.WOOD),
    IRON("iron", new ItemOrTag(Items.IRON_INGOT), ArmorMaterials.IRON, Tiers.IRON),
    GOLD("gold", new ItemOrTag(Items.GOLD_INGOT), ArmorMaterials.GOLD, Tiers.GOLD),
    DIAMOND("diamond", new ItemOrTag(Items.DIAMOND), ArmorMaterials.DIAMOND, Tiers.DIAMOND);

    public String id;
    public ItemOrTag mat;
    public ArmorMaterials armormat;
    public Tiers toolmat;

    VanillaMaterial(String id, ItemOrTag mat, ArmorMaterials armormat, Tiers toolmat) {
        this.id = id;
        this.mat = mat;
        this.armormat = armormat;
        this.toolmat = toolmat;
    }

    public static class ItemOrTag {
        public Item item;
        public TagKey<Item> tag;

        public ItemOrTag(Item item) {
            this.item = item;
        }

        public ItemOrTag(TagKey<Item> tag) {
            this.tag = tag;
        }
    }
}
