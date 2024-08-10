package com.robertx22.mine_and_slash.database.data.stats.types.resources.magic_shield;

import com.robertx22.mine_and_slash.database.data.stats.Stat;
import com.robertx22.mine_and_slash.database.data.stats.StatScaling;
import com.robertx22.mine_and_slash.saveclasses.DeathStatsData;
import com.robertx22.mine_and_slash.saveclasses.unit.ResourceType;
import com.robertx22.mine_and_slash.uncommon.effectdatas.DamageEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.utils.SoundUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class MagicShield extends Stat {
    public static String GUID = "magic_shield";

    private MagicShield() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.order = 0;
        this.icon = "\u2764";
        this.format = ChatFormatting.LIGHT_PURPLE.getName();

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


        float current = effect.targetData.getResources().getMagicShield();

        if (current > 0) {


            float dmgReduced = Mth.clamp(dmg, 0, current);

            if (dmgReduced > 0) {

                SoundUtils.playSound(effect.target, SoundEvents.GENERIC_HURT, 0.5F, 1);
                SoundUtils.playSound(effect.target, SoundEvents.GUARDIAN_HURT, 1, 1);

                if (effect.target instanceof Player) {
                    DeathStatsData.record((Player) effect.target, effect.getElement(), dmgReduced);
                }

                effect.targetData.getResources().spend(effect.target, ResourceType.magic_shield, dmgReduced);


                float finald = dmg - dmgReduced;

                if (finald <= 0) {
                    effect.absorbedCompletely = true;
                }
                return finald;

            }

        }
        return dmg;
    }

    private static class SingletonHolder {
        private static final MagicShield INSTANCE = new MagicShield();
    }
}
