package com.robertx22.age_of_exile.gui.screens.spell;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.TextUTIL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class PickHotbarButton extends ImageButton {

    static ResourceLocation SPELL_SLOT = SlashRef.guiId("spells/pick_hotbar");

    public static int BUTTON_SIZE_X = 20;
    public static int BUTTON_SIZE_Y = 20;

    Minecraft mc = Minecraft.getInstance();
    SpellScreen screen;

    public int num;

    public PickHotbarButton(SpellScreen screen, int num, int xPos, int yPos) {
        super(xPos, yPos, BUTTON_SIZE_X, BUTTON_SIZE_Y, 0, 0, BUTTON_SIZE_Y, SPELL_SLOT, (button) -> {

            SpellScreen.IS_PICKING_HOTBAR_SPELL = true;
            SpellScreen.NUMBER_OF_HOTBAR_FOR_PICKING = num;
        });
        this.screen = screen;
        this.num = num;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float delta) {

        setMODTooltip();// todo will this work?

        Minecraft mc = Minecraft.getInstance();


        Spell spell = Load.spells(mc.player)
                .getSpellByNumber(num);

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(SPELL_SLOT, getX(), getY(), BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

        if (spell != null) {
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            gui.blit(spell.getIconLoc(), getX() + 2, getY() + 2, 16, 16, 16, 16, 16, 16);
        }

    }


    public void setMODTooltip() {


        List<Component> tooltip = new ArrayList<>();

        TooltipInfo info = new TooltipInfo(mc.player);

        Spell spell = Load.spells(mc.player)
                .getSpellByNumber(num);

        if (spell != null) {
            tooltip.addAll(spell.GetTooltipString(info));
        } else {
            tooltip.add(Component.literal("Click to start selecting a spell to place on hotbar."));
            tooltip.add(Component.literal("Click again on desired spell to confirm"));
        }


        this.setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));

    }

    /*
    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
    }

     */

}

