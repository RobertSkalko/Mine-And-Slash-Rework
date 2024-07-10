package com.robertx22.age_of_exile.gui.wiki;

import com.robertx22.age_of_exile.database.data.StatMod;
import com.robertx22.age_of_exile.database.data.aura.AuraGem;
import com.robertx22.age_of_exile.database.data.exile_effects.ExileEffect;
import com.robertx22.age_of_exile.database.data.gear_slots.GearSlot;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.runes.Rune;
import com.robertx22.age_of_exile.database.data.runewords.RuneWord;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.support_gem.SupportGem;
import com.robertx22.age_of_exile.database.data.unique_items.UniqueGear;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.gui.wiki.all.CurrencyBestiary;
import com.robertx22.age_of_exile.gui.wiki.all.DBItemEntry;
import com.robertx22.age_of_exile.gui.wiki.all.ProfExpBestiary;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.GearBlueprint;
import com.robertx22.age_of_exile.loot.blueprints.SkillGemBlueprint;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Gui;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class BestiaryGroup<T> {

    // add effect todo
    private static List<BestiaryGroup> all = new ArrayList<>();

    static BestiaryGroup RUNE = new DBItemEntry<Rune>(ExileRegistryTypes.RUNE, Words.Rune, "rune", x -> new BestiaryEntry.Item(x.getItem().getDefaultInstance()));
    static BestiaryGroup GEM = new DBItemEntry<Gem>(ExileRegistryTypes.GEM, Words.Gem, "gem", x -> new BestiaryEntry.Item(x.getItem().getDefaultInstance()));
    public static BestiaryGroup EFFECT = new DBItemEntry<ExileEffect>(ExileRegistryTypes.EXILE_EFFECT, Words.STATUS_EFFECT, "effect",
            x -> {
                var tooltip = new ArrayList<Component>();
                tooltip.addAll(x.GetTooltipString(new TooltipInfo()));
                return new BestiaryEntry.Tooltip(x.getEffectDisplayItem().getDefaultInstance(), CLOC.translate(x.locName()), tooltip);
            });

    public static BestiaryGroup RUNEWORD = new DBItemEntry<RuneWord>(ExileRegistryTypes.RUNEWORDS, Words.Runeword, "runeword",
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
                return new BestiaryEntry.Tooltip(Items.BOOK.getDefaultInstance(), CLOC.translate(x.locName()), tooltip);
            });

    static BestiaryGroup UNIQUE_GEAR = new DBItemEntry<UniqueGear>(ExileRegistryTypes.UNIQUE_GEAR, Words.Unique_Gear, "unique_gear", x -> {
        var b = new GearBlueprint(LootInfo.ofLevel(1));
        b.uniquePart.set(x);
        b.rarity.set(ExileDB.GearRarities().get(IRarity.UNIQUE_ID));
        b.gearItemSlot.set(x.getBaseGear());
        return new BestiaryEntry.NamedItem(b.createStack(), CLOC.translate(x.locName()));
    });


    static BestiaryGroup SPELL = new DBItemEntry<Spell>(ExileRegistryTypes.SPELL, Words.SPELL, "spell", x -> {
        var b = new SkillGemBlueprint(LootInfo.ofLevel(1), SkillGemData.SkillGemType.SKILL);
        b.setType(x.GUID());
        return new BestiaryEntry.NamedItem(b.createStack(), CLOC.translate(x.locName()));
    });

    static BestiaryGroup SUPP = new DBItemEntry<SupportGem>(ExileRegistryTypes.SUPPORT_GEM, Words.SUPPGEM, "supp_gem", x -> {
        var b = new SkillGemBlueprint(LootInfo.ofLevel(1), SkillGemData.SkillGemType.SUPPORT);
        b.setType(x.GUID());
        return new BestiaryEntry.NamedItem(b.createStack(), CLOC.translate(x.locName()));
    });
    static BestiaryGroup AURA = new DBItemEntry<AuraGem>(ExileRegistryTypes.AURA, Words.AURA, "aura", x -> {
        var b = new SkillGemBlueprint(LootInfo.ofLevel(1), SkillGemData.SkillGemType.AURA);
        b.setType(x.GUID());
        return new BestiaryEntry.NamedItem(b.createStack(), CLOC.translate(x.locName()));
    });

    public static List<BestiaryGroup> getAll() {

        if (all.isEmpty()) {

            all.add(new CurrencyBestiary());
            all.add(GEM);
            all.add(RUNE);
            all.add(UNIQUE_GEAR);
            all.add(RUNEWORD);
            all.add(AURA);
            all.add(SUPP);
            all.add(EFFECT);
            all.add(SPELL);
            all.add(new ProfExpBestiary());
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


}
