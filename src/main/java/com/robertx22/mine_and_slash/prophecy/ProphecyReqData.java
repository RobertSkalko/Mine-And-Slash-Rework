package com.robertx22.mine_and_slash.prophecy;

import com.robertx22.mine_and_slash.loot.LootInfo;

public class ProphecyReqData {

    public ReqType req = ReqType.TIER;
    public int value = 0;

    public ProphecyReqData(ReqType req, int value) {
        this.req = req;
        this.value = value;
    }

    public enum ReqType {
        LEVEL() {
            @Override
            public boolean can(LootInfo info, ProphecyReqData data) {
                return info.level >= data.value;
            }
        }, TIER() {
            @Override
            public boolean can(LootInfo info, ProphecyReqData data) {
                return info.map_tier >= data.value;
            }
        };

        public abstract boolean can(LootInfo info, ProphecyReqData data);
    }

}
