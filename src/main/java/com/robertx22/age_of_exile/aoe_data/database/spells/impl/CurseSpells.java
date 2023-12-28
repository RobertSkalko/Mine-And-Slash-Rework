package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.ModEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class CurseSpells implements ExileRegistryInit {
    public static String CURSE_OF_AGONY = "curse_of_agony";
    public static String CURSE_OF_WEAK = "curse_of_weak";
    public static String CURSE_OF_DESPAIR = "curse_of_despair";


    static void curse(String id, String name, EffectCtx effect) {
        SpellBuilder.of(id, PlayStyle.INT, SpellConfiguration.Builder.instant(10, 20 * 30).setSwingArm()
                        , name, Arrays.asList(SpellTags.area, SpellTags.curse))
                .manualDesc(
                        "Curse enemies with " + effect.locname +
                                " and deal " + SpellCalcs.CURSE.getLocDmgTooltip() + " " + Elements.Chaos.getIconNameDmg())

                .onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.playSound(SoundEvents.WITHER_SKELETON_HURT, 1D, 1D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.SMOKE, 200D, 3D))
                .onExpire(PartBuilder.aoeParticles(ParticleTypes.SQUID_INK, 200D, 3D))
                .onExpire(PartBuilder.damageInAoe(SpellCalcs.CURSE, Elements.Chaos, 3D))
                .onExpire(PartBuilder.addExileEffectToEnemiesInAoe(effect.resourcePath, 3D, 20 * 15D))
                .build();
    }

    @Override
    public void registerAll() {

        curse(CURSE_OF_AGONY, "Curse of Agony", ModEffects.CURSE_AGONY);
        curse(CURSE_OF_WEAK, "Curse of Weakness", ModEffects.CURSE_WEAKNESS);
        curse(CURSE_OF_DESPAIR, "Curse of Despair", ModEffects.DESPAIR);

    }
}
