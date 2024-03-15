package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.EntityActivation;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.components.SpellConfiguration;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SummonPetAction;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.TargetSelector;
import com.robertx22.age_of_exile.database.data.spells.map_fields.MapField;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.CastingWeapon;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashEntities;
import com.robertx22.age_of_exile.tags.TagList;
import com.robertx22.age_of_exile.tags.imp.SpellTag;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayStyle;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpellBuilder {
    Spell spell;


    public static SpellBuilder of(String id, PlayStyle style, SpellConfiguration config, String name, List<SpellTag> tags) {
        SpellBuilder builder = new SpellBuilder();

        builder.spell = new Spell();
        builder.spell.identifier = id;
        builder.spell.config = config;
        builder.spell.loc_name = name;
        builder.spell.config.setStyle(style);

        builder.spell.getConfig().tags = new TagList<SpellTag>();
        builder.spell.getConfig().tags.addAll(tags);
        builder.spell.getConfig().tags.add(style.getTag()); // maybe get rid of style after this

        return builder;
    }

    public static SpellBuilder forEffect() {
        SpellBuilder builder = new SpellBuilder();
        builder.spell = new Spell();
        builder.spell.identifier = "";
        builder.spell.config = new SpellConfiguration();
        builder.spell.loc_name = "";

        return builder;
    }


    public SpellBuilder weaponReq(CastingWeapon wep) {
        this.spell.config.castingWeapon = wep;
        return this;
    }

    public SpellBuilder weight(int w) {
        this.spell.weight = w;
        return this;
    }

    public SpellBuilder addStat(StatMod stat) {
        this.spell.statsForSkillGem.add(stat);
        return this;
    }


    public SpellBuilder onCast(ComponentPart comp) {
        this.spell.attached.on_cast.add(comp);
        comp.addActivationRequirement(EntityActivation.ON_CAST);
        return this;
    }

    public SpellBuilder summons(EntityType type, int duration, int amount, SummonType st, boolean countsTowardsMax) {
        onCast(PartBuilder.justAction(SummonPetAction.SUMMON_PET.create(type, duration, amount, st, countsTowardsMax)))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.WITCH, 200D, 3.5D))
                .onCast(PartBuilder.aoeParticles(ParticleTypes.SOUL, 200D, 3.5D))
                .onCast(PartBuilder.playSound(SoundEvents.EVOKER_PREPARE_SUMMON, 0.5D, 1D));
        return this;
    }

    public SpellBuilder summons(EntityType type, int duration, int amount, SummonType st) {
        return summons(type, duration, amount, st, true);
    }

    public SpellBuilder teleportForward() {
        this.onCast(PartBuilder.justAction(SpellAction.SUMMON_AT_SIGHT.create(SlashEntities.SIMPLE_PROJECTILE.get(), 1D, 0D)))
                .onExpire(PartBuilder.justAction(SpellAction.SUMMON_BLOCK.create(Blocks.AIR, 1D)
                        .put(MapField.ENTITY_NAME, "block")
                        .put(MapField.BLOCK_FALL_SPEED, 0D)
                        .put(MapField.FIND_NEAREST_SURFACE, false)
                        .put(MapField.IS_BLOCK_FALLING, false)))
                .onExpire("block", PartBuilder.justAction(SpellAction.TP_TARGET_TO_SELF.create())
                        .addTarget(TargetSelector.CASTER.create()));
        return this;
    }

    public SpellBuilder onTick(ComponentPart comp) {
        return this.addEntityAction(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder onAttacked(ComponentPart comp) {
        return this.addEntityAction(Spell.DEFAULT_EN_NAME, comp.addActivationRequirement(EntityActivation.ENTITY_BASIC_ATTACKED));
    }

    public SpellBuilder addSpecificAction(String id, ComponentPart comp) {
        this.addEntityAction(id, comp);
        return this;
    }

    public SpellBuilder onExpire(ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_EXPIRE);
        return this.addEntityAction(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder onHit(ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_HIT);
        return this.addEntityAction(Spell.DEFAULT_EN_NAME, comp);
    }

    public SpellBuilder manualDesc(String desc) {

        if (!spell.locDesc.isEmpty()) {
            throw new RuntimeException("Already set manual desc!");
        }

        this.spell.manual_tip = true;
        this.spell.locDesc = desc;
        return this;
    }

    public SpellBuilder levelReq(int lvl) {
        this.spell.min_lvl = lvl;
        return this;
    }

    public SpellBuilder defaultAndMaxLevel(int lvl) {
        this.spell.default_lvl = lvl;
        maxLevel(lvl);
        return this;
    }

    public SpellBuilder derivesLevelFromSpell(String spell) {
        this.spell.lvl_based_on_spell = spell;
        return this;
    }

    public SpellBuilder showOtherSpellTooltip(String spell) {
        this.spell.show_other_spell_tooltip = spell;
        return this;
    }

    public SpellBuilder maxLevel(int lvl) {
        this.spell.max_lvl = lvl;
        return this;
    }

    public SpellBuilder disableInMapDimension() {
        this.spell.disabled_dims.add(WorldUtils.DUNGEON_DIM_ID.toString());
        return this;
    }

    public SpellBuilder onTick(String entity, ComponentPart comp) {
        return this.addEntityAction(entity, comp);
    }

    public SpellBuilder onCast(String entity, ComponentPart comp) {
        this.spell.attached.on_cast.add(comp);
        comp.addActivationRequirement(EntityActivation.ON_CAST);
        return this.addEntityAction(entity, comp);
    }

    public SpellBuilder onExpire(String entity, ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_EXPIRE);
        return this.addEntityAction(entity, comp);
    }

    public SpellBuilder onHit(String entity, ComponentPart comp) {
        comp.addActivationRequirement(EntityActivation.ON_HIT);
        return this.addEntityAction(entity, comp);
    }

    private SpellBuilder addEntityAction(String entity, ComponentPart comp) {
        Objects.requireNonNull(comp);

        if (!spell.attached.entity_components.containsKey(entity)) {
            spell.attached.entity_components.put(entity, new ArrayList<>());
        }

        this.spell.attached.getDataForEntity(entity).add(comp);

        return this;
    }

    public SpellBuilder addEffectToTooltip(EffectCtx eff) {
        this.spell.effect_tip = eff.resourcePath;
        return this;
    }

    public Spell build() {
        Objects.requireNonNull(spell);
        this.spell.addToSerializables();
        return spell;
    }

    public Spell buildForEffect() {
        Objects.requireNonNull(spell);
        return spell;
    }

    public SpellBuilder isBasicAttack() {
        this.spell.config.is_basic_attack = true;
        return this;
    }
}
