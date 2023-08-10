package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.database.data.spells.entities.SimpleArrowEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleProjectileEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.SimpleTridentEntity;
import com.robertx22.age_of_exile.database.data.spells.entities.StationaryFallingBlockEntity;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SkeletonSummon;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.WolfSummon;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.ZombieSummon;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class SlashEntities {

    public static void init() {

    }

    public static RegObj<EntityType<SimpleProjectileEntity>> SIMPLE_PROJECTILE = projectile(SimpleProjectileEntity::new, "spell_projectile");
    public static RegObj<EntityType<SimpleArrowEntity>> SIMPLE_ARROW = projectile(SimpleArrowEntity::new, "spell_arrow");
    public static RegObj<EntityType<StationaryFallingBlockEntity>> SIMPLE_BLOCK_ENTITY = projectile(StationaryFallingBlockEntity::new, "spell_block_entity", false);
    public static RegObj<EntityType<SimpleTridentEntity>> SIMPLE_TRIDENT = projectile(SimpleTridentEntity::new, "spell_trident", false);

    public static RegObj<EntityType<WolfSummon>> SPIRIT_WOLF = mob(WolfSummon::new, EntityType.WOLF, "spirit_wolf");
    public static RegObj<EntityType<ZombieSummon>> ZOMBIE = mob(ZombieSummon::new, EntityType.SKELETON, "zombie");
    public static RegObj<EntityType<SkeletonSummon>> SKELETON = mob(SkeletonSummon::new, EntityType.SKELETON, "skeleton");


    private static <T extends Entity> RegObj<EntityType<T>> projectile(EntityType.EntityFactory<T> factory, String id) {
        return projectile(factory, id, true);

    }

    private static <T extends Entity> RegObj<EntityType<T>> mob(EntityType.EntityFactory<T> factory, EntityType like, String id) {

        RegObj<EntityType<T>> def = Def.entity(id, () -> EntityType.Builder.of(factory, MobCategory.MISC)
                .sized(like.getDimensions().width, like.getDimensions().height)
                .setTrackingRange(10)
                .build(id));

        return def;
    }

    private static <T extends Entity> RegObj<EntityType<T>> projectile(EntityType.EntityFactory<T> factory,
                                                                       String id, boolean itemRender) {

        RegObj<EntityType<T>> def = Def.entity(id, () -> EntityType.Builder.of(factory, MobCategory.MISC)
                .sized(0.5F, 0.5F)
                .setUpdateInterval(20)
                .setTrackingRange(4)
                .build(id));

        return def;
    }

}


