package com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.states;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.OpacityController;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;

import javax.annotation.Nullable;

public abstract class OpacityControllerState {

    public OpacityController opacityController;

    public PerkButton button;

    public OpacityControllerState(OpacityController opacityController, @Nullable PerkButton button) {
        this.opacityController = opacityController;
        this.button = button;
    }

    public abstract float onWholeImage();

    public abstract float onSingleButton();

    public abstract float onSingleButtonWhenWholeImage();
}
