package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarityType;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OmenData {


    public String id = "";
    public int lvl = 1;

    public String rar = IRarity.COMMON_ID;

    public HashMap<GearRarityType, Integer> rarities = new HashMap<>();

    public List<OmenSlotReq> slot_req = new ArrayList<>();

    public List<AffixData> aff = new ArrayList<>();


    public Omen getOmen() {
        return ExileDB.Omens().get(id);
    }

    // the more difficult the omen is to assemble, the more stats it provides
    // kinda automatically makes newbie omens weaker and endgame omens harder to get
    public static int getStatPercent(HashMap<GearRarityType, Integer> rarities, List<OmenSlotReq> slot_req, GearRarity rar) {
        int num = 0;
        for (Map.Entry<GearRarityType, Integer> en : rarities.entrySet()) {
            num += en.getValue() * 10;
        }
        num += slot_req.size() * 10;

        num *= rar.stat_percents.max / 100F;

        return num;
    }

    public GearRarity getRarity() {
        return ExileDB.GearRarities().get(rar);
    }

    public static class OmenSlotReq {
        public String slot;
        public GearRarityType rtype;

        public OmenSlotReq(String slot, GearRarityType rtype) {
            this.slot = slot;
            this.rtype = rtype;
        }
    }
}
