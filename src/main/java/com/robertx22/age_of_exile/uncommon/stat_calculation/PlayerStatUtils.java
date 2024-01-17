package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.aoe_data.database.base_stats.BaseStatsAdder;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.base_stats.BaseStatsConfig;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.misc.BonusExp;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlayerStatUtils {
    private static List<Integer> all;

    public static List<StatContext> addToolStats(Player p) {
        ItemStack stack = p.getMainHandItem();
        if (StackSaving.TOOL.has(stack)) {
            return Arrays.asList(new MiscStatCtx(StackSaving.TOOL.loadFrom(stack).GetAllStats()));
        } else {
            return Arrays.asList();
        }
    }

    public static StatContext addBonusExpPerCharacters(Player p) {

        var data = Load.Unit(p);
        var pd = Load.player(p);
        int lvl = data.getLevel();


        List<Integer> all = pd.characters.getAllCharacters().stream().filter(x -> !x.name.equals(pd.characters.getCurrent().name)).map(x -> x.lvl).collect(Collectors.toList());


        int higher = (int) all.stream().filter(x -> x > lvl).count();

        if (higher > 0) {
            return new MiscStatCtx(Arrays.asList(ExactStatData.noScaling(
                    ServerContainer.get().BONUS_EXP_PERCENT_PER_HIGHER_LVL_CHARACTERS.get() * higher, ModType.FLAT, BonusExp.getInstance().GUID())));
        }
        return new MiscStatCtx(Arrays.asList());
    }

    public static List<StatContext> addNewbieElementalResists(EntityData data) {
        List<ExactStatData> stats = new ArrayList<>();

        int value = 50;

        if (data.getLevel() > 24) {
            value = 25;
        }
        if (data.getLevel() > 49) {
            value = 0;
        }
        if (data.getLevel() > 74) {
            value = -25;
        }
        for (Elements ele : Elements.getAllSingle()) {
            if (ele != Elements.Physical) {
                stats.add(ExactStatData.noScaling(value, ModType.FLAT, new ElementalResist(ele).GUID()));
            }
        }
        return Arrays.asList(new MiscStatCtx(stats));

    }

    public static List<StatContext> AddPlayerBaseStats(LivingEntity en) {

        try {
            BaseStatsConfig stats = ExileDB.BaseStats()
                    .get(BaseStatsAdder.PLAYER);
            Objects.requireNonNull(stats);
            return stats.getStatAndContext(en);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Arrays.asList();

    }

}
