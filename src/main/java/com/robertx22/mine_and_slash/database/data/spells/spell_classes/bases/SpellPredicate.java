package com.robertx22.mine_and_slash.database.data.spells.spell_classes.bases;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Predicate;

public class SpellPredicate {
    public Predicate<LivingEntity> predicate;
    public MutableComponent text;

    public SpellPredicate(Predicate<LivingEntity> predicate, MutableComponent text) {
        this.predicate = predicate;
        this.text = text;

    }

}


