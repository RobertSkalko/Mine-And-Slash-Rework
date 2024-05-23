package com.robertx22.age_of_exile.uncommon.stat_calculation;

import com.robertx22.age_of_exile.aoe_data.database.stats.OffenseStats;
import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.database.data.EntityConfig;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.rarities.MobRarity;
import com.robertx22.age_of_exile.database.data.stats.types.SummonStat;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.BonusPhysicalAsElemental;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.HealthRegen;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.MapItemData;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.enumclasses.ModType;
import com.robertx22.library_of_exile.utils.EntityUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class MobStatUtils {

    public static List<StatContext> addSummonStats(TamableAnimal en) {
        List<ExactStatData> stats = new ArrayList<>();

        LivingEntity caster = en.getOwner();

        if (caster instanceof Player player) {
            var spell = Load.Unit(en).summonedPetData.getSourceSpell();
            var data = Load.player(player).getSpellUnitStats(player, spell);

            for (Map.Entry<String, StatData> e : data.getStats().stats.entrySet()) {
                if (e.getValue().GetStat() instanceof SummonStat sstat) {
                    stats.add(sstat.giveToSummon(e.getValue()));
                }
            }

        }
        return Arrays.asList(new MiscStatCtx(stats));

    }

    public static List<StatContext> addMobBaseElementalBonusDamages(LivingEntity en, EntityData data) {
        List<ExactStatData> stats = new ArrayList<>();

        for (Elements ele : Elements.getAllSingle()) {
            if (ele != Elements.Physical) {
                stats.add(ExactStatData.noScaling(GameBalanceConfig.get().PERCENT_OF_PHYS_AS_BONUS_OF_EACH_ELEMENT_DMG_FOR_MOBS, ModType.FLAT, new BonusPhysicalAsElemental(ele).GUID()));
            }
        }
        return Arrays.asList(new MiscStatCtx(stats));

    }


    // todo test this
    public static List<StatContext> addMapTierStats(LivingEntity en) {
        List<StatContext> list = new ArrayList<>();

        MapData map = Load.mapAt(en.level(), en.blockPosition());
        if (map != null) {
            MapItemData data = map.map;


            list.add(new MiscStatCtx(data.getTierStats()));
        }

        return list;

    }

    public static List<StatContext> getAffixStats(LivingEntity en) {
        List<StatContext> list = new ArrayList<>();
        Load.Unit(en)
                .getAffixData()
                .getAffixes()
                .forEach(x -> list.addAll(x.getStatAndContext(en)));
        return list;

    }

    public static List<StatContext> getWorldMultiplierStats(LivingEntity en) {

        List<StatContext> list = new ArrayList<>();

        List<ExactStatData> stats = new ArrayList<>();

        float val = (-1F + ExileDB.getDimensionConfig(en.level()).mob_strength_multi) * 100F;

        stats.add(ExactStatData.noScaling(val, ModType.MORE, Health.getInstance()
                .GUID()));
        stats.add(ExactStatData.noScaling(val, ModType.MORE, OffenseStats.TOTAL_DAMAGE.get()
                .GUID()));

        list.add(new MiscStatCtx(stats));

        return list;

    }

    public static List<StatContext> getMobConfigStats(LivingEntity entity, EntityData unitdata) {
        List<StatContext> list = new ArrayList<>();
        List<ExactStatData> stats = new ArrayList<>();

        EntityConfig config = ExileDB.getEntityConfig(entity, unitdata);

        config.stats.stats.forEach(x -> stats.add(x.toExactStat(unitdata.getLevel())));

        float hp = (float) ((-1F + config.hp_multi) * 100F);
        float dmg = (float) ((-1F + config.dmg_multi) * 100F);
        float stat = (float) ((-1F + config.stat_multi) * 100F);

        stats.add(ExactStatData.noScaling(hp, ModType.MORE, Health.getInstance()
                .GUID()));
        stats.add(ExactStatData.noScaling(dmg, ModType.FLAT, OffenseStats.TOTAL_DAMAGE.get()
                .GUID()));

        stats.add(ExactStatData.noScaling(stat, ModType.MORE, DodgeRating.getInstance()
                .GUID()));
        stats.add(ExactStatData.noScaling(stat, ModType.MORE, Armor.getInstance()
                .GUID()));


        for (Elements ele : Elements.getAllSingle()) {
            if (ele != Elements.Physical) {
                stats.add(ExactStatData.noScaling(stat, ModType.MORE, new ElementalResist(ele).GUID()));
            }
        }
        stats.add(ExactStatData.noScaling(stat, ModType.MORE, new ElementalResist(Elements.Elemental)
                .GUID()));
        stats.add(ExactStatData.noScaling(stat, ModType.MORE, Health.getInstance()
                .GUID()));

        list.add(new MiscStatCtx(stats));

        return list;
    }

    public static List<StatContext> getMobBaseStats(EntityData unitdata, LivingEntity en) {
        List<StatContext> list = new ArrayList<>();
        List<ExactStatData> stats = new ArrayList<>();

        MobRarity rar = unitdata.getMobRarity();

        int lvl = unitdata.getLevel();

        float vanillahp = EntityUtils.getVanillaMaxHealth(en);

        if (rar.forcesCustomHp()) {
            vanillahp = rar.force_custom_hp;
        }

        float hpToAdd = vanillahp * rar.ExtraHealthMulti();

        hpToAdd += (ServerContainer.get().EXTRA_MOB_STATS_PER_LEVEL.get() * lvl) * hpToAdd;

        if (hpToAdd < 0) {
            hpToAdd = 0;
        }


        stats.add(ExactStatData.scaleTo(hpToAdd, ModType.FLAT, Health.getInstance().GUID(), lvl));

        stats.add(ExactStatData.scaleTo(0.5F, ModType.FLAT, HealthRegen.getInstance().GUID(), lvl));

        stats.add(ExactStatData.scaleTo(1, ModType.FLAT, OffenseStats.ACCURACY.get().GUID(), lvl));
        stats.add(ExactStatData.scaleTo(10 * rar.StatMultiplier(), ModType.FLAT, Armor.getInstance().GUID(), lvl));

        for (Elements ele : Elements.getAllSingle()) {
            if (ele != Elements.Physical) {
                stats.add(ExactStatData.noScaling(10 * rar.StatMultiplier(), ModType.FLAT, new ElementalResist(ele).GUID()));
            }
        }

        stats.add(ExactStatData.scaleTo(5 * rar.DamageMultiplier(), ModType.FLAT, OffenseStats.CRIT_CHANCE.get().GUID(), lvl));

  
        list.add(new MiscStatCtx(stats));

        return list;

    }

}
