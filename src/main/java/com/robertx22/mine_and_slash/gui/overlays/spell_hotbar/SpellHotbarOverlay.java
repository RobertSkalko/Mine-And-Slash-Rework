package com.robertx22.mine_and_slash.gui.overlays.spell_hotbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.mine_and_slash.config.forge.ClientConfigs;
import com.robertx22.mine_and_slash.gui.overlays.EffectsOverlay;
import com.robertx22.mine_and_slash.gui.overlays.GuiPosition;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.client.SpellKeybind;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class SpellHotbarOverlay {


    public static ResourceLocation getHotbarTex() {
        return hotbarTex(ClientConfigs.getConfig().HORIZONTAL_HOTBAR.get(), ClientConfigs.getConfig().HOTBAR_SWAPPING.get(), SpellKeybind.IS_ON_SECONd_HOTBAR ? 2 : 1);
    }

    static ResourceLocation hotbarTex(Boolean horizontal, Boolean swap, Integer swapnum) {
        String swaptex = swap ? "_swap" + swapnum : "";
        String horizontaltex = horizontal ? "_horizontal" : "";
        return new ResourceLocation(SlashRef.MODID, "textures/gui/spells/hotbar/" + "hotbar" + swaptex + horizontaltex + ".png");
    }

    Minecraft mc = Minecraft.getInstance();


    public void onHudRender(GuiGraphics gui) {

        try {
            if (mc.options.renderDebug) {
                return;
            }
            if (mc.player.isSpectator()) {
                return;
            }
            if (ChatUtils.isChatOpen() && !ClientConfigs.getConfig().HORIZONTAL_HOTBAR.get()) {
                return;
            }
            if (Load.player(mc.player) == null) {
                return;
            }

            int WIDTH = 22;
            int HEIGHT = 162;

            if (ClientConfigs.getConfig().HORIZONTAL_HOTBAR.get()) {
                WIDTH = 162;
                HEIGHT = 22;
            }

            RenderSystem.enableBlend(); // enables transparency

            int x = 0;
            int y = mc.getWindow().getGuiScaledHeight() / 2 - HEIGHT / 2;

            if (ClientConfigs.getConfig().HORIZONTAL_HOTBAR.get()) {
                x = mc.getWindow().getGuiScaledWidth() / 2 - WIDTH / 2;
                y = (int) (mc.getWindow().getGuiScaledHeight() - HEIGHT / 2 - 60);
            }

            renderHotbar(gui, x, y);

            int spells = ClientConfigs.getConfig().HOTBAR_SWAPPING.get() ? 8 : 8;


            for (int i = 0; i < spells; i++) {

                int place = i;
                int xp = x + 3;
                int yp = y + 3;

                var spellRen = new SpellOnHotbarRender(place, gui, xp, yp);
                spellRen.render();
            }

            RenderSystem.disableBlend(); // enables transparency

            if (ClientConfigs.getConfig().GUI_POSITION.get() != GuiPosition.TOP_LEFT) {
                int offset = 0;
                offset = 80;
                EffectsOverlay.render(3, y + 85 + offset, mc.player, gui, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void renderHotbar(GuiGraphics gui, int x, int y) {
        int WIDTH = 22;
        int HEIGHT = 162;

        if (ClientConfigs.getConfig().HORIZONTAL_HOTBAR.get()) {
            WIDTH = 162;
            HEIGHT = 22;
        }

        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        var hotbar = getHotbarTex();

        gui.blit(hotbar, x, y, 0, 0, WIDTH, HEIGHT);

    }

}