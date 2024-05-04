package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import net.minecraft.resources.ResourceLocation;

public class AscendancyTree extends SkillTreeScreen {

    public AscendancyTree() {
        super(TalentTree.SchoolType.ASCENDANCY);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/ascendancy.png");
    }

    @Override
    public Words screenName() {
        return Words.AscClasses;
    }
}
