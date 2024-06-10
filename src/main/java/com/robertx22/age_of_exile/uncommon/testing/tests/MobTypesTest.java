package com.robertx22.age_of_exile.uncommon.testing.tests;

import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityTypeUtils;
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

                System.out.println(ForgeRegistries.ENTITY_TYPES.getKey(type).toString() + ": " + ent.id);
            }

        }

    }
}
