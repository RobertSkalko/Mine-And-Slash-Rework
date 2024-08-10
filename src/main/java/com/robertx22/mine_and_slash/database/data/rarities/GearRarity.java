package com.robertx22.mine_and_slash.database.data.rarities;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientTextureUtils;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;
import java.util.function.Consumer;

public final class GearRarity extends BaseRarity implements IGearRarity, IAutoGson<GearRarity> {
    public static GearRarity SERIALIZER = new GearRarity();

    public GearRarity() {
        super();
    }

    public GearRarity edit(Consumer<GearRarity> co) {
        co.accept(this);
        return this;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEAR_RARITY;
    }

    public int favor_needed = 0;
    public float favor_loot_multi = 1;
    public int favor_per_hour = 0;

    public float getFavorGainEverySecond() {
        if (favor_loot_multi > 0) {
            return favor_per_hour / (60F * 60F);
        }
        return 0;
    }

    public int affix_rarity_weight = 1000;


    public LootableGearTier lootable_gear_tier = LootableGearTier.LOW;
    public int item_model_data_num = -1;
    public MinMax stat_percents = new MinMax(0, 0);
    public Potential pot = new Potential(100);
    public int min_affixes = 0;
    public MinMax sockets = new MinMax(0, 2);
    public int item_tier = -1;
    public float item_tier_power;
    public int min_lvl = 0;
    public String min_map_rarity_to_drop = IRarity.COMMON_ID;
    public float item_value_multi;
    public boolean announce_in_chat = false;
    public boolean can_have_runewords = false;
    public boolean is_unique_item = false;
    public MinMax map_tiers = new MinMax(0, 100);

    public int map_lives = 5;

    public float map_xp_multi = 1;


    public int map_resist_req = 0;

    transient ResourceLocation glintFull;
    transient ResourceLocation glintCircle;
    transient ResourceLocation glintTexBorder;

    public boolean isNear(GearRarity rar) {
        return Math.abs(rar.item_tier - this.item_tier) < 2;
    }

    public MinMax getPossibleMapTiers() {
        return this.map_tiers;
    }

    public static GearRarity getRarityFromMapTier(int tier) {
        return ExileDB.GearRarities().getFiltered(x -> x.map_tiers.isInRange(tier)).stream().findAny().orElse(ExileDB.GearRarities().getDefault());
    }

    public enum LootableGearTier {
        LOW(0), MID(1), HIGH(2);
        int tier;

        LootableGearTier(int tier) {
            this.tier = tier;
        }
    }


    @Override
    public Class<GearRarity> getClassForSerialization() {
        return GearRarity.class;
    }


    public static class Potential {
        public int total;

        public Potential(int total) {
            this.total = total;
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

    public ResourceLocation getGlintTextureCircle() {
        if (glintCircle == null) {
            ResourceLocation tex = SlashRef.id("textures/gui/rarity_glint/circle/" + GUID() + ".png");
            if (ClientTextureUtils.textureExists(tex)) {
                glintCircle = tex;
            } else {
                glintCircle = SlashRef.id("textures/gui/rarity_glint/circle/default.png");
            }
        }
        return glintCircle;

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
        return ExileDB.GearRarities().isRegistered(higher_rar);
    }

    public GearRarity getHigherRarity() {
        if (this.higher_rar.isEmpty()) {
            return null;
        }
        return ExileDB.GearRarities().get(higher_rar);
    }

    public Optional<GearRarity> getLowerRarity() {
        var lower = ExileDB.GearRarities().getFilterWrapped(x -> x.getHigherRarity() == this).list;

        if (!lower.isEmpty()) {
            return Optional.of(lower.get(0));
        }
        return Optional.ofNullable(null);
    }

}
