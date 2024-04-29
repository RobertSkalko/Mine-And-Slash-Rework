package com.robertx22.age_of_exile.database.data.aura;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.AutoHashClass;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.effects.defense.MaxElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.BlockChance;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AuraGems {
    public static List<AuraInfo> ALL = new ArrayList<>();

    public static class AuraInfo extends AutoHashClass {
        public String id;
        public String name;

        public AuraInfo(String id, String name) {
            this.id = id;
            this.name = name;
            ALL.add(this);
        }

        public AuraInfo(AuraGem gem) {
            this.id = gem.id;
            this.name = id;
        }


        public AuraGem getAura() {
            return ExileDB.AuraGems().get(id);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

    }

    public static AuraInfo FIRE_RESIST = new AuraInfo("fire_res", "Flame Barrier");
    public static AuraInfo COLD_RESIST = new AuraInfo("cold_res", "Frost Barrier");
    public static AuraInfo LIGHTNING_RESIST = new AuraInfo("light_res", "Thunder Barrier");
    public static AuraInfo CHAOS_RESIST = new AuraInfo("chaos_res", "Chaos Barrier");
    public static AuraInfo ELEMENT_RESIST = new AuraInfo("ele_res", "Elemental Barrier");


    public static AuraInfo ARMOR = new AuraInfo("armor", "Armor");
    public static AuraInfo DODGE = new AuraInfo("dodge", "Dodge");
    public static AuraInfo MAGIC_SHIELD = new AuraInfo("magic_shield", "Magic Shield");
    public static AuraInfo BLOCK = new AuraInfo("block", "Guardian");

    public static AuraInfo magic_shield_reg = new AuraInfo("magic_shield_reg", "MS Regen");
    public static AuraInfo health_reg = new AuraInfo("health_reg", "Health Regen");
    public static AuraInfo energy_reg = new AuraInfo("energy_reg", "Energy Regen");
    public static AuraInfo mana_reg = new AuraInfo("mana_reg", "Mana Regen");

    public static AuraInfo cold_damage = new AuraInfo("cold_damage", "Cold Damage");
    public static AuraInfo fire_damage = new AuraInfo("fire_damage", "Fire Damage");
    public static AuraInfo lightning_damage = new AuraInfo("lightning_damage", "Lightning Damage");
    public static AuraInfo physical_damage = new AuraInfo("physical_damage", "Physical Damage");
    public static AuraInfo chaos_damage = new AuraInfo("chaos_damage", "Chaos Damage");

    public static AuraInfo cold_ailment = new AuraInfo("cold_ailment", "Decree of Frost");
    public static AuraInfo fire_ailment = new AuraInfo("fire_ailment", "Decree of Fire");
    public static AuraInfo lightning_ailment = new AuraInfo("lightning_ailment", "Decree of Lightning");
    public static AuraInfo physical_ailment = new AuraInfo("physical_ailment", "Decree of Pain");
    public static AuraInfo chaos_ailment = new AuraInfo("chaos_ailment", "Decree of Chaos");
    public static AuraInfo summon_dmg = new AuraInfo("summon_dmg", "Overlord");

    // i should probably standardize this somehow?
    // keys like id-name and maybe element info are needed to make 1 registry generated based on the other
    public static void initKeys() {

    }

    public static void init() {


        // flat ele + ailment
        new AuraGem(cold_ailment, PlayStyle.DEX, 0.4F, Arrays.asList(
                new StatMod(10, 50, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.FLAT),
                new StatMod(5, 25, new AilmentChance(Ailments.FREEZE), ModType.FLAT)
        )).levelReq(10).addToSerializables();
        new AuraGem(fire_ailment, PlayStyle.STR, 0.4F, Arrays.asList(
                new StatMod(10, 50, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.FLAT),
                new StatMod(5, 25, new AilmentChance(Ailments.BURN), ModType.FLAT)
        )).levelReq(10).addToSerializables();
        new AuraGem(lightning_ailment, PlayStyle.INT, 0.4F, Arrays.asList(
                new StatMod(10, 50, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Nature), ModType.FLAT),
                new StatMod(5, 25, new AilmentChance(Ailments.ELECTRIFY), ModType.FLAT)
        )).levelReq(10).addToSerializables();
        new AuraGem(physical_ailment, PlayStyle.STR, 0.4F, Arrays.asList(
                new StatMod(10, 50, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.FLAT),
                new StatMod(5, 25, new AilmentChance(Ailments.BLEED), ModType.FLAT)
        )).levelReq(10).addToSerializables();
        new AuraGem(chaos_ailment, PlayStyle.DEX, 0.4F, Arrays.asList(
                new StatMod(10, 50, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Shadow), ModType.FLAT),
                new StatMod(5, 25, new AilmentChance(Ailments.POISON), ModType.FLAT)
        )).levelReq(10).addToSerializables();

        new AuraGem(summon_dmg, PlayStyle.INT, 0.3F, Arrays.asList(
                new StatMod(10, 40, OffenseStats.SUMMON_DAMAGE.get(), ModType.MORE)
        )).levelReq(20).addToSerializables();


        // ele dmg
        new AuraGem(cold_damage, PlayStyle.DEX, 0.4F, Arrays.asList(
                new StatMod(10, 35, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Cold), ModType.MORE)
        )).levelReq(30).addToSerializables();
        new AuraGem(fire_damage, PlayStyle.STR, 0.4F, Arrays.asList(
                new StatMod(10, 35, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Fire), ModType.MORE)
        )).levelReq(30).addToSerializables();
        new AuraGem(lightning_damage, PlayStyle.INT, 0.4F, Arrays.asList(
                new StatMod(10, 35, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Nature), ModType.MORE)
        )).levelReq(30).addToSerializables();
        new AuraGem(physical_damage, PlayStyle.STR, 0.4F, Arrays.asList(
                new StatMod(10, 35, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Physical), ModType.MORE)
        )).levelReq(30).addToSerializables();
        new AuraGem(chaos_damage, PlayStyle.DEX, 0.4F, Arrays.asList(
                new StatMod(10, 35, OffenseStats.ELEMENTAL_DAMAGE.get(Elements.Shadow), ModType.MORE)
        )).levelReq(30).addToSerializables();


        // regens
        new AuraGem(magic_shield_reg, PlayStyle.INT, 0.25F, Arrays.asList(
                new StatMod(1, 5, MagicShieldRegen.getInstance(), ModType.FLAT)
        )).levelReq(1).addToSerializables();
        new AuraGem(health_reg, PlayStyle.STR, 0.25F, Arrays.asList(
                new StatMod(1, 5, HealthRegen.getInstance(), ModType.FLAT)
        )).levelReq(1).addToSerializables();
        new AuraGem(energy_reg, PlayStyle.DEX, 0.25F, Arrays.asList(
                new StatMod(1, 5, EnergyRegen.getInstance(), ModType.FLAT)
        )).levelReq(1).addToSerializables();
        new AuraGem(mana_reg, PlayStyle.INT, 0.25F, Arrays.asList(
                new StatMod(1, 5, ManaRegen.getInstance(), ModType.FLAT)
        )).levelReq(1).addToSerializables();


        // defenses
        new AuraGem(ARMOR, PlayStyle.STR, 0.4F, Arrays.asList(
                new StatMod(10, 30, Armor.getInstance(), ModType.MORE),
                new StatMod(5, 15, Armor.getInstance(), ModType.FLAT)
        )).levelReq(10).addToSerializables();

        new AuraGem(DODGE, PlayStyle.DEX, 0.4F, Arrays.asList(
                new StatMod(10, 30, DodgeRating.getInstance(), ModType.MORE),
                new StatMod(5, 15, DodgeRating.getInstance(), ModType.FLAT)
        )).levelReq(10).addToSerializables();

        new AuraGem(MAGIC_SHIELD, PlayStyle.INT, 0.4F, Arrays.asList(
                new StatMod(12, 35, MagicShield.getInstance(), ModType.MORE),
                new StatMod(2, 5, MagicShield.getInstance(), ModType.FLAT)
        )).levelReq(10).addToSerializables();

        new AuraGem(BLOCK, PlayStyle.INT, 0.4F, Arrays.asList(
                new StatMod(5, 15, BlockChance.getInstance(), ModType.FLAT)
        )).levelReq(20).addToSerializables();


        // resist
        new AuraGem(ELEMENT_RESIST, PlayStyle.INT, 0.4F, Arrays.asList(
                new StatMod(15, 30, new ElementalResist(Elements.Fire), ModType.FLAT),
                new StatMod(15, 30, new ElementalResist(Elements.Cold), ModType.FLAT),
                new StatMod(15, 30, new ElementalResist(Elements.Nature), ModType.FLAT)
        )).levelReq(20).addToSerializables();

        new AuraGem(COLD_RESIST, PlayStyle.DEX, 0.35F, Arrays.asList(
                new StatMod(25, 50, new ElementalResist(Elements.Cold), ModType.FLAT),
                new StatMod(1, 5, new MaxElementalResist(Elements.Cold), ModType.FLAT)
        )).levelReq(20).addToSerializables();

        new AuraGem(FIRE_RESIST, PlayStyle.STR, 0.35F, Arrays.asList(
                new StatMod(25, 50, new ElementalResist(Elements.Fire), ModType.FLAT),
                new StatMod(1, 5, new MaxElementalResist(Elements.Fire), ModType.FLAT)
        )).levelReq(20).addToSerializables();

        new AuraGem(LIGHTNING_RESIST, PlayStyle.INT, 0.35F, Arrays.asList(
                new StatMod(25, 50, new ElementalResist(Elements.Nature), ModType.FLAT),
                new StatMod(1, 5, new MaxElementalResist(Elements.Nature), ModType.FLAT)
        )).levelReq(20).addToSerializables();

        new AuraGem(CHAOS_RESIST, PlayStyle.INT, 0.35F, Arrays.asList(
                new StatMod(25, 50, new ElementalResist(Elements.Shadow), ModType.FLAT),
                new StatMod(1, 5, new MaxElementalResist(Elements.Shadow), ModType.FLAT)
        )).levelReq(20).addToSerializables();

        WatcherEyeAffixes.init();
    }
}
