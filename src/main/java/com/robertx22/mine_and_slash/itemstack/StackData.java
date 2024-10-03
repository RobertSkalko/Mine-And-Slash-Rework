package com.robertx22.mine_and_slash.itemstack;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.robertx22.library_of_exile.utils.ItemstackDataSaver;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class StackData<T> {

    ExileStack stack;


    public StackData(ExileStack stack, ItemstackDataSaver<T> saver) {
        this.stack = stack;
        this.saver = saver;

        stack.map.put(getId(), this);
    }


    private ItemstackDataSaver<T> saver;

    private Supplier<T> getterNon = () -> saver.loadFrom(stack.getStack());
    private Supplier<T> getter = Suppliers.memoize(getterNon);

    public T get() {
        return getter.get();
    }

    public String getId() {
        return saver.GUID();
    }

    public T getOrCreate() {
        if (!has()) {
            this.set(saver.getConstructor().get());
        }
        return get();
    }

    public boolean has() {
        return saver.has(stack.getStack());
    }

    public T createDefault() {
        return saver.getConstructor().get();
    }

    public boolean hasAndTrue(Predicate<T> p) {
        if (has()) {
            if (p.test(get())) {
                return true;
            }
        }
        return false;
    }

    public void set(T data) {
        this.saver.saveTo(stack.getStack(), data);
        resetGetterCache();
    }

    public void edit(Consumer<T> c) {
        c.accept(getOrCreate());
        save();
    }

    public boolean setIfHas(T data) {
        if (has()) {
            set(data);
            return true;
        }
        return false;
    }

    public void save() {
        this.saver.saveTo(stack.getStack(), get());
        resetGetterCache();
    }

    // this seems dumb
    public void resetGetterCache() {
        getter = Suppliers.memoize(getterNon);
    }


}
