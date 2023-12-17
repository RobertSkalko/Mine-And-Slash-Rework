package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.LeveledValue;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class ExileEffect implements JsonExileRegistry<ExileEffect>, IAutoGson<ExileEffect>, IAutoLocName {

    public static ExileEffect SERIALIZER = new ExileEffect();

    public String id;
    public String one_of_a_kind_id = "";
    public EffectType type = EffectType.neutral;
    public int max_stacks = 1;

    public transient String locName = "";

    public List<String> tags = new ArrayList<>();

    public boolean hasTag(EffectTags tag) {
        return tags.contains(tag.name());
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public List<VanillaStatData> mc_stats = new ArrayList<>();

    public List<StatMod> stats = new ArrayList<>();

    public AttachedSpell spell;

    public ExileStatusEffect getStatusEffect() {
        return SlashPotions.getExileEffect(id);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.EXILE_EFFECT;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<ExileEffect> getClassForSerialization() {
        return ExileEffect.class;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.StatusEffects;
    }

    @Override
    public String locNameLangFileGUID() {
        return "effect." + BuiltInRegistries.MOB_EFFECT.getKey(getStatusEffect())
                .toString();
    }

    @Override
    public String locNameForLangFile() {
        return this.locName;
    }

    public void onTick(LivingEntity entity, ExileEffectInstanceData data) {

        try {
            if (entity.isDeadOrDying()) {
                return;
            }
            if (spell == null) {
                return;
            }
            if (data == null) {
                return;
            }
            LivingEntity caster = data.getCaster(entity.level());
            if (caster == null) {
                return;
            }

            SpellCtx ctx = SpellCtx.onTick(caster, entity, data.calcSpell);
            spell.tryActivate(Spell.DEFAULT_EN_NAME, ctx); // source is default name at all times
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<ExactStatData> getExactStats(LivingEntity caster, ExileEffectInstanceData data) {

        if (caster == null) {
            return Arrays.asList();
        }

        return this.stats.stream()
                .map(x -> {
                    LeveledValue lvlval = new LeveledValue(0, 100);
                    int perc = (int) lvlval.getValue(caster, data.getSpell());

                    var result = x.ToExactStat((int) (perc * data.str_multi), Load.Unit(caster).getLevel());

                    if (data.stacks > 1) {
                        var inc = (data.stacks - 1) * 100F;
                        result.percentIncrease = inc;
                        result.increaseByAddedPercent();
                    }

                    return result;

                })
                .collect(Collectors.toList());

    }

    public List<Component> GetTooltipString(TooltipInfo info, CalculatedSpellData data) {
        List<Component> list = new ArrayList<>();

        list.add(Words.STATUS_EFFECT.locName().append(": ").append(this.locName())
                .withStyle(ChatFormatting.YELLOW));
        if (!stats.isEmpty()) {
            list.add(Words.Stats.locName().append(Words.PER_STACK.locName()).withStyle(ChatFormatting.GREEN));

            for (StatMod stat : this.stats) {
                for (Component comp : stat.getEstimationTooltip(Load.Unit(info.player).getLevel())) {
                    list.add(comp);
                }
            }

        }

        if (max_stacks > 1) {
            list.add(Chats.MAX_STACKS.locName(max_stacks));
        }

        List<EffectTags> tags = this.tags.stream()
                .map(EffectTags::valueOf)
                .toList();

        var tagtext = Words.TAGS.locName().append(TooltipUtils.getMutabletags(tags.stream().iterator(), Gui.SPELL_TAG_SEPARATOR.locName()));


        list.add(tagtext.withStyle(ChatFormatting.YELLOW));

        list.add(ExileText.emptyLine().get());

        return list;

    }

    @Override
    public int Weight() {
        return 1000;
    }
}
