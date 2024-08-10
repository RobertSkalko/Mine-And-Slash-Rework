package com.robertx22.mine_and_slash.database.data.spells.summons.entity.golems;

import com.robertx22.mine_and_slash.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;


// todo add full ele affix, and sometiemes do aoe attack
public abstract class GolemSummon extends SummonEntity {


    public GolemSummon(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    /*
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (super.doHurtTarget(pEntity)) {
            if (!this.level().isClientSide) {
                if (getOwner() instanceof Player en) {
                    int chance = (int) (5 + Load.Unit(en).getUnit().getCalculatedStat(GolemSpellChance.getInstance()).getValue());
                    if (RandomUtils.roll(chance)) {
                        var spell = ExileDB.Spells().get(this.aoeSpell());
                        // todo this doesnt affect summon damage.. hm
                        var c = (new SpellCastContext(en, 0, spell));
                        spell.getAttached().onCast(SpellCtx.onCast(en, c.calcData).setSourceEntity(this));
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

     */

    public abstract String affix();

    public abstract String aoeSpell();

    public abstract Elements ele();

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {


        Load.Unit(this).getAffixData().affixes.add(affix());

        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public boolean isBaby() {
        return true;
    }
}
