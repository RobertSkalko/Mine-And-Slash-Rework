package com.robertx22.mine_and_slash.uncommon.interfaces;

import com.robertx22.library_of_exile.registry.IGUID;

import java.util.List;

public interface IGenerated<T extends IGUID> {

    public List<T> generateAllPossibleStatVariations();
}
