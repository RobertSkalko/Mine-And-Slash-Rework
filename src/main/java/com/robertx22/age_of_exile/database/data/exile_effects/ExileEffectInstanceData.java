package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class ExileEffectInstanceData {

    public CalculatedSpellData calcSpell = new CalculatedSpellData(null);

    public String caster_uuid = "";
    public String spell_id = "";
    public int stacks = 1;
    public float str_multi = 1;


    public Spell getSpell() {
        return ExileDB.Spells()
                .get(spell_id);
    }

    public LivingEntity getCaster(Level world) {
        try {
            return Utilities.getLivingEntityByUUID(world, UUID.fromString(caster_uuid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
