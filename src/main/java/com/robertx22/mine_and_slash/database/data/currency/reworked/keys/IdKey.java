package com.robertx22.mine_and_slash.database.data.currency.reworked.keys;

public class IdKey extends KeyInfo {

    String id;

    public IdKey(String id) {
        this.id = id;
    }

    @Override
    public String GUID() {
        return id;
    }
}
