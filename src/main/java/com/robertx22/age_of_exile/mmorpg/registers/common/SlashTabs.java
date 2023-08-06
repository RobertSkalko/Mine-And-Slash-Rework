package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.item.CreativeModeTab;

public class SlashTabs {
    public static RegObj<CreativeModeTab> CREATIVE = Def.creativeTab("tab", () -> new CreativeModeTab.Builder(CreativeModeTab.Row.TOP, 10)
            .icon(() -> SlashItems.IDENTIFY_TOME.get().getDefaultInstance())
            .build());

    public static void init() {

    }
}
