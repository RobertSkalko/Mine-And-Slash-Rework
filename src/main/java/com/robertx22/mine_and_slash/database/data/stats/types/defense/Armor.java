package com.robertx22.mine_and_slash.database.data.stats.types.defense;

import com.robertx22.mine_and_slash.database.data.stats.IUsableStat;
import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.database.data.stats.effects.defense.ArmorEffect;
import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.ChatFormatting;

public class Armor extends Stat implements IUsableStat {

    public static Armor getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Decreases physical damage taken by a percent";
    }

    public static String GUID = "armor";

    private Armor() {

        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.icon = UNICODE.STAR;
        this.format = ChatFormatting.BLUE.getName();

        this.statEffect = new ArmorEffect();

    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return Elements.Physical;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public float getMaxMulti() {
        return 0.9F;
    }

    @Override
    public float valueNeededToReachMaximumPercentAtLevelOne() {
        return 100;
    }

    @Override
    public String locNameForLangFile() {
        return "Armor";
    }

    private static class SingletonHolder {
        private static final Armor INSTANCE = new Armor();
    }
}
