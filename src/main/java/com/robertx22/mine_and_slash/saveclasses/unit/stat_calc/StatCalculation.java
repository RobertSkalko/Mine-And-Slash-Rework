package com.robertx22.mine_and_slash.saveclasses.unit.stat_calc;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.capability.player.PlayerData;
import com.robertx22.mine_and_slash.capability.player.helper.GemInventoryHelper;
import com.robertx22.mine_and_slash.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.mine_and_slash.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.mine_and_slash.event_hooks.my_events.CollectGearEvent;
import com.robertx22.mine_and_slash.gui.screens.stat_gui.StatCalcInfoData;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.saveclasses.unit.GearData;
import com.robertx22.mine_and_slash.saveclasses.unit.InCalcStatContainer;
import com.robertx22.mine_and_slash.saveclasses.unit.StatData;
import com.robertx22.mine_and_slash.saveclasses.unit.Unit;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.GearStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.SimpleStatCtx;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.AddToAfterCalcEnd;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.Cached;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.CommonStatUtils;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.MobStatUtils;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.PlayerStatUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatCalculation {

    public static List<StatContext> getStatsWithoutSuppGems(LivingEntity entity, EntityData data, AttackInformation dmgData) {
        List<StatContext> statContexts = new ArrayList<>();

        List<GearData> gears = new ArrayList<>();

        new CollectGearEvent.CollectedGearStacks(entity, gears, dmgData);

        statContexts = collectStatsWithCtx(entity, data, gears);

        statContexts.removeIf(x -> x.stats.isEmpty());

        if (entity instanceof Player p) {
            var pd = Load.player(p);
            pd.ctxs = new StatCalcInfoData();
            for (StatContext ctx : statContexts) {
                if (ctx instanceof SimpleStatCtx s) {
                    pd.ctxs.list.add(s);
                }
            }
        }

        return statContexts;
    }

    // the List<StatContext> is modified so i cant reuse it until the code is redone and fixed
    // todo trying to rewrite calc code..
    public static void calc(Unit unit, List<StatContext> statsWithoutSuppGems, LivingEntity entity, int skillGem, AttackInformation dmgData) {

        if (entity.level().isClientSide) {
            return;
        }

        EntityData data = Load.Unit(entity);
        unit.clearStats();

        List<StatContext> gemstats = new ArrayList<>();

        if (entity instanceof Player p) {
            PlayerData playerData = Load.player(p);
            gemstats.addAll(collectGemStats(p, data, playerData, skillGem));
        }

        InCalcStatContainer statCalc = new InCalcStatContainer();

        var allstats = new ArrayList<StatContext>();

        allstats.addAll(gemstats);
        allstats.addAll(statsWithoutSuppGems);

        var sc = new CtxStats(allstats);

        sc.applyCtxModifierStats();
        sc.applyToInCalc(statCalc);


        if (entity instanceof Player p) {


            //        Load.player(p).ctxStats = new SavedStatCtxList(sc);
        }

        InCalc incalc = new InCalc(unit);
        incalc.addVanillaHpToStats(entity, statCalc);
        incalc.modify(data, statCalc);

        unit.setStats(statCalc.calculate());

        // apply stats that add to others

        var stats = new HashMap<String, StatData>(unit.getStats().stats);

        var copiedStats = unit.getStats().clone();

        for (Map.Entry<String, StatData> en : stats.entrySet()) {
            if (en.getValue().GetStat() instanceof AddToAfterCalcEnd aff) {
                aff.affectStats(copiedStats, unit.getStats(), en.getValue());
            }
        }

        for (StatData stat : unit.getStats().stats.values()) {
            stat.softCapStat(unit);
        }

        Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC.forEach(x -> {
            AttributeInstance in = entity.getAttribute(x.left);
            if (in != null && in.getModifier(x.right) != null) {
                in.removeModifier(x.right);
            }
        });

        unit.getStats().stats.values()
                .forEach(x -> {
                    if (x.GetStat() instanceof AttributeStat) {
                        AttributeStat stat = (AttributeStat) x.GetStat();
                        stat.addToEntity(entity, x);
                    }
                });


    }


    private static List<StatContext> collectGemStats(Player p, EntityData data, PlayerData playerData, int skillGem) {
        List<StatContext> statContexts = new ArrayList<>();

        if (skillGem > -1 && skillGem <= GemInventoryHelper.MAX_SKILL_GEMS) {
            var gem = playerData.getSkillGemInventory().getHotbarGem(skillGem);
            for (SkillGemData d : gem.getSupportDatas()) {
                if (d.getSupport() != null) {
                    statContexts.add(new SimpleStatCtx(StatContext.StatCtxType.SUPPORT_GEM, d.getSupport().GetAllStats(data, d)));
                }
            }
            var spell = gem.getSpell();
            if (spell != null) {
                var stats = spell.getStats(p);
                if (!stats.isEmpty()) {
                    statContexts.add(new SimpleStatCtx(StatContext.StatCtxType.INNATE_SPELL, stats));
                }
            }
        }
        return statContexts;
    }


    private static List<StatContext> collectStatsWithCtx(LivingEntity entity, EntityData data, List<GearData> gears) {
        List<StatContext> statContexts = new ArrayList<>();


        statContexts.addAll(CommonStatUtils.addExactCustomStats(entity));
        statContexts.add(data.getStatusEffectsData().getStats(entity));
        statContexts.addAll(addGearStats(gears));
        statContexts.addAll(CommonStatUtils.addMapAffixStats(entity));
        statContexts.addAll(CommonStatUtils.addBaseStats(entity));


        if (entity instanceof Player p) {
            var playerData = Load.player(p);

            playerData.aurasOn = new ArrayList<>();
            for (SkillGemData aura : playerData.getSkillGemInventory().getAurasGems()) {
                playerData.aurasOn.add(aura.id);
            }

            statContexts.add(CommonStatUtils.addStatCompat(p));
            statContexts.addAll(PlayerStatUtils.addToolStats(p));

            statContexts.add(PlayerStatUtils.addBonusExpPerCharacters(p));

            statContexts.addAll(playerData.buff.getStatAndContext(p));

            statContexts.addAll(playerData.getSkillGemInventory().getAuraStats(entity));
            statContexts.addAll(playerData.getJewels().getStatAndContext(entity));
            statContexts.addAll(playerData.statPoints.getStatAndContext(entity));

            statContexts.addAll(PlayerStatUtils.addNewbieElementalResists(data));
            statContexts.addAll(playerData.talents.getStatAndContext(entity));
            statContexts.addAll(playerData.ascClass.getStatAndContext(entity));
            statContexts.addAll(playerData.prophecy.getStatAndContext(entity));

        } else {
            statContexts.addAll(MobStatUtils.getMobBaseStats(data, entity));


            if (data.isSummon()) {
                statContexts.addAll(MobStatUtils.addSummonStats((TamableAnimal) entity));
            } else {
                statContexts.addAll(MobStatUtils.getAffixStats(entity));
                statContexts.addAll(MobStatUtils.getWorldMultiplierStats(entity));
                //  statContexts.addAll(MobStatUtils.addMobBaseElementalBonusDamages(entity, data));
                statContexts.addAll(MobStatUtils.addMapTierStats(entity));
                statContexts.addAll(MobStatUtils.getMobConfigStats(entity, data));
            }
        }

        return statContexts;
    }

    static List<StatContext> addGearStats(List<GearData> gears) {

        List<StatContext> ctxs = new ArrayList<>();

        gears.forEach(x -> {
            List<ExactStatData> stats = x.gear.GetAllStats();

            if (x.percentStatUtilization != 100) {
                // multi stats like for offfhand weapons
                float multi = x.percentStatUtilization / 100F;
                stats.forEach(s -> s.multiplyBy(multi));
            }
            ctxs.add(GearStatCtx.of(x.gear, stats));

            var ench = x.gear.getEnchantCompatStats(x.stack);
            if (ench != null) {
                ctxs.add(ench);
            }
        });


        return ctxs;

    }
}
