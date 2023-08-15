package com.robertx22.age_of_exile.aoe_data.database.unique_gears.uniques.jewelry;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.aoe_data.database.stats.old.DatapackStats;
import com.robertx22.age_of_exile.aoe_data.database.unique_gears.UniqueGearBuilder;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentDuration;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.offense.SkillDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.energy.EnergyRegen;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.ManaRegen;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraCostReduction;
import com.robertx22.age_of_exile.database.data.stats.types.spirit.AuraEffect;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;

import java.util.Arrays;

public class UniqueNecklaces implements ExileRegistryInit {

    @Override
    public void registerAll() {

        UniqueGearBuilder.of(
                        "rabbit_paw",
                        "Rabbit's Paw",
                        BaseGearTypes.NECKLACE)
                .stats(Arrays.asList(
                        new StatMod(2, 10, DatapackStats.MOVE_SPEED, ModType.FLAT),
                        new StatMod(5, 25, EnergyRegen.getInstance(), ModType.PERCENT),
                        new StatMod(2, 6, DatapackStats.DEX, ModType.FLAT)
                ))
                .setReplacesName()
                .build();

        UniqueGearBuilder.of(
                        "ghast_necklace",
                        "Ghast Tear",
                        BaseGearTypes.NECKLACE)

                .stats(Arrays.asList(
                        new StatMod(25, 25, DatapackStats.MANA_PER_10_INT, ModType.FLAT),
                        new StatMod(15, 25, SkillDamage.getInstance(), ModType.FLAT),
                        new StatMod(1, 3, DatapackStats.STR, ModType.FLAT),
                        new StatMod(2, 6, DatapackStats.DEX, ModType.FLAT)
                ))

                .build();

        UniqueGearBuilder.of(
                        "skull_of_spirits",
                        "Skull of Spirits",
                        BaseGearTypes.NECKLACE)
                .stats(Arrays.asList(
                        new StatMod(15, 50, Stats.SUMMON_DAMAGE.get(), ModType.FLAT),
                        new StatMod(10, 30, ManaRegen.getInstance(), ModType.PERCENT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Cold), ModType.FLAT),
                        new StatMod(-25, -25, new ElementalResist(Elements.Fire), ModType.FLAT)
                ))
                .build();

        UniqueGearBuilder.of("futility_of_suffering", "Futility of Suffering", BaseGearTypes.NECKLACE)
                .stats(Arrays.asList(
                        new StatMod(50, 100, new AilmentChance(Ailments.POISON), ModType.FLAT),
                        new StatMod(50, 50, new AilmentDuration(Ailments.POISON), ModType.FLAT),
                        new StatMod(-100, -100, ManaRegen.getInstance(), ModType.MORE),
                        new StatMod(-100, -100, EnergyRegen.getInstance(), ModType.MORE)
                ))

                .build();

        UniqueGearBuilder.of("the_unseen_eye", "Unseen Eye", BaseGearTypes.NECKLACE)
                .keepsBaseName()
                .stats(Arrays.asList(
                        AuraCostReduction.getInstance().mod(5, 10),
                        AuraEffect.getInstance().mod(-10, 10),
                        DodgeRating.getInstance().mod(5, 25).percent(),
                        new ElementalResist(Elements.Chaos).mod(25, 25)
                ))
                .build();

        UniqueGearBuilder.of("master_torture", "Master of Torture", BaseGearTypes.NECKLACE)
                .keepsBaseName()
                .stat(Stats.DAMAGE_TO_CURSED.get().mod(25, 50))
                .stat(new ElementalResist(Elements.Chaos).mod(10, 25))
                .stat(new ElementalResist(Elements.Cold).mod(10, 25))
                .stat(new ElementalResist(Elements.Fire).mod(10, 25))
                .build();

        
    }
}
