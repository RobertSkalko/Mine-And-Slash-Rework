package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class MobTypesTest {

    public static void run(ServerLevel world) {

        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {

            Entity en = type.create(world);

            if (en instanceof LivingEntity) {
                EntityTypeUtils.EntityClassification ent = EntityTypeUtils.getType((LivingEntity) en);

                System.out.println(BuiltInRegistries.ENTITY_TYPE.getKey(type)
                        .toString() + ": " + ent.id);
            }

        }

    }
}
