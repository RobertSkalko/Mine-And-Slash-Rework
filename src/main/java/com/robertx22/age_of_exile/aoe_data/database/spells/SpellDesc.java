package com.robertx22.age_of_exile.aoe_data.database.spells;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.age_of_exile.uncommon.utilityclasses.TooltipUtils;
import com.robertx22.library_of_exile.utils.CLOC;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class SpellDesc {

    public static String NEWLINE = "[LINE]";

    public static List<String> getTooltip(LivingEntity caster, Spell spell) {

        String tip = CLOC.translate(spell.locDesc());
        String copy = tip;

        int amount = StringUTIL.countMatches(tip, "calc:");

        int counted = 0;
        for (ValueCalculation calc : ExileDB.ValueCalculations()
                .getList()) {
            String id = "[calc:" + calc.id + "]";

            tip = tip.replace(id, CLOC.translate(calc.getShortTooltip(caster, spell)));

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
