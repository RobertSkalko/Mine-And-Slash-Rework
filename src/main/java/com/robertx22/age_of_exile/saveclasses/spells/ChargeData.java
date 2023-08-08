package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.player.EntitySpellData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChargeData {
    private HashMap<String, Integer> charges = new HashMap<>();

    private HashMap<String, Integer> charge_regen = new HashMap<>();

    public int getCurrentTicksChargingOf(String id) {
        return charge_regen.getOrDefault(id, 0);
    }

    public boolean hasCharge(String id) {
        return charges.getOrDefault(id, 0) > 0;
    }

    public void spendCharge(Player player, String id) {

        if (player.level().isClientSide) {
            return;
        }

        charges.put(id, Mth.clamp(charges.getOrDefault(id, 0) - 1, 0, 100000));

        Load.spells(player)
                .syncToClient(player);

    }

    public int getCharges(String id) {
        return charges.getOrDefault(id, 0);
    }

    public void addCharge(String id, Spell spell) {
        int charge = Mth.clamp(charges.getOrDefault(id, 0) + 1, 0, spell.config.charges);
        charges.put(id, charge);
    }

    public void onTicks(Player player, int ticks) {

        if (player.level().isClientSide) {
            return;
        }

        boolean sync = false;

        EntitySpellData.ISpellsCap sdata = Load.spells(player);

        List<String> chargesadded = new ArrayList<>(); // no duplicate charge regen

        for (Spell s : sdata.getSpells()) {

            String id = s.config.charge_name;

            if (getCharges(id) >= s.config.charges) {
                continue;
            }

            if (!chargesadded.contains(id)) {

                if (s.config.charges > 0) {

                    chargesadded.add(id);

                    charge_regen.put(s.config.charge_name, ticks + charge_regen.getOrDefault(s.config.charge_name, 0));

                    if (charge_regen.get(id) >= s.config.charge_regen) {
                        charge_regen.put(id, 0);
                        addCharge(id, s);

                        sync = true;

                    }

                }

            }

        }

        if (sync) {
            Load.spells(player)
                    .syncToClient(player);
        }
    }
}
