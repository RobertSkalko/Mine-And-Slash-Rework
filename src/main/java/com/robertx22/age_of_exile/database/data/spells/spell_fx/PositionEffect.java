package com.robertx22.age_of_exile.database.data.spells.spell_fx;

import com.lowdragmc.photon.client.emitter.IParticleEmitter;
import com.lowdragmc.photon.client.fx.FXEffect;
import com.lowdragmc.photon.client.fx.FXHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.*;

import static com.robertx22.age_of_exile.database.data.spells.components.MapHolder.getSkillFXFromRawString;
@OnlyIn(Dist.CLIENT)
public class PositionEffect extends FXEffect {

    public static Map<Vec3, List<PositionEffect>> CACHE = new HashMap();

    UUID entityUUID = null;

    String skillFXName = "";

    Vec3 pos = new Vec3(0,0,0);

    Vec3 newPos;

    Boolean stop;

    public void setLifespan(Integer lifespan) {
        this.lifespan = lifespan;
    }

    private Integer lifespan;

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public PositionEffect(UUID entityUUID, String skillFXName, Vec3 pos) {
        super(FXHelper.getFX(getSkillFXFromRawString(skillFXName)), Minecraft.getInstance().level);
        this.entityUUID = entityUUID;
        this.skillFXName = skillFXName;
        this.pos = pos;
        this.newPos = pos;
        this.stop = false;
        this.lifespan = 0;
    }

    @Override
    public void start() {
        this.emitters.clear();
        this.emitters.addAll(fx.generateEmitters());
        if (this.emitters.isEmpty()) return;
        /*if (!allowMulti) {
            var effects = CACHE.computeIfAbsent(pos, p -> new ArrayList<>());
            var iter = effects.iterator();
            while (iter.hasNext()) {
                var effect = iter.next();
                boolean removed = false;
                if (effect.getEmitters().stream().noneMatch(e -> e.self().isAlive())) {
                    iter.remove();
                    removed = true;
                }
                if (effect.fx.equals(fx) && !removed) {
                    return;
                }
            }
            effects.add(this);
        }*/
        for (var emitter : emitters) {
            if (!emitter.isSubEmitter()) {
                emitter.reset();
                emitter.self().setDelay(delay);
                emitter.emmitToLevel(this, level, pos.x, pos.y, pos.z, xRotation, yRotation, zRotation);

            }
        }
    }

    @Override
    public boolean updateEmitter(IParticleEmitter emitter) {
        var finalNewPos = new Vector3f((float) (newPos.x + xOffset), (float) (newPos.y + yOffset), (float) (newPos.z + zOffset));
        this.lifespan++;
        if(!stop && this.lifespan <= 20 * 6){
            emitter.updatePos(finalNewPos);
            return false;
        }
        emitter.remove(forcedDeath);
        return forcedDeath;
    }

    public void setNewPos(Vec3 newPos) {
        this.newPos = newPos;
    }
}
