package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.animation;

import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;

public interface IOrderExecuteAnimationPiece<T> {

    void executeThis();
    void executeNext();
    T append(T next);
    T goBack();
    int getTimeInterval();
    void minusOneTimeInterval();
    boolean isAllDone();
}
