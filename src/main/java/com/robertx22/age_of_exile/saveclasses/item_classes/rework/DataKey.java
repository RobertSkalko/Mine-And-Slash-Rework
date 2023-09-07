package com.robertx22.age_of_exile.saveclasses.item_classes.rework;

import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.HashMap;

public abstract class DataKey<T> {

    public final String key;

    public DataKey(String key) {
        this.key = key;
    }


    protected T get(HashMap<String, String> map) {
        if (map.containsKey(key)) {
            String o = map.get(key);

            if (o != null) {
                T finished = stringToObject(o);
                return finished;
            }
        }
        return getEmpty();
    }

    public abstract T getEmpty();

    // could be any generic, ints are saved as ints, while complex objects are saved as strings usually
    protected abstract T stringToObject(String o);

    protected abstract String objectToString(T o);


    public static class EnumKey<T extends Enum> extends DataKey<T> {

        T empty;

        public EnumKey(T empty, String key) {
            super(key);
            this.empty = empty;
        }

        @Override
        public T getEmpty() {
            return empty;
        }

        @Override
        protected T stringToObject(String o) {
            try {
                T tried = (T) T.valueOf(empty.getClass(), o);
                return tried;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getEmpty();
        }

        @Override
        protected String objectToString(T o) {
            return o.name();
        }
    }


    public static class RegistryKey<T extends ExileRegistry> extends DataKey<T> {

        ExileRegistryType type;

        public RegistryKey(String key, ExileRegistryType type) {
            super(key);
            this.type = type;
        }


        @Override
        public T getEmpty() {
            return (T) Database.getRegistry(type).getDefault();
        }

        @Override
        protected T stringToObject(String o) {
            String id = o;
            Object obj = Database.getRegistry(type).get(id);

            try {
                return (T) obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getEmpty();
        }

        @Override
        protected String objectToString(T o) {
            return o.GUID();
        }
    }

    public static class StringKey extends DataKey<String> {

        public StringKey(String key) {
            super(key);
        }

        @Override
        public String getEmpty() {
            return "";
        }

        @Override
        protected String stringToObject(String o) {
            return o;
        }

        @Override
        protected String objectToString(String o) {
            return o;
        }
    }

    public static class IntKey extends DataKey<Integer> {

        public IntKey(String key) {
            super(key);
        }

        @Override
        public Integer getEmpty() {
            return 0;
        }

        @Override
        protected Integer stringToObject(String o) {
            return Integer.valueOf(o);
        }

        @Override
        protected String objectToString(Integer o) {
            return o.toString();
        }
    }

    public static class FloatKey extends DataKey<Float> {

        public FloatKey(String key) {
            super(key);
        }

        @Override
        public Float getEmpty() {
            return 0F;
        }

        @Override
        protected Float stringToObject(String o) {
            return Float.valueOf(o);
        }

        @Override
        protected String objectToString(Float o) {
            return o.toString();
        }
    }

    public static class BooleanKey extends DataKey<Boolean> {

        public BooleanKey(String key) {
            super(key);
        }

        @Override
        public Boolean getEmpty() {
            return false;
        }

        @Override
        protected Boolean stringToObject(String o) {
            return Boolean.valueOf(o);
        }

        @Override
        protected String objectToString(Boolean o) {
            return o.toString();
        }
    }

}
