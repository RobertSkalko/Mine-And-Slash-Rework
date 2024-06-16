package com.robertx22.age_of_exile.capability.player.container;

import com.robertx22.age_of_exile.gui.inv_gui.GuiInventoryGrids;
import com.robertx22.age_of_exile.gui.inv_gui.InvGuiScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class SpellButton extends ImageButton {

    public static int BUTTON_SIZE_X = 16;
    public static int BUTTON_SIZE_Y = 16;

    int slot;

    public SpellButton(int slot, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SlashRef.guiId("empty_spell"), (button) -> {
            Minecraft.getInstance().setScreen(new InvGuiScreen(GuiInventoryGrids.ofSelectableSpells(ClientOnly.getPlayer(), slot)));
        });
        this.slot = slot;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        if (this.isHovered()) {
            setModTooltip();
        }
        super.render(gui, mouseX, mouseY, delta);
    }

    public SkillGemData getSpell() {
        return Load.player(ClientOnly.getPlayer()).spellCastingData.getSpellData(slot).getData();
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float delta) {
        //  super.renderWidget(gui, mouseX, mouseY, delta);


        boolean flicker = Load.player(ClientOnly.getPlayer()).spellCastingData.learnedSpellButHotbarIsEmpty();

        var mc = Minecraft.getInstance();

        // todo check if this causes seizures
        float color = flicker ? MathHelper.clamp((mc.player.tickCount % 25 + mc.getPartialTick()) * 0.13f, 0, 3) : 1F;

        gui.setColor(1.0F, color, 1.0F, 1.0F);
        if (hasSpell()) {
            gui.blit(getSpell().getSpell().getIconLoc(), getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);
        } else {
            gui.blit(SlashRef.guiId("empty_spell"), getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);
        }
    }

    public boolean hasSpell() {
        return getSpell() != null && getSpell().getSpell() != null;
    }

    public void setModTooltip() {

        List<Component> tooltip = new ArrayList<>();
        if (hasSpell()) {
            Minecraft mc = Minecraft.getInstance();
            tooltip = getSpell().getTooltip(mc.player);
        }
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));

    }

    @Override
    protected ClientTooltipPositioner createTooltipPositioner() {
        return DefaultTooltipPositioner.INSTANCE;
    }

}
