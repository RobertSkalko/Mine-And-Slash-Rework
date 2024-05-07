package com.robertx22.age_of_exile.aoe_data.database.mob_affixes;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.ResourceStats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.affixes.Affix;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusPhysicalAsElemental;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysicalToElement;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.ChatFormatting;

import static com.robertx22.age_of_exile.uncommon.enumclasses.Elements.*;

public class MobAffixes implements ExileRegistryInit {

    public static String FULL_COLD = "full_cold";
    public static String FULL_FIRE = "full_fire";
    public static String FULL_LIGHTNING = "full_fire";

    static void eleAffix(String name, Elements element) {
        new MobAffix(element.guidName + "_mob_affix", name, element.format, Affix.Type.prefix)
                .setMods(
                        new StatMod(25, 25, new PhysicalToElement(element)),
                        new StatMod(100, 100, new BonusPhysicalAsElemental(element), ModType.FLAT),
                        new StatMod(10, 10, ExtraMobDropsStat.getInstance()))
                .setWeight(2000)
                .addToSerializables();
    }

    static void fullEle(Elements element) {
        new MobAffix("full_" + element.guidName, "Of " + element.dmgName, element.format, Affix.Type.suffix)
                .setMods(
                        new StatMod(100, 100, new PhysicalToElement(element)),
                        new StatMod(50, 50, new BonusPhysicalAsElemental(element), ModType.FLAT),
                        new StatMod(25, 25, new ElementalResist(element))
                )
                .setWeight(0)
                .addToSerializables();
    }

    @Override
    public void registerAll() {

        eleAffix("Freezing", Cold);
        eleAffix("Flaming", Fire);
        eleAffix("Lightning", Nature);

        // eleAffix("Smiting", Holy);
        eleAffix("Poisoned", Shadow);


        new MobAffix("winter", "Winter Lord", Cold.format, Affix.Type.prefix)
                .setMods(
                        new StatMod(15, 15, Health.getInstance()),
                        new StatMod(5, 5, new AilmentChance(Ailments.FREEZE)),
                        new StatMod(75, 75, new PhysicalToElement(Cold)),
                        new StatMod(100, 100, new BonusPhysicalAsElemental(Cold), ModType.FLAT),
                        new StatMod(20, 20, ExtraMobDropsStat.getInstance()))
                .icon(Cold.format + Cold.icon)
                .setWeight(250)
                .addToSerializables();

        new MobAffix("fire_lord", "Fire Lord", Fire.format, Affix.Type.prefix)
                .setMods(
                        new StatMod(15, 15, Health.getInstance()),
                        new StatMod(5, 5, new AilmentChance(Ailments.BURN)),
                        new StatMod(75, 75, new PhysicalToElement(Fire)),
                        new StatMod(100, 100, new BonusPhysicalAsElemental(Fire), ModType.FLAT),
                        new StatMod(20, 20, ExtraMobDropsStat.getInstance()))
                .icon(Fire.format + Fire.icon)
                .setWeight(250)
                .addToSerializables();

        new MobAffix("nature_lord", "Chaos Lord", Shadow.format, Affix.Type.prefix)
                .setMods(
                        new StatMod(15, 15, Health.getInstance()),
                        new StatMod(5, 5, new AilmentChance(Ailments.POISON)),
                        new StatMod(75, 75, new PhysicalToElement(Shadow)),
                        new StatMod(100, 100, new BonusPhysicalAsElemental(Shadow), ModType.FLAT),
                        new StatMod(20, 20, ExtraMobDropsStat.getInstance()))
                .icon(Shadow.format + Shadow.icon)
                .setWeight(250)
                .addToSerializables();

        new MobAffix("phys_lord", "Fighter Lord", ChatFormatting.GRAY, Affix.Type.prefix)
                .setMods(
                        new StatMod(15, 15, Health.getInstance()),
                        new StatMod(100, 100, new BonusPhysicalAsElemental(Physical)),
                        new StatMod(20, 20, ExtraMobDropsStat.getInstance()))
                .setWeight(250)
                .addToSerializables();

        new MobAffix("vampire", "Vampriric", ChatFormatting.RED, Affix.Type.prefix)
                .setMods(new StatMod(25, 25, Health.getInstance()),
                        new StatMod(15, 15, ResourceStats.LIFESTEAL.get()),
                        new StatMod(15, 15, ExtraMobDropsStat.getInstance()))
                .setWeight(500)
                .addToSerializables();


        fullEle(Cold);
        fullEle(Nature);
        fullEle(Fire);
        fullEle(Shadow);
        // fullEle(Holy);


    }
}
