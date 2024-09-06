package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle;

import java.util.function.Consumer;

public interface ISpawnTarget<T> {
    Consumer<T> getSpawnStrategy();
}
