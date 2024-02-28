package com.robertx22.age_of_exile.prophecy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProphecyData {


    public String start = "";
    public int amount = 1;
    public int cost = 0;
    public List<ProphecyModifierData> mods = new ArrayList<>();


    public List<ProphecyReqData> getRequirements() {


        List<ProphecyReqData> list = new ArrayList<>();

        int lvlreq = mods.stream().max(Comparator.comparingInt(x -> x.get().lvl_req)).get().get().lvl_req;
        int tierreq = mods.stream().max(Comparator.comparingInt(x -> x.get().tier_req)).get().get().tier_req;

        if (lvlreq > 0) {
            list.add(new ProphecyReqData(ProphecyReqData.ReqType.LEVEL, lvlreq));
        }
        if (tierreq > 0) {
            list.add(new ProphecyReqData(ProphecyReqData.ReqType.TIER, tierreq));
        }

        return list;
    }

}
