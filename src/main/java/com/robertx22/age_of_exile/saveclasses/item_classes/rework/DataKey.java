package com.robertx22.age_of_exile.saveclasses.item_classes.rework;

import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.HashMap;

public abstract class DataKey<T> {

    String key;

    public DataKey(String key) {
        this.key = key;
    }


    protected T get(HashMap<String, Object> map) {
        if (map.containsKey(key)) {
            Object o = map.get(key);

            if (o != null) {
                T finished = savedObjectToObject(o);
                return finished;
            }
        }

        return null;
    }

    public abstract T getEmpty();

    // could be any generic, ints are saved as ints, while complex objects are saved as strings usually
    protected abstract T savedObjectToObject(Object o);

    protected abstract Object objectToSavable(T o);

    protected static abstract class Generic<T> extends DataKey<T> {

        public Generic(String key) {
            super(key);
        }


        @Override
        protected T savedObjectToObject(Object o) {
            return (T) o;
        }

        @Override
        protected Object objectToSavable(T o) {
            return o;
        }
    }

    public static class EnumKey<T extends Enum> extends DataKey<T> {

        T en;
        T empty;

        public EnumKey(T en, T empty, String key) {
            super(key);
            this.empty = empty;
            this.en = en;
        }

        @Override
        public T getEmpty() {
            return empty;
        }

        @Override
        protected T savedObjectToObject(Object o) {
            try {
                T tried = (T) T.valueOf(en.getClass(), (String) o);
                return tried;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getEmpty();
        }

        @Override
        protected Object objectToSavable(T o) {
            return o.name();
        }
    }


    public static class RegistryKey<T extends ExileRegistry<T>> extends DataKey<T> {

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
        protected T savedObjectToObject(Object o) {
            String id = (String) o;
            Object obj = Database.getRegistry(type).get(id);

            try {
                return (T) obj;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return getEmpty();
        }

        @Override
        protected Object objectToSavable(T o) {
            return o.GUID();
        }
    }

    public static class StringKey extends Generic<String> {

        public StringKey(String key) {
            super(key);
        }

        @Override
        public String getEmpty() {
            return "";
        }
    }

    public static class IntKey extends Generic<Integer> {

        public IntKey(String key) {
            super(key);
        }

        @Override
        public Integer getEmpty() {
            return 0;
        }
    }

    public static class FloatKey extends Generic<Float> {

        public FloatKey(String key) {
            super(key);
        }

        @Override
        public Float getEmpty() {
            return 0F;
        }
    }

    public static class BooleanKey extends Generic<Boolean> {

        public BooleanKey(String key) {
            super(key);
        }

        @Override
        public Boolean getEmpty() {
            return false;
        }
    }

}
