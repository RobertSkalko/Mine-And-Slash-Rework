package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.database.data.spells.entities.renders.ModTridentRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.MySpriteRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.RangerArrowRenderer;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;


public class RenderRegister {

    public static void regRenders(EntityRenderersEvent.RegisterRenderers x) {


        x.registerEntityRenderer(SlashEntities.SIMPLE_PROJECTILE.get(), (d) -> new MySpriteRenderer<>(d, Minecraft.getInstance()
                .getItemRenderer()));

        x.registerEntityRenderer(SlashEntities.SIMPLE_ARROW.get(), m -> new RangerArrowRenderer(m));
        x.registerEntityRenderer(SlashEntities.SIMPLE_BLOCK_ENTITY.get(), m -> new FallingBlockRenderer(m));
        x.registerEntityRenderer(SlashEntities.SIMPLE_TRIDENT.get(), m -> new ModTridentRenderer(m));

   
    }


}

