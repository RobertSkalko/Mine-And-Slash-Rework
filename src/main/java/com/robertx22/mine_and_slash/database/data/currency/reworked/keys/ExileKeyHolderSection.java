package com.robertx22.mine_and_slash.database.data.currency.reworked.keys;

public abstract class ExileKeyHolderSection<T extends ExileKeyHolder> {

    T holder;

    public ExileKeyHolderSection(T holder) {
        this.holder = holder;

        this.holder.sections.add(this);
    }

    public T get() {
        return holder;
    }


    public abstract void init();
}
