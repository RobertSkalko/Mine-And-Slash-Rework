package com.robertx22.age_of_exile.gui.screens;

import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenContainerPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.resources.ResourceLocation;

public class OpenSkillGems implements IContainerNamedScreen {

    @Override
    public void openContainer() {

        Packets.sendToServer(new OpenContainerPacket(OpenContainerPacket.GuiType.SKILL_GEMS));
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/skill_gems.png");
    }

    @Override
    public Words screenName() {
        return Words.SkillGem;
    }
}
