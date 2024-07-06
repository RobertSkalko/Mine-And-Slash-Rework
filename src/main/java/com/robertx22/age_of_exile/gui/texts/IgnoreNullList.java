package com.robertx22.age_of_exile.gui.texts;

import com.google.common.collect.Lists;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.*;

public class IgnoreNullList<E> extends AbstractList<E> {

    private final List<E> list;


    public IgnoreNullList(List<E> pList) {
        this.list = pList;
    }

    public IgnoreNullList() {
        this.list = new ArrayList<>();
    }

    public static <E> IgnoreNullList<E> create() {
        return new IgnoreNullList(Lists.newArrayList());
    }

    public static <E> IgnoreNullList<E> createWithCapacity(int pInitialCapacity) {
        return new IgnoreNullList(Lists.newArrayListWithCapacity(pInitialCapacity));
    }

    public static <E> IgnoreNullList<E> withSize(int pSize, E pDefaultValue) {
        Validate.notNull(pDefaultValue);
        Object[] $$2 = new Object[pSize];
        Arrays.fill($$2, pDefaultValue);
        return new IgnoreNullList(Arrays.asList($$2));
    }

    @SafeVarargs
    public static <E> IgnoreNullList<E> of(E... pElements) {
        IgnoreNullList<E> objects = new IgnoreNullList<>();
        for (E pElement : pElements) {
            objects.add(pElement);
        }
        return objects;
    }

    @Nonnull
    public E get(int pIndex) {
        return this.list.get(pIndex);
    }

    public E set(int pIndex, E pValue) {
        Validate.notNull(pValue);
        return this.list.set(pIndex, pValue);
    }

    @CanIgnoreReturnValue
    public boolean add(E pValue) {
        if (pValue != null) {
            this.list.add(pValue);
            return true;
        }
        return false;
    }

    @CanIgnoreReturnValue
    public IgnoreNullList<E> addAll(List<? extends E> elements) {
        if (elements == null || elements.isEmpty()) return this;
        for (E element : elements) {
            if (element != null) add(element);
        }
        return this;
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends E> c) {
        if (!c.isEmpty()) {
            for (E element : c) {
                if (element != null) add(element);
            }
        }
        return true;
    }

    public E remove(int pIndex) {
        return this.list.remove(pIndex);
    }

    public int size() {
        return this.list.size();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

}
