package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.mine_and_slash.capability.player.container.BackpackMenu;
import com.robertx22.mine_and_slash.capability.player.container.SkillGemsMenu;
import com.robertx22.mine_and_slash.database.data.profession.screen.CraftingStationMenu;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class SlashContainers {

    public static void init() {

    }

    public static RegObj<MenuType<SkillGemsMenu>> SKILL_GEMS = Def.container("runeword", () -> IForgeMenuType.create((x, y, z) -> new SkillGemsMenu(x, y)));
    public static RegObj<MenuType<CraftingStationMenu>> CRAFTING = Def.container("crafting", () -> IForgeMenuType.create((x, y, z) -> new CraftingStationMenu(x, y)));
    public static RegObj<MenuType<BackpackMenu>> BACKPACK = Def.container("backpack", () -> IForgeMenuType.create((id, pInv, buf) -> new BackpackMenu(id, pInv)));

}
