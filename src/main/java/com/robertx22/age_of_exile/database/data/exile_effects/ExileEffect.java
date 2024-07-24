package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import com.robertx22.age_of_exile.database.data.value_calc.LeveledValue;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.age_of_exile.tags.TagList;
import com.robertx22.age_of_exile.tags.imp.EffectTag;
import com.robertx22.age_of_exile.tags.imp.SpellTag;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.wrappers.ExileText;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExileEffect implements JsonExileRegistry<ExileEffect>, IAutoGson<ExileEffect>, IAutoLocName {

    public static ExileEffect SERIALIZER = new ExileEffect();

    public String id;
    public String one_of_a_kind_id = "";
    public EffectType type = EffectType.neutral;
    public int max_stacks = 1;

    public int getMaxCharges(EntityData data) {


        return data.maxCharges.bonus.getOrDefault(this.GUID(), 0) + this.max_stacks;

    }

    public SpellTag remove_on_spell_cast = null;
    public boolean stacks_affect_stats = true;

    public transient String locName = "";

    public TagList<EffectTag> tags = new TagList<>();
    public TagList<SpellTag> spell_tags = new TagList<>(); // used for augments

    public boolean hasTag(EffectTag tag) {
        return tags.contains(tag);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public List<VanillaStatData> mc_stats = new ArrayList<>();

    public List<StatMod> stats = new ArrayList<>();

    public AttachedSpell spell;


    public ResourceLocation getTexture() {
        return SlashRef.id("textures/item/mob_effects/" + GUID() + ".png");
    }

    public Item getEffectDisplayItem() {
        var id = SlashRef.id("mob_effects/" + GUID());
        return ForgeRegistries.ITEMS.getValue(id);
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
        return SlashRef.MODID + ".effect." + GUID();
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

    public List<ExactStatData> getExactStats(LivingEntity caster, Spell spell, int stacks, float multi) {

        if (caster == null) {
            return Arrays.asList();
        }

        return this.stats.stream()
                .map(x -> {
                    LeveledValue lvlval = new LeveledValue(0, 100);
                    int perc = (int) lvlval.getValue(caster, spell);

                    var result = x.ToExactStat((int) (perc), Load.Unit(caster).getLevel());

                    if (stacks > 1 && this.stacks_affect_stats) {
                        var inc = (stacks - 1) * 100F;
                        result.percentIncrease = inc;
                        result.increaseByAddedPercent();
                    }

                    result.percentIncrease = (100 * multi) - 100;
                    result.increaseByAddedPercent();

                    return result;

                })
                .collect(Collectors.toList());

    }

    public List<Component> GetTooltipString(StatRangeInfo info) {
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
            if (!this.stacks_affect_stats) {
                list.add(Chats.STACKS_DONT_MULTIPLY_STATS.locName());
            }
        }

        List<EffectTag> tags = this.tags.getTags(EffectTag.SERIALIZER);

        var tagtext = Words.TAGS.locName().append(TooltipUtils.joinMutableComps(tags.stream().map(IAutoLocName::locName).iterator(), Gui.COMMA_SEPARATOR.locName()));


        list.add(tagtext.withStyle(ChatFormatting.YELLOW));

        list.add(ExileText.emptyLine().get());

        return list;

    }

    // todo
    public void onApply(LivingEntity entity) {

        try {
            ExileEffectInstanceData data = getSavedData(entity);

            if (data != null) {
                int stacks = data.stacks;
                mc_stats.forEach(x -> x.applyVanillaStats(entity, stacks));
                Load.Unit(entity).setEquipsChanged();
            }

            Load.Unit(entity).setEquipsChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ExileEffectInstanceData getSavedData(LivingEntity en) {
        return Load.Unit(en).getStatusEffectsData().get(this);
    }

    public void onRemove(LivingEntity target) {

        try {

            mc_stats.forEach(x -> x.removeVanillaStats(target));

            ExileEffectInstanceData data = getSavedData(target);

            if (data != null) {
                LivingEntity caster = data.getCaster(target.level());
                if (caster != null && spell != null) {
                    SpellCtx ctx = SpellCtx.onExpire(caster, target, data.calcSpell);
                    spell.tryActivate(Spell.DEFAULT_EN_NAME, ctx); // source is default name at all times
                }
            }

            EntityData unitdata = Load.Unit(target);
            unitdata.getStatusEffectsData()
                    .get(this).stacks = 0;
            unitdata.setEquipsChanged();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
