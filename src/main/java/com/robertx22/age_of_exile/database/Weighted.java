package com.robertx22.age_of_exile.database;

import com.robertx22.library_of_exile.registry.IWeighted;

public class Weighted<T> implements IWeighted {
    int w;
    public T obj;

    public Weighted(T obj, int w) {
        this.w = w;
        this.obj = obj;
    }

    @Override
    public int Weight() {
        return w;
    }
}
