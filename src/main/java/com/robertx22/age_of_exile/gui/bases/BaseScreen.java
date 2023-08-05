package com.robertx22.age_of_exile.gui.bases;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.event_hooks.player.OnKeyPress;
import com.robertx22.age_of_exile.mmorpg.registers.client.KeybindsRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TextComponent;

public class BaseScreen extends Screen {

    protected BaseScreen(int width, int height) {
        super(new TextComponent(""));
        this.sizeX = width;
        this.sizeY = height;
    }

    public Minecraft mc = Minecraft.getInstance();

    public int guiLeft = 0;
    public int guiTop = 0;

    public int sizeX = 0;
    public int sizeY = 0;

    public void renderBackground(PoseStack matrix, ResourceLocation id) {

        mc.getTextureManager()
                .bind(id);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blit(matrix, mc.getWindow()
                        .getGuiScaledWidth() / 2 - sizeX / 2,
                mc.getWindow()
                        .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (KeybindsRegister.HUB_SCREEN_KEY.matches(keyCode, scanCode)) {
            Minecraft.getInstance()
                    .setScreen(null);
            OnKeyPress.cooldown = 5;
            return false;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - this.sizeX) / 2;
        this.guiTop = (this.height - this.sizeY) / 2;
    }

    public <T extends AbstractWidget> T publicAddButton(T w) {
        return this.addButton(w);
    }

}
