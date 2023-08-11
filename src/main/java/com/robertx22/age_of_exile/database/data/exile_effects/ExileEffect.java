package com.robertx22.age_of_exile.database.data.exile_effects;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.spells.components.AttachedSpell;
import com.robertx22.age_of_exile.database.data.spells.entities.EntitySavedSpellData;
import com.robertx22.age_of_exile.database.data.value_calc.LeveledValue;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashPotions;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.vanilla_mc.potion_effects.types.ExileStatusEffect;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.Level;

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

    public List<ExactStatData> getExactStats(Level world, EntitySavedSpellData data) {

        if (data.getCaster(world) == null) {
            return Arrays.asList();
        }

        return this.stats.stream()
                .map(x -> {

                    LeveledValue lvlval = new LeveledValue(0, 100);

                    int perc = (int) lvlval.getValue(data.getCaster(world), data.getSpell());

                    return x.ToExactStat(perc, data.lvl);
                })
                .collect(Collectors.toList());

    }

    public List<MutableComponent> GetTooltipString(TooltipInfo info, EntitySavedSpellData data) {
        List<MutableComponent> list = new ArrayList<>();

        list.add(Component.literal("Status Effect: ").append(this.locName())
                .withStyle(ChatFormatting.YELLOW));
        if (!stats.isEmpty()) {
            list.add(Words.Stats.locName()
                    .append(": ")
                    .withStyle(ChatFormatting.GREEN));
            getExactStats(info.player.level(), data).forEach(x -> {
                list.addAll(x.GetTooltipString(info));
            });
        }

        if (max_stacks > 1) {
            list.add(Component.literal("Maximum Stacks: " + max_stacks));
        }

        List<EffectTags> tags = this.tags.stream()
                .map(x -> EffectTags.valueOf(x))
                .collect(Collectors.toList());

        String string = "Tags: ";

        for (EffectTags x : tags) {
            string += x.name + " ";
        }

        list.add(Component.literal(ChatFormatting.YELLOW + string));

        return list;

    }

    @Override
    public int Weight() {
        return 1000;
    }
}
