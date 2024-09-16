package com.robertx22.mine_and_slash.aoe_data.database.spells.impl;

import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.a_libraries.player_animations.SpellAnimations;
import com.robertx22.mine_and_slash.aoe_data.database.spells.PartBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellBuilder;
import com.robertx22.mine_and_slash.aoe_data.database.spells.SpellCalcs;
import com.robertx22.mine_and_slash.database.data.spells.components.SpellConfiguration;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.mine_and_slash.tags.all.SpellTags;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.PlayStyle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;

import java.util.Arrays;

public class DexSpells implements ExileRegistryInit {
    public static String EXECUTE = "execute";

    @Override
    public void registerAll() {

        SpellBuilder.of(EXECUTE, PlayStyle.DEX, SpellConfiguration.Builder.instant(10, 20 * 60)
                                .setSwingArm(), "Execute",
                        Arrays.asList(SpellTags.area, SpellTags.damage, SpellTags.weapon_skill, SpellTags.PHYSICAL))
                .singleAnimation(SpellAnimations.SINGLE_MELEE_SLASH)
                .manualDesc(
                        "Slash enemies in front of you for " + SpellCalcs.EXECUTE.getLocDmgTooltip()
                                + " " + Elements.Physical.getIconNameDmg()
                )
                .weaponReq(CastingWeapon.MELEE_WEAPON)
                .onCast(PartBuilder.playSound(SoundEvents.WITHER_DEATH, 1D, 1D))
                .onCast(PartBuilder.swordSweepParticles())
                .onCast(PartBuilder.damageInFront(SpellCalcs.EXECUTE, Elements.Physical, 1D, 2D)
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SOUL, 5D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.CRIT, 25D, 1D, 0.1D))
                        .addPerEntityHit(PartBuilder.groundEdgeParticles(ParticleTypes.SMOKE, 45D, 1D, 0.1D)))
                .build();


    }
}
