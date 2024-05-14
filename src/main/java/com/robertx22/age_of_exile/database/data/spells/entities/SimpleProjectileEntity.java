package com.robertx22.age_of_exile.database.data.spells.entities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.components.ProjectileCastHelper;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.IMyRenderAsItem;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;
import com.robertx22.age_of_exile.uncommon.utilityclasses.PlayerUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SimpleProjectileEntity extends AbstractArrow implements IMyRenderAsItem, IDatapackSpellEntity {

    CalculatedSpellData spellData;

    private int xTile;
    private int yTile;
    private int zTile;

    protected boolean inGround;

    private int ticksInGround = 0;

    public boolean moveTowardsEnemies = false;

    private static final EntityDataAccessor<CompoundTag> SPELL_DATA = SynchedEntityData.defineId(SimpleProjectileEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<String> ENTITY_NAME = SynchedEntityData.defineId(SimpleProjectileEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> EXPIRE_ON_ENTITY_HIT = SynchedEntityData.defineId(SimpleProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HIT_ALLIES = SynchedEntityData.defineId(SimpleProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> PIERCE = SynchedEntityData.defineId(SimpleProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DEATH_TIME = SynchedEntityData.defineId(SimpleProjectileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHAINS = SynchedEntityData.defineId(SimpleProjectileEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> EXPIRE_ON_BLOCK_HIT = SynchedEntityData.defineId(SimpleProjectileEntity.class, EntityDataSerializers.BOOLEAN);

    public Entity ignoreEntity;

    boolean collidedAlready = false;

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;
    }

    protected boolean onExpireProc(LivingEntity caster) {
        return true;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>();
    }

    @Override
    public void setItemSlot(EquipmentSlot slotIn, ItemStack stack) {

    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override // seems to help making it hit easier?
    public float getPickRadius() {
        return 1.0F;
    }

    public int getTicksInGround() {
        return this.ticksInGround;
    }

    public int getDeathTime() {
        return entityData.get(DEATH_TIME);
    }

    public void setDeathTime(int newVal) {
        this.entityData.set(DEATH_TIME, newVal);
    }

    public SimpleProjectileEntity(EntityType<? extends Entity> type, Level worldIn) {
        super((EntityType<? extends AbstractArrow>) type, worldIn);
        this.xTile = -1;
        this.yTile = -1;
        this.zTile = -1;
    }

    public Entity getEntityHit(HitResult result, double radius) {

        EntityHitResult enres = null;

        if (result instanceof EntityHitResult) {
            enres = (EntityHitResult) result;
        }

        if (enres == null) {
            return null;
        }

        if (enres.getEntity() instanceof Entity) {
            if (enres.getEntity() != this.getCaster()) {
                return enres.getEntity();
            }
        }

        if (getCaster() != null) {
            List<LivingEntity> entities = EntityFinder.start(getCaster(), LivingEntity.class, position())
                    .radius(radius)
                    .build();

            if (entities.size() > 0) {

                LivingEntity closest = entities.get(0);

                for (LivingEntity en : entities) {
                    if (en != closest) {
                        if (this.distanceTo(en) < this.distanceTo(closest)) {
                            closest = en;
                        }
                    }
                }

                return closest;
            }
        }

        return null;

    }

    public void onTick() {

        if (getCaster() != null) {

            tryMoveTowardsTargets();

            if (!level().isClientSide) {
                this.getSpellData()
                        .getSpell()
                        .getAttached()
                        .tryActivate(getScoreboardName(), SpellCtx.onTick(getCaster(), this, getSpellData()));

            }
        }
    }

    @Override
    public void remove(RemovalReason r) {

        LivingEntity caster = getCaster();

        if (caster != null) {
            if (!level().isClientSide) {
                this.getSpellData()
                        .getSpell()
                        .getAttached()
                        .tryActivate(getScoreboardName(), SpellCtx.onExpire(caster, this, getSpellData()));
            }
        }

        super.remove(r);
    }

    @Override
    protected float getWaterInertia() {
        return 0.99F;
    }

    @Override
    public final void tick() {

        if (this.removeNextTick) {
            this.remove(RemovalReason.KILLED);
            return;
        }

        try {
            super.tick();
        } catch (Exception e) {
            e.printStackTrace();
            this.scheduleRemoval();
        }

        if (this.getSpellData() == null || getCaster() == null) {
            if (tickCount > 100) {
                this.scheduleRemoval();
            }
            return;
        }

        try {
            onTick();

            if (this.inGround) {
                ticksInGround++;
            }


            if (this.tickCount >= this.getDeathTime()) {
                onExpireProc(this.getCaster());
                this.scheduleRemoval();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.scheduleRemoval();
        }

    }

    public void tryMoveTowardsTargets() {
        if (moveTowardsEnemies) {
            int radius = getSpellData().getSpell().config.tracking_radius;

            var b = EntityFinder.start(getCaster(), LivingEntity.class, position())
                    .finder(EntityFinder.SelectionType.RADIUS)
                    .searchFor(getSpellData().getSpell().config.tracks)
                    .radius(radius);

            var target = b.getClosest();

            if (target != null) {
                var vel = ProjectileCastHelper.positionToVelocity(new MyPosition(position()), new MyPosition(target.getEyePosition()));
                vel = vel.normalize().multiply(speed, speed, speed); // todo this doesnt fix the speed problem
                setDeltaMovement(vel);

                PlayerUtils.getNearbyPlayers(level(), blockPosition(), 40)
                        .forEach(p -> {
                            ((ServerPlayer) p).connection.send(new ClientboundSetEntityMotionPacket(this));
                        });
            }
        }
    }

    @Override
    protected EntityHitResult findHitEntity(Vec3 pos, Vec3 posPlusMotion) {

        EntityHitResult res = ProjectileUtil.getEntityHitResult(
                this.level(), this, pos, posPlusMotion, this.getBoundingBox()
                        .expandTowards(this.getDeltaMovement())
                        .inflate(1D), (e) -> {
                    return !e.isSpectator() && e.isPickable() && e instanceof Entity && e != this.getCaster() && e != this.ignoreEntity;
                });

        if (!this.entityData.get(HIT_ALLIES)) {
            if (res != null && getCaster() != null && res.getEntity() instanceof LivingEntity) {
                if (AllyOrEnemy.allies.is(getCaster(), (LivingEntity) res.getEntity())) {
                    return null; // don't hit allies with spells, let them pass
                }
            }
        }
        return res;
    }

    @Override
    protected void onHit(HitResult raytraceResultIn) {

        HitResult.Type raytraceresult$type = raytraceResultIn.getType();
        if (raytraceresult$type == HitResult.Type.ENTITY) {
            this.onImpact(raytraceResultIn);
            this.playSound(SoundEvents.SHULKER_BULLET_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));

        } else if (raytraceresult$type == HitResult.Type.BLOCK) {

            if (collidedAlready) {
                return;
            }
            collidedAlready = true;

            BlockHitResult blockraytraceresult = (BlockHitResult) raytraceResultIn;
            BlockState blockstate = this.level().getBlockState(blockraytraceresult.getBlockPos());

            Vec3 vec3d = blockraytraceresult.getLocation()
                    .subtract(this.getX(), this.getY(), this.getZ());
            this.setDeltaMovement(vec3d);

            this.inGround = true;

            this.onImpact(blockraytraceresult);

            blockstate.onProjectileHit(this.level(), blockstate, blockraytraceresult, this);
        }

    }

    protected void onImpact(HitResult result) {

        Entity entityHit = getEntityHit(result, 0.3D);

        if (entityHit != null) {
            if (level().isClientSide) {
                SoundUtils.playSound(this, SoundEvents.GENERIC_HURT, 1F, 0.9F);
            }

            LivingEntity caster = getCaster();

            LivingEntity en = null;

            if (entityHit instanceof LivingEntity == false) {
                // HARDCODED support for dumb ender dragon non living entity dragon parts
                if (entityHit instanceof EnderDragonPart) {
                    EnderDragonPart part = (EnderDragonPart) entityHit;
                    if (!part.isInvulnerableTo(this.damageSources().mobAttack(caster))) {
                        en = part.parentMob;
                    }
                }
            } else if (entityHit instanceof LivingEntity) {
                en = (LivingEntity) entityHit;
            }

            if (en == null) {
                return;
            }

            if (caster != null) {
                if (!Load.Unit(caster)
                        .alreadyHit(this, en)) {
                    if (!level().isClientSide) {
                        this.getSpellData()
                                .getSpell()
                                .getAttached()
                                .tryActivate(getScoreboardName(), SpellCtx.onHit(caster, this, en, getSpellData()));
                    }
                }
            }

        } else {

            if (level().isClientSide) {
                SoundUtils.playSound(this, SoundEvents.STONE_HIT, 0.7F, 0.9F);
            }

        }

        if (entityHit != null) {
            if (!entityData.get(EXPIRE_ON_ENTITY_HIT)) {
                return;
            } else {
                scheduleRemoval();
            }
        }

        if (result instanceof BlockHitResult && entityData.get(EXPIRE_ON_BLOCK_HIT)) {
            scheduleRemoval();
        }


        if (!level().isClientSide) {


            if (getCaster() != null) {


                int chains = this.entityData.get(CHAINS).intValue();

                if (chains > 0) {
                    chains--;

                    if (entityHit == null) {
                        chains = 0;
                    }


                    var radius = getSpellData().data.getNumber(EventData.AREA_MULTI, 1F).number;

                    var b = EntityFinder.start(getCaster(), LivingEntity.class, position())
                            .finder(EntityFinder.SelectionType.RADIUS)
                            .searchFor(AllyOrEnemy.enemies)
                            .radius(5 * radius);

                    if (entityHit instanceof LivingEntity hit) {
                        b.excludeEntity(hit);
                    }
                    var target = b.getClosest();

                    if (target != null) {

                        SimpleProjectileEntity en = (SimpleProjectileEntity) getType().create(level());
                        en.setPos(position());
                        var sd = this.getSpellDataCopy(); // important so it doesnt affect old ones
                        sd.chains_did++; // when upping chain count
                        en.init(caster, sd, holder);
                        en.entityData.set(CHAINS, chains);
                        var vel = ProjectileCastHelper.positionToVelocity(new MyPosition(position()), new MyPosition(target.getEyePosition()));
                        en.setDeltaMovement(vel.normalize().multiply(speed, speed, speed));
                        level().addFreshEntity(en);

                    }
                }
            }
        }

    }

    boolean removeNextTick = false;

    public void scheduleRemoval() {
        this.discard();

        removeNextTick = true;
    }

    static Gson GSON = new Gson();

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {

        try {

            // super.writeCustomDataToTag(nbt);

            nbt.putInt("xTile", this.xTile);
            nbt.putInt("yTile", this.yTile);
            nbt.putInt("zTile", this.zTile);

            nbt.putByte("inGround", (byte) (this.inGround ? 1 : 0));

            nbt.putInt("deathTime", this.getDeathTime());

            nbt.putString("data", GSON.toJson(spellData));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {

        try {

//            super.readCustomDataFromTag(nbt);

            this.xTile = nbt.getInt("xTile");
            this.yTile = nbt.getInt("yTile");
            this.zTile = nbt.getInt("zTile");

            this.inGround = nbt.getByte("inGround") == 1;

            this.setDeathTime(nbt.getInt("deathTime"));

            this.spellData = GSON.fromJson(nbt.getString("data"), CalculatedSpellData.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    LivingEntity caster;

    public LivingEntity getCaster() {
        if (caster == null) {
            try {
                this.caster = Utilities.getLivingEntityByUUID(level(), UUID.fromString(getSpellData().caster_uuid));
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        return caster;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SPELL_DATA, new CompoundTag());
        this.entityData.define(ENTITY_NAME, "");
        this.entityData.define(EXPIRE_ON_ENTITY_HIT, true);
        this.entityData.define(EXPIRE_ON_BLOCK_HIT, true);
        this.entityData.define(HIT_ALLIES, false);
        this.entityData.define(PIERCE, false);
        this.entityData.define(DEATH_TIME, 100);
        this.entityData.define(CHAINS, 0);
        super.defineSynchedData();
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    public CalculatedSpellData getSpellData() {
        try {
            if (level().isClientSide) {
                if (spellData == null) {
                    CompoundTag nbt = entityData.get(SPELL_DATA);
                    if (nbt != null) {
                        this.spellData = GSON.fromJson(nbt.getString("spell"), CalculatedSpellData.class);
                    }
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return spellData;
    }

    public CalculatedSpellData getSpellDataCopy() {
        return GSON.fromJson(GSON.toJson(getSpellData()), CalculatedSpellData.class);
    }

    @Override
    public ItemStack getItem() {
        try {
            Item item = VanillaUTIL.REGISTRY.items().get(new ResourceLocation(getSpellData().data.getString(EventData.ITEM_ID)));
            if (item != null) {
                return new ItemStack(item);
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }

        return new ItemStack(Items.AIR);
    }

    public String getScoreboardName() {
        return entityData.get(ENTITY_NAME);
    }

    @Override
    public void playerTouch(Player player) {
        // don't allow player to pickup lol
    }

    MapHolder holder;
    float speed = 0;

    @Override
    public void init(LivingEntity caster, CalculatedSpellData data, MapHolder holder) {
        this.holder = holder;
        this.spellData = data;

        this.pickup = Pickup.DISALLOWED;

        this.setNoGravity(!holder.getOrDefault(MapField.GRAVITY, true));
        this.setDeathTime(holder.get(MapField.LIFESPAN_TICKS)
                .intValue());

        this.entityData.set(EXPIRE_ON_ENTITY_HIT, holder.getOrDefault(MapField.EXPIRE_ON_ENTITY_HIT, true));
        this.entityData.set(EXPIRE_ON_BLOCK_HIT, holder.getOrDefault(MapField.EXPIRE_ON_BLOCK_HIT, true));
        this.entityData.set(HIT_ALLIES, holder.getOrDefault(MapField.HITS_ALLIES, false));
        this.entityData.set(CHAINS, holder.getOrDefault(MapField.CHAIN_COUNT, 0D).intValue());

        this.checkInsideBlocks();


        if (data.data.getBoolean(EventData.PIERCE)) {
            this.entityData.set(EXPIRE_ON_ENTITY_HIT, false);
        }

        this.moveTowardsEnemies = holder.getOrDefault(MapField.TRACKS_ENEMIES, false);
        this.speed = holder.getOrDefault(MapField.PROJECTILE_SPEED, 1D).floatValue();

        data.data.setString(EventData.ITEM_ID, holder.get(MapField.ITEM));
        CompoundTag nbt = new CompoundTag();
        nbt.putString("spell", GSON.toJson(spellData));
        entityData.set(SPELL_DATA, nbt);
        this.setOwner(caster);

        String name = holder.get(MapField.ENTITY_NAME);
        entityData.set(ENTITY_NAME, name);

    }
}