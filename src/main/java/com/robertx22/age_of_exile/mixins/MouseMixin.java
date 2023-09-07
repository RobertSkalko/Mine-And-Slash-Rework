package com.robertx22.age_of_exile.mixins;

import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MouseHandler.class)
public interface MouseMixin {
    @Accessor
    void setXpos(double x);

    @Accessor
    void setYpos(double y);
}


