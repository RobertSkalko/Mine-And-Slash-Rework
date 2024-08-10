package com.robertx22.mine_and_slash.mmorpg.registers.deferred_wrapper;

import net.minecraftforge.registries.RegistryObject;

public class RegObj<T> {
    public RegObj(RegistryObject<T> obj) {
        this.obj = obj;
        
    }

    private final RegistryObject<T> obj;

    //DO NOT CALL THIS BEFORE THE FORGE'S REGISTRY EVENT FOR THIS TYPE HAS BEEN CALLED
    public T get() {
        return obj.get();
    }

    public RegistryObject<T> getRegistryObject() {
        return obj;
    }

}
