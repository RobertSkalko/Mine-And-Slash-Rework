package com.robertx22.age_of_exile.database.data.spells.entities;

import com.google.gson.Gson;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.mixin_ducks.FallingBlockAccessor;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.ArrayList;

public class StationaryFallingBlockEntity extends FallingBlockEntity implements IDatapackSpellEntity {

    public StationaryFallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, Level world) {
        super(SlashEntities.SIMPLE_BLOCK_ENTITY.get(), world);
    }


    public StationaryFallingBlockEntity(Level world, BlockPos pos, BlockState block) {
        this(SlashEntities.SIMPLE_BLOCK_ENTITY.get(), world);
        FallingBlockAccessor acc = (FallingBlockAccessor) this;
        acc.setBlockState(block);
        acc.setDestroyedOnLanding(false);
        this.blocksBuilding = true;
        this.setPos(pos.getX() + 0.5D, pos.getY() + (double) ((1.0F - this.getBbHeight()) / 2.0F), pos.getZ() + 0.5D);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = pos.getX();
        this.yo = pos.getY();
        this.zo = pos.getZ();
        this.setStartPos(this.blockPosition());
    }

    @Override
    public BlockState getBlockState() {

        try {
            return VanillaUTIL.REGISTRY.blocks().get(new ResourceLocation(this.entityData.get(BLOCK)))
                    .defaultBlockState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.getBlockState();
    }

    int lifespan = 1000;

    CalculatedSpellData spellData;

    private static final EntityDataAccessor<CompoundTag> SPELL_DATA = SynchedEntityData.defineId(StationaryFallingBlockEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<String> ENTITY_NAME = SynchedEntityData.defineId(StationaryFallingBlockEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<String> BLOCK = SynchedEntityData.defineId(StationaryFallingBlockEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Boolean> IS_FALLING = SynchedEntityData.defineId(StationaryFallingBlockEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Float> FALL_SPEED = SynchedEntityData.defineId(StationaryFallingBlockEntity.class, EntityDataSerializers.FLOAT);

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return new ArrayList<>();
    }

    boolean removeNextTick = false;

    public void scheduleRemoval() {
        removeNextTick = true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {

        if (this.removeNextTick) {
            this.remove(RemovalReason.KILLED);
            return;
        }

        //this.age++; this is called somewhere again idk

        if (entityData.get(IS_FALLING)) {
            if (!this.isNoGravity()) {

                float speed = entityData.get(FALL_SPEED);
                speed *= 1 + 0.03F * tickCount;

                this.setDeltaMovement(this.getDeltaMovement()
                        .add(0.0D, speed, 0.0D));
            }
            this.move(MoverType.SELF, this.getDeltaMovement());

            if (this.onGround()) {
                remove(RemovalReason.KILLED);
            }

        }

        try {


            if (!level().isClientSide) {
                if (getSpellData() != null) {
                    var caster = getSpellData().getCaster(level());
                    if (caster != null) {
                        this.getSpellData()
                                .getSpell()
                                .getAttached()
                                .tryActivate(getScoreboardName(), SpellCtx.onTick(caster, this, getSpellData()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.remove(RemovalReason.KILLED);
        }

        if (tickCount > lifespan) {
            remove(RemovalReason.KILLED);
        }
    }

    // todo does this work?

    boolean exploded = false;

    @Override
    public void remove(Entity.RemovalReason pReason) {
        try {
            if (!exploded) {
                exploded = true;
                if (getSpellData() != null) {
                    LivingEntity caster = getSpellData().getCaster(level());

                    if (caster != null) {
                        this.getSpellData()
                                .getSpell()
                                .getAttached()
                                .tryActivate(getScoreboardName(), SpellCtx.onExpire(caster, this, getSpellData()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.remove(pReason);
    }

    static Gson GSON = new Gson();

    public CalculatedSpellData getSpellData() {
        if (level().isClientSide) {
            if (spellData == null) {
                CompoundTag nbt = entityData.get(SPELL_DATA);
                if (nbt != null) {
                    this.spellData = GSON.fromJson(nbt.getString("spell"), CalculatedSpellData.class);
                }
            }
        }
        return spellData;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(SPELL_DATA, new CompoundTag());
        this.entityData.define(ENTITY_NAME, "");
        this.entityData.define(BLOCK, "");
        this.entityData.define(IS_FALLING, false);
        this.entityData.define(FALL_SPEED, -0.04F);
        super.defineSynchedData();
    }

    public String getScoreboardName() {
        return entityData.get(ENTITY_NAME);
    }

    @Override
    public void init(LivingEntity caster, CalculatedSpellData data, MapHolder holder) {
        this.spellData = data;

        this.lifespan = holder.get(MapField.LIFESPAN_TICKS)
                .intValue();

        lifespan *= data.data.getNumber(EventData.DURATION_MULTI, 1).number;


        data.data.setString(EventData.ITEM_ID, holder.get(MapField.ITEM));
        CompoundTag nbt = new CompoundTag();
        nbt.putString("spell", GSON.toJson(spellData));
        entityData.set(SPELL_DATA, nbt);
        entityData.set(ENTITY_NAME, holder.get(MapField.ENTITY_NAME));
        entityData.set(BLOCK, holder.get(MapField.BLOCK));
        entityData.set(FALL_SPEED, holder.getOrDefault(MapField.BLOCK_FALL_SPEED, -0.04D)
                .floatValue());

    }
}
