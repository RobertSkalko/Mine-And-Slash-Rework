package com.robertx22.mine_and_slash.uncommon.interfaces.data_items;

import net.minecraft.world.entity.ai.attributes.Attribute;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cached {

    public static List<ImmutablePair<Attribute, UUID>> VANILLA_STAT_UIDS_TO_CLEAR_EVERY_STAT_CALC = new ArrayList<>();

    public static void reset() {


    }

}
