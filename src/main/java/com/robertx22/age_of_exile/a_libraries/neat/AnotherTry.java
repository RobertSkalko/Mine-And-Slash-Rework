package com.robertx22.age_of_exile.a_libraries.neat;

import com.robertx22.age_of_exile.mixin_methods.RenderMobInfo;
import com.robertx22.age_of_exile.mmorpg.ForgeEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderNameTagEvent;

public class AnotherTry {

    public static void register() {

        ForgeEvents.registerForgeEvent(RenderNameTagEvent.class, event -> {

            try {
                Minecraft mc = Minecraft.getInstance();

                if (event.getEntity() instanceof LivingEntity en) {
                    RenderMobInfo.renderLivingEntityLabelIfPresent(mc.font, mc.getEntityRenderDispatcher(), en, event.getPoseStack(),
                            event.getMultiBufferSource(), event.getPackedLight());
                }
            } catch (Exception e) {
                // throw new RuntimeException(e);
            }

        });


    }

}
