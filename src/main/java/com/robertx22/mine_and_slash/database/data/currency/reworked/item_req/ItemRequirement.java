package com.robertx22.mine_and_slash.database.data.currency.reworked.item_req;

import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser.CustomSerializer;
import com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser.CustomSerializers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser.GsonCustomSer;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.gear.LevelNotMaxReq;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocDesc;
import net.minecraft.network.chat.MutableComponent;

public abstract class ItemRequirement implements JsonExileRegistry<ItemRequirement>, IAutoLocDesc, GsonCustomSer<ItemRequirement> {

    public static ItemRequirement SERIALIZER = new LevelNotMaxReq("");

    public String serializer = "";

    public String id = "";

    public transient String locname = "";

    public int weight = 1000;


    public ItemRequirement(String serializer, String id) {
        this.serializer = serializer;
        this.id = id;
    }


    public abstract MutableComponent getDescWithParams();


    @Override
    public void addToSerializables() {
        getSerMap().register(this);
        Database.getRegistry(this.getExileRegistryType()).addSerializable(this);
    }

    @Override
    public CustomSerializer getSerMap() {
        return CustomSerializers.ITEM_REQ;
    }

    @Override
    public String getSer() {
        return serializer;
    }


    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.ITEM_REQ;
    }


    @Override
    public String GUID() {
        return id;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Currency_Items;
    }

    @Override
    public String locDescLangFileGUID() {
        return SlashRef.MODID + ".item_req." + GUID();
    }

    @Override
    public int Weight() {
        return weight;
    }

    public abstract boolean isValid(ExileStack obj);


}
