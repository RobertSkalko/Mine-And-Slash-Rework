package com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck;

import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ButtonStatusUpdateTask implements IDelayCheckTask<PerkButton>{

    private final Predicate<PerkButton> condition;

    private final Consumer<PerkButton> task;

    private int lastTime;


    public ButtonStatusUpdateTask(Predicate<PerkButton> condition, Consumer<PerkButton> task, int lastTime) {
        this.condition = condition;
        this.task = task;
        this.lastTime = lastTime;
    }


    @Override
    public boolean canRun(PerkButton button) {
        return condition.test(button);
    }

    @Override
    public void run(PerkButton button) {
        task.accept(button);
    }


    @Override
    public boolean isExpired() {
        lastTime--;
        return lastTime <= 0;
    }
}
