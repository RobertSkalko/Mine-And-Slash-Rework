package com.robertx22.age_of_exile.gui.overlays;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;

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

                if (horizontal) {
                    x += spacing;
                } else {
                    y += spacing;
                }
            }
        }

    }
}
