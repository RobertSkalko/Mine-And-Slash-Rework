package com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser;

import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.ItemModification;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_req.ItemRequirement;

public class CustomSerializers {

    public static CustomSerializer<ItemModification> ITEM_MODIFICATION = new CustomSerializer<>();
    public static CustomSerializer<ItemRequirement> ITEM_REQ = new CustomSerializer<>();

}
