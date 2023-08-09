package com.robertx22.age_of_exile.database.registrators;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.*;
import com.robertx22.age_of_exile.database.data.stats.types.core_stats.AllAttributes;
import com.robertx22.age_of_exile.database.data.stats.types.defense.*;
import com.robertx22.age_of_exile.database.data.stats.types.generated.*;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuality;
import com.robertx22.age_of_exile.database.data.stats.types.loot.TreasureQuantity;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.data.stats.types.misc.DamageTakenToMana;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.offense.FullSwingDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.offense.WeaponDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.RegeneratePercentStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.Blood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.BloodUser;
import com.robertx22.age_of_exile.database.data.stats.types.resources.blood.HealthRestorationToBlood;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.Energy;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldHeal;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShieldRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCostReduction;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.database.data.stats.types.totem.ProjectileTotem;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.IGenerated;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.ArrayList;
import java.util.List;

public class StatsRegister implements ExileRegistryInit {

    @Override
    public void registerAll() {

        // SpecialStats.init();

        List<Stat> All = new ArrayList<>();

        List<Stat> generated = new ArrayList<Stat>() {
            {
                {

                    for (Ailment ailment : ExileDB.Ailments().getList()) {
                        add(new AilmentChance(ailment));
                        add(new AilmentDamage(ailment));
                        add(new AilmentDuration(ailment));
                        add(new AilmentEffectStat(ailment));
                        add(new AilmentResistance(ailment));
                        add(new AilmentProcStat(ailment));
                    }


                    add(RegeneratePercentStat.HEALTH);
                    add(RegeneratePercentStat.MANA);
                    add(ArmorPenetration.getInstance());


                    add(AllAilmentDamage.getInstance());
                    add(HitDamage.getInstance());
                    add(BlockChance.getInstance());

                    add(AuraCostReduction.getInstance());
                    add(AuraEffect.getInstance());

                    add(ProjectileTotem.getInstance());
                    
                    add(WeaponDamage.getInstance());

                    add(FullSwingDamage.getInstance());

                    add(MagicShield.getInstance());
                    add(MagicShieldRegen.getInstance());
                    add(MagicShieldHeal.getInstance());

                    add(SpellDodge.getInstance());

                    add(TreasureQuality.getInstance());
                    add(TreasureQuantity.getInstance());

                    add(new BonusAttackDamage(Elements.Physical));
                    add(new ElementalResist(Elements.Physical));
                    add(new ElementalPenetration(Elements.Physical));
                    add(new ElementalFocus(Elements.Physical));
                    add(new PhysicalToElement(Elements.Physical));

                    add(AllAttributes.getInstance());
                    add(SkillDamage.getInstance());

                    add(ExtraMobDropsStat.getInstance());
                    add(BonusExp.getInstance());
                    add(DamageTakenToMana.getInstance());

                    add(new UnknownStat());

                    // Resources
                    add(DamageAbsorbedByMana.getInstance());
                    add(HealthRestorationToBlood.getInstance());
                    add(Blood.getInstance());
                    add(BloodUser.getInstance());
                    add(Health.getInstance());
                    add(HealthRegen.getInstance());

                    add(Mana.getInstance());
                    add(ManaRegen.getInstance());

                    add(EnergyRegen.getInstance());
                    add(Energy.getInstance());
                    // Resources

                    add(Armor.getInstance());
                    add(DodgeRating.getInstance());
                    add(DamageShield.getInstance());


                }
            }
        };

        for (Stat stat : generated) {
            if (stat instanceof IGenerated) {
                for (Stat gen : ((IGenerated<Stat>) stat).generateAllPossibleStatVariations()) {
                    All.add(gen);
                }
            } else {
                All.add(stat);
            }
        }
        All.forEach(x -> x.registerToExileRegistry());

    }

}
