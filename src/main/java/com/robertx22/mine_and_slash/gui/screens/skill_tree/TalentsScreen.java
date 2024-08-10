package com.robertx22.mine_and_slash.gui.screens.skill_tree;

import com.robertx22.mine_and_slash.database.data.talent_tree.TalentTree;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import net.minecraft.resources.ResourceLocation;

public class TalentsScreen extends SkillTreeScreen {

    public TalentsScreen() {
        super(TalentTree.SchoolType.TALENTS);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/talents.png");
    }

    @Override
    public Words screenName() {
        return Words.Talents;
    }

    @Override
    public boolean shouldAlert() {
        return Load.player(ClientOnly.getPlayer()).talents.hasFreePoints(ClientOnly.getPlayer(), TalentTree.SchoolType.TALENTS);
    }
}
