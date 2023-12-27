package com.robertx22.age_of_exile.database.data.spells.spell_fx;

import com.lowdragmc.photon.client.emitter.IParticleEmitter;
import com.lowdragmc.photon.client.fx.EntityEffect;
import com.lowdragmc.photon.client.fx.FX;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class PlayerEffect extends EntityEffect {

    private Integer lifeSpan;

    private Integer maxLife;
    public PlayerEffect(FX fx, Level level, Player player) {
        super(fx, level, player);
        this.setYOffset(1.2D);
        this.lifeSpan = 0;
        this.maxLife = 60;
    }
    public PlayerEffect(FX fx, Level level, Player player, Double heightChange) {
        this(fx, level, player);
        this.setYOffset(1.2D + heightChange);
    }
    public PlayerEffect(FX fx, Level level, Player player, Integer maxLife) {
        this(fx, level, player);
        this.maxLife = maxLife;
    }
    public PlayerEffect(FX fx, Level level, Player player, Double heightChange, Integer maxLife) {
        this(fx, level, player);
        this.setYOffset(1.2D + heightChange);
        this.maxLife = maxLife;
    }



    @Override
    public boolean updateEmitter(IParticleEmitter emitter) {
        this.lifeSpan++;
        if(lifeSpan > maxLife){
            this.setForcedDeath(true);
        }
        return super.updateEmitter(emitter);
    }
}
