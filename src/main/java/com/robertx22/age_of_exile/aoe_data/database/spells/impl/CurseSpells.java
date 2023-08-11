package com.robertx22.age_of_exile.aoe_data.database.spells.impl;

import com.robertx22.age_of_exile.aoe_data.database.exile_effects.adders.NegativeEffects;
import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class CurseSpells implements ExileRegistryInit {

    
    static void curse(String id, String name, EffectCtx effect) {
        SpellBuilder.of(id, PlayStyle.INT, SpellConfiguration.Builder.instant(10, 20 * 30)
                                .setSwingArm()
                        , name,
                        Arrays.asList(SpellTag.area, SpellTag.curse))
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

        curse("curse_of_agony", "Curse of Agony", NegativeEffects.CURSE_AGONY);
        curse("curse_of_weak", "Curse of Weakness", NegativeEffects.CURSE_WEAKNESS);
        curse("curse_of_despair", "Curse of Despair", NegativeEffects.DESPAIR);

    }
}
