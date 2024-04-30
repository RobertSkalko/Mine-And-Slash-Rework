package com.robertx22.age_of_exile.database.data.spells.summons.entity;

import com.robertx22.age_of_exile.database.data.spells.components.ProjectileCastHelper;
import com.robertx22.age_of_exile.database.data.spells.entities.AutoAimingProj;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class SummonEntity extends TamableAnimal implements RangedAttackMob {

    public SummonEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    protected AbstractArrow getArrow(ItemStack pArrowStack, float pVelocity) {
        return ProjectileUtil.getMobArrow(this, pArrowStack, pVelocity);
    }


    @Override
    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {

        if (true) {
            autoAimingRangedAttack(pTarget);
        } else {

            ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
            AbstractArrow abstractarrow = this.getArrow(itemstack, pDistanceFactor);
            if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
                abstractarrow = ((net.minecraft.world.item.BowItem) this.getMainHandItem().getItem()).customArrow(abstractarrow);
            double d0 = pTarget.getX() - this.getX();
            double d1 = pTarget.getY(0.3333333333333333D) - abstractarrow.getY();
            double d2 = pTarget.getZ() - this.getZ();
            double d3 = Math.sqrt(d0 * d0 + d2 * d2);
            abstractarrow.shoot(d0, d1 + d3 * (double) 0.2F, d2, 5, 0);
            this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
            this.level().addFreshEntity(abstractarrow);
        }
    }


    /**
     * Launches a Wither skull toward (par2, par4, par6)
     */
    private void autoAimingRangedAttack(LivingEntity target) {

        SoundUtils.playSound(this, SoundEvents.ARROW_SHOOT, 1, 0.2F);


        AutoAimingProj en = SlashEntities.AUTO_AIMING_SKELETON_SKULL.get().create(level());

        en.setOwner(this);

        en.setPosRaw(getX(), getEyeY(), getZ());

        en.setDeltaMovement(ProjectileCastHelper.positionToVelocity(new MyPosition(getEyePosition()), new MyPosition(target.getEyePosition())));

        en.target = target;
        en.speed = 2;

        this.level().addFreshEntity(en);
    }

    public boolean usesMelee() {
        return true;
    }

    public boolean usesRanged() {
        return false;
    }

    @Override
    protected void registerGoals() {


        if (usesMelee()) {
            this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        }
        if (usesRanged()) {
            this.goalSelector.addGoal(5, new RangedBowAttackGoal<>(this, 1.0D, 20, 15F));
        }

        this.goalSelector.addGoal(6, new RandomSwimmingGoal(this, 1, 1));
        this.goalSelector.addGoal(7, new FollowOwnerGoal(this, 1.0D, 6.0F, 1.0F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        //this.targetSelector.addGoal(1, new HurtByTargetGoal(this));


        // this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Monster.class, false));
        this.targetSelector.addGoal(3, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new OwnerHurtTargetGoal(this));
    }

    public Entity focusEntity = null;

    @Override
    public boolean canAttack(LivingEntity pTarget) {
        LivingEntity owner = getOwner();

        if (owner == null) {
            return false;
        }
        if (!pTarget.isAlive()) {
            return false;
        }
        if (AllyOrEnemy.allies.is(owner, pTarget)) {
            return false;
        }
        // aggro the focus target, otherwise find something else
        if (focusEntity != null && focusEntity.isAlive() && isInAggroRadius((LivingEntity) focusEntity)) {
            return focusEntity == pTarget;
        } else {
            return isInAggroRadius(pTarget);
        }

    }

    // todo test this
    private boolean isInAggroRadius(LivingEntity target) {
        var aggroRadius = Load.Unit(this).summonedPetData.aggro_radius;

        int distance = (int) target.distanceTo(this);

        return aggroRadius >= distance;

    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }
}
