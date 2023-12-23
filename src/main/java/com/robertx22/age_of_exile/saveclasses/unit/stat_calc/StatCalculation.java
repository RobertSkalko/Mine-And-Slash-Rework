package com.robertx22.age_of_exile.saveclasses.unit.stat_calc;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.capability.player.helper.GemInventoryHelper;
import com.robertx22.age_of_exile.database.data.stats.datapacks.stats.AttributeStat;
import com.robertx22.age_of_exile.database.data.stats.types.LearnSpellStat;
import com.robertx22.age_of_exile.event_hooks.damage_hooks.util.AttackInformation;
import com.robertx22.age_of_exile.event_hooks.my_events.CollectGearEvent;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.unit.GearData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.GearStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.interfaces.data_items.Cached;
import com.robertx22.age_of_exile.uncommon.stat_calculation.CommonStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.MobStatUtils;
import com.robertx22.age_of_exile.uncommon.stat_calculation.PlayerStatUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.EntityUnitPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class StatCalculation {

    // todo trying to rewrite calc code..
    public static Unit calc(LivingEntity entity, int skillGem, AttackInformation dmgData) {

        Unit unit = new Unit();

        EntityData data = Load.Unit(entity);

        unit.clearStats();

        Unit.DirtyCheck old = unit.getDirtyCheck();


        List<GearData> gears = new ArrayList<>();
        new CollectGearEvent.CollectedGearStacks(entity, gears, dmgData);

        var statContexts = collectStatsWithCtx(entity, data, gears, skillGem);

        statContexts.applyCtxModifierStats();
        statContexts.applyToInCalc(unit);


        InCalc incalc = new InCalc(unit);
        incalc.addVanillaHpToStats(entity);
        incalc.modify(unit);

        unit.getStats().calculate();

        Unit.DirtyCheck aftercalc = unit.getDirtyCheck();

        Cached.VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC.forEach(x -> {
            AttributeInstance in = entity.getAttribute(x.left);
            if (in.getModifier(x.right) != null) {
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


        if (old.isDirty(aftercalc)) {
            if (Unit.shouldSendUpdatePackets((LivingEntity) entity)) {
                Packets.sendToTracking(unit.getUpdatePacketFor(entity, data), entity);
            }
        }

        if (entity instanceof Player p) {
            Load.player(p).spellCastingData.resetSpells();
            unit.getStats().stats.values()
                    .forEach(x -> {
                        if (x.GetStat() instanceof LearnSpellStat learn) {
                            Load.player(p).spellCastingData.addSpell(new SpellCastingData.InsertedSpell(learn.spell.GUID(), (int) x.getValue()));
                        }
                    });

            Load.player(p).getSkillGemInventory().removeAurasIfCantWear(p);

            Packets.sendToClient((Player) entity, new EntityUnitPacket(entity));
        }
        return unit;

    }


    private static CtxStats collectStatsWithCtx(LivingEntity entity, EntityData data, List<GearData> gears, int skillGem) {
        List<StatContext> statContexts = new ArrayList<>();


        statContexts.addAll(CommonStatUtils.addExactCustomStats(entity));
        statContexts.add(data.getStatusEffectsData().getStats(entity));
        statContexts.addAll(addGearStats(gears));
        CommonStatUtils.addMapAffixStats(entity);

        if (data.isSummon()) {
            statContexts.addAll(MobStatUtils.addSummonStats((TamableAnimal) entity));
        }

        if (entity instanceof Player p) {
            var playerData = Load.player(p);

            statContexts.addAll(PlayerStatUtils.addToolStats(p));

            statContexts.addAll(playerData.buff.getStatAndContext(p));

            statContexts.addAll(playerData.getSkillGemInventory().getAuraStats(entity));
            statContexts.addAll(playerData.getJewels().getStatAndContext(entity));
            statContexts.addAll(playerData.statPoints.getStatAndContext(entity));

            statContexts.addAll(PlayerStatUtils.AddPlayerBaseStats(entity));
            statContexts.addAll(PlayerStatUtils.addNewbieElementalResists(data));
            statContexts.addAll(playerData.talents.getStatAndContext(entity));
            statContexts.addAll(playerData.ascClass.getStatAndContext(entity));

            if (skillGem > -1 && skillGem <= GemInventoryHelper.MAX_SKILL_GEMS) {
                var gem = playerData.getSkillGemInventory().getHotbarGem(skillGem);
                for (SkillGemData d : gem.getSupportDatas()) {
                    if (d.getSupport() != null) {
                        statContexts.add(new MiscStatCtx(d.getSupport().GetAllStats(data, d)));
                    }
                }
                var spell = gem.getSpell();
                if (spell != null) {
                    var stats = spell.getStats(p);
                    statContexts.add(new MiscStatCtx(stats));
                }
            }
        } else {
            statContexts.addAll(MobStatUtils.getMobBaseStats(data, entity));
            statContexts.addAll(MobStatUtils.getAffixStats(entity));
            statContexts.addAll(MobStatUtils.getWorldMultiplierStats(entity));
            MobStatUtils.addMobBaseElementalBonusDamages(entity, data);
            MobStatUtils.addMapTierStats(entity);
            statContexts.addAll(MobStatUtils.getMobConfigStats(entity, data));
        }

        return new CtxStats(statContexts);
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
            ctxs.add(new GearStatCtx(x.gear, stats));

        });

        return ctxs;

    }
}
