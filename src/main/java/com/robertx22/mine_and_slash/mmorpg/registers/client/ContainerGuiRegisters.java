package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.mine_and_slash.capability.player.container.BackpackScreen;
import com.robertx22.mine_and_slash.capability.player.container.SkillGemsScreen;
import com.robertx22.mine_and_slash.database.data.profession.all.Professions;
import com.robertx22.mine_and_slash.database.data.profession.screen.*;
import com.robertx22.mine_and_slash.mmorpg.registers.common.SlashContainers;
import net.minecraft.client.gui.screens.MenuScreens;

public class ContainerGuiRegisters {

    //ublic static HashMap<String, MenuScreens.ScreenConstructor>

    public static void reg() {

        MenuScreens.register(SlashContainers.STATIONS.get(Professions.SALVAGING).get(), SalvagingScreen::new);
        MenuScreens.register(SlashContainers.STATIONS.get(Professions.GEAR_CRAFTING).get(), GearCraftingScreen::new);
        MenuScreens.register(SlashContainers.STATIONS.get(Professions.ALCHEMY).get(), AlchemyScreen::new);
        MenuScreens.register(SlashContainers.STATIONS.get(Professions.INFUSING).get(), InfusingScreen::new);
        MenuScreens.register(SlashContainers.STATIONS.get(Professions.COOKING).get(), CookingScreen::new);

        MenuScreens.register(SlashContainers.SKILL_GEMS.get(), SkillGemsScreen::new);
        MenuScreens.register(SlashContainers.BACKPACK.get(), BackpackScreen::new);


    }

}
