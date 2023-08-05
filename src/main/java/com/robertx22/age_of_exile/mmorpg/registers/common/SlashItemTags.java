package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SlashItemTags {

    public static final TagKey<Item> CHIPPED_GEMS = get("chipped_gems");
    public static final TagKey<Item> FLAWED_GEMS = get("flawed_gems");
    public static final TagKey<Item> REGULAR_GEMS = get("regular_gems");

    public static void init() {

    }

    private static TagKey<Item> get(String id) {
        return ItemTags.create(new ResourceLocation(SlashRef.MODID + ":" + id));
    }
}
