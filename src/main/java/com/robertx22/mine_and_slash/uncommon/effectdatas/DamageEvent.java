package com.robertx22.mine_and_slash.uncommon.effectdatas;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.SoundUtils;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.DamageNullifiedParticle;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.ElementDamageParticle;
import com.robertx22.mine_and_slash.aoe_data.database.ailments.Ailment;
import com.robertx22.mine_and_slash.capability.entity.CooldownsData;
import com.robertx22.mine_and_slash.capability.player.data.PlayerConfigData;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.exile_effects.ExileEffect;
import com.robertx22.mine_and_slash.database.data.exile_effects.ExileEffectInstanceData;
import com.robertx22.mine_and_slash.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.mine_and_slash.database.data.rarities.MobRarity;
import com.robertx22.mine_and_slash.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.mine_and_slash.database.data.stats.layers.StatLayerData;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.FullSwingDamage;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.DamageAbsorbedByMana;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield.MagicShield;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.event.MASEvent;
import com.robertx22.mine_and_slash.event.server.TriggerInteractionResultEvent;
import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.mine_and_slash.loot.LootUtils;
import com.robertx22.mine_and_slash.mixin_ducks.DamageSourceDuck;
import com.robertx22.mine_and_slash.mixin_ducks.LivingEntityAccesor;
import com.robertx22.mine_and_slash.mixin_ducks.ProjectileEntityDuck;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.item_classes.GearItemData;
import com.robertx22.mine_and_slash.uncommon.MathHelper;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.datasaving.StackSaving;
import com.robertx22.mine_and_slash.uncommon.effectdatas.rework.EventData;
import com.robertx22.mine_and_slash.uncommon.enumclasses.AttackType;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.enumclasses.WeaponTypes;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.DmgNumPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

