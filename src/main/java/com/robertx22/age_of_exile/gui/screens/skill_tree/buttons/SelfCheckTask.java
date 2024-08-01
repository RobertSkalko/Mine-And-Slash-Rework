package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class SelfCheckTask {
    private Predicate<PerkButton> condition;

    private Consumer<PerkButton> task;

    private Integer lastTime;

    public SelfCheckTask(Predicate<PerkButton> condition, Consumer<PerkButton> task, Integer lastTime) {
        this.condition = condition;
        this.task = task;
        this.lastTime = lastTime;
    }

    public Predicate<PerkButton> getCondition() {
        return condition;
    }

    public Consumer<PerkButton> getTask() {
        return task;
    }

    public Integer getLastTime() {
        return lastTime;
    }

    public void setLastTime(Integer lastTime) {
        this.lastTime = lastTime;
    }

    public void sendTo(PerkButton button){
        if (button.selfCheckTasks.stream().anyMatch(x -> x.equals(this))) return;
        button.selfCheckTasks.add(this);
    }
}
