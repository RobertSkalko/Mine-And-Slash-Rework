package com.robertx22.mine_and_slash.itemstack;

import java.util.HashMap;
import java.util.function.Function;

public class ExileStacklessData {

    public HashMap<String, Object> map = new HashMap<>();

    private transient ExileStack stack = ExileStack.of(null);

    private <T> T get(StackData<T> data) {
        return (T) map.get(data.getId());
    }

    // todo switch all these to stackkeys

    public <T> T get(Function<ExileStack, StackData<T>> data) {
        var o = data.apply(this.stack);
        return get(o);
    }

    private <T> T getOrCreate(StackData<T> data) {
        if (!map.containsKey(data.getId())) {
            map.put(data.getId(), data.createDefault());
        }
        return get(data);
    }

    public <T> T getOrCreate(Function<ExileStack, StackData<T>> data) {
        var o = data.apply(this.stack);
        return getOrCreate(o);
    }

    private <T> void set(StackData<T> data, T obj) {
        map.put(data.getId(), obj);
    }

    public <T> void set(Function<ExileStack, StackData<T>> data, T obj) {
        var o = data.apply(this.stack);
        set(o, obj);
    }

    public void apply(ExileStack stack) {


        for (StackData data : stack.getAll()) {
            if (map.containsKey(data.getId())) {
                var d = map.get(data.getId());
                data.set(d);
            }
        }
    }

    public static ExileStacklessData from(ExileStack stack) {
        var b = new ExileStacklessData();
        for (StackData data : stack.getAll()) {
            if (data.has()) {
                b.set(data, data.get());
            }
        }
        return b;
    }


}
