package com.robertx22.mine_and_slash.itemstack;

import java.util.function.Function;

public class StackKey<T> {

    String key;
    Function<ExileStack, StackData<T>> sup;

    public StackKey(String id, Function<ExileStack, StackData<T>> sup) {
        this.key = id;
        this.sup = sup;
        
    }

    public StackData<T> get(ExileStack stack) {
        return stack.get(this);
    }
}
