package com.robertx22.mine_and_slash.database.registry;

import com.robertx22.library_of_exile.registry.ExileRegistryContainer;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.SyncTime;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ExileDBWrap {

    public static Set<ExileDBWrap> ALL = new HashSet<>();

    public ExileRegistryType type;

    public Runnable callRegister;

    public ExileRegistryContainer container;

    public static ExileDBWrap ofDatapack(String id, int order, ISerializable ser, SyncTime sync) {
        var o = new ExileDBWrap();
        o.type = ExileRegistryType.register(SlashRef.MODID, id, order, ser, sync);
        o.container = new ExileRegistryContainer(o.type, "");
        return o;
    }

    public static ExileDBWrap container(String def {
        var o = new ExileDBWrap();
        o.type = ExileRegistryType.register(SlashRef.MODID, id, order, ser, sync);
        o.container = new ExileRegistryContainer(o.type, "");
        return o;
    }

    public ExileDBWrap edit(Consumer<ExileDBWrap> c) {
        c.accept(this);
        return this;
    }
}
