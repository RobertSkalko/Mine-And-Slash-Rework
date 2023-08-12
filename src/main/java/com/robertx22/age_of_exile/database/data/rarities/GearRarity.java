package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.aoe_data.database.gear_slots.GearSlots;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import com.robertx22.age_of_exile.vanilla_mc.items.gearitems.VanillaMaterial;
import com.robertx22.library_of_exile.registry.IAutoGson;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.Arrays;
import java.util.List;

public final class GearRarity extends BaseRarity implements IGearRarity, IAutoGson<GearRarity> {
    public static GearRarity SERIALIZER = new GearRarity();

    public GearRarity() {
        super(RarityType.GEAR);
    }

    public enum LootableGearTier {
        LOW(0), MID(1), HIGH(2);
        int tier;

        LootableGearTier(int tier) {
            this.tier = tier;
        }
    }


    // todo this could be improved, or made datapacks
    public Item getLootableItem(GearSlot slot) {

        List<Item> boots = Arrays.asList(Items.IRON_BOOTS, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS);
        List<Item> chest = Arrays.asList(Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.NETHERITE_CHESTPLATE);
        List<Item> legs = Arrays.asList(Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.NETHERITE_LEGGINGS);
        List<Item> helmet = Arrays.asList(Items.IRON_HELMET, Items.DIAMOND_HELMET, Items.NETHERITE_HELMET);

        List<Item> sword = Arrays.asList(Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
        List<Item> staff = Arrays.asList(SlashItems.GearItems.STAFFS.get(VanillaMaterial.WOOD).get(), SlashItems.GearItems.STAFFS.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.STAFFS.get(VanillaMaterial.DIAMOND).get());
        List<Item> ring = Arrays.asList(SlashItems.GearItems.RINGS.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.RINGS.get(VanillaMaterial.GOLD).get(), SlashItems.GearItems.RINGS.get(VanillaMaterial.DIAMOND).get());
        List<Item> necklace = Arrays.asList(SlashItems.GearItems.NECKLACES.get(VanillaMaterial.IRON).get(), SlashItems.GearItems.NECKLACES.get(VanillaMaterial.GOLD).get(), SlashItems.GearItems.NECKLACES.get(VanillaMaterial.DIAMOND).get());


        String id = slot.id;

        if (id.equals(GearSlots.RING)) {
            return ring.get(lootable_gear_tier.tier);
        }
        if (id.equals(GearSlots.NECKLACE)) {
            return necklace.get(lootable_gear_tier.tier);
        }
        if (id.equals(GearSlots.SWORD)) {
            return sword.get(lootable_gear_tier.tier);
        }
        if (id.equals(GearSlots.STAFF)) {
            return staff.get(lootable_gear_tier.tier);
        }
        if (id.equals(GearSlots.BOOTS)) {
            return boots.get(lootable_gear_tier.tier);
        }
        if (id.equals(GearSlots.PANTS)) {
            return legs.get(lootable_gear_tier.tier);
        }
        if (id.equals(GearSlots.CHEST)) {
            return chest.get(lootable_gear_tier.tier);
        }
        if (id.equals(GearSlots.HELMET)) {
            return helmet.get(lootable_gear_tier.tier);
        }
        if (id.equals(GearSlots.BOW)) {
            return Items.BOW;
        }
        if (id.equals(GearSlots.SHIELD)) {
            return Items.SHIELD;
        }
        if (id.equals(GearSlots.CROSBOW)) {
            return Items.CROSSBOW;
        }

        return Items.AIR;

    }


    @Override
    public Class<GearRarity> getClassForSerialization() {
        return GearRarity.class;
    }

    public LootableGearTier lootable_gear_tier = LootableGearTier.LOW;

    public int item_model_data_num = -1;

    public MinMax skill_gem_percents = new MinMax(0, 0);


    public Potential pot = new Potential(100, 1);

    
    public int min_affixes = 0;

    public int max_sockets = 3;

    public int socket_chance = 25;

    public int item_tier = -1;


    public float item_tier_power;
    public float item_value_multi;
    public boolean announce_in_chat = false;

    public boolean is_unique_item = false;

    transient ResourceLocation glintFull;
    transient ResourceLocation glintTexBorder;


    public static class Potential {
        public int total;
        public float multi;

        public Potential(int total, float multi) {
            this.total = total;
            this.multi = multi;
        }
    }

    public ResourceLocation getGlintTextureFull() {

        if (glintFull == null) {
            ResourceLocation tex = SlashRef.id("textures/gui/rarity_glint/full/" + GUID() + ".png");
            if (ClientTextureUtils.textureExists(tex)) {
                glintFull = tex;
            } else {
                glintFull = SlashRef.id("textures/gui/rarity_glint/full/default.png");
            }
        }
        return glintFull;

    }

    public ResourceLocation getGlintTextureBorder() {

        if (glintTexBorder == null) {
            ResourceLocation tex = SlashRef.id("textures/gui/rarity_glint/border/" + GUID() + ".png");
            if (ClientTextureUtils.textureExists(tex)) {
                glintTexBorder = tex;
            } else {
                glintTexBorder = SlashRef.id("textures/gui/rarity_glint/border/default.png");
            }
        }
        return glintTexBorder;

    }

    public boolean isHigherThan(GearRarity other) {
        return this.item_tier > other.item_tier;
    }


    @Override
    public float valueMulti() {
        return this.item_value_multi;
    }


    @Override
    public float itemTierPower() {
        return item_tier_power;
    }

    @Override
    public int getAffixAmount() {
        return min_affixes;
    }


    public boolean hasHigherRarity() {
        return ExileDB.GearRarities()
                .isRegistered(higher_rar);
    }

    public GearRarity getHigherRarity() {
        return ExileDB.GearRarities()
                .get(higher_rar);
    }


}
