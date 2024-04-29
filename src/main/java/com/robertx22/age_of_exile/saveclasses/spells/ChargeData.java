package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        Load.player(player).playerDataSync.setDirty();

    }

    public int getCharges(String id) {
        return charges.getOrDefault(id, 0);
    }

    public void addCharge(String id, Spell spell) {
        int charge = Mth.clamp(charges.getOrDefault(id, 0) + 1, 0, spell.config.charges);
        charges.put(id, charge);
    }

    public void addOneCharges() {

        for (Map.Entry<String, Integer> en : charge_regen.entrySet()) {
            charge_regen.put(en.getKey(), 100000);
        }

    }

    public void onTicks(Player player, int ticks) {

        if (player.level().isClientSide) {
            return;
        }

        boolean sync = false;


        List<String> chargesadded = new ArrayList<>(); // no duplicate charge regen

        for (SpellCastingData.InsertedSpell data : Load.player(player).spellCastingData.getAllHotbarSpells()) {


            Spell s = data.getData().getSpell();

            String id = s.config.charge_name;

            if (getCharges(id) >= s.config.charges) {
                continue;
            }

            if (!chargesadded.contains(id)) {

                if (s.config.charges > 0) {

                    chargesadded.add(id);

                    float regen = charge_regen.getOrDefault(s.config.charge_name, 0);
                    regen += (float) ticks * (float) Load.Unit(player).getUnit().getCalculatedStat(SpellChangeStats.COOLDOWN_REDUCTION.get()).getMultiplier();
                    charge_regen.put(s.config.charge_name, (int) (regen));

                    if (charge_regen.get(id) >= s.config.charge_regen) {
                        charge_regen.put(id, 0);
                        addCharge(id, s);

                        sync = true;

                    }

                }

            }

        }

        if (sync) {

            Load.player(player).playerDataSync.setDirty();
        }
    }
}
