package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.items.misc.ICreativeTabNbt;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class SlashTabs {
    public static RegObj<CreativeModeTab> CREATIVE = Def.creativeTab("tab", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 2)
            .icon(() -> SlashItems.IDENTIFY_TOME.get().getDefaultInstance())
            .title(Component.literal("Mine and Slash"))
            .displayItems((pParameters, x) -> {
                for (Item item : VanillaUTIL.REGISTRY.items().getAll()) {
                    if (VanillaUTIL.REGISTRY.items().getKey(item).getNamespace().equals(SlashRef.MODID)) {
                        if (item instanceof ICreativeTabNbt nbt) {
                            for (ItemStack stack : nbt.createAllVariationsForCreativeTabs()) {
                                x.accept(stack);
                            }
                        } else {
                            x.accept(() -> item);
                        }
                    }
                }
            })
            .build());

    public static void init() {


    }
}