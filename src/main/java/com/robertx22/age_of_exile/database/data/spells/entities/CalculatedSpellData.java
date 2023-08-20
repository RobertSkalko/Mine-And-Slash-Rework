package com.robertx22.age_of_exile.database.data.spells.entities;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpellStatsCalculationEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class CalculatedSpellData {

    public EventData data = new EventData();

    public String caster_uuid = "";
    public String spell_id = "";
    public int lvl = 1;

    public CalculatedSpellData(SpellStatsCalculationEvent event) {
    }


    // this is buggy in dev because the player's UUID changes (random name each time game is started)
    // so after restart of game, the caster is null
    // but works fine outside of dev
    public LivingEntity getCaster(Level world) {
        try {
            return Utilities.getLivingEntityByUUID(world, UUID.fromString(caster_uuid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Spell getSpell() {
        if (!ExileDB.Spells().isRegistered(spell_id)) {
            return null;
        }
        return ExileDB.Spells().get(spell_id);
    }

}
