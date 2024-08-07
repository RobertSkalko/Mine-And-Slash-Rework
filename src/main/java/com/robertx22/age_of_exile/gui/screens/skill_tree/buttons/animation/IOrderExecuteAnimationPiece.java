package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.animation;

import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;

public interface IOrderExecuteAnimationPiece {

    void executeThis();
    void executeNext();
    <T extends IOrderExecuteAnimationPiece> T append(T next);
    <T extends IOrderExecuteAnimationPiece> T goBack();
    int getTimeInterval();
    void minusOneTimeInterval();
    boolean isAllDone();
}
