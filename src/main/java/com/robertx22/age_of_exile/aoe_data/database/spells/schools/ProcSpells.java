package com.robertx22.age_of_exile.aoe_data.database.spells.schools;

import com.robertx22.age_of_exile.aoe_data.database.spells.PartBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.SpellCalcs;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.DamageBuilder;
import com.robertx22.age_of_exile.aoe_data.database.spells.builders.ParticleBuilder;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.vanity.ParticleShape;
import com.robertx22.age_of_exile.tags.all.SpellTags;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class ProcSpells {

    public static String PROFANE_EXPLOSION = "profane_explosion";

    public static void init() {

        SpellBuilder.of(PROFANE_EXPLOSION, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(0, 20 * 1, 0),
                        "Profane Explosion", Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.CHAOS))
                .manualDesc("Causes an explosion dealing " + SpellCalcs.SHATTER_PROC.getLocDmgTooltip(Elements.Shadow))
                .onCast(PartBuilder.playSound(SoundEvents.GLASS_BREAK, 1D, 1D))
                .defaultAndMaxLevel(1)

                .onCast(ParticleBuilder.of(ParticleTypes.WITCH, 3.5f).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(200).build())
                .onCast(ParticleBuilder.of(ParticleTypes.WITCH, 3.5f).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(100).build())
                .onCast(ParticleBuilder.of(ParticleTypes.PORTAL, 3.5f).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(55).build())

                .onCast(DamageBuilder.radius(Elements.Shadow, 2, SpellCalcs.PROFANE_EXPLOSION).build().noKnock())

                .build();
    }
}
