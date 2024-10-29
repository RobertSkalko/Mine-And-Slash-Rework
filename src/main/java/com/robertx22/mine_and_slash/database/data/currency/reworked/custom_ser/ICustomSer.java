package com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser;

import com.robertx22.library_of_exile.registry.serialization.ISerializable;

public interface ICustomSer<T> extends ISerializable<T> {

    public CustomSerializer getSerMap();

    public String getSer();
}
