package com.robertx22.mine_and_slash.prophecy.gui;

import com.robertx22.mine_and_slash.database.data.map_affix.MapAffix;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.prophecy.AcceptProphecyAffixPacket;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public class ProphecyAffixButton extends ImageButton {
    static ResourceLocation ID = new ResourceLocation(SlashRef.MODID, "textures/gui/prophecy/affix.png");

    MapAffix data;

    public enum Info {
        IS_TAKEN, IS_OFFER;
    }

    Info info;

    public ProphecyAffixButton(MapAffix data, Info info, boolean canTake, int x, int y) {
        super(x, y, 16, 16, 0, 0, 1, ID, (action) -> {
            if (canTake) {
                Packets.sendToServer(new AcceptProphecyAffixPacket(data.GUID()));
                Minecraft.getInstance().setScreen(null);
            }
        });
        this.info = info;
        this.data = data;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        pGuiGraphics.blit(SlashRef.guiId("prophecy/icon"), getX(), getY(), 0, 0, 16, 16, 16, 16);

        var stats = data.getStats(100, Load.Unit(ClientOnly.getPlayer()).getLevel());

        var tip = new ArrayList<Component>();

        if (info == Info.IS_OFFER) {
            tip.add(Words.ProphecyPlayerAffix.locName());
        } else {
            tip.add(Words.ProphecyPlayerAffixTaken.locName());

        }

        for (ExactStatData stat : stats) {
            tip.addAll(stat.GetTooltipString());
        }


        tip.add(Component.empty());

        if (info == Info.IS_OFFER) {
            tip.add(Words.ProphecyPlayerAffixInfo.locName());
        }

        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tip)));


        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);


    }
}
