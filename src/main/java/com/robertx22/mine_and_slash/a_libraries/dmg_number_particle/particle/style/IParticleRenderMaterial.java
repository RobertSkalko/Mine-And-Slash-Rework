package com.robertx22.mine_and_slash.a_libraries.dmg_number_particle.particle.style;

import com.robertx22.mine_and_slash.uncommon.enumclasses.Elements;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IParticleRenderMaterial<T> {

    T getMat();

    boolean isCrit();


    record singleElement(Pair<Elements, String> mat, boolean isCrit) implements IParticleRenderMaterial<Pair<Elements, String>>{
        @Override
        public Pair<Elements, String> getMat() {
            return mat;
        }

        @Override
        public boolean isCrit() {
            return isCrit;
        }
    }


    record multipleElements(List<Pair<Elements, String>> mat, boolean isCrit) implements IParticleRenderMaterial<List<Pair<Elements, String>>>{

        @Override
        public List<Pair<Elements, String>> getMat() {
            return mat;
        }

        @Override
        public boolean isCrit() {
            return isCrit;
        }
    }

    record simpleText(String number) implements IParticleRenderMaterial<String>{

        @Override
        public String getMat() {
            return number;
        }

        @Override
        public boolean isCrit() {
            return false;
        }
    }
}
