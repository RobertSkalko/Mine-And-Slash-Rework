package com.robertx22.age_of_exile.gui.screens;

import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.gui.bases.IContainerNamedScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenBackpackPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.resources.ResourceLocation;

public class OpenBackpack implements IContainerNamedScreen {

    Backpacks.BackpackType type;

    public OpenBackpack(Backpacks.BackpackType type) {
        this.type = type;
    }

    @Override
    public void openContainer() {
        Packets.sendToServer(new OpenBackpackPacket(type));
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/" + type.id + "_bag.png");
    }

    @Override
    public Words screenName() {
        return type.name;
    }
}
