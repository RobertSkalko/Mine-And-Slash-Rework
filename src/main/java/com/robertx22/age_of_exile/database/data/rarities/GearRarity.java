package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.MinMax;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
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

    public float getFavorGainEverySecond() {
        if (favor_loot_multi <= 1) {
            return ServerContainer.get().FAVOR_GAIN_PER_SECOND.get().floatValue();
        }
        return 0;
    }

    public int affix_rarity_weight = 1000;


    public LootableGearTier lootable_gear_tier = LootableGearTier.LOW;
    public int item_model_data_num = -1;
    public MinMax stat_percents = new MinMax(0, 0);
    public Potential pot = new Potential(100, 0.5F);
    public int min_affixes = 0;
    public int max_sockets = 3;
    public int socket_chance = 25;
    public int item_tier = -1;
    public float item_tier_power;
    public int min_lvl = 0;
    public float item_value_multi;
    public boolean announce_in_chat = false;
    public boolean is_unique_item = false;
    public MinMax map_tiers = new MinMax(0, 100);

    public int max_spell_links = 1;

    transient ResourceLocation glintFull;
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
