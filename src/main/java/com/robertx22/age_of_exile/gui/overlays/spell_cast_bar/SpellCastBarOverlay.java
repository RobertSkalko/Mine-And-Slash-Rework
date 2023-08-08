package com.robertx22.age_of_exile.gui.overlays.spell_cast_bar;

import com.robertx22.age_of_exile.capability.player.EntitySpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;

public class SpellCastBarOverlay {

    private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation("textures/gui/bars.png");

    static int WIDTH = 182;
    static int HEIGHT = 5;

    Minecraft mc = Minecraft.getInstance();

    public void onHudRender(GuiGraphics gui) {

        EntitySpellData.ISpellsCap data = Load.spells(mc.player);

        if (data == null) {
            return;
        }

        if (data.getCastingData()
                .isCasting() && data.getCastingData().castingTicksLeft > 0) {

            int x = mc.getWindow()
                    .getGuiScaledWidth() / 2 - WIDTH / 2;
            int y = (int) (mc.getWindow()
                    .getGuiScaledHeight() / 1.25F - HEIGHT / 2);

            float percent =
                    ((float) data.getCastingData().lastSpellCastTimeInTicks - (float) data.getCastingData().castingTicksLeft) / (float) data
                            .getCastingData().lastSpellCastTimeInTicks;

            render(gui, x, y, BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.NOTCHED_20, percent);
        }
    }

    private void render(GuiGraphics gui, int x, int y, BossEvent.BossBarColor color, BossEvent.BossBarOverlay overlay, float percent) {

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        gui.blit(GUI_BARS_TEXTURES, x, y, 0, color.ordinal() * 5 * 2, WIDTH, HEIGHT);
        if (overlay != BossEvent.BossBarOverlay.PROGRESS) {
            gui.blit(GUI_BARS_TEXTURES, x, y, 0, 80 + (overlay.ordinal() - 1) * 5 * 2, WIDTH, HEIGHT);
        }

        int i = (int) (percent * 183.0F);
        if (i > 0) {
            gui.blit(GUI_BARS_TEXTURES, x, y, 0, color.ordinal() * 5 * 2 + 5, i, HEIGHT);
            if (overlay != BossEvent.BossBarOverlay.PROGRESS) {
                gui.blit(GUI_BARS_TEXTURES, x, y, 0, 80 + (overlay.ordinal() - 1) * 5 * 2 + 5, i, HEIGHT);
            }
        }

    }

}
