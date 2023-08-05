package com.robertx22.age_of_exile.database.data.spells.entities;

import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.UUID;

public class EntitySavedSpellData {

    String caster_uuid = "";
    public String spell_id = "";
    String exile_effect_id = "";
    public String item_id = "";
    public int lvl = 1;
    public float area_multi = 1;
    public float proj_speed_multi = 1;
    public int extra_proj = 0;
    public boolean pierce = false;

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

    public static EntitySavedSpellData create(int lvl, LivingEntity caster, ExileEffect exEffect) {
        Objects.requireNonNull(caster);

        EntitySavedSpellData data = new EntitySavedSpellData();
        data.exile_effect_id = exEffect.GUID();
        data.lvl = lvl;

        data.caster_uuid = caster.getUUID()
                .toString();

        return data;
    }

    public static EntitySavedSpellData create(int lvl, LivingEntity caster, Spell spell) {
        Objects.requireNonNull(caster);

        EntitySavedSpellData data = new EntitySavedSpellData();
        data.spell_id = spell.GUID();
        data.lvl = lvl;
        data.caster_uuid = caster.getUUID()
                .toString();

        return data;
    }

    public Spell getSpell() {
        return ExileDB.Spells()
                .get(spell_id);
    }

}
