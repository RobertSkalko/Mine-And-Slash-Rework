package com.robertx22.age_of_exile.database.data.rarities;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientTextureUtils;
import com.robertx22.library_of_exile.registry.IAutoGson;
import net.minecraft.util.ResourceLocation;

public final class GearRarity extends BaseRarity implements IGearRarity, IAutoGson<GearRarity> {
    public static GearRarity SERIALIZER = new GearRarity();

    public GearRarity() {
        super(RarityType.GEAR);
    }

    @Override
    public Class<GearRarity> getClassForSerialization() {
        return GearRarity.class;
    }

    public int item_model_data_num = -1;


    public int potential = 100;

    public int min_affixes = 0;

    public int max_sockets = 3;

    public int item_tier = -1;


    public float item_tier_power;
    public float item_value_multi;
    public boolean announce_in_chat = false;

    public boolean is_unique_item = false;

    transient ResourceLocation glintFull;
    transient ResourceLocation glintTexBorder;


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
