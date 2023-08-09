package com.robertx22.age_of_exile.mixins;

import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(FlatLevelSource.class)
public class FeatureMixin<FC extends FeatureConfiguration> {

    // todo add a check for did gen

    /*
    @Inject(method = "fillFromNoise", at = @At(value = "HEAD"))
    private void render(Executor pExecutor, Blender pBlender, RandomState pRandom, StructureManager pStructureManager, ChunkAccess pChunk, CallbackInfoReturnable<CompletableFuture<ChunkAccess>> cir) {


        // this isnt called often enough to work

        Feature f = SlashFeatures.DUNGEON.get();
        boolean bool = f.place(new FeaturePlaceContext<>(Optional.empty(), level, pChunkGenerator, pRandom, pChunk, pConfig));

    }

     */
}
