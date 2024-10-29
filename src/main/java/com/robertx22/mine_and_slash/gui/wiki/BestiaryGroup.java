package com.robertx22.mine_and_slash.gui.wiki;

import com.robertx22.library_of_exile.utils.CLOC;
import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.affixes.Affix;
import com.robertx22.mine_and_slash.database.data.aura.AuraGem;
import com.robertx22.mine_and_slash.database.data.currency.base.CodeCurrency;
import com.robertx22.mine_and_slash.database.data.exile_effects.ExileEffect;
import com.robertx22.mine_and_slash.database.data.gems.Gem;
import com.robertx22.mine_and_slash.database.data.runes.Rune;
import com.robertx22.mine_and_slash.database.data.runewords.RuneWord;
import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.support_gem.SupportGem;
import com.robertx22.mine_and_slash.database.data.unique_items.UniqueGear;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.gui.wiki.all.DBItemEntry;
import com.robertx22.mine_and_slash.gui.wiki.all.ProfExpBestiary;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterEntry;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.GroupFilterType;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all.AffixTypeFilter;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all.RunewordRunesCountFilter;
import com.robertx22.mine_and_slash.loot.LootInfo;
import com.robertx22.mine_and_slash.loot.blueprints.GearBlueprint;
import com.robertx22.mine_and_slash.loot.blueprints.SkillGemBlueprint;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import com.robertx22.mine_and_slash.uncommon.localization.Gui;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class BestiaryGroup<T> {


    // add effect todo
    private static List<BestiaryGroup> all = new ArrayList<>();


    public static BestiaryGroup<?> CURRENCY = new BestiaryGroup<CodeCurrency>() {
        @Override
        public List<BestiaryEntry> getAll(int lvl) {
            return ExileDB.Currency().getList().stream().map(x -> new BestiaryEntry.Item(x, x.getItem().getDefaultInstance())).collect(Collectors.toList());
        }

        @Override
        public Component getName() {
            return Words.Currency.locName();
        }

        @Override
        public String texName() {
            return "currency";
        }
    };

    public static BestiaryGroup<?> AFFIX = new DBItemEntry<Affix>(ExileRegistryTypes.AFFIX, Words.AFFIXES, "affix", x -> {

        List<Component> list = new ArrayList<>();

        list.add(x.locName().withStyle(ChatFormatting.AQUA));
        for (StatMod mod : x.getStats()) {
            list.addAll(mod.getEstimationTooltip(1));
        }

        list.addAll(x.requirements().GetTooltipString());

        list.add(Component.empty());
        list.add(Component.literal("Weight: " + x.weight));
        list.add(Component.literal("Id: " + x.GUID()));
        list.add(Component.literal("Affix Type: " + x.type.name()));

        return new BestiaryEntry.Tooltip<Affix>(x, Items.MAP.asItem().getDefaultInstance(), x.locName().getString(), list);
    });


    public static BestiaryGroup<?> RUNE = new DBItemEntry<Rune>(ExileRegistryTypes.RUNE, Words.Rune, "rune", x -> new BestiaryEntry.Item(x, x.getItem().getDefaultInstance()));
    public static BestiaryGroup<?> GEM = new DBItemEntry<Gem>(ExileRegistryTypes.GEM, Words.Gem, "gem", x -> new BestiaryEntry.Item(x, x.getItem().getDefaultInstance()));
    public static BestiaryGroup<?> EFFECT = new DBItemEntry<ExileEffect>(ExileRegistryTypes.EXILE_EFFECT, Words.STATUS_EFFECT, "effect",
            x -> {
                var tooltip = new ArrayList<Component>();
                tooltip.addAll(x.GetTooltipString(new StatRangeInfo(ModRange.hide())));
                return new BestiaryEntry.Tooltip(x, x.getEffectDisplayItem().getDefaultInstance(), CLOC.translate(x.locName()), tooltip);
            });

    public static BestiaryGroup<?> RUNEWORD = new DBItemEntry<RuneWord>(ExileRegistryTypes.RUNEWORDS, Words.Runeword, "runeword",
            x -> {
                var tooltip = new ArrayList<Component>();

                tooltip.add(x.locName().withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));
                tooltip.add(Component.empty());

                var slotsText = Words.ON_SLOTS.locName();

                List<MutableComponent> list = x.slots.stream().map(string -> ExileDB.GearSlots().get(string).locName()).toList();

                tooltip.add(slotsText.append(TooltipUtils.joinMutableComps(list.iterator(), Gui.COMMA_SEPARATOR.locName())));
                tooltip.add(Component.empty());


                tooltip.add(Component.literal(RuneWord.join(x.runes.listIterator(), ", ").toUpperCase(Locale.ROOT)).withStyle(ChatFormatting.RED));
                tooltip.add(Component.empty());
                tooltip.add(Words.Stats.locName().append(":"));

                for (StatMod stat : x.stats) {
                    tooltip.addAll(stat.getEstimationTooltip(1));
                }
                return new BestiaryEntry.Tooltip(x, Items.BOOK.getDefaultInstance(), CLOC.translate(x.locName()), tooltip);
            });

    public static BestiaryGroup<?> UNIQUE_GEAR = new DBItemEntry<UniqueGear>(ExileRegistryTypes.UNIQUE_GEAR, Words.Unique_Gear, "unique_gear", x -> {
        var b = new GearBlueprint(LootInfo.ofLevel(1));
        b.uniquePart.set(x);
        b.rarity.set(ExileDB.GearRarities().get(IRarity.UNIQUE_ID));
        b.gearItemSlot.set(x.getBaseGear());
        return new BestiaryEntry.NamedItem(x, b.createStack(), CLOC.translate(x.locName()));
    });


    public static BestiaryGroup<?> SPELL = new DBItemEntry<Spell>(ExileRegistryTypes.SPELL, Words.SPELL, "spell", x -> {
        var b = new SkillGemBlueprint(LootInfo.ofLevel(1), SkillGemData.SkillGemType.SKILL);
        b.setType(x.GUID());
        return new BestiaryEntry.NamedItem(x, b.createStack(), CLOC.translate(x.locName())).setIcon(x.getIconLoc());
    });

    public static BestiaryGroup<?> SUPP = new DBItemEntry<SupportGem>(ExileRegistryTypes.SUPPORT_GEM, Words.SUPPGEM, "supp_gem", x -> {
        var b = new SkillGemBlueprint(LootInfo.ofLevel(1), SkillGemData.SkillGemType.SUPPORT);
        b.setType(x.GUID());
        return new BestiaryEntry.NamedItem(x, b.createStack(), CLOC.translate(x.locName()));
    });
    public static BestiaryGroup<?> AURA = new DBItemEntry<AuraGem>(ExileRegistryTypes.AURA, Words.AURA, "aura", x -> {
        var b = new SkillGemBlueprint(LootInfo.ofLevel(1), SkillGemData.SkillGemType.AURA);
        b.setType(x.GUID());
        return new BestiaryEntry.NamedItem(x, b.createStack(), CLOC.translate(x.locName()));
    });

    public static List<BestiaryGroup> getAll() {

        if (all.isEmpty()) {

            all.add(CURRENCY);
            all.add(AFFIX);
            all.add(GEM);
            all.add(RUNE);
            all.add(UNIQUE_GEAR);
            all.add(RUNEWORD);
            all.add(AURA);
            all.add(SUPP);
            all.add(EFFECT);
            all.add(SPELL);
            all.add(new ProfExpBestiary());


            AFFIX.addFilter(Words.AFFIX_TYPES, () -> Arrays.stream(Affix.AffixSlot.values()).map(x -> new AffixTypeFilter(x)).collect(Collectors.toList()));

            RUNEWORD.addFilter(Words.RUNE_COUNT, () -> {
                Set<Integer> nums = new HashSet<>();
                for (RuneWord r : ExileDB.RuneWords().getList()) {
                    nums.add(r.runes.size());
                }
                return nums.stream().map(e -> new RunewordRunesCountFilter(e)).collect(Collectors.toList());
            });
        }

        return all;

    }

    public final ResourceLocation getTextureLoc() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/bestiary/group_icons/" + texName() + ".png");
    }

    public abstract List<BestiaryEntry> getAll(int lvl);

    public abstract Component getName();

    public int getSize() {
        return getAll(1).size();
    }


    public abstract String texName();


    public void addFilter(Words word, Supplier<List<GroupFilterEntry>> entries) {
        var filter = new GroupFilterType(this, word, entries);
    }

}

