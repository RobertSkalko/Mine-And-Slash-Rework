package com.robertx22.age_of_exile.database.data.spells.spell_classes;

import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.actions.PositionSource;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Objects;

public class SpellCtx {

    public Level world;

    // the entity the effect came from, player summons fireball. fireball hits enemy, dmg comes from fireball
    public Entity sourceEntity;
    public LivingEntity caster;
    public LivingEntity target;

    public final EntityActivation activation;

    public CalculatedSpellData calculatedSpellData;


    public SpellCtx setSourceEntity(Entity en) {
        this.sourceEntity = en;
        return this;
    }

    private PositionSource positionSource = PositionSource.SOURCE_ENTITY;

    public Entity getPositionEntity() {
        return positionSource.get(this);
    }

    public SpellCtx setPositionSource(PositionSource s) {
        this.positionSource = s;
        return this;
    }

    public BlockPos getBlockPos() {
        return positionSource.get(this).blockPosition();
    }

    public Vec3 getPos() {
        return positionSource.get(this).position();
    }

    private SpellCtx(EntityActivation act, Entity sourceEntity, LivingEntity caster, LivingEntity target, CalculatedSpellData calculatedSpellData) {
        this.sourceEntity = sourceEntity;
        this.caster = caster;
        this.target = target;
        this.calculatedSpellData = calculatedSpellData;
        this.world = caster.level();
        this.activation = act;


    }

    public static SpellCtx onCast(LivingEntity caster, CalculatedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(data);
        return new SpellCtx(EntityActivation.ON_CAST, caster, caster, caster, data);
    }

    // todo this might not work or need custom mapholder data to set it to target
    public static SpellCtx onHit(LivingEntity caster, Entity sourceEntity, LivingEntity target, CalculatedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);

        Load.Unit(caster).onSpellHitTarget(sourceEntity, target);
        return new SpellCtx(EntityActivation.ON_HIT, sourceEntity, caster, target, data).setPositionSource(PositionSource.TARGET);
    }

    public static SpellCtx onEntityHit(SpellCtx ctx, LivingEntity target) {
        Objects.requireNonNull(ctx);
        Objects.requireNonNull(target);
        return new SpellCtx(EntityActivation.PER_ENTITY_HIT, ctx.sourceEntity, ctx.caster, target, ctx.calculatedSpellData).setPositionSource(PositionSource.TARGET);
    }

    public static SpellCtx onExpire(LivingEntity caster, Entity sourceEntity, CalculatedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);
        LivingEntity target = sourceEntity instanceof LivingEntity ? (LivingEntity) sourceEntity : null;
        return new SpellCtx(EntityActivation.ON_EXPIRE, sourceEntity, caster, target, data);
    }

    public static SpellCtx onTick(LivingEntity caster, Entity sourceEntity, CalculatedSpellData data) {
        Objects.requireNonNull(caster);
        Objects.requireNonNull(sourceEntity);
        Objects.requireNonNull(data);
        LivingEntity target = sourceEntity instanceof LivingEntity ? (LivingEntity) sourceEntity : null;
        return new SpellCtx(EntityActivation.ON_TICK, sourceEntity, caster, target, data);
    }

}
