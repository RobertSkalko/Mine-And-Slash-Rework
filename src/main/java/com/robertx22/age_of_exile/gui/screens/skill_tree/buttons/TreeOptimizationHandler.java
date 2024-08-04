package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class TreeOptimizationHandler {

    public static boolean isOptEnable(){
        return ClientConfigs.getConfig().ENABLE_SKILL_TREE_OPTIMIZATION.get();
    }

    public static void runOptTask(Runnable runnable){
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> (DistExecutor.SafeRunnable) runnable::run);
    }


}
