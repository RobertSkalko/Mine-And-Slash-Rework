package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

public class GLUtils {

    public static void renderAlwaysSeenText(Runnable render){
        RenderSystem.disableDepthTest();
        render.run();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
    }
}
