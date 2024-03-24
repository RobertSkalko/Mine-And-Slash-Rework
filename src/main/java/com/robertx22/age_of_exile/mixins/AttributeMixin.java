package com.robertx22.age_of_exile.mixins;

import com.robertx22.age_of_exile.mixin_ducks.IDirty;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AttributeMap.class)
public class AttributeMixin implements IDirty {


    private boolean atriDirty = false;

    @Inject(method = "onAttributeModified", at = @At(value = "HEAD"), cancellable = true)
    public void hookLoot(AttributeInstance attri, CallbackInfo ci) {
        this.setAttribDirty(true);

    }

    @Override
    public boolean isAttribDirty() {
        return atriDirty;
    }

    @Override
    public void setAttribDirty(boolean b) {
        this.atriDirty = b;
    }
}
