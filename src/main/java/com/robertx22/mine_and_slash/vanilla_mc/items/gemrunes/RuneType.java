package com.robertx22.mine_and_slash.vanilla_mc.items.gemrunes;

import com.robertx22.mine_and_slash.aoe_data.database.stats.EffectStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.OffenseStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.ResourceStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.SpellChangeStats;
import com.robertx22.mine_and_slash.aoe_data.database.stats.base.ResourceAndAttack;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.Armor;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.ArmorPenetration;
import com.robertx22.mine_and_slash.database.data.stats.types.defense.DodgeRating;
import com.robertx22.mine_and_slash.database.data.stats.types.generated.ElementalResist;
import com.robertx22.mine_and_slash.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.Health;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.mine_and_slash.database.data.stats.types.special.SpecialStats;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraCapacity;
import com.robertx22.mine_and_slash.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;

import java.util.function.Supplier;

// todo make it easier for me to know which runes are rare, maybe something like A_RUNE_MYTHIC ?
// todo add unused runes if datapacks want to "use" them
public enum RuneType {
    YUN(100, "yun", "Yun", 4, 0.5F, () -> StatPerType.of()
            .addArmor(AuraEffect.getInstance().mod(1, 5))
            .addJewerly(SpecialStats.BETTER_FOOD_BUFFS.mod(2, 10))
            .addWeapon(SpellChangeStats.COOLDOWN_REDUCTION.get().mod(3, 10))

    ),
    VEN(100, "ven", "Ven", 4, 0.5F, () -> StatPerType.of()
            .addArmor(EffectStats.EFFECT_DURATION_YOU_CAST.get().mod(1, 5))
            .addJewerly(AuraCapacity.getInstance().mod(1, 5))
            .addWeapon(OffenseStats.SUMMON_DAMAGE.get().mod(5, 25))
    ),
    NOS(1000, "nos", "Nos", 0, 0F, () -> StatPerType.of()
            .addArmor(Health.getInstance().mod(1, 5).percent())
            .addJewerly(HealthRegen.getInstance().mod(0.2F, 1))
            .addWeapon(ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.health, AttackType.hit)).mod(0.2F, 1))
    ),
    MOS(1000, "mos", "Mos", 0, 0f, () -> StatPerType.of()
            .addArmor(MagicShield.getInstance().mod(1, 5).percent())
            .addJewerly(MagicShieldRegen.getInstance().mod(1, 5).percent())
            .addWeapon(ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.magic_shield, AttackType.hit)).mod(0.2F, 1))
    ),
    ITA(1000, "ita", "Ita", 0, 0f, () -> StatPerType.of()
            .addArmor(ManaRegen.getInstance().mod(1, 5).percent())
            .addJewerly(ManaRegen.getInstance().mod(0.2F, 1))
            .addWeapon(ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.mana, AttackType.hit)).mod(0.2F, 1))
    ),
    CEN(1000, "cen", "Cen", 1, 0.1F, () -> StatPerType.of()
            .addArmor(Armor.getInstance().mod(1, 5).percent())
            .addJewerly(Armor.getInstance().mod(1, 5).percent())
            .addWeapon(ArmorPenetration.getInstance().mod(5, 15).percent())
    ),
    FEY(1000, "fey", "Fey", 1, 0.2F, () -> StatPerType.of()
            .addArmor(EnergyRegen.getInstance().mod(1, 5).percent())
            .addJewerly(EnergyRegen.getInstance().mod(0.2F, 1))
            .addWeapon(ResourceStats.RESOURCE_ON_HIT.get(new ResourceAndAttack(ResourceType.energy, AttackType.hit)).mod(0.2F, 1))
    ),
    DOS(1000, "dos", "Dos", 1, 0.1F, () -> StatPerType.of()
            .addArmor(DodgeRating.getInstance().mod(1, 5).percent())
            .addJewerly(SpellChangeStats.PROJECTILE_SPEED.get().mod(2, 8))
            .addWeapon(OffenseStats.PROJECTILE_DAMAGE.get().mod(5, 15))
    ),
    ANO(1000, "ano", "Ano", 2, 0.2F, () -> StatPerType.of()
            .addArmor(HealthRegen.getInstance().mod(0.1F, 0.75F))
            .addJewerly(ManaRegen.getInstance().mod(0.1F, 1F))
            .addWeapon(EnergyRegen.getInstance().mod(1, 3))
    ),
    TOQ(1000, "toq", "Toq", 2, 0.3F, () -> StatPerType.of()
            .addArmor(OffenseStats.NON_CRIT_DAMAGE.get().mod(1, 5))
            .addJewerly(OffenseStats.CRIT_CHANCE.get().mod(1, 5))
            .addWeapon(OffenseStats.CRIT_CHANCE.get().mod(5, 10))
    ),
    ORU(500, "oru", "Oru", 4, 0.4F, () -> StatPerType.of()
            .addArmor(OffenseStats.CRIT_DAMAGE.get().mod(1, 5))
            .addJewerly(OffenseStats.CRIT_DAMAGE.get().mod(2, 10))
            .addWeapon(OffenseStats.CRIT_DAMAGE.get().mod(5, 15))
    ),
    WIR(200, "wir", "Wir", 4, 0.4F, () -> StatPerType.of()
            .addArmor(OffenseStats.TOTAL_DAMAGE.get().mod(5, 10))
            .addJewerly(OffenseStats.TOTAL_DAMAGE.get().mod(5, 10))
            .addWeapon(OffenseStats.TOTAL_DAMAGE.get().mod(5, 10))
    ),
    ENO(1000, "eno", "Eno", 3, 0.3F, () -> StatPerType.of()
            .addArmor(SpellChangeStats.MANA_COST.get().mod(-2, -5))
            .addJewerly(SpellChangeStats.MANA_COST.get().mod(-3, -7))
            .addWeapon(SpellChangeStats.MANA_COST.get().mod(-4, -10))
    ),
    HAR(1000, "har", "Har", 3, 0.3f, () -> StatPerType.of()
            .addArmor(ResourceStats.HEAL_STRENGTH.get().mod(3, 10))
            .addJewerly(ResourceStats.HEAL_STRENGTH.get().mod(5, 12))
            .addWeapon(ResourceStats.HEAL_STRENGTH.get().mod(6, 20))
    ),
    XER(1000, "xer", "Xer", 3, 0.4f, () -> StatPerType.of()
            .addArmor(OffenseStats.SUMMON_DAMAGE.get().mod(3, 8))
            .addJewerly(OffenseStats.SUMMON_DAMAGE.get().mod(4, 12))
            .addWeapon(OffenseStats.SUMMON_DAMAGE.get().mod(5, 15))
    ),
    OWD(0, "owd", "Owd", 0, 0.0f, () -> StatPerType.of()
            .addArmor(OffenseStats.ACCURACY.get().mod(3, 15).percent())
            .addJewerly(OffenseStats.ACCURACY.get().mod(3, 15).percent())
            .addWeapon(OffenseStats.ACCURACY.get().mod(5, 25).percent())
    ),
    NET(0, "net", "Net", 1, 0.15f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Fire).mod(5, 25))
            .addJewerly(new ElementalResist(Elements.Fire).mod(5, 25))
            .addWeapon(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire).mod(1, 10))
    ),
    UND(0, "und", "Und", 2, 0.3f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Cold).mod(5, 25))
            .addJewerly(new ElementalResist(Elements.Cold).mod(5, 25))
            .addWeapon(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold).mod(1, 10))
    ),
    BRI(0, "bri", "Bri", 3, 0.4f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Nature).mod(5, 25))
            .addJewerly(new ElementalResist(Elements.Nature).mod(5, 25))
            .addWeapon(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Nature).mod(1, 10))
    ),
    DAW(0, "daw", "Daw", 3, 0.4f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Shadow).mod(3, 15))
            .addJewerly(new ElementalResist(Elements.Shadow).mod(3, 15))
            .addWeapon(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire).mod(1, 10))
    ),
    END(0, "end", "End", 4, 0.55f, () -> StatPerType.of()
            .addArmor(new ElementalResist(Elements.Physical).mod(5, 25))
            .addJewerly(new ElementalResist(Elements.Physical).mod(5, 25))
            .addWeapon(OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Physical).mod(1, 10))
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
