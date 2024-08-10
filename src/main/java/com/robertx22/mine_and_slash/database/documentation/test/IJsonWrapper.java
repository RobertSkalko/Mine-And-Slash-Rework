package com.robertx22.mine_and_slash.database.documentation.test;

import com.robertx22.mine_and_slash.database.documentation.JsonField;

import java.util.Comparator;
import java.util.HashMap;

public interface IJsonWrapper<T> {

    HashMap<String, JsonField> getFieldMap();

    public default String generateDocumentation() {
        final String[] doc = {""};

        var map = getFieldMap();

        map.entrySet().stream().sorted(Comparator.comparing(b -> b.getKey())).forEach(x -> {
            doc[0] += x.getValue().createDocumentation(x.getKey());
        });

        return doc[0];

    }
}
