package com.robertx22.mine_and_slash.loot;

import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

import java.util.Locale;

public enum LootModifierEnum implements IAutoLocName {
    PROFESSION_BONUS_STAT("Profession Bonus Stat"),
    LOW_LEVEL_RECIPE_PENALTY("Low-LVL Recipe penalty"),


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


    public MutableComponent getFullName(float multi) {
        return locName().append(" ").append(": x" + MMORPG.DECIMAL_FORMAT.format(multi)).withStyle(getFormat(multi));
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
