package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.Executor;

public class ExileTreeTexture extends SimpleTexture {
    private final NativeImage image;



    public ExileTreeTexture(ResourceLocation pLocation, NativeImage image) {
        super(pLocation);
        this.image = image;
    }

    @Override
    public void load(@NotNull ResourceManager pResourceManager) throws IOException {

        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(() -> {
                this.doLoad(image, false, false);
            });
        } else {
            this.doLoad(image, false, false);
        }

    }
    private void doLoad(NativeImage pImage, boolean pBlur, boolean pClamp) {
        TextureUtil.prepareImage(this.getId(), 0, pImage.getWidth(), pImage.getHeight());
        pImage.upload(0, 0, 0, 0, 0, pImage.getWidth(), pImage.getHeight(), pBlur, pClamp, false, true);

    }

    @Override
    public void reset(TextureManager pTextureManager, ResourceManager pResourceManager, ResourceLocation pPath, Executor pExecutor) {
    }
}
