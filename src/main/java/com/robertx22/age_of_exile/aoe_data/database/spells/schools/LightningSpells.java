package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class LightningSpells implements ExileRegistryInit {
    public static String LIGHTNING_NOVA = "lightning_nova";

    @Override
    public void registerAll() {

        SpellBuilder.of(LIGHTNING_NOVA, PlayStyle.INT, SpellConfiguration.Builder.instant(30, 25 * 10), "Lightning Nova",
                        Arrays.asList(SpellTag.area, SpellTag.damage))
                .manualDesc(
                        "Deal " + SpellCalcs.LIGHNING_NOVA.getLocDmgTooltip()
                                + " " + Elements.Lightning.getIconNameDmg() + " to nearby enemies.")

                .weaponReq(CastingWeapon.ANY_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.TRIDENT_THUNDER, 1D, 1D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.ELECTRIC_SPARK, 200D, 3.5D, 0.5D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.ELECTRIC_SPARK, 300D, 3.5D, 0.5D))
                .onCast(PartBuilder.groundParticles(ParticleTypes.ELECTRIC_SPARK, 250D, 4D, 0.5D))
                .onCast(PartBuilder.playSound(SoundEvents.GENERIC_HURT, 0.5D, 1D))
                .onCast(PartBuilder.damageInAoe(SpellCalcs.LIGHNING_NOVA, Elements.Lightning, 4D)
                        .addPerEntityHit(PartBuilder.playSoundPerTarget(SoundEvents.GENERIC_HURT, 1D, 1D)))
                .build();
    }
}
