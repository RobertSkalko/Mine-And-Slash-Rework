package com.robertx22.mine_and_slash.mmorpg;

import com.robertx22.mine_and_slash.maps.dungeon_reg.Dungeon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestReflection {
    public static void test() {
        try {
            Dungeon c = new Dungeon();
            Class klass = c.getClass();
            Field[] fields = getAllFields(klass);
            for (Field field : fields) {
                MMORPG.LOGGER.log(field.getName());
            }
        } catch (Throwable a_th) {
            a_th.printStackTrace();
        }
        MMORPG.LOGGER.log("DONE TESTING REFLECTION");

    }

    public static Field[] getAllFields(Class klass) {
        List<Field> fields = new ArrayList<Field>();
        fields.addAll(Arrays.asList(klass.getDeclaredFields()));
        if (klass.getSuperclass() != null) {
            fields.addAll(Arrays.asList(getAllFields(klass.getSuperclass())));
        }
        return fields.toArray(new Field[]{});
    }
}