public class DamageEvent extends EffectEvent {
    public static ResourceKey<DamageType> DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, SlashRef.id("mod"));

    public static String ID = "on_damage";

    public LivingEntity petEntity;


    @Override
    public String GUID() {
        return ID;
    }

    protected DamageEvent(AttackInformation attackInfo, LivingEntity source, LivingEntity target, float dmg) {
        super(dmg, source, target);
        this.attackInfo = attackInfo;
        calcBlock();

        addMobDamageMultipliers();

    }

    public void addMobDamageMultipliers() {

        try {
            if (source instanceof Player == false) {

                if (target instanceof Player) {
                    if (sourceData.getLevel() > targetData.getLevel()) {
                        float penalty = LootUtils.getLevelDistancePunishmentMulti(sourceData.getLevel(), targetData.getLevel());

                        if (penalty < 1) {
                            float dmgmulti = 2F - penalty;
                            this.addMoreMulti(Words.HIGH_LVL_MOB_DMG_MULTI.locName(), EventData.NUMBER, dmgmulti);
                        }
                    }
                }

                var balance = GameBalanceConfig.get();


                if (balance.MOB_DMG_POWER_SCALING != 1) {
                    float multi = (float) (balance.MOB_DMG_POWER_SCALING_BASE * (float) Math.pow(balance.MOB_DMG_POWER_SCALING, sourceData.getLevel()));
                    this.addMoreMulti(Words.LVL_EXPONENT_MOB_DMG.locName(), EventData.NUMBER, multi);
                }

                MobRarity rar = sourceData.getMobRarity();

                float enconfigmulti = (float) ExileDB.getEntityConfig(source, sourceData).dmg_multi;

                this.addMoreMulti(Words.MOB_RARITY_MULTI.locName(), EventData.NUMBER, rar.DamageMultiplier());

                if (enconfigmulti != 1) {
                    this.addMoreMulti(Words.MOB_CONFIG_MULTI.locName(), EventData.NUMBER, enconfigmulti);
                }

                if (WorldUtils.isDungeonWorld(source.level())) {
                    if (target instanceof Player) {
                        var map = Load.mapAt(target.level(), target.blockPosition());
                        if (map != null && map.map != null) {
                            if (!map.map.getStatReq().meetsReq(map.map.lvl, Load.Unit(target))) {
                                float minusres = map.map.getStatReq().getLackingResistNumber(map.map.lvl, Load.Unit(target));
                                float multi = Math.max((float) (minusres * GameBalanceConfig.get().MOB_DMG_MULTI_PER_MAP_RES_REQ_LACKING), 2.0f);
                                this.addMoreMulti(Words.MAP_RES_REQ_LACK_DMG_MULTI.locName(), EventData.NUMBER, multi);
                            }
                        }
                    }
                }

            } else {
                if (targetData.getLevel() > sourceData.getLevel()) {
                    if (target instanceof Player == false) {
                        float penalty = LootUtils.getLevelDistancePunishmentMulti(sourceData.getLevel(), targetData.getLevel());
                        if (penalty < 1) {
                            this.addMoreMulti(Words.DMG_TO_HIGH_LVL_MOB_DMG_MULTI.locName(), EventData.NUMBER, penalty);
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Component getDamageName() {

        try {
            if (this.data.isBasicAttack()) {
                return Component.literal("Attack");
            }
            if (this.data.isSpellEffect()) {
                return getSpell().locName();
            }

            var id = data.getString(EventData.AILMENT);

            if (ExileDB.Ailments().isRegistered(id)) {
                var ailment = ExileDB.Ailments().get(id);
                return ailment.locName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Component.literal("[Error, dmg isn't a basic attack, spell or ailment]");
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
                    this.data.setHitAvoided(EventData.IS_BLOCKED);
                }
            }
        }
    }

    public float getActualDamage() {
        float dmg = this.data.getNumber();
        if (dmg <= 0) {
            return 0;
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


    private float getAttackSpeedDamageMulti() {

        float multi = 1;

        float cool = data.getNumber(EventData.ATTACK_COOLDOWN).number;

        // we no longer remove damage from fast clicked attacks, energy exists! dmg *= cool;

        if (cool < 0.1F) {
            // we dont want to allow too fast mob clickings
            multi = 0;
            this.cancelDamage();
        }


        if (cool > 0.8F) {
            multi = sourceData.getUnit().getCalculatedStat(FullSwingDamage.getInstance()).getMultiplier();
            //ParticleUtils.spawnDefaultSlashingWeaponParticles(source);
        }

        return multi;

    }

    private void modifyIfArrowDamage() {
        if (attackInfo != null && attackInfo.getSource() != null) {
            if (attackInfo.getSource().getDirectEntity() instanceof ProjectileEntityDuck) {
                if (data.getWeaponType() == WeaponTypes.bow) {
                    // don't use this for crossbows, only bows need to be charged fully

                    ProjectileEntityDuck duck = (ProjectileEntityDuck) attackInfo.getSource().getDirectEntity();

                    float arrowmulti = duck.my$getDmgMulti();

                    this.addMoreMulti(Words.ARROW_DRAW_AMOUNT_MULTI.locName(), EventData.NUMBER, arrowmulti);

                    // multiply dmg by saved charge value
                }
            }
        }

    }

    public boolean areBothPlayers() {
        if (source instanceof ServerPlayer && target instanceof ServerPlayer) {
            return true;
        }
        return false;
    }

    public void cancelDamage() {
        this.data.getNumber(EventData.NUMBER).number = 0;
        this.data.setBoolean(EventData.DISABLE_KNOCKBACK, true);

        this.data.setBoolean(EventData.CANCELED, true);
        if (attackInfo != null) {
            attackInfo.setAmount(0);
            attackInfo.setCanceled(true);
        }
        return;
    }

    public boolean stopFriendlyFire() {
        if (WorldUtils.isMapWorldClass(source.level())) {
            // in maps, we dont want mobs to damage each other
            if (AllyOrEnemy.allies.is(source, target)) {
                cancelDamage();
                return true;
            }
        } else {
            // outside maps, we want zombies to kill villagers etc
            if (source instanceof Player) {
                if (AllyOrEnemy.allies.is(source, target)) {
                    cancelDamage();
                    return true;
                }
            }
        }
        return false;
    }

    static AttributeModifier NO_KNOCKBACK = new AttributeModifier(
            UUID.fromString("e926df30-c376-11ea-87d0-0242ac131053"),
            Attributes.KNOCKBACK_RESISTANCE.getDescriptionId(),
            100,
            AttributeModifier.Operation.ADDITION
    );

    @Override
    public String getName() {
        return "Damage Event";
    }

    public float wepdmgMulti = 1;

    @Override
    public void initBeforeActivating() {
        calcAttackCooldown();

        if (source instanceof Player) {
            if (data.isBasicAttack()) {
                this.addMoreMulti(Words.ATTACK_SPEED_MULTI.locName(), EventData.NUMBER, getAttackSpeedDamageMulti());
            }
            modifyIfArrowDamage();

        }

        // todo this should be in layers too or multis
        if (areBothPlayers()) {
            this.addMoreMulti(Words.PVP_DMG_MULTI.locName(), EventData.NUMBER, ServerContainer.get().PVP_DMG_MULTI.get().floatValue());
        }


        if (this.data.isBasicAttack()) {
            if (this.attackInfo != null && attackInfo.weaponData != null) {
                if (!data.getBoolean(EventData.UNARMED_ATTACK)) {
                    float multi = attackInfo.weaponData.GetBaseGearType().getGearSlot().getBasicDamageMulti();
                    this.wepdmgMulti = multi;
                    this.addMoreMulti(Words.WEAPON_BASIC_ATTACK_DMG_MULTI.locName(), EventData.NUMBER, multi);
                }
            }
        }
    }

    public boolean absorbedCompletely = false;

    // todo this is using total ele dmg and saying only 1 ele, fuck
    public MutableComponent getDamageMessage(DmgByElement info) {

        MutableComponent ele = Component.literal(getElement().getIconNameDmg());

        if (info.isMixedDamage()) {
            ele = Component.literal("\u2600" + " ").append(Words.MULTI_ELEMENT.locName()).withStyle(ChatFormatting.LIGHT_PURPLE);
        }

        return Words.DAMAGE_MESSAGE.locName(source.getDisplayName(), MMORPG.DECIMAL_FORMAT.format(info.totalDmg), ele, getDamageName())
                .withStyle(Style.EMPTY.applyFormat(ChatFormatting.RED).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, getInfoHoverMessage(info, true))));

    }


    // wait, bonus archmage dmg is applied to any bonus ele dmg??

    public MutableComponent getInfoHoverMessage(DmgByElement info, boolean doBonusDmg) {
        // int main = info.dmgmap.getOrDefault(getElement(), 0F).intValue();


        MutableComponent msg = Component.empty();

        if (this.isSpell()) {
            msg.append(Component.literal("Spell: ").append(getSpell().locName()).append("\n").withStyle(ChatFormatting.AQUA));
        }
        if (this.data.isBasicAttack()) {
            msg.append(Component.literal("Basic Attack\n").withStyle(ChatFormatting.RED));
        }
        if (this.data.getAttackType() == AttackType.dot) {
            msg.append(Component.literal("Damage Over Time\n").withStyle(ChatFormatting.RED));
        }

        if (!this.data.getString(EventData.AILMENT).isEmpty()) {
            String ailment = this.data.getString(EventData.AILMENT);
            var ai = ExileDB.Ailments().get(ailment);
            msg.append(Component.literal("Ailment: ").append(ai.locName()).append("\n").withStyle(ai.element.format));
        }

        msg.append(Component.literal("\n" + getElement().getIconNameDmg() + ":\n").withStyle(getElement().format));

        msg.append(Component.literal("Base Damage: " + (int) this.data.getOriginalNumber(EventData.NUMBER).number + "\n").withStyle(ChatFormatting.BLUE));

        msg.append(Component.literal("Damage Info: \n").withStyle(ChatFormatting.RED));

        for (StatLayerData layerData : this.getSortedLayers()) {
            if (layerData.numberID.equals(EventData.NUMBER)) {
                msg.append(layerData.getLayer().getTooltip(layerData).append("\n"));
            }
        }

        if (!getMoreMultis().isEmpty()) {
            msg.append(Component.literal("Multipliers: \n").withStyle(ChatFormatting.LIGHT_PURPLE));

            for (MoreMultiData multi : this.getMoreMultis()) {
                if (multi.numberid.equals(EventData.NUMBER)) {
                    msg.append(multi.text.append(": ").append(Component.literal("x" + MMORPG.DECIMAL_FORMAT.format(multi.multi)))).append("\n");
                }
            }
        }

        msg.append(Component.literal("Final Damage: " + (int) info.dmgmap.getOrDefault(getElement(), 0F).intValue() + "\n").withStyle(ChatFormatting.GOLD));


        if (doBonusDmg) {
            if (info.isMixedDamage()) {

                for (Entry<Elements, Float> en : info.dmgmap.entrySet()) {
                    if (en.getKey() != getElement()) {

                        msg.append(Component.literal("\n- Bonus Damage Types:\n").withStyle(ChatFormatting.YELLOW));

                        var dmg = info.eventMap.get(en.getKey());
                        msg.append(dmg.getInfoHoverMessage(info, false));

                    }
                }
            }
        }

        if (doBonusDmg) {
            msg.append(Component.literal("Total Combined Damage: " + (int) info.totalDmg + "\n").withStyle(ChatFormatting.GOLD));
        }

        return msg;
    }

    @Override
    protected void activate() {

        if (target.getHealth() <= 0F || !target.isAlive()) {
            return;
        }

        if (stopFriendlyFire()) {
            return;
        }


        this.targetData.lastDamageTaken = this;

        DmgByElement info = calculateAllBonusElementalDamage();

        if (data.isHitAvoided()) {
            if (attackInfo != null) {
                attackInfo.setAmount(0);
                attackInfo.setCanceled(true);
            }
            cancelDamage();
            MASEvent.INSTANCE.post(new TriggerInteractionResultEvent(getAttackType().isAttack() ? DamageNullifiedParticle.Type.DODGE : DamageNullifiedParticle.Type.RESIST, (ServerPlayer)source, target));
            //sendDamageParticle(info);

            //move this sound to InteractionResultHandler.
            //SoundUtils.playSound(target, SoundEvents.SHIELD_BLOCK, 1, 1.5F);
            return;
        }

        float dmg = info.totalDmg;


        if (target instanceof Player p) {
            if (Load.player(p).config.isConfigEnabled(PlayerConfigData.Config.DAMAGE_MESSAGES)) {
                try {
                    p.sendSystemMessage(getDamageMessage(info));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (source instanceof Player p) {
            if (Load.player(p).config.isConfigEnabled(PlayerConfigData.Config.DAMAGE_MESSAGES)) {
                p.sendSystemMessage(getDamageMessage(info));
            }
        }

        if (target instanceof Player p) { // todo this code sucks
            // a getter should not modify anything
            dmg = DamageAbsorbedByMana.modifyEntityDamage(this, dmg);
            dmg = MagicShield.modifyEntityDamage(this, dmg);
        }

        float vanillaDamage = HealthUtils.realToVanilla(target, dmg);

        if (absorbedCompletely) {
            if (vanillaDamage < 0.0001F) {
                vanillaDamage = 0.0001F;
            }
        }

        if (this.data.isCanceled()) {
            cancelDamage();
            return;
        }


        AttributeInstance attri = target.getAttribute(Attributes.KNOCKBACK_RESISTANCE);

        if (data.getBoolean(EventData.DISABLE_KNOCKBACK) || this.getAttackType() == AttackType.dot) {
            if (!attri.hasModifier(NO_KNOCKBACK)) {
                attri.addPermanentModifier(NO_KNOCKBACK);
            }
        }

        DamageSource dmgsource = new DamageSource(source.level().registryAccess().registry(Registries.DAMAGE_TYPE).get().getHolderOrThrow(DAMAGE_TYPE), source, source, source.position());


        if (this.data.isSpellEffect()) {
            if (!data.getBoolean(EventData.DISABLE_KNOCKBACK) && dmg > 0 && !data.isHitAvoided()) {
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


        var config = ExileDB.getEntityConfig(target, Load.Unit(target));

        if (target instanceof Player == false && config != null && config.set_health_damage_override) {
            float hp = MathHelper.clamp(target.getHealth() - vanillaDamage, 0, target.getMaxHealth());
            target.setHealth(hp);
            // todo this might create bugs but its probably better that damage actually works..
            if (target.getHealth() <= 0) {
                target.die(target.damageSources().mobAttack(this.source));
            }
            if (attackInfo != null) {
                attackInfo.setAmount(0.000001F);
            }
        } else {
            if (attackInfo != null) {
                DamageSourceDuck duck = (DamageSourceDuck) attackInfo.getSource();
                duck.setMnsDamage(vanillaDamage);
                duck.tryOverrideDmgWithMns(attackInfo);
            } else {
                if (target instanceof Player == false) {
                    int inv = target.invulnerableTime;
                    target.invulnerableTime = 0;
                    target.hurt(dmgsource, vanillaDamage);
                    target.invulnerableTime = inv;
                } else {
                    target.hurt(dmgsource, vanillaDamage);
                }
            }
        }
        //target.invulnerableTime = 20;
        //target.hurtTime = 20;


        if (attri.hasModifier(NO_KNOCKBACK)) {
            attri.removeModifier(NO_KNOCKBACK);
        }


        if (dmg > 0) {


            // todo can this be done better?
            if (this.data.isBasicAttack()) {
                for (Entry<String, ExileEffectInstanceData> e : targetData.getStatusEffectsData().exileMap.entrySet().stream().toList()) {
                    if (!e.getValue().shouldRemove()) {
                        var data = e.getValue();
                        var sd = data.calcSpell;
                        var ctx = SpellCtx.onEntityBasicAttacked(this.source, sd, target);
                        ExileEffect eff = ExileDB.ExileEffects().get(e.getKey());
                        if (eff.spell != null) {
                            eff.spell.tryActivate(SpellCtx.ON_ENTITY_ATTACKED, ctx); // i can use this kind of as event
                        }
                    }
                }
            }


            if (source instanceof Player p) {


                p.setLastHurtMob(target); // this allows summons to know who to attack

                sourceData.getCooldowns().setOnCooldown(CooldownsData.IN_COMBAT, 20 * 10);
                if (target instanceof Mob) {
                    if (petEntity instanceof LivingEntity && Load.Unit(petEntity).isSummon()) {
                        GenerateThreatEvent threatEvent = new GenerateThreatEvent(petEntity, (Mob) target, ThreatGenType.deal_dmg, dmg);
                        threatEvent.Activate();
                    } else {
                        GenerateThreatEvent threatEvent = new GenerateThreatEvent((Player) source, (Mob) target, ThreatGenType.deal_dmg, dmg);
                        threatEvent.Activate();
                    }
                }
            } else if (source instanceof Mob) {
                if (target instanceof Player) {
                    targetData.getCooldowns().setOnCooldown(CooldownsData.IN_COMBAT, 20 * 10);

                    GenerateThreatEvent threatEvent = new GenerateThreatEvent((Player) target, (Mob) source, ThreatGenType.take_dmg, dmg);
                    threatEvent.Activate();
                }
            }
            MASEvent.INSTANCE.post(new TriggerInteractionResultEvent(ElementDamageParticle.DamageInformation.fromDmgByElement(info, data.isCrit()), (ServerPlayer)source, target));
            //sendDamageParticle(info);

            // target.invulnerableTime = 20;

        }

    }

    private void sendDamageParticle(DmgByElement info) {

        String text = "";

        if (source instanceof ServerPlayer) {
            ServerPlayer player = (ServerPlayer) source;

            if (data.isHitAvoided()) {
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

        private HashMap<Elements, Float> dmgmap = new HashMap<>();
        private HashMap<Elements, DamageEvent> eventMap = new HashMap<>();


        public float totalDmg = 0;

        public boolean isMixedDamage() {
            int bonusdmg = (int) dmgmap.entrySet().stream().filter(x -> true).count();
            return bonusdmg > 1;
        }

        public HashMap<Elements, Float> getDmgmap() {
            return dmgmap;
        }

        public void addDmg(DamageEvent event, float dmg, Elements element) {

            Elements ele = element;

            if (ele == null) {
                ele = Elements.Physical;
            }

            float total = (dmgmap.getOrDefault(element, 0F) + dmg);

            dmgmap.put(ele, total);
            eventMap.put(ele, event);

            totalDmg += dmg;
        }

    }

    // this calculates all the bonus elemental damages, uses the specific numbers for particles only, and the totalvalue for actually dealing dmg, ONCE
    public DmgByElement calculateAllBonusElementalDamage() {
        DmgByElement info = new DmgByElement();

        for (Entry<Elements, Integer> entry : bonusElementDamageMap.entrySet()) {
            if (entry.getValue() > 0) {

                // this how do i make a copy of the same event that it was at the start..except element
                DamageEvent bonus = EventBuilder.ofDamage(attackInfo, source, target, entry.getValue())
                        .setupDamage(AttackType.hit, data.getWeaponType(), data.getStyle())
                        .set(x -> {
                            if (wepdmgMulti != 1) {
                                x.addMoreMulti(Words.WEAPON_BASIC_ATTACK_DMG_MULTI.locName(), EventData.NUMBER, wepdmgMulti);
                            }
                            x.setElement(entry.getKey());
                        })
                        .build();

                if (isSpell()) {
                    bonus.data.setString(EventData.SPELL, this.data.getString(EventData.SPELL));
                }
                bonus.data.setBoolean(EventData.IS_BONUS_ELEMENT_DAMAGE, true);

                bonus.data.setBoolean(EventData.IS_BASIC_ATTACK, this.data.getBoolean(EventData.IS_BASIC_ATTACK));
                bonus.data.setBoolean(EventData.IS_ATTACK_FULLY_CHARGED, this.data.getBoolean(EventData.IS_ATTACK_FULLY_CHARGED));
                bonus.data.setupNumber(EventData.ATTACK_COOLDOWN, this.data.getNumber(EventData.ATTACK_COOLDOWN).number);
                bonus.data.setupNumber(EventData.DMG_EFFECTIVENESS, this.data.getNumber(EventData.DMG_EFFECTIVENESS).number);

                bonus.calculateEffects();

                bonus.setElement(entry.getKey());
                bonus.calculateEffects();
                float dmg = bonus.getActualDamage();

                info.addDmg(bonus, dmg, bonus.getElement());

            }
        }
        info.addDmg(this, this.getActualDamage(), this.getElement());

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
