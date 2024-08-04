package com.robertx22.age_of_exile.database.documentation.test;

import com.robertx22.age_of_exile.database.documentation.JsonField;
import com.robertx22.age_of_exile.mmorpg.TestReflection;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.*;

public abstract class JsonWrapper<T extends ExileRegistry> implements IJsonWrapper<T> {
    // todo put this outside
    public static List<ExileRegistryType> checked = new ArrayList<>();


    List<JsonField> allFields = new ArrayList<>();
    HashMap<String, JsonField> fieldMap = new HashMap<>();


    public void testFieldsExist() {
        if (!checked.contains(getWrapped().getExileRegistryType())) {
            checked.add(getWrapped().getExileRegistryType());

            var fields = TestReflection.getAllFields(getWrapped().getClass());
            for (Map.Entry<String, JsonField> en : getFieldMap().entrySet()) {
                var opt = Arrays.stream(fields).filter(x -> x.getName().equals(en.getKey())).findAny();
                if (!opt.isPresent()) {
                    throw new RuntimeException(en.getKey() + " bad field name");
                }
            }
        }
    }

    public <T extends JsonField> T of(String fieldname, T res) {
        allFields.add(res);
        fieldMap.put(fieldname, res);
        return res;
    }

    public abstract T getWrapped();
}
