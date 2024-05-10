package com.robertx22.age_of_exile.capability.player.container;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.skill_gem.MaxLinks;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class SuppGemOverlayButton extends ImageButton {

    public static int BUTTON_SIZE_X = 20;
    public static int BUTTON_SIZE_Y = 19;


    boolean can;

    MaxLinks links;

    public SuppGemOverlayButton(boolean can, MaxLinks links, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SlashRef.guiId("blocked_slot"), (button) -> {
            //Minecraft.getInstance().setScreen(new InvGuiScreen(GuiInventoryGrids.ofSelectableSpells(ClientOnly.getPlayer(), slot)));
        });
        this.can = can;
        this.links = links;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {

        List<Component> tooltip = new ArrayList<>();
        if (!can) {
            tooltip.add(Words.LockedSuppGemSlot.locName().withStyle(ChatFormatting.RED));

            tooltip.add(Component.empty());

            if (links == null) {
                tooltip.add(Words.NoSocketedSpell.locName().withStyle(ChatFormatting.YELLOW));

            } else {
                if (links.cappedByLevel) {
                    tooltip.add(Words.IncreaseYourLevel.locName().withStyle(ChatFormatting.YELLOW));
                }
                if (links.cappedBySpellLevel) {
                    tooltip.add(Words.IncreaseSpellLevel.locName().withStyle(ChatFormatting.YELLOW));
                }
            }
        }
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));

        super.render(gui, mouseX, mouseY, delta);
    }


    static ResourceLocation id = SlashRef.guiId("blocked_slot");

    @Override
    protected boolean clicked(double pMouseX, double pMouseY) {
        return false;
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {

        //  super.renderWidget(gui, mouseX, mouseY, delta);

        if (!can) {
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            gui.blit(id, getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);
        }
    }


}
