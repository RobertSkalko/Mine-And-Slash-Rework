package com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface IDelayChecker {

    default <T extends IDelayChecker> void receive(IDelayCheckTask<T> task) {

        Collection<IDelayCheckTask<T>> container = this.getTasksContainer();
        container.add(task);

    }

    <T extends IDelayChecker> Collection<IDelayCheckTask<T>> getTasksContainer();

    default void handleTaskList(){
        if (getTasksContainer().isEmpty()) return;
        Iterator<IDelayCheckTask<IDelayChecker>> iterator = getTasksContainer().iterator();
        while (iterator.hasNext()) {
            IDelayCheckTask<IDelayChecker> next = iterator.next();
            if (next.canRun(this)) {
                next.run(this);
                iterator.remove();
            } else {
                if (next.isExpired()){
                    iterator.remove();
                }
            }
        }

    }
}
