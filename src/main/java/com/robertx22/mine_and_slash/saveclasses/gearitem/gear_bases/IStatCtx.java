package com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases;

import com.robertx22.mine_and_slash.saveclasses.unit.stat_ctx.StatContext;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public interface IStatCtx {

    List<StatContext> getStatAndContext(LivingEntity en);

}
