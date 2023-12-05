package com.robertx22.age_of_exile.database.data.spells;

public enum SpellTag {
    projectile("Projectile"),
    magic("Magic Skill"),
    weapon_skill("Weapon Skill"),
    movement("Movement"),
    damage("Damage"),
    minion_explode("Minion Explode"),
    summon("Summon"),
    has_pet_ability("Has Pet Ability"),
    heal("Heal"),
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
}
