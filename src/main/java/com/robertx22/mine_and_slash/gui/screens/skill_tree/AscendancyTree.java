package com.robertx22.mine_and_slash.gui.screens.skill_tree;

import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
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

    @Override
    public boolean shouldAlert() {
        return Load.player(ClientOnly.getPlayer()).talents.hasFreePoints(ClientOnly.getPlayer(), TalentTree.SchoolType.ASCENDANCY);
    }
}
