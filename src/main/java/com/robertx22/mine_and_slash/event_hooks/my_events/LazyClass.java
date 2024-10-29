package com.robertx22.mine_and_slash.event_hooks.my_events;

import java.util.function.Supplier;

public class LazyClass<T> {

    private T obj;

    private Supplier<T> sup;

    public LazyClass(Supplier<T> sup) {
        this.sup = sup;
    }

    public T get() {
        if (obj == null) {
            obj = sup.get();
        }
        return obj;
    }
}
