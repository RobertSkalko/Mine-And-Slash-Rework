package com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck;

public interface IDelayCheckTask<T extends IDelayChecker> {

    boolean canRun(T p);

    void run(T p);

    boolean isExpired();

    default void sendTo(T p) {
        p.receive(this);
    }
}
