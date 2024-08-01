package com.robertx22.age_of_exile.gui.screens.skill_tree.opacity;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.states.NonSearching;
import com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.states.OpacityControllerState;
import com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.states.Searching;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;

import javax.annotation.Nullable;

// rewritten with state machine design mode.
public class OpacityController {

    private OpacityControllerState state;
    private final OpacityControllerState nonSearching;
    private final OpacityControllerState searching;
    public PlayerData playerData = Load.player(ClientOnly.getPlayer());
    public static final float HIGHLIGHT = 1.0f;
    public static final float HIDE = 0.2f;

    public OpacityController(@Nullable PerkButton button) {
        this.nonSearching = new NonSearching(this, button);
        this.searching = new Searching(this, button);
        detectCurrentState(Load.player(ClientOnly.getPlayer()));
    }

    public void detectCurrentState(PlayerData playerData){
        this.playerData = playerData;
        boolean searching = SkillTreeScreen.SEARCH.getValue().isEmpty();

        boolean newbie = playerData.talents.getAllocatedPoints(TalentTree.SchoolType.TALENTS) < 1;
        //System.out.println("search? " + searching);
        //System.out.println("talent? " + (newbie));
        this.state =  searching ? this.nonSearching : this.searching;
    }

    public OpacityControllerState getState() {
        return state;
    }

    public boolean isSearching(){
        return state == searching;
    }

    public float getWholeImage(){
        return this.state.onWholeImage();
    }

    public float getSingleButton(){
        return this.state.onSingleButton();
    }


    public float getSingleButtonWhenWholeImage(){
        return this.state.onSingleButtonWhenWholeImage();
    }


}
