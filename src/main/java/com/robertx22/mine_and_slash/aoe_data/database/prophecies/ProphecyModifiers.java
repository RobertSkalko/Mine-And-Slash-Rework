package com.robertx22.mine_and_slash.aoe_data.database.prophecies;

import com.robertx22.library_of_exile.registry.DataGenKey;
import com.robertx22.library_of_exile.registry.ExileRegistryInit;
import com.robertx22.mine_and_slash.aoe_data.database.base_gear_types.BaseGearTypes;
import com.robertx22.mine_and_slash.database.data.gear_types.bases.BaseGearType;
import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyModifier;
import com.robertx22.mine_and_slash.database.data.prophecy.ProphecyModifierType;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;

public class ProphecyModifiers implements ExileRegistryInit {
    @Override
    public void registerAll() {

        of(IRarity.UNCOMMON, 100, ProphecyModifierType.GEAR_RARITY, IRarity.UNCOMMON, 1.1F);
        of(IRarity.RARE_ID, 500, ProphecyModifierType.GEAR_RARITY, IRarity.RARE_ID, 1.5F);
        of(IRarity.EPIC_ID, 1000, ProphecyModifierType.GEAR_RARITY, IRarity.EPIC_ID, 2F).levelReq(25).tierReq(20);
        of(IRarity.LEGENDARY_ID, 500, ProphecyModifierType.GEAR_RARITY, IRarity.LEGENDARY_ID, 3).levelReq(40).tierReq(25);
        of(IRarity.MYTHIC_ID, 100, ProphecyModifierType.GEAR_RARITY, IRarity.MYTHIC_ID, 4).levelReq(50).tierReq(50);

        of(IRarity.UNIQUE_ID, 50, ProphecyModifierType.GEAR_RARITY, IRarity.UNIQUE_ID, 4).levelReq(25).tierReq(25);
        ///   of(IRarity.UNIQUE_ID, 10, ProphecyModifierType.GEAR_RARITY, IRarity.UNIQUE_ID, 3).levelReq(25).tierReq(25).forceLeagueUniques(LeagueMechanics.PROPHECY);


        BaseGearTypes.init();

        for (DataGenKey<BaseGearType> e : BaseGearTypes.ALL) {
            of(e.GUID(), 1000, ProphecyModifierType.GEAR_TYPE, e.GUID(), 2);
        }

    }

    public static class Builder {
        ProphecyModifier m = new ProphecyModifier();

        public Builder(ProphecyModifier m) {
            this.m = m;
        }

        public Builder tierReq(int tier) {
            m.tier_req = tier;
            return this;
        }


        public Builder levelReq(int lvl) {
            m.lvl_req = lvl;
            return this;
        }
    }


    public static Builder of(String id, int weight, ProphecyModifierType modifier, String data, float costmulti) {

        ProphecyModifier m = new ProphecyModifier();
        m.id = id;
        m.weight = weight;
        m.modifier_type = modifier;
        m.data = data;
        m.cost_multi = costmulti;

        m.addToSerializables();

        return new Builder(m);
    }
}
