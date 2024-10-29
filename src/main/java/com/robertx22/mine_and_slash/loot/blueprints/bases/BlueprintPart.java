package com.robertx22.mine_and_slash.loot.blueprints.bases;

import com.robertx22.library_of_exile.main.ExileLog;
import com.robertx22.mine_and_slash.loot.blueprints.ItemBlueprint;

public abstract class BlueprintPart<T, C extends ItemBlueprint> {

    private T part;

    protected C blueprint;

    public boolean canBeNull = false;

    public BlueprintPart(C blueprint) {
        this.blueprint = blueprint;
    }

    protected abstract T generateIfNull();

    public void set(T t) {

        if (part == null) {
            part = t;
        } else {
            ExileLog.get().warn("Do not override an already set and created part!");
        }
    }

    public void override(T t) {
        part = t;
    }

    public boolean isGenerated() {
        return part != null;
    }

    public T get() {

        if (part == null) {
            part = generateIfNull();
        }

        if (!canBeNull) {
            if (part == null) {
                ExileLog.get().warn("Item is null even after being generated!");
            }
        }

        return part;
    }

}
