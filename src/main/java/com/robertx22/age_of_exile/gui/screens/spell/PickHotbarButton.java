package com.robertx22.age_of_exile.gui.screens.spell;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

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
    public void render(PoseStack matrix, int mouseX, int mouseY, float delta) {
        Minecraft mc = Minecraft.getInstance();

        Spell spell = Load.spells(mc.player)
            .getSpellByNumber(num);

        mc.getTextureManager()
            .bind(SPELL_SLOT);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrix, x, y, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X, BUTTON_SIZE_X);

        if (spell != null) {
            mc.getTextureManager()
                .bind(spell.getIconLoc());
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            blit(matrix, x + 2, y + 2, 16, 16, 16, 16, 16, 16);
        }

    }

    @Override
    public void renderToolTip(PoseStack matrix, int x, int y) {
        if (isInside(x, y)) {

            List<Component> tooltip = new ArrayList<>();

            TooltipInfo info = new TooltipInfo(mc.player);

            Spell spell = Load.spells(mc.player)
                .getSpellByNumber(num);

            if (spell != null) {
                tooltip.addAll(spell.GetTooltipString(info));
            } else {
                tooltip.add(new TextComponent("Click to start selecting a spell to place on hotbar."));
                tooltip.add(new TextComponent("Click again on desired spell to confirm"));
            }
            GuiUtils.renderTooltip(matrix, tooltip, x, y);

        }
    }

    public boolean isInside(int x, int y) {
        return GuiUtils.isInRect(this.x, this.y, BUTTON_SIZE_X, BUTTON_SIZE_Y, x, y);
    }

}

