package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.uncommon.STATICS;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.biome.AmbientParticleSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BiomeSpecialEffects.class)
public class NetherParticleMixin {

    private static Optional<AmbientParticleSettings> OPT = Optional.of(new AmbientParticleSettings(ParticleTypes.WARPED_SPORE, 0.01428F));

    @Inject(method = "getAmbientParticleSettings", at = @At(value = "HEAD"), cancellable = true)
    private void hookonStackInserted(CallbackInfoReturnable<Optional<AmbientParticleSettings>> cir) {
        try {
            if (STATICS.NETHER_PARTICLES) {
                cir.setReturnValue(OPT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
