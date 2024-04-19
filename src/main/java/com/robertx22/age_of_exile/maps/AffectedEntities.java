package com.robertx22.age_of_exile.maps;

import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public enum AffectedEntities {
    Mobs, Players, None, All;

    public static AffectedEntities of(LivingEntity en) {
        if (Load.Unit(en).isSummon()) {
            return None;
        }
        if (en instanceof Player) {
            return Players;
        } else {
            return Mobs;
        }
    }
}