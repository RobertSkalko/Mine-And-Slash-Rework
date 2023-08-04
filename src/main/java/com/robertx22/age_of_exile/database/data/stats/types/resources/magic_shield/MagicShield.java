package com.robertx22.age_of_exile.database.data.stats.types.resources.magic_shield;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatScaling;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class MagicShield extends Stat {
    public static String GUID = "magic_shield";

    private MagicShield() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.order = 0;
        this.icon = "\u2764";
        this.format = TextFormatting.LIGHT_PURPLE.getName();

    }


    public static MagicShield getInstance() {
        return MagicShield.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Like health, but works differently and might need different ways to restore.";
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public String locNameForLangFile() {
        return "Magic Shield";
    }

    public static float modifyEntityDamage(DamageEvent effect, float dmg) {


        float current = effect.targetData.getResources()
                .getMagicShield();

        if (current > 0) {


            float dmgReduced = MathHelper.clamp(dmg, 0, current);

            if (dmgReduced > 0) {

                if (effect.target instanceof PlayerEntity) {
                    DeathStatsData.record((PlayerEntity) effect.target, effect.getElement(), dmgReduced);
                }

                effect.targetData.getResources()
                        .spend(effect.target, ResourceType.magic_shield, dmgReduced);

                return dmg - dmgReduced;

            }

        }
        return dmg;
    }

    private static class SingletonHolder {
        private static final MagicShield INSTANCE = new MagicShield();
    }
}
