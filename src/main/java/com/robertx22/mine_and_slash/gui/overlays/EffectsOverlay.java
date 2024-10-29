package com.robertx22.mine_and_slash.gui.overlays;

import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.config.forge.overlay.OverlayType;
import com.robertx22.mine_and_slash.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

import java.text.DecimalFormat;
import java.util.Map;

public class EffectsOverlay {

    public static void render(GuiGraphics gui, boolean horizontal) {
        Player p = ClientOnly.getPlayer();

        var config = ClientConfigs.getConfig().getOverlayConfig(OverlayType.EFFECTS_VERTICAL);


        int x = config.getPos().x;
        int y = config.getPos().y;

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


                PointData textOff = new PointData(9, -5);
                if (!horizontal) {
                    textOff = new PointData(11, -12);
                }
                // todo check

                GuiUtils.renderScaledText(gui, (int) x + textOff.x, (int) y - textOff.y, 0.8F, text, ChatFormatting.YELLOW);

                if (horizontal) {
                    x += spacing;
                } else {
                    y += spacing;
                }
            }
        }

    }
}
