package com.robertx22.mine_and_slash.aoe_data.database.spells;

import com.robertx22.mine_and_slash.database.data.spells.components.Spell;
import com.robertx22.mine_and_slash.database.data.value_calc.ValueCalculation;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.TooltipUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class SpellDesc {

    public static String NEWLINE = "[LINE]";

    public static int countMatches(String str, String sub) {
        if (sub.equals("")) {
            return 0;
        }
        if (str == null) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf(sub, idx)) != -1) {
            count++;
            idx += sub.length();
        }
        return count;
    }

    public static List<String> getTooltip(LivingEntity caster, Spell spell) {

        String tip = spell.locDesc().getString();
        String copy = tip;

        int amount = countMatches(tip, "calc:");

        int counted = 0;
        for (ValueCalculation calc : ExileDB.ValueCalculations()
                .getList()) {
            String id = "[calc:" + calc.id + "]";

            tip = tip.replace(id, calc.getShortTooltip(caster, spell).getString());
            
            if (!tip.equals(copy)) {
                counted++;
                copy = tip;

                if (counted >= amount) {
                    break;
                }
            }
        }

        List<String> list = TooltipUtils.cutIfTooLong(tip);

        list = list.stream().map(x -> ChatFormatting.GRAY + x).collect(Collectors.toList());

        return list;

    }
}
