package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.library_of_exile.registry.ExileRegistry;

public interface IDesc {

    String getDesc();

    IBaseAutoLoc.AutoLocGroup getLocGroup();

    default WrappedLocDesc getDescWrapper() {
        return new WrappedLocDesc((ExileRegistry) this, getLocGroup(), getDesc());
    }
}
