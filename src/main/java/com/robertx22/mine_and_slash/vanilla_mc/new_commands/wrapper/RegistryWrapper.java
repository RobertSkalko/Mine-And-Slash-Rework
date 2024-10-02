package com.robertx22.mine_and_slash.vanilla_mc.new_commands.wrapper;

import com.mojang.brigadier.context.CommandContext;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;

import java.util.Arrays;

public class RegistryWrapper<T extends ExileRegistry> extends StringWrapper {
    ExileRegistryType type;


    public RegistryWrapper(ExileRegistryType type) {
        super(type.id, () -> Arrays.asList());
        this.registrySuggestion(type);
        this.type = type;
    }

    public T getFromRegistry(CommandContext c) {
        return (T) Database.getRegistry(type).get(get(c));
    }

    @Override
    public String getter(CommandContext ctx) {
        var id = super.getter(ctx);

        if (id.equals("random")) {
            return Database.getRegistry(type).random().GUID();
        }

        return id;
    }


}
