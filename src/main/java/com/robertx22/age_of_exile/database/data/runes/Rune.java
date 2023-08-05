package com.robertx22.age_of_exile.database.data.runes;

import com.robertx22.age_of_exile.database.data.BaseRuneGem;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class Rune extends BaseRuneGem implements IAutoGson<Rune>, JsonExileRegistry<Rune> {

    public static Rune SERIALIZER = new Rune();

    public Item getItem() {
        return VanillaUTIL.REGISTRY.items().get(new ResourceLocation(item_id));
    }

    @Override
    public Class<Rune> getClassForSerialization() {
        return Rune.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.RUNE;
    }

}
