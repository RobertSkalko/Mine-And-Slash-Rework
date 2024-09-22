package com.robertx22.mine_and_slash.database.data.currency.reworked.keys;

public class StringKey extends KeyInfo {

    public String str;

    public StringKey(String str) {
        this.str = str;
    }

    @Override
    public String GUID() {
        return str;
    }
}
