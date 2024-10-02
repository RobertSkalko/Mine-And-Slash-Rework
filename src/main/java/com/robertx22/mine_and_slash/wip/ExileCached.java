package com.robertx22.mine_and_slash.wip;

import java.util.function.Supplier;

public class ExileCached<T> {

    private T obj;

    private Supplier<T> sup;

    public ExileCached(Supplier<T> sup) {
        this.sup = sup;
    }

    public T get() {
        if (obj == null) {
            obj = sup.get();
        }
        return obj;
    }

    public void clear() {
        obj = null;
    }
}
