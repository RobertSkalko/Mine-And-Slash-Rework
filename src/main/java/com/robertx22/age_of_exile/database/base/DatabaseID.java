package com.robertx22.age_of_exile.database.base;


import com.google.gson.InstanceCreator;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

public abstract class DatabaseID<T extends ExileRegistry> implements InstanceCreator {

    public String id = "";

    public DatabaseID(String id) {
        this.id = id;
    }

    public abstract ExileRegistryType getExileRegistryType();

    public T get() {
        return (T) Database.getRegistry(getExileRegistryType()).get(id);
    }
}
