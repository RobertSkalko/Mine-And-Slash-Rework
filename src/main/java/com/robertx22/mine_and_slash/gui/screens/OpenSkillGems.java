package com.robertx22.mine_and_slash.gui.screens;

import com.robertx22.mine_and_slash.gui.bases.IAlertScreen;
import com.robertx22.mine_and_slash.gui.bases.IContainerNamedScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.vanilla_mc.packets.OpenContainerPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.resources.ResourceLocation;

public class OpenSkillGems implements IContainerNamedScreen, IAlertScreen {

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
        return Words.Hotbar;
    }

    @Override
    public boolean shouldAlert() {
        return Load.player(ClientOnly.getPlayer()).spellCastingData.learnedSpellButHotbarIsEmpty();
    }
}
