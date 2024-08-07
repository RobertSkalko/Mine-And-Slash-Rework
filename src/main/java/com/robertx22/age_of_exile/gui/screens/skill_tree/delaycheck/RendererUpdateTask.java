package com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck;

import com.robertx22.age_of_exile.gui.screens.skill_tree.connections.PerkConnectionRenderer;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class RendererUpdateTask implements IDelayCheckTask<PerkConnectionRenderer>{
    private final Predicate<PerkConnectionRenderer> condition;

    private final Consumer<PerkConnectionRenderer> task;

    private int lastTime;


    public RendererUpdateTask(Predicate<PerkConnectionRenderer> condition, Consumer<PerkConnectionRenderer> task, int lastTime) {
        this.condition = condition;
        this.task = task;
        this.lastTime = lastTime;
    }


    @Override
    public boolean canRun(PerkConnectionRenderer p) {
        return condition.test(p);
    }

    @Override
    public void run(PerkConnectionRenderer p) {
        task.accept(p);
    }

    @Override
    public boolean isExpired() {
        lastTime--;
        return lastTime <= 0;
    }

}
