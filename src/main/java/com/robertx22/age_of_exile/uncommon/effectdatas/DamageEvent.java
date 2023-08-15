package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.aoe_data.database.ailments.Ailment;
import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.damage_hooks.util.DmgSourceUtils;
import com.robertx22.age_of_exile.database.data.stats.types.offense.FullSwingDamage;
import com.robertx22.age_of_exile.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.age_of_exile.mixin_ducks.LivingEntityAccesor;
import com.robertx22.age_of_exile.mixin_ducks.ProjectileEntityDuck;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.AttackType;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.WeaponTypes;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.DashUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.HealthUtils;
import com.robertx22.age_of_exile.uncommon.utilityclasses.NumberUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.DmgNumPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class DamageEvent extends EffectEvent {
    public static ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, SlashRef.id("mod"));

    public static String ID = "on_damage";

    @Override
    public String GUID() {
        return ID;
    }

    protected DamageEvent(AttackInformation attackInfo, LivingEntity source, LivingEntity target, float dmg) {
        super(dmg, source, target);
        this.attackInfo = attackInfo;
        calcBlock();
    }

    public static String dmgSourceName = SlashRef.MODID + ".custom_damage";

    AttackInformation attackInfo;
    private HashMap<Elements, Integer> bonusElementDamageMap = new HashMap();

    public AttackType getAttackType() {
        return data.getAttackType();
    }

    public Elements getElement() {
        return data.getElement();
    }

    public void addBonusEleDmg(Elements element, float dmg) {
        bonusElementDamageMap.put(element, (int) (bonusElementDamageMap.getOrDefault(element, 0) + dmg));
    }

    private void calcBlock() {


        if (targetData
                .getResources()
                .getEnergy() < 1) {
            return;
        }

        // blocking check
        if (target.isBlocking() && attackInfo != null) {
            Vec3 vec3d = attackInfo.getSource()
                    .getSourcePosition();
            if (vec3d != null) {
                Vec3 vec3d2 = target.getViewVector(1.0F);
                Vec3 vec3d3 = vec3d.vectorTo(target.position())
                        .normalize();
                vec3d3 = new Vec3(vec3d3.x, 0.0D, vec3d3.z);
                if (vec3d3.dot(vec3d2) < 0.0D) {
                    this.data.setBoolean(EventData.IS_BLOCKED, true);
                }
            }
        }
    }

    public float getActualDamage() {
        float dmg = this.data.getNumber();

        if (dmg <= 0) {
            return 0;
        }

        if (source instanceof Player) {
            if (data.isBasicAttack()) {
                dmg = modifyByAttackSpeedIfMelee(dmg);
                dmg = modifyIfArrowDamage(dmg);
            }
        }

        if (areBothPlayers()) {
            dmg *= ServerContainer.get().PVP_DMG_MULTI.get();
        }

        if (!data.isDodged() && target instanceof Player) { // todo this code sucks
            // a getter should not modify anything
            dmg = MagicShield.modifyEntityDamage(this, dmg);
            dmg = DamageAbsorbedByMana.modifyEntityDamage(this, dmg);

            if (dmg > 0) {


            }

        }

        return dmg;
    }

    private void calcAttackCooldown() {
        float cool = 1;

        WeaponTypes weaponType = data.getWeaponType();

        if (weaponType.isMelee()) {

            if (this.source instanceof Player) {


                GearItemData gear = StackSaving.GEARS.loadFrom(source.getMainHandItem());

                if (gear != null) {
                    float atkpersec = 1;

                    float secWaited = (float) (source.tickCount - source.getLastHurtMobTimestamp()) / 20F;

                    float secNeededToWaitForFull = 1F / atkpersec;

                    cool = secWaited / secNeededToWaitForFull;

                    cool = Mth.clamp(cool, 0F, 1F);

                }
            }
        }
        data.setupNumber(EventData.ATTACK_COOLDOWN, cool);
    }


    private float modifyByAttackSpeedIfMelee(float dmg) {

        float cool = data.getNumber(EventData.ATTACK_COOLDOWN).number;

        // we no longer remove damage from fast clicked attacks, energy exists! dmg *= cool;

        if (cool < 0.1F) {
            // we dont want to allow too fast mob clickings
            this.cancelDamage();
        }


        if (cool > 0.8F) {
            dmg *= sourceData.getUnit().getCalculatedStat(FullSwingDamage.getInstance()).getMultiplier();
            //ParticleUtils.spawnDefaultSlashingWeaponParticles(source);
        }

        return dmg;

    }

    private float modifyIfArrowDamage(float dmg) {
        if (attackInfo != null && attackInfo.getSource() != null) {
            if (attackInfo.getSource()
                    .getDirectEntity() instanceof ProjectileEntityDuck) {
                if (data.getWeaponType() == WeaponTypes.bow) {
                    // don't use this for crossbows, only bows need to be charged fully

                    ProjectileEntityDuck duck = (ProjectileEntityDuck) attackInfo.getSource()
                            .getDirectEntity();

                    float arrowmulti = duck.my$getDmgMulti();

                    dmg *= arrowmulti;
                    // multiply dmg by saved charge value
                }
            }
        }

        return dmg;

    }

    public boolean areBothPlayers() {
        if (source instanceof ServerPlayer && target instanceof ServerPlayer) {
            return true;
        }
        return false;
    }

    public void cancelDamage() {
        this.data.getNumber(EventData.NUMBER).number = 0;

        this.data.setBoolean(EventData.CANCELED, true);
        if (attackInfo != null) {
            attackInfo.setAmount(0);
            attackInfo.setCanceled(true);
        }
        return;
    }

    public boolean stopFriendlyFire() {

        if (this.data.isSpellEffect()) {

            if (AllyOrEnemy.allies.is(source, target)) {
                cancelDamage();
                return true;
            }
        }

        /*
        if (areBothPlayers()) {
            if (source.equals(target)) {
                return false; // it's the same player, let him hit himself
            }
            if (TeamUtils.areOnSameTeam((ServerPlayer) source, (ServerPlayer) target)) {
                return true;
            }
            Player sp = (Player) this.source;
            if (!sp.canHarmPlayer((Player) target)) {
                return true;
            }
        } else {
            if (this.data.isSpellEffect()) {

                if (AllyOrEnemy.allies.is(source, target)) {
                    cancelDamage();
                    return true;
                }
            }
        }

         */

        return false;
    }

    AttributeModifier NO_KNOCKBACK = new AttributeModifier(
            UUID.fromString("e926df30-c376-11ea-87d0-0242ac131053"),
            Attributes.KNOCKBACK_RESISTANCE.getDescriptionId(),
            100,
            AttributeModifier.Operation.ADDITION
    );

    @Override
    public void initBeforeActivating() {
        calcAttackCooldown();
    }

    @Override
    protected void activate() {

        if (target.getHealth() <= 0F || !target.isAlive()) {
            return;
        }

        if (stopFriendlyFire()) {
            return;
        }

        DmgByElement info = getDmgByElement();

        if (data.isDodged()) {
            if (attackInfo != null) {
                attackInfo.event.damage = 0; // todo test this
            }
            cancelDamage();
            sendDamageParticle(info);
            SoundUtils.playSound(target, SoundEvents.SHIELD_BLOCK, 1, 1.5F);
            return;
        }

        float dmg = info.totalDmg;


        float vanillaDamage = HealthUtils.realToVanilla(target, dmg);

        if (this.data.isCanceled()) {
            cancelDamage();
            return;
        }


        if (target instanceof Player) {
            info.dmgmap.forEach((key, value) -> {
                DeathStatsData.record((Player) target, key, value);
            });
        }

        DamageSource dmgsource = new DamageSource(source.level().registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(DAMAGE_TYPE), source);

        // todo MyDamageSource dmgsource = new MyDamageSource(ds, source, getElement(), dmg);

        if (attackInfo == null || !(DmgSourceUtils.isMyDmgSource(attackInfo.getSource()))) { // todo wtf

            AttributeInstance attri = target.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

            if (data.getBoolean(EventData.DISABLE_KNOCKBACK) || this.getAttackType() == AttackType.dot) {
                if (!attri.hasModifier(NO_KNOCKBACK)) {
                    attri.addPermanentModifier(NO_KNOCKBACK);
                }
            }

            // todo test how different inv speeds feel
            if (target instanceof Player == false) {
                target.invulnerableTime = 3; // disable iframes hopefully
                target.hurtTime = 3;
            }


            if (this.data.isSpellEffect()) {
                if (!data.getBoolean(EventData.DISABLE_KNOCKBACK) && dmg > 0 && !data.isDodged()) {
                    // if magic shield absorbed the damage, still do knockback
                    DashUtils.knockback(source, target);
                }

                // play spell hurt sounds or else spells will feel like they do nothing
                LivingEntityAccesor duck = (LivingEntityAccesor) target;
                SoundEvent sound = SoundEvents.GENERIC_HURT;
                float volume = duck.myGetHurtVolume();
                float pitch = duck.myGetHurtPitch();
                SoundUtils.playSound(target, sound, volume, pitch);
            }

            if (target instanceof EnderDragon) {
                try {
                    // Dumb vanilla hardcodings require dumb workarounds
                    EnderDragon dragon = (EnderDragon) target;
                    EnderDragonPart part = Arrays.stream(dragon.getSubEntities())
                            .filter(x -> x.name.equals("body"))
                            .findFirst()
                            .get();
                    dragon.hurt(part, dmgsource, vanillaDamage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                if (attackInfo != null && attackInfo.event != null) {
                    if (source instanceof Player) {
                        attackInfo.event.damage *= ServerContainer.get().PLAYER_VANILLA_DMG_MULTI.get();
                    } else {
                        attackInfo.event.damage = 0;// todo test this
                    }
                }
                int inv = target.invulnerableTime;
                target.invulnerableTime = 0;
                target.hurt(dmgsource, vanillaDamage);
                target.invulnerableTime = inv;
            }

            if (target instanceof Player == false) {


                if (getAttackType() == AttackType.dot) {
                    target.invulnerableTime = 0; // disable iframes hopefully
                    target.hurtTime = 0;
                }
            }

            // allow multiple dmg same tick

            if (attri.hasModifier(NO_KNOCKBACK)) {
                attri.removeModifier(NO_KNOCKBACK);
            }

        }

        if (this.target.isDeadOrDying()) {
            OnMobKilledByDamageEvent event = new OnMobKilledByDamageEvent(this);
            event.Activate();
        }

        if (dmg > 0) {
            if (source instanceof Player) {
                sourceData.getCooldowns()
                        .setOnCooldown(CooldownsData.IN_COMBAT, 20 * 10);

                if (target instanceof Mob) {

                    GenerateThreatEvent threatEvent = new GenerateThreatEvent((Player) source, (Mob) target, ThreatGenType.deal_dmg, dmg);
                    threatEvent.Activate();
                }
            } else if (source instanceof Mob) {
                if (target instanceof Player) {
                    targetData.getCooldowns()
                            .setOnCooldown(CooldownsData.IN_COMBAT, 20 * 10);

                    GenerateThreatEvent threatEvent = new GenerateThreatEvent((Player) target, (Mob) source, ThreatGenType.take_dmg, dmg);
                    threatEvent.Activate();
                }
            }

            sendDamageParticle(info);

        }

    }

    private void sendDamageParticle(DmgByElement info) {

        String text = "";

        if (source instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) source;

            if (data.isDodged()) {
                if (getAttackType().isAttack()) {
                    text = "Dodge";
                } else {
                    text = "Resist";
                }

                DmgNumPacket packet = new DmgNumPacket(target, text, false, ChatFormatting.GOLD);
                Packets.sendToClient(player, packet);
                return;
            }

            for (Entry<Elements, Float> entry : info.dmgmap.entrySet()) {
                if (entry.getValue()
                        .intValue() > 0) {

                    text = entry.getKey().format + NumberUtils.formatDamageNumber(this, entry.getValue()
                            .intValue());

                    DmgNumPacket packet = new DmgNumPacket(target, text, data.isCrit(), entry.getKey().format);
                    Packets.sendToClient(player, packet);
                }
            }

        }
    }

    public static class DmgByElement {

        public HashMap<Elements, Float> dmgmap = new HashMap<>();
        public Elements highestDmgElement;
        public float highestDmgValue;
        public float totalDmg = 0;

        public void addDmg(float dmg, Elements element) {

            Elements ele = element;

            if (ele == null) {
                ele = Elements.Physical;
            }

            float total = (dmgmap.getOrDefault(element, 0F) + dmg);

            dmgmap.put(ele, total);

            totalDmg += dmg;

            if (total > highestDmgValue) {
                highestDmgElement = ele;
                highestDmgValue = total;
            }

        }

    }

    public DmgByElement getDmgByElement() {
        DmgByElement info = new DmgByElement();

        for (Entry<Elements, Integer> entry : bonusElementDamageMap.entrySet()) {
            if (entry.getValue() > 0) {

                // this seems like it could be buggy
                DamageEvent bonus = EventBuilder.ofDamage(attackInfo, source, target, entry.getValue())
                        .setupDamage(AttackType.attack, data.getWeaponType(), data.getStyle())
                        .set(x -> x.setElement(entry.getKey()))
                        .build();

                bonus.data.setBoolean(EventData.IS_BASIC_ATTACK, this.data.getBoolean(EventData.IS_BASIC_ATTACK));
                bonus.data.setBoolean(EventData.IS_ATTACK_FULLY_CHARGED, this.data.getBoolean(EventData.IS_ATTACK_FULLY_CHARGED));
                bonus.data.setupNumber(EventData.ATTACK_COOLDOWN, this.data.getNumber(EventData.ATTACK_COOLDOWN).number);
                bonus.calculateEffects();

                bonus.setElement(entry.getKey());
                bonus.calculateEffects();
                float dmg = bonus.getActualDamage();

                info.addDmg(dmg, bonus.getElement());

            }
        }
        info.addDmg(this.getActualDamage(), this.getElement());

        return info;

    }

    public Elements GetElement() {
        return getElement();
    }

    public void setElement(Elements ele) {
        this.data.setElement(ele);
    }

    public void setisAilmentDamage(Ailment al) {
        this.data.setString(EventData.AILMENT, al.GUID());
    }

    public void setPenetration(float val) {
        this.data.getNumber(EventData.PENETRATION).number = val;
    }

    public float getPenetration() {
        return this.data.getNumber(EventData.PENETRATION).number;
    }

}
