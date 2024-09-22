package com.robertx22.mine_and_slash.database.data.currency.reworked.keys;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.custom.MaximumUsesReq;

public class MaxUsesKey extends KeyInfo {

    public MaximumUsesReq.Data data;

    public MaxUsesKey(MaximumUsesReq.Data data) {
        this.data = data;
    }

    @Override
    public String GUID() {
        return data.use_id();
    }
}
