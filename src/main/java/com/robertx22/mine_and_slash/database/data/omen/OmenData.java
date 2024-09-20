package com.robertx22.mine_and_slash.database.data.omen;

import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarityType;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_parts.AffixData;
import com.robertx22.mine_and_slash.saveclasses.unit.GearData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

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

    public List<MutableComponent> getReqTooltip() {
        List<MutableComponent> all = new ArrayList<>();

        for (Map.Entry<GearRarityType, Integer> en : rarities.entrySet()) {
            all.add(en.getKey().word.locName().append(": " + en.getValue()).withStyle(en.getKey().color));
        }
        all.add(Component.empty());

        for (OmenSlotReq req : slot_req) {
            var rar = req.rtype.word.locName().withStyle(req.rtype.color);
            all.add(ExileDB.GearSlots().get(req.slot).locName().append(": ").withStyle(ChatFormatting.GREEN).append(rar));
        }
        return all;

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

    public int calcPiecesEquipped(Player p) {

        HashMap<GearRarityType, Integer> map = new HashMap<>();

        for (GearData gear : Load.Unit(p).equipmentCache.getGear()) {
            if (gear.gear != null) {
                var type = gear.gear.getRarity().type;

                var opt = slot_req.stream().filter(e -> e.slot.equals(gear.gear.GetBaseGearType().gear_slot)).findAny();

                if (opt.isPresent()) {
                    if (type != opt.get().rtype) {
                        continue;
                    }
                }
                if (map.getOrDefault(type, 0) < rarities.getOrDefault(type, 0)) {
                    map.put(type, map.getOrDefault(type, 0) + 1);
                }
            }
        }
        int num = 0;

        for (Map.Entry<GearRarityType, Integer> en : map.entrySet()) {
            num += en.getValue();
        }
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
