package com.robertx22.age_of_exile.database.data.stats.types.defense;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.effects.base.BaseDamageEffect;
import com.robertx22.age_of_exile.database.data.stats.priority.StatPriority;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.effectdatas.DamageEvent;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.interfaces.EffectSides;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.ShieldItem;

public class BlockChance extends Stat {

    public static String GUID = "block_chance";

    public static BlockChance getInstance() {
        return BlockChance.SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Chance to passively block damage, requires a shield in the offhand, but don't have to activate it.";
    }

    private BlockChance() {
        this.min = 0;
        this.max = 75;
        this.group = StatGroup.MAIN;

        this.statEffect = new BlockChance.Effect();

        this.format = ChatFormatting.BLUE.getName();

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
        return true;
    }

    @Override
    public String locNameForLangFile() {
        return "Block Chance";
    }

    // todo might need to make a spearate block effect instead of just using the dodge mech
    private static class Effect extends BaseDamageEffect {

        @Override
        public StatPriority GetPriority() {
            return StatPriority.Damage.HIT_PREVENTION;
        }

        @Override
        public EffectSides Side() {
            return EffectSides.Target;
        }

        @Override
        public DamageEvent activate(DamageEvent effect, StatData data, Stat stat) {
            float chance = data.getValue();
            if (RandomUtils.roll(chance)) {
                effect.data.getNumber(EventData.NUMBER).number = 0;
                effect.data.setBoolean(EventData.IS_DODGED, true);
            }
            return effect;
        }

        @Override
        public boolean canActivate(DamageEvent effect, StatData data, Stat stat) {
            return effect.getAttackType().isHit() && effect.target.getOffhandItem().getItem() instanceof ShieldItem;
        }
    }

    private static class SingletonHolder {
        private static final BlockChance INSTANCE = new BlockChance();
    }
}
