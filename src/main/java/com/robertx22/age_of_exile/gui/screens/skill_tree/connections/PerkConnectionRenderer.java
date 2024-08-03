package com.robertx22.age_of_exile.gui.screens.skill_tree.connections;

import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkPointPair;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.IDelayCheckTask;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.IDelayChecker;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.RendererUpdateTask;

import java.util.ArrayList;
import java.util.Collection;

public class PerkConnectionRenderer implements IDelayChecker {


    PerkPointPair pair;
    Perk.Connection connection;

    private final ArrayList<RendererUpdateTask> updateList = new ArrayList<>();

    public PerkPointPair pair() {
        return pair;
    }

    public Perk.Connection connection() {
        return connection;
    }

    public void mutateConnectionTo(Perk.Connection connection){
        this.connection = connection;
    }

    public PerkConnectionRenderer(PerkPointPair pair, Perk.Connection connection) {
        this.pair = pair;
        this.connection = connection;
    }

    @Override
    public int hashCode() {
        return pair.hashCode();
    }

    public int hashCodeWithConnection(){
        long result = 17;
        result = 29 * result + pair.hashCode() | connection.hashCode();

        return (int) result;
    }

    @Override
    public ArrayList<RendererUpdateTask> getTasksContainer() {
        return updateList;
    }
}
