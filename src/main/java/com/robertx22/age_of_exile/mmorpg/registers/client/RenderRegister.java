package com.robertx22.age_of_exile.mmorpg.registers.client;

import com.robertx22.age_of_exile.database.data.spells.entities.renders.ModTridentRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.MySpriteRenderer;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.RangerArrowRenderer;
import com.robertx22.age_of_exile.database.data.spells.summons.render.ModSkeletonRender;
import com.robertx22.age_of_exile.database.data.spells.summons.render.ModSpiderRender;
import com.robertx22.age_of_exile.database.data.spells.summons.render.ModWolfRender;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraftforge.client.event.EntityRenderersEvent;


public class RenderRegister {

    public static void regRenders(EntityRenderersEvent.RegisterRenderers x) {

        x.registerEntityRenderer(SlashEntities.SPIRIT_WOLF.get(), ctx -> new ModWolfRender(SlashRef.id("textures/entity/spirit_wolf.png"), ctx));
        x.registerEntityRenderer(SlashEntities.SKELETON.get(), ctx -> new ModSkeletonRender(SlashRef.id("textures/entity/skeleton.png"), ctx));
        x.registerEntityRenderer(SlashEntities.ZOMBIE.get(), ctx -> new ModSkeletonRender(SlashRef.id("textures/entity/zombie.png"), ctx));
        x.registerEntityRenderer(SlashEntities.SPIDER.get(), ctx -> new ModSpiderRender(SlashRef.id("textures/entity/spider.png"), ctx));

        x.registerEntityRenderer(SlashEntities.FIRE_GOLEM.get(), ctx -> new ModSkeletonRender(SlashRef.id("textures/entity/fire_golem.png"), ctx));
        x.registerEntityRenderer(SlashEntities.COLD_GOLEM.get(), ctx -> new ModSkeletonRender(SlashRef.id("textures/entity/cold_golem.png"), ctx));
        x.registerEntityRenderer(SlashEntities.LIGHTNING_GOLEM.get(), ctx -> new ModSkeletonRender(SlashRef.id("textures/entity/lightning_golem.png"), ctx));

        // minions
        x.registerEntityRenderer(SlashEntities.THORNY_MINION.get(), ctx -> new ModSkeletonRender(SlashRef.id("textures/entity/thorny_minion.png"), ctx));
        x.registerEntityRenderer(SlashEntities.EXPLODE_MINION.get(), ctx -> new ModSkeletonRender(SlashRef.id("textures/entity/explody_minion.png"), ctx));


        x.registerEntityRenderer(SlashEntities.SIMPLE_PROJECTILE.get(), (d) -> new MySpriteRenderer<>(d, Minecraft.getInstance()
                .getItemRenderer()));

        x.registerEntityRenderer(SlashEntities.SIMPLE_ARROW.get(), m -> new RangerArrowRenderer(m));
        x.registerEntityRenderer(SlashEntities.SIMPLE_BLOCK_ENTITY.get(), m -> new FallingBlockRenderer(m));
        x.registerEntityRenderer(SlashEntities.SIMPLE_TRIDENT.get(), m -> new ModTridentRenderer(m));
        x.registerEntityRenderer(SlashEntities.AUTO_AIMING_SKELETON_SKULL.get(), m -> new AutoSkullRender(m));


    }


}

