package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.player.container.SkillGemsMenu;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class SlashContainers {

    public static void init() {

    }

    public static RegObj<MenuType<SkillGemsMenu>> SKILL_GEMS = Def.container("runeword", () -> IForgeMenuType.create((x, y, z) -> new SkillGemsMenu(x, y)));

}
