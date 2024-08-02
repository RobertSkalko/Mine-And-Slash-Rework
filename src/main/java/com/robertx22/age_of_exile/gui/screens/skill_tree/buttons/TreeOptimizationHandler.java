package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;

public class TreeOptimizationHandler {

    public static boolean isOptEnable(){
        return ClientConfigs.getConfig().ENABLE_SKILL_TREE_OPTIMIZATION.get();
    }


}
