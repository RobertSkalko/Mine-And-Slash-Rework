package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.capability.player.container.SkillGemsScreen;
import com.robertx22.age_of_exile.database.data.profession.CraftingStationScreen;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashContainers;
import net.minecraft.client.gui.screens.MenuScreens;

public class ContainerGuiRegisters {

    public static void reg() {


        MenuScreens.register(SlashContainers.SKILL_GEMS.get(), SkillGemsScreen::new);
        MenuScreens.register(SlashContainers.CRAFTING.get(), CraftingStationScreen::new);

        //ScreenManager.register(SlashContainers.BACKPACK.get(), BackpackScreen::new);
        //  ScreenManager.register(SlashContainers.RUNEWORD.get(), RuneWordStationGui::new);
        // ScreenManager.register(SlashContainers.MAT_POUCH.get(), MatBagScreen::new);
        //ScreenManager.register(SlashContainers.PROF_CRAFTING.get(), ProfCraftScreen::new);

    }

}
