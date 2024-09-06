package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import com.google.common.collect.ImmutableMap;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.IParticleRenderStrategy;
import com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style.Original;
import com.robertx22.mine_and_slash.aoe_data.database.unique_gears.uniques.armor.PantsUniques;
import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.NumberUtils;
import com.robertx22.mine_and_slash.vanilla_mc.packets.DmgNumPacket;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpellResultParticleSpawner {

    public static <T> void spawn(Consumer<T> spawnWay, T mat){
        spawnWay.accept(mat);
    }

    public enum Modes{
        ORIGINAL(Original::new, info -> {
            var mat = (ElementDamageParticle.DamageInformation)info;
            SpellResultParticleSpawner.spawn(m -> {
                ImmutableMap<Elements, Float> dmgMap = m.getDmgMap();
                for (Map.Entry<Elements, Float> entry : dmgMap.entrySet()) {
                    if (entry.getValue()
                            .intValue() > 0) {

                        entry.getKey().format + NumberUtils.formatDamageNumber(this, entry.getValue()
                                .intValue());

                        DmgNumPacket packet = new DmgNumPacket(target, text, data.isCrit(), entry.getKey().format);
                        Packets.sendToClient(player, packet);
                    }
                }
            }, mat);
        }),
        WYNN;

        public final Supplier<IParticleRenderStrategy> strategySupplier;
        public final Consumer<?> spawn;

        Modes(Supplier<IParticleRenderStrategy> strategySupplier, Consumer<?> spawn) {
            this.strategySupplier = strategySupplier;
            this.spawn = spawn;
        }
    }
}
