package com.robertx22.age_of_exile.database.data.spells.entities;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.robertx22.age_of_exile.database.data.spells.components.MapHolder;
import com.robertx22.age_of_exile.database.data.spells.entities.renders.IMyRenderAsItem;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.uncommon.utilityclasses.Utilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import java.util.UUID;

public class FXEntity extends Entity implements IDatapackSpellEntity, IMyRenderAsItem {

    CalculatedSpellData spellData;
    private Integer lifeSpan;

    private static final EntityDataAccessor<CompoundTag> SPELL_DATA = SynchedEntityData.defineId(FXEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<String> ENTITY_NAME = SynchedEntityData.defineId(FXEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> DEATH_TIME = SynchedEntityData.defineId(FXEntity.class, EntityDataSerializers.INT);

    public FXEntity(EntityType<? extends Entity> type, Level worldIn) {
        super(SlashEntities.FX_ENTITY.get(), worldIn);
        this.lifeSpan = 0;
    }

    public FXEntity(Level world) {
        this(SlashEntities.FX_ENTITY.get(), world);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }


    public int getDeathTime() {
        return entityData.get(DEATH_TIME);
    }

    public void setDeathTime(int newVal) {
        this.entityData.set(DEATH_TIME, newVal);
    }


    protected void tickDespawn() {
        ++this.lifeSpan;
        if (this.lifeSpan >= getDeathTime()) {
            this.remove(RemovalReason.KILLED);
        }

    }

    @Override
    public void remove(RemovalReason r) {

        LivingEntity caster = getCaster();

        if (caster != null) {
            this.getSpellData()
                    .getSpell()
                    .getAttached()
                    .tryActivate(getScoreboardName(), SpellCtx.onExpire(caster, this, getSpellData()));
        }

        super.remove(r);
    }


    @Override
    public void tick() {

        tickDespawn();
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
            if (lifeSpan > 100) {
                this.scheduleRemoval();
            }
        }

    }



    boolean removeNextTick = false;

    public void scheduleRemoval() {
        removeNextTick = true;
    }

    static Gson GSON = new Gson();

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {

        try {

            nbt.putInt("deathTime", this.getDeathTime());

            nbt.putString("data", GSON.toJson(spellData));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {

        try {


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
        this.entityData.define(DEATH_TIME, 100);
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


    public String getScoreboardName() {
        return entityData.get(ENTITY_NAME);
    }

    @Override
    public void playerTouch(Player player) {
    }

    MapHolder holder;
    float speed = 0;

    @Override
    public void init(LivingEntity caster, CalculatedSpellData data, MapHolder holder) {
        try {
            this.holder = holder;
            this.spellData = data;

            this.setNoGravity(!holder.getOrDefault(MapField.GRAVITY, true));
            this.setDeathTime(holder.get(MapField.LIFESPAN_TICKS)
                    .intValue());

            this.checkInsideBlocks();

            this.speed = holder.getOrDefault(MapField.PROJECTILE_SPEED, 1D).floatValue();

            CompoundTag nbt = new CompoundTag();
            nbt.putString("spell", GSON.toJson(spellData));
            entityData.set(SPELL_DATA, nbt);

            String name = holder.get(MapField.ENTITY_NAME);
            entityData.set(ENTITY_NAME, name);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ItemStack getItem() {
        return Items.AIR.getDefaultInstance();
    }
}