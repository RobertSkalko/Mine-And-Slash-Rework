package com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.states;

import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SearchHandler;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.OpacityController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;

public class Searching extends OpacityControllerState {

    private final EditBox search = SkillTreeScreen.SEARCH;

    public Searching(OpacityController opacityController, PerkButton button) {
        super(opacityController, button);
    }

    @Override
    public float onWholeImage() {
        return 0.5f;
    }

    @Override
    public float onSingleButton() {
        SearchHandler searchHandler = button.getScreen().searchHandler;

        if (searchHandler.checkThisButtonIsSearchResult(button)) return OpacityController.HIGHLIGHT;
        PerkStatus status = opacityController.playerData.talents.getStatus(Minecraft.getInstance().player, button.school, button.point);

        if (search.getValue().equals("all")) {
            if (status == PerkStatus.CONNECTED) return OpacityController.HIGHLIGHT;
        }

        // this is copied from the original code
        // the logic here has been moved to NonSearching state;

        //opacity = status.getOpacity();


        return OpacityController.HIDE;
    }

    @Override
    public float onSingleButtonWhenWholeImage() {
        return onSingleButton();
    }
}
