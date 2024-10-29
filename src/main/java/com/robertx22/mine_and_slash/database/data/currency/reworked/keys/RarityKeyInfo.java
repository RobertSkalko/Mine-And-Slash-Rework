package com.robertx22.mine_and_slash.database.data.currency.reworked.keys;

// todo..
public class RarityKeyInfo extends KeyInfo {

    public String rar;

    public RarityKeyInfo(String rar) {
        this.rar = rar;
    }

    @Override
    public String GUID() {
        return rar;
    }
}
