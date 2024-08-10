package com.robertx22.mine_and_slash.uncommon.testing.tests;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class MobTypesTest {

    public static void run(ServerLevel world) {

        for (EntityType<?> type : ForgeRegistries.ENTITY_TYPES) {

            Entity en = type.create(world);

            if (en instanceof LivingEntity) {
                EntityTypeUtils.EntityClassification ent = EntityTypeUtils.getType((LivingEntity) en);

                MMORPG.LOGGER.log(ForgeRegistries.ENTITY_TYPES.getKey(type).toString() + ": " + ent.id);
            }

        }

    }
}
