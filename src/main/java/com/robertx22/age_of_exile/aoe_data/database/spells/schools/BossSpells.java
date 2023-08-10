package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class BossSpells implements ExileRegistryInit {
    public static String CLOSE_NOVA = "close_nova";
    public static String MINION_EXPLOSION = "minion_explosion";


    @Override
    public void registerAll() {

        
        SpellBuilder.of(MINION_EXPLOSION, PlayStyle.STR, SpellConfiguration.Builder.instant(0, 0), "Minion Explosion",
                        Arrays.asList(SpellTag.area, SpellTag.damage))

                .onCast(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))

                .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 500D, 3D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.EXPLOSION, 100D, 3D))

                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.WITCH, 500D, 6D, 1D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 100D, 6D, 1D))

                .onCast(PartBuilder.damageInAoe(SpellCalcs.BOSS_MINION_EXPLOSION, Elements.Elemental, 10D))
                .build();

        SpellBuilder.of(CLOSE_NOVA, PlayStyle.STR, SpellConfiguration.Builder.instant(0, 0), "Close Nova",
                        Arrays.asList(SpellTag.area, SpellTag.damage))

                .onCast(PartBuilder.playSound(SoundEvents.GENERIC_EXPLODE, 1D, 1D))

                .onCast(PartBuilder.nova(ParticleTypes.WITCH, 200D, 3D, 0.05D))
                .onCast(PartBuilder.nova(ParticleTypes.EXPLOSION, 50D, 3D, 0.05D))

                .onCast(PartBuilder.nova(ParticleTypes.WITCH, 100D, 2D, 0.05D))
                .onCast(PartBuilder.nova(ParticleTypes.EXPLOSION, 25D, 2D, 0.05D))

                .onCast(PartBuilder.nova(ParticleTypes.WITCH, 100D, 1D, 0.05D))
                .onCast(PartBuilder.nova(ParticleTypes.EXPLOSION, 10D, 1D, 0.05D))

                .onCast(PartBuilder.nova(ParticleTypes.SMOKE, 200D, 1D, 0.05D))
                .onCast(PartBuilder.groundEdgeParticles(ParticleTypes.EXPLOSION, 1D, 0D, 0.2D))

                .onCast(PartBuilder.damageInAoe(SpellCalcs.BOSS_CLOSE_NOVA, Elements.Elemental, 3D))
                .build();

    }
}
