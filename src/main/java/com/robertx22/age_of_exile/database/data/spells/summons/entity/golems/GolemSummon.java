package com.robertx22.age_of_exile.database.data.spells.summons.entity.golems;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.spells.summons.entity.SummonEntity;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;


// todo add full ele affix, and sometiemes do aoe attack
public abstract class GolemSummon extends SummonEntity {


    public GolemSummon(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);

    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (super.doHurtTarget(pEntity)) {

            if (RandomUtils.roll(10)) {
                if (!this.level().isClientSide) {
                    if (getOwner() instanceof Player en) {
                        var spell = ExileDB.Spells().get(this.aoeSpell());
                        // todo this doesnt affect summon damage.. hm
                        spell.cast(new SpellCastContext(en, 0, spell));
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

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
