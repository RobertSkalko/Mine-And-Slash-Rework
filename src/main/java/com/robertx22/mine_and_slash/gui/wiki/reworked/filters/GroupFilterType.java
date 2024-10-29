package com.robertx22.mine_and_slash.gui.wiki.reworked.filters;

import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.gui.texts.textblocks.WorksOnBlock;
import com.robertx22.mine_and_slash.gui.wiki.BestiaryGroup;
import com.robertx22.mine_and_slash.gui.wiki.reworked.NewWikiScreen;
import com.robertx22.mine_and_slash.gui.wiki.reworked.filters.all.*;
import com.robertx22.mine_and_slash.tags.imp.SpellTag;
import com.robertx22.mine_and_slash.uncommon.localization.Words;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class GroupFilterType {
    public static List<GroupFilterType> ALL = new ArrayList<>();


    public static GroupFilterType AFFIX_SLOTS = new GroupFilterType(BestiaryGroup.AFFIX, Words.ON_SLOTS, () -> ExileDB.GearTypes().getList().stream().map(x -> new AffixForBaseGearFilter(x)).collect(Collectors.toList()));
   

    public static GroupFilterType UNIQUE_SLOTS = new GroupFilterType(BestiaryGroup.UNIQUE_GEAR, Words.ON_SLOTS, () -> ExileDB.GearSlots().getList().stream().map(x -> new UniqueSlotFilter(x)).collect(Collectors.toList()));
    public static GroupFilterType UNIQUE_LEAGUES = new GroupFilterType(BestiaryGroup.UNIQUE_GEAR, Words.LEAGUE, () -> ExileDB.LeagueMechanics().getList().stream().map(x -> new UniqueLeagueFilter(x)).collect(Collectors.toList()));
    public static GroupFilterType CURRENCY_LEAGUES = new GroupFilterType(BestiaryGroup.CURRENCY, Words.LEAGUE, () -> ExileDB.LeagueMechanics().getList().stream().map(x -> new CurrencyLeagueFilter(x)).collect(Collectors.toList()));


    public static GroupFilterType RUNEWORD_SLOTS = new GroupFilterType(BestiaryGroup.RUNEWORD, Words.ON_SLOTS, () -> ExileDB.GearSlots().getList().stream().map(x -> new RunewordSlotFilter(x)).collect(Collectors.toList()));
    public static GroupFilterType SPELL_TAGS = new GroupFilterType(BestiaryGroup.SPELL, Words.TAGS, () -> SpellTag.getAll().stream().map(x -> new SpellTagFilter(x)).collect(Collectors.toList()));
    public static GroupFilterType CURRENCY_FOR_ITEM_TYPES = new GroupFilterType(BestiaryGroup.CURRENCY, Words.ITEM_TYPES, () -> Arrays.stream(WorksOnBlock.ItemType.values()).map(x -> new ItemTypeTargetFilter(x)).collect(Collectors.toList()));

    static {
        init();
    }

    public static void init() {

        forGroups(Words.RARITIES, () -> ExileDB.GearRarities().getList().stream().map(x -> new RarityFilter(x)).collect(Collectors.toList()),
                BestiaryGroup.GEM,
                BestiaryGroup.CURRENCY
        );

    }

    static void forGroups(Words word, Supplier<List<GroupFilterEntry>> entries, BestiaryGroup... groups) {
        for (BestiaryGroup group : groups) {
            new GroupFilterType(group, word, entries);
        }
    }

    public BestiaryGroup forGroup;
    public Words word;
    public Supplier<List<GroupFilterEntry>> entries;


    public List<GroupFilterEntry> getEntriesWithAtLeastOneResult(NewWikiScreen screen) {

        return entries.get().stream().filter(x -> screen.group.getAll(1).stream().anyMatch(e -> x.isValid(e))).collect(Collectors.toList());
    }

    public GroupFilterType(BestiaryGroup forGroup, Words word, Supplier<List<GroupFilterEntry>> entries) {
        this.forGroup = forGroup;
        this.word = word;
        this.entries = entries;

        ALL.add(this);
    }
}
