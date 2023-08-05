package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import net.minecraft.world.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.SetTag;
import net.minecraft.resources.ResourceLocation;

public class SlashItemTags {

    public static final SetTag.Named<Item> CHIPPED_GEMS = get("chipped_gems");
    public static final SetTag.Named<Item> FLAWED_GEMS = get("flawed_gems");
    public static final SetTag.Named<Item> REGULAR_GEMS = get("regular_gems");

    public static void init() {

    }

    private static SetTag.Named<Item> get(String id) {
        return ItemTags.createOptional(new ResourceLocation(SlashRef.MODID + ":" + id));
    }
}
