package com.robertx22.age_of_exile.database.documentation;

import com.robertx22.library_of_exile.registry.ExileRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemField extends JsonField<Item, String> {

    public ItemField(String jsonField) {
        super(jsonField, "A valid vanilla item registry id, like \"minecraft:diamond\"");
    }

    @Override
    protected Item getInternal() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(getBaseType()));
    }

    @Override
    public boolean isEmpty() {
        return ForgeRegistries.ITEMS.containsKey(new ResourceLocation(getBaseType()));
    }

    @Override
    public void runTestInternal(ExileRegistry containingClass) {

    }
}
