package com.robertx22.age_of_exile.vanilla_mc.items.gemrunes;

import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.special.SpecialStats;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

import java.util.function.Supplier;

// todo make it easier for me to know which runes are rare, maybe something like A_RUNE_MYTHIC ?
// todo add unused runes if datapacks want to "use" them
public enum RuneType {
    YUN(100, "yun", "Yun", 4, 0.5F, () -> StatPerType.of()
            .addArmor(AuraEffect.getInstance().mod(1, 5))
            .addJewerly(SpecialStats.BETTER_FOOD_BUFFS.mod(2, 10))
            .addWeapon(Stats.COOLDOWN_REDUCTION.get().mod(3, 10))

    ),
    VEN(100, "ven", "Ven", 4, 0.5F, () -> StatPerType.of()
            .addArmor(Stats.EFFECT_DURATION_YOU_CAST.get().mod(1, 5))
            .addJewerly(AuraCapacity.getInstance().mod(1, 5))
            .addWeapon(Stats.SUMMON_DAMAGE.get().mod(5, 25))
    ),
    NOS(1000, "nos", "Nos", 0, 0F, () -> StatPerType.of()
            .addArmor(Health.getInstance().mod(1, 5).percent())
            .addJewerly(HealthRegen.getInstance().mod(0.2F, 1))
            .addWeapon(Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.hit)).mod(0.2F, 1))
    ),
    MOS(1000, "mos", "Mos", 0, 0f, () -> StatPerType.of()
            .addArmor(MagicShield.getInstance().mod(1, 5).percent())
            .addJewerly(MagicShieldRegen.getInstance().mod(1, 5).percent())
            .addWeapon(Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.magic_shield, AttackType.hit)).mod(0.2F, 1))
    ),
    ITA(1000, "ita", "Ita", 0, 0f, () -> StatPerType.of()
            .addArmor(ManaRegen.getInstance().mod(1, 5).percent())
            .addJewerly(ManaRegen.getInstance().mod(0.2F, 1))
            .addWeapon(Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit)).mod(0.2F, 1))
    ),
    CEN(1000, "cen", "Cen", 1, 0.1F, () -> StatPerType.of()
            .addArmor(Armor.getInstance().mod(1, 5).percent())
            .addJewerly(Armor.getInstance().mod(1, 5).percent())
            .addWeapon(ArmorPenetration.getInstance().mod(5, 15).percent())
    ),
    FEY(1000, "fey", "Fey", 1, 0.2F, () -> StatPerType.of()
            .addArmor(EnergyRegen.getInstance().mod(1, 5).percent())
            .addJewerly(EnergyRegen.getInstance().mod(0.2F, 1))
            .addWeapon(Stats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.energy, AttackType.hit)).mod(0.2F, 1))
    ),
    DOS(1000, "dos", "Dos", 1, 0.1F, () -> StatPerType.of()
            .addArmor(DodgeRating.getInstance().mod(1, 5).percent())
            .addJewerly(Stats.PROJECTILE_SPEED.get().mod(2, 8))
            .addWeapon(Stats.PROJECTILE_DAMAGE.get().mod(5, 15))
    ),
    ANO(1000, "ano", "Ano", 2, 0.2F, () -> StatPerType.of()
            .addArmor(HealthRegen.getInstance().mod(0.1F, 0.75F))
            .addJewerly(ManaRegen.getInstance().mod(0.1F, 1F))
            .addWeapon(EnergyRegen.getInstance().mod(1, 3))
    ),
    TOQ(1000, "toq", "Toq", 2, 0.3F, () -> StatPerType.of()
            .addArmor(Stats.NON_CRIT_DAMAGE.get().mod(1, 5))
            .addJewerly(Stats.CRIT_CHANCE.get().mod(1, 5))
            .addWeapon(Stats.CRIT_CHANCE.get().mod(5, 10))
    ),
    ORU(500, "oru", "Oru", 4, 0.4F, () -> StatPerType.of()
            .addArmor(Stats.CRIT_DAMAGE.get().mod(1, 5))
            .addJewerly(Stats.CRIT_DAMAGE.get().mod(2, 10))
            .addWeapon(Stats.CRIT_DAMAGE.get().mod(5, 15))
    ),
    WIR(200, "wir", "Wir", 4, 0.4F, () -> StatPerType.of()
            .addArmor(Stats.TOTAL_DAMAGE.get().mod(5, 10))
            .addJewerly(Stats.TOTAL_DAMAGE.get().mod(5, 10))
            .addWeapon(Stats.TOTAL_DAMAGE.get().mod(5, 10))
    ),
    ENO(1000, "eno", "Eno", 3, 0.3F, () -> StatPerType.of()
            .addArmor(Stats.MANA_COST.get().mod(-2, -5))
            .addJewerly(Stats.MANA_COST.get().mod(-3, -7))
            .addWeapon(Stats.MANA_COST.get().mod(-4, -10))
    ),
    HAR(1000, "har", "Har", 3, 0.3f, () -> StatPerType.of()
            .addArmor(Stats.HEAL_STRENGTH.get().mod(3, 10))
            .addJewerly(Stats.HEAL_STRENGTH.get().mod(5, 12))
            .addWeapon(Stats.HEAL_STRENGTH.get().mod(6, 20))
    ),
    XER(1000, "xer", "Xer", 3, 0.4f, () -> StatPerType.of()
            .addArmor(Stats.SUMMON_DAMAGE.get().mod(3, 8))
            .addJewerly(Stats.SUMMON_DAMAGE.get().mod(4, 12))
            .addWeapon(Stats.SUMMON_DAMAGE.get().mod(5, 15))
    ),
    OWD(0, "owd", "Owd", 0, 0.0f, () -> StatPerType.of()
            .addArmor(Stats.ACCURACY.get().mod(3, 15).percent())
            .addJewerly(Stats.ACCURACY.get().mod(3, 15).percent())
            .addWeapon(Stats.ACCURACY.get().mod(5, 25).percent())
    ),
    NET(0, "net", "Net", 1, 0.15f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Fire).mod(5, 25))
            .addJewerly(new ElementalResist(Elements.Fire).mod(5, 25))
            .addWeapon(Stats.ELEMENTAL_DAMAGE.get(Elements.Fire).mod(1, 10))
    ),
    UND(0, "und", "Und", 2, 0.3f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Physical).mod(5, 25))
            .addJewerly(new ElementalResist(Elements.Physical).mod(5, 25))
            .addWeapon(Stats.ELEMENTAL_DAMAGE.get(Elements.Physical).mod(1, 10))
    ),
    BRI(0, "bri", "Bri", 3, 0.4f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Cold).mod(5, 25))
            .addJewerly(new ElementalResist(Elements.Cold).mod(5, 25))
            .addWeapon(Stats.ELEMENTAL_DAMAGE.get(Elements.Cold).mod(1, 10))
    ),
    DAW(0, "daw", "Daw", 3, 0.4f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Chaos).mod(3, 15))
            .addJewerly(new ElementalResist(Elements.Chaos).mod(3, 15))
            .addWeapon(Stats.ELEMENTAL_DAMAGE.get(Elements.Fire).mod(1, 10))
    ),
    END(0, "end", "End", 4, 0.55f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Lightning).mod(5, 25))
            .addJewerly(new ElementalResist(Elements.Lightning).mod(5, 25))
            .addWeapon(Stats.ELEMENTAL_DAMAGE.get(Elements.Lightning).mod(1, 10))
    ),
    SID(0, "sid", "Sid", 4, 0.65f, () -> StatPerType.of()
            .addArmor(TreasureQuantity.getInstance().mod(1, 8))
            .addJewerly(TreasureQuantity.getInstance().mod(1, 8))
            .addWeapon(TreasureQuantity.getInstance().mod(1, 8))
    );

    public String id;
    public String locName;
    public int tier;
    public int weight;
    public float lvlmin;

    public Supplier<StatPerType> stats;

    RuneType(int weight, String id, String locName, int tier, float lvl, Supplier<StatPerType> stats) {
        this.id = id;
        this.locName = locName;
        this.tier = tier;
        this.weight = weight;
        this.lvlmin = lvl;
        this.stats = stats;
    }

}
