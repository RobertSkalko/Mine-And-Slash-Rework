package com.robertx22.age_of_exile.aoe_data.database.mob_affixes;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailments;
import com.robertx22.age_of_exile.aoe_data.database.stats.Stats;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.mob_affixes.MobAffix;
import com.robertx22.age_of_exile.database.data.stats.types.ailment.AilmentChance;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusAttackDamage;
import com.robertx22.age_of_exile.database.data.stats.types.generated.PhysicalToElement;
import com.robertx22.age_of_exile.database.data.stats.types.misc.ExtraMobDropsStat;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.ChatFormatting;

import static com.robertx22.age_of_exile.uncommon.enumclasses.Elements.*;

public class MobAffixes implements ExileRegistryInit {

    public static String THORNY = "thorny_mobs";
    public static String FULL_COLD = "full_cold";
    public static String FULL_FIRE = "full_fire";
    public static String FULL_LIGHTNING = "full_fire";

    static void eleAffix(String name, Elements element) {
        new MobAffix(element.guidName + "_mob_affix", new BonusAttackDamage(element).getFormatAndIcon(), element.format)
                .setMods(
                        new StatMod(75, 75, new PhysicalToElement(element)),
                        new StatMod(1, 1, new BonusAttackDamage(element), ModType.FLAT),
                        new StatMod(10, 10, ExtraMobDropsStat.getInstance()))
                .setWeight(2000)
                .addToSerializables();
    }

    static void fullEle(Elements element) {
        new MobAffix("full_" + element.guidName, new PhysicalToElement(element).getFormatAndIcon(), element.format)
                .setMods(
                        new StatMod(100, 100, new PhysicalToElement(element)))
                .setWeight(0)
                .addToSerializables();
    }

    @Override
    public void registerAll() {

        eleAffix("Freezing", Cold);
        eleAffix("Flaming", Fire);
        eleAffix("Poisoned", Chaos);

        new MobAffix("reflect", Stats.DAMAGE_REFLECTED.get()
                .getFormatAndIcon(), Physical.format)
                .setMods(
                        new StatMod(15, 15, Stats.DAMAGE_REFLECTED.get()))
                .setWeight(200)
                .addToSerializables();

        new MobAffix("winter", new BonusAttackDamage(Cold).getFormatAndIcon(), Cold.format)
                .setMods(
                        new StatMod(15, 15, Health.getInstance()),
                        new StatMod(5, 5, new AilmentChance(Ailments.FREEZE)),
                        new StatMod(75, 75, new PhysicalToElement(Cold)),
                        new StatMod(1, 1, new BonusAttackDamage(Cold), ModType.FLAT),
                        new StatMod(20, 20, ExtraMobDropsStat.getInstance()))
                .icon(Cold.format + Cold.icon)
                .setWeight(250)
                .addToSerializables();

        new MobAffix("fire_lord", new BonusAttackDamage(Fire).getFormatAndIcon(), Fire.format)
                .setMods(
                        new StatMod(15, 15, Health.getInstance()),
                        new StatMod(5, 5, new AilmentChance(Ailments.BURN)),
                        new StatMod(75, 75, new PhysicalToElement(Fire)),
                        new StatMod(1, 1, new BonusAttackDamage(Fire), ModType.FLAT),
                        new StatMod(20, 20, ExtraMobDropsStat.getInstance()))
                .icon(Fire.format + Fire.icon)
                .setWeight(250)
                .addToSerializables();

        new MobAffix("nature_lord", new BonusAttackDamage(Chaos).getFormatAndIcon(), Chaos.format)
                .setMods(
                        new StatMod(15, 15, Health.getInstance()),
                        new StatMod(5, 5, new AilmentChance(Ailments.POISON)),
                        new StatMod(75, 75, new PhysicalToElement(Chaos)),
                        new StatMod(1, 1, new BonusAttackDamage(Chaos), ModType.FLAT),
                        new StatMod(20, 20, ExtraMobDropsStat.getInstance()))
                .icon(Chaos.format + Chaos.icon)
                .setWeight(250)
                .addToSerializables();

        new MobAffix("phys_lord", new BonusAttackDamage(Physical).getFormatAndIcon(), ChatFormatting.GRAY)
                .setMods(
                        new StatMod(15, 15, Health.getInstance()),
                        new StatMod(2, 2, new BonusAttackDamage(Physical)),
                        new StatMod(20, 20, ExtraMobDropsStat.getInstance()))
                .setWeight(250)
                .addToSerializables();

        new MobAffix("vampire", Stats.LIFESTEAL.get()
                .getFormatAndIcon(), ChatFormatting.RED)
                .setMods(new StatMod(25, 25, Health.getInstance()),
                        new StatMod(15, 15, Stats.LIFESTEAL.get()),
                        new StatMod(15, 15, ExtraMobDropsStat.getInstance()))
                .setWeight(500)
                .addToSerializables();


        // special ones that are only set from boss spells etc
        new MobAffix(THORNY, Stats.DAMAGE_REFLECTED.get()
                .getFormatAndIcon(), ChatFormatting.RED)
                .setMods(new StatMod(25, 50, Stats.DAMAGE_REFLECTED.get()))
                .setWeight(0)
                .addToSerializables();

        fullEle(Cold);
        fullEle(Lightning);
        fullEle(Fire);


    }
}
