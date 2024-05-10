package com.robertx22.age_of_exile.gui.overlays;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.GuiUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

import java.text.DecimalFormat;
import java.util.Map;

public class EffectsOverlay {

    public static void render(int x, int y, Player p, GuiGraphics gui, boolean horizontal) {


        int size = 16;
        int spacing = 18;

        Minecraft mc = Minecraft.getInstance();

        for (Map.Entry<String, ExileEffectInstanceData> en : Load.Unit(p).getStatusEffectsData().exileMap.entrySet()) {
            if (!en.getValue().shouldRemove()) {
                var eff = ExileDB.ExileEffects().get(en.getKey());

                gui.blit(eff.getTexture(), x, y, size, size, 0, 0, size, size, size, size);

                gui.drawString(mc.font, en.getValue().stacks + "", (int) x, (int) y, ChatFormatting.WHITE.getColor(), true);

                int ticks = en.getValue().ticks_left;
                int sec = ticks / 20;
                String text = (int) sec + "s";

                if (sec > 60) {
                    int min = sec / 60;
                    text = (int) min + "m";
                } else {

                    DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.0");

                    if (sec < 10) {
                        text = (int) sec + "s";
                    } else {
                        text = DECIMAL_FORMAT.format(sec / (float) 60) + "m";
                    }

                }

                int yoff = 21;
                if (!horizontal) {
                    yoff = 14;
                }

                GuiUtils.renderScaledText(gui, (int) x + 9, (int) y + yoff, 0.8F, text, ChatFormatting.YELLOW);
                // gui.drawString(mc.font, text, (int) x, (int) y + 18, ChatFormatting.YELLOW.getColor(), true);

                if (horizontal) {
                    x += spacing;
                } else {
                    y += spacing;
                }
            }
        }

    }
}
