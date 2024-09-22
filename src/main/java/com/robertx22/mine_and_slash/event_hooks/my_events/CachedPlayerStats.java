package com.robertx22.mine_and_slash.event_hooks.my_events;

import com.robertx22.mine_and_slash.capability.DirtySync;
import com.robertx22.mine_and_slash.saveclasses.skill_gem.SkillGemData;
import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.CommonStatUtils;
import com.robertx22.mine_and_slash.uncommon.stat_calculation.PlayerStatUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class CachedPlayerStats {

    public Player p;
    public List<StatContext> allStatsWithoutSuppGems = new ArrayList<>();

    public List<StatContext> statContexts = new ArrayList<>();

    private StatContext statCompat;

    public DirtySync STAT_COMPAT = new DirtySync("stat_compat", x -> {
        recalcStatCompat();
        Load.Unit(p).equipmentCache.STAT_CALC.setDirty();
    });
    public StatContext omenStats;

    public StatContext getStatCompatStats() {
        if (statCompat == null) {
            recalcStatCompat();
        }
        return statCompat;
    }

    private void recalcStatCompat() {
        this.statCompat = CommonStatUtils.addStatCompat(p);
    }

    // I guess these could be all stats that don't change often, fine to set these to recalc everything
    public DirtySync ALLOCATED = new DirtySync("misc_player", x -> {
        recalcAllocated();
        Load.Unit(p).equipmentCache.STAT_CALC.setDirty();
    }) {
        @Override
        public void setDirty() {
            super.setDirty();
        }

    };

    public void setAllDirty() {
        ALLOCATED.setDirty();
        STAT_COMPAT.setDirty();
    }

    public void tick() {

        ALLOCATED.onTickTrySync(p);
        STAT_COMPAT.onTickTrySync(p);

    }

    public CachedPlayerStats(Player p) {
        this.p = p;
    }

    private void recalcAllocated() {

        if (false) {
            p.sendSystemMessage(Component.literal("Re calcing player stuff"));
        }


        statContexts = new ArrayList<>();

        var playerData = Load.player(p);

        playerData.aurasOn = new ArrayList<>();
        for (SkillGemData aura : playerData.getSkillGemInventory().getAurasGems()) {
            playerData.aurasOn.add(aura.id);
        }


        statContexts.addAll(PlayerStatUtils.addToolStats(p)); // todo this needs fixing

        statContexts.add(PlayerStatUtils.addBonusExpPerCharacters(p));

        statContexts.addAll(playerData.buff.getStatAndContext(p));

        statContexts.addAll(playerData.getSkillGemInventory().getAuraStats(p));
        statContexts.addAll(playerData.getJewels().getStatAndContext(p));
        statContexts.addAll(playerData.statPoints.getStatAndContext(p));

        statContexts.addAll(PlayerStatUtils.addNewbieElementalResists(Load.Unit(p)));
        statContexts.addAll(playerData.talents.getStatAndContext(p));
        statContexts.addAll(playerData.ascClass.getStatAndContext(p));
        statContexts.addAll(playerData.prophecy.getStatAndContext(p));


    }
}
