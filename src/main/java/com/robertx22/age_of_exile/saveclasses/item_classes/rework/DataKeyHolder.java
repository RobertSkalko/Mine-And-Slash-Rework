package com.robertx22.age_of_exile.saveclasses.item_classes.rework;

import java.util.ArrayList;
import java.util.List;

public class DataKeyHolder {

    private List<String> ids = new ArrayList<>();

    protected <T extends DataKey> T of(T o) {
        if (ids.contains(o.key)) {
            throw new RuntimeException(o.key + " key is used more than once!");
        }
        ids.add(o.key);
        return o;
    }
}
