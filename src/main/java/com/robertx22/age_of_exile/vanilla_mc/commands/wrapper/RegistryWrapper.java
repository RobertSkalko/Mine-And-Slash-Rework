package com.robertx22.age_of_exile.vanilla_mc.commands.wrapper;

import com.mojang.brigadier.context.CommandContext;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

public class RegistryWrapper<T extends ExileRegistry> extends StringWrapper {
    ExileRegistryType type;


    public RegistryWrapper(ExileRegistryType type) {
        this.registrySuggestion(type);
        this.type = type;
    }

    @Override
    public String get(CommandContext ctx) {
        var id = super.get(ctx);

        if (id.equals("random")) {
            return Database.getRegistry(type).random().GUID();
        }

        return id;
    }

    public T getFromDB(CommandContext ctx) {
        var id = get(ctx);

        if (id.equals("random")) {
            return (T) Database.getRegistry(type).random();
        }

        return (T) Database.getRegistry(type).get(id);
    }

}
