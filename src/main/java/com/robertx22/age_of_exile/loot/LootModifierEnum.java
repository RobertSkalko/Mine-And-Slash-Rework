package com.robertx22.age_of_exile.loot;

import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.uncommon.interfaces.IAutoLocName;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

import java.text.DecimalFormat;
import java.util.Locale;

public enum LootModifierEnum implements IAutoLocName {
    MAP_CHEST("Map Chest"),
    CHEST("Chest"),
    BREAK_SPAWNER("Break Spawner"),
    LEVEL_DISTANCE_PENALTY("Level Distance Penalty"),
    MOB_HEALTH("Mob Health"),
    MOB_DATAPACK("Mob Datapack"),
    MOB_BONUS_LOOT_STAT("Mob Bonus Loot Stat"),
    MOB_RARITY("Mob Rarity"),
    LOW_LEVEL_BOOST("Low Level"),
    FAVOR("Favor Rank"),
    PLAYER_LOOT_QUANTITY("Player Loot Quantity"),
    DIMENSION_LOOT("Dimension"),
    ADVENTURE_MAP("Adventure Map"),
    ANTI_MOB_FARM_MOD("Anti Mob Farm Mod"),
    ;
    public String locname;

    LootModifierEnum(String locname) {
        this.locname = locname;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.LootModifier;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".loot_modifier." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    private static final DecimalFormat format = new DecimalFormat("0.00");

    public MutableComponent getFullName(float multi) {
        return locName().append(" ").append(": x" + format.format(multi)).withStyle(getFormat(multi));
    }

    public ChatFormatting getFormat(float multi) {
        if (multi < 1) {
            return ChatFormatting.RED;
        }
        if (multi > 1) {
            return ChatFormatting.GREEN;
        }
        return ChatFormatting.YELLOW;
    }

    @Override
    public String GUID() {
        return name().toLowerCase(Locale.ROOT);
    }
}
