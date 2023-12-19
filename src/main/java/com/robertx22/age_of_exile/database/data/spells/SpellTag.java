package com.robertx22.age_of_exile.database.data.spells;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;

import java.util.Locale;

public enum SpellTag implements IAutoLocName {
    projectile("Projectile"),
    magic("Magic Skill"),
    weapon_skill("Weapon Skill"),
    movement("Movement"),
    damage("Damage"),
    minion_explode("Minion Explode"),
    summon("Summon"),
    has_pet_ability("Has Pet Ability"),
    heal("Heal"),
    rejuvenate("Rejuvenate"),
    arrow("Arrow"),
    curse("Curse"),
    shield("Shield"),
    shout("Shout"),
    trap("Trap"),
    beast("Beast"),
    song("Song"),
    chaining("Chaining"),
    golem("Golem"),
    area("Area"),
    staff_spell("Staff Spell"),
    totem("Totem");

    public String locname;

    SpellTag(String locname) {
        this.locname = locname;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Misc;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".spell_tag." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
