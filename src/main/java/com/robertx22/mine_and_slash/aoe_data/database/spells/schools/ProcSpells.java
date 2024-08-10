package com.robertx22.mine_and_slash.aoe_data.database.spells.schools;

import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.aoe_data.database.spells.builders.DamageBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.builders.ParticleBuilder;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.components.actions.vanity.ParticleShape;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class ProcSpells {

    public static String PROFANE_EXPLOSION = "profane_explosion";
    public static String IGNITE_EXPLOSION = "ignite_explosion";
    public static String BLOOD_EXPLOSION = "blood_explosion";

    public static void init() {
        SpellBuilder.of(BLOOD_EXPLOSION, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(0, 20 * 1, 0),
                        "Blood Explosion", Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.PHYSICAL))
                .manualDesc("Causes a bloody explosion dealing " + SpellCalcs.BLOOD_EXPLOSION.getLocDmgTooltip(Elements.Physical))
                .onCast(PartBuilder.playSound(SoundEvents.GLASS_BREAK, 1D, 1D))
                .defaultAndMaxLevel(1)

                .onCast(ParticleBuilder.of(ParticleTypes.CRIT, 2F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(300).build())
                .onCast(ParticleBuilder.of(ParticleTypes.CRIT, 1.5F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(150).build())
                .onCast(ParticleBuilder.of(ParticleTypes.CRIMSON_SPORE, 1F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(55).build())

                .onCast(DamageBuilder.radius(Elements.Physical, 2, SpellCalcs.BLOOD_EXPLOSION).build().noKnock())

                .build();

        SpellBuilder.of(PROFANE_EXPLOSION, PlayStyle.INT, SpellConfiguration.Builder.nonInstant(0, 20 * 1, 0),
                        "Profane Explosion", Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.CHAOS))
                .manualDesc("Causes an explosion dealing " + SpellCalcs.PROFANE_EXPLOSION.getLocDmgTooltip(Elements.Shadow))
                .onCast(PartBuilder.playSound(SoundEvents.GLASS_BREAK, 1D, 1D))
                .defaultAndMaxLevel(1)

                .onCast(ParticleBuilder.of(ParticleTypes.WITCH, 2F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(300).build())
                .onCast(ParticleBuilder.of(ParticleTypes.WITCH, 1.5F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(150).build())
                .onCast(ParticleBuilder.of(ParticleTypes.PORTAL, 1F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(55).build())

                .onCast(DamageBuilder.radius(Elements.Shadow, 2, SpellCalcs.PROFANE_EXPLOSION).build().noKnock())

                .build();

        SpellBuilder.of(IGNITE_EXPLOSION, PlayStyle.STR, SpellConfiguration.Builder.nonInstant(0, 20 * 1, 0),
                        "Ignite Explosion", Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.FIRE))
                .manualDesc("Causes an fiery explosion dealing " + SpellCalcs.IGNITE_EXPLOSION.getLocDmgTooltip(Elements.Fire))
                .onCast(PartBuilder.playSound(SoundEvents.GLASS_BREAK, 1D, 1D))
                .defaultAndMaxLevel(1)

                .onCast(ParticleBuilder.of(ParticleTypes.FLAME, 2.5F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(300).build())
                .onCast(ParticleBuilder.of(ParticleTypes.FLAME, 2F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(150).build())
                .onCast(ParticleBuilder.of(ParticleTypes.FLAME, 1.5F).shape(ParticleShape.CIRCLE_2D_EDGE).height(1).amount(55).build())

                .onCast(DamageBuilder.radius(Elements.Fire, 2.5F, SpellCalcs.IGNITE_EXPLOSION).build().noKnock())

                .build();
    }
}
