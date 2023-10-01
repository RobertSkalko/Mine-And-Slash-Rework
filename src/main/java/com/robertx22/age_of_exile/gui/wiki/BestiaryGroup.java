package com.robertx22.age_of_exile.gui.wiki;

import com.robertx22.age_of_exile.database.data.aura.AuraGem;
import com.robertx22.age_of_exile.database.data.gems.Gem;
import com.robertx22.age_of_exile.database.data.runes.Rune;
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
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.IRarity;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class BestiaryGroup<T> {

    private static List<BestiaryGroup> all = new ArrayList<>();

    static BestiaryGroup RUNE = new DBItemEntry<Rune>(ExileRegistryTypes.RUNE, Words.Rune, "rune", x -> new BestiaryEntry.Item(x.getItem().getDefaultInstance()));
    static BestiaryGroup GEM = new DBItemEntry<Gem>(ExileRegistryTypes.GEM, Words.Gem, "gem", x -> new BestiaryEntry.Item(x.getItem().getDefaultInstance()));

    static BestiaryGroup UNIQUE_GEAR = new DBItemEntry<UniqueGear>(ExileRegistryTypes.UNIQUE_GEAR, Words.Unique_Gear, "unique_gear", x -> {
        var b = new GearBlueprint(LootInfo.ofLevel(1));
        b.uniquePart.set(x);
        b.rarity.set(ExileDB.GearRarities().get(IRarity.UNIQUE_ID));
        b.gearItemSlot.set(x.getBaseGear());
        return new BestiaryEntry.NamedItem(b.createStack(), CLOC.translate(x.locName()));
    });

    static BestiaryGroup SPELL = new DBItemEntry<Spell>(ExileRegistryTypes.SPELL, Words.SkillGem, "skill_gem", x -> {
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
            all.add(SPELL);
            all.add(AURA);
            all.add(SUPP);
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
