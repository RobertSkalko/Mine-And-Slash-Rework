package com.robertx22.age_of_exile.gui.screens;

import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenJewelsPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.resources.ResourceLocation;

public class OpenJewelsScreen implements IContainerNamedScreen {

    @Override
    public void openContainer() {

        Packets.sendToServer(new OpenJewelsPacket());
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/jewel.png");
    }

    @Override
    public Words screenName() {
        return Words.Jewel;
    }
}
