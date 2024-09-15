package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.mine_and_slash.a_libraries.curios.MyCurioUtils;
import com.robertx22.mine_and_slash.capability.DirtySync;
import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.mine_and_slash.saveclasses.unit.GearData;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CachedEntityStats {

    public CachedEntityStats(LivingEntity entity) {
        this.entity = entity;

    }


    private List<GearData> gear = new ArrayList<>();

    private GearData weapon;

    public LivingEntity entity;

    // todo keep this updated for thrown weapon and stuff
    public AttackInformation attackInfo; // this was used to retrieve thrown weapons like tridents

    private StatContext statusEffects;


    LazyClass<EntityData> unitdata = new LazyClass<>(() -> Load.Unit(entity));


    public DirtySync STAT_CALC = new DirtySync("stat_calc", x -> {
        unitdata.get().recalcStats_DONT_CALL();
    });

    public DirtySync GEAR = new DirtySync("gear", x -> {
        recalcGears();
        recalcPlayerStuff();
        STAT_CALC.setDirty();
    });

    public DirtySync WEAPON = new DirtySync("weapon", x -> {
        recalcWeapon();
        STAT_CALC.setDirty();
    });

    public DirtySync STATUS = new DirtySync("status_effects", x -> {
        recalcStatusEffects();
        STAT_CALC.setDirty();
    });


    private void recalcPlayerStuff() {
        if (entity instanceof Player p) {
            Load.player(p).cachedStats.setAllDirty();
        }
    }

    public void setAllDirty() {
        GEAR.setDirty();
        WEAPON.setDirty();
        STATUS.setDirty();

        recalcPlayerStuff();
    }

    public void onTick() {
        GEAR.onTickTrySync(entity);
        WEAPON.onTickTrySync(entity);
        STATUS.onTickTrySync(entity);

        STAT_CALC.onTickTrySync(entity);

    }

    public List<GearData> getGear() {
        List<GearData> all = new ArrayList<>(gear);
        if (weapon != null) {
            all.add(weapon);
        }
        return all;
    }


    public StatContext getStatusEffectStats() {
        if (statusEffects == null) {
            recalcStatusEffects();
        }
        return statusEffects;
    }

    private void recalcStatusEffects() {
        this.statusEffects = unitdata.get().getStatusEffectsData().getStats(entity);
    }

    private void recalcWeapon() {
        Boolean hasWeapon = false;

        var slot = EquipmentSlot.MAINHAND;

        GearData data = getDataFor(slot, entity, unitdata.get());
        data.gear = StackSaving.GEARS.loadFrom(entity.getMainHandItem());

        if (data.gear != null) {
            if (data.gear.GetBaseGearType()
                    .isWeapon()) {
                hasWeapon = true;
            }
        }
        if (!hasWeapon) {
            if (attackInfo != null && attackInfo.weaponData != null) {
                // add weapon damage of throw weapons
                weapon = new GearData(attackInfo.weapon, EquipmentSlot.MAINHAND, unitdata.get());
                hasWeapon = true;
            }
        } else {
            weapon = data;
        }

        if (weapon != null && !weapon.isUsableBy(unitdata.get())) {
            weapon = null;
        }
    }

    private void recalcGears() {
       
        List<GearData> list = new ArrayList<>();

        List<EquipmentSlot> ARMORS = Arrays.asList(EquipmentSlot.CHEST, EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.HEAD, EquipmentSlot.OFFHAND);

        for (EquipmentSlot slot : ARMORS) {
            GearData data = getDataFor(slot, entity, unitdata.get());
            list.add(data);
        }

        if (entity instanceof Player) {
            MyCurioUtils.getAllSlots((Player) entity)
                    .forEach(x -> {
                        GearData data = new GearData(x, null, unitdata.get());
                        list.add(data);
                    });
        }

        this.gear = list.stream().filter(x -> x.isUsableBy(unitdata.get())).collect(Collectors.toList());

    }

    GearData getDataFor(EquipmentSlot slot, LivingEntity en, EntityData data) {
        ItemStack stack = en.getItemBySlot(slot);
        return new GearData(stack, slot, data);
    }

}
