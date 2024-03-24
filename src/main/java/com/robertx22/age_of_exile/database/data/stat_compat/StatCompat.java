package com.robertx22.age_of_exile.database.data.stat_compat;

import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mixin_ducks.IDirty;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Consumer;

public class StatCompat implements JsonExileRegistry<StatCompat>, IAutoGson<StatCompat> {

    public static StatCompat SERIALIZER = new StatCompat();

    public String id = "";

    public String attribute_id = "";
    public StatScaling scaling = StatScaling.NONE;
    public String mns_stat_id = "";
    public float conversion = 0.5F;
    public int minimum_cap = 0;
    public int maximum_cap = 100;
    public ModType mod_type = ModType.PERCENT;

    public StatCompat(String id) {
        this.id = id + "_compat";
    }

    private StatCompat() {
    }


    public void editAndReg(Consumer<StatCompat> co) {
        co.accept(this);
        addToSerializables();
    }

    private Attribute getAttribute() {
        return BuiltInRegistries.ATTRIBUTE.get(new ResourceLocation(attribute_id));
    }

    public ExactStatData getResult(LivingEntity en, int lvl) {

        int val = (int) (en.getAttributeValue(getAttribute()) * conversion);
        int value = MathHelper.clamp(val, minimum_cap, maximum_cap);

        if (value != 0) {
            value = (int) scaling.scale(value, lvl);
            var data = ExactStatData.noScaling(value, mod_type, mns_stat_id);
            return data;
        }
        return null;
    }

    public static void onTick(LivingEntity en) {

        IDirty check = (IDirty) en.getAttributes();

        if (check.isAttribDirty()) {
            check.setAttribDirty(false);
            Load.Unit(en).gear.setDirty();
        }
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.STAT_COMPAT;
    }


    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1;
    }

    @Override
    public Class<StatCompat> getClassForSerialization() {
        return StatCompat.class;
    }
}
