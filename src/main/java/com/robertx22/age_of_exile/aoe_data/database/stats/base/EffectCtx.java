package com.robertx22.age_of_exile.aoe_data.database.stats.base;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.database.data.exile_effects.EffectType;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class EffectCtx extends AutoHashClass implements IGUID {

    public EffectType type;
    public String resourcePath;
    public String id;
    public Elements element;
    public String locname;

    public ResourceLocation getEffectLocation() {
        return new ResourceLocation(SlashRef.MODID, resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ExileEffectInstanceData getData(LivingEntity en) {
        var data = Load.Unit(en).getStatusEffectsData();
        var effect = ExileDB.ExileEffects().get(id);
        var result = data.get(effect);
        return result;
    }

    public EffectCtx(String id, String locname, Elements element, EffectType type) {
        this.id = id;
        this.resourcePath = id;
        this.element = element;
        this.locname = locname;
        this.type = type;

        ModEffects.ALL.add(this);
    }

    @Override
    public String GUID() {
        return resourcePath;
    }
}
