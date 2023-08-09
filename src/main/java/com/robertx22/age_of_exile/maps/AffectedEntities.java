package com.robertx22.age_of_exile.maps;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public enum AffectedEntities {
    Mobs, Players, All;

    public static AffectedEntities of(LivingEntity en) {
        if (en instanceof Player) {
            return Players;
        } else {
            return Mobs;
        }
    }
}