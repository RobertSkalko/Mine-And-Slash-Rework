package com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod;

import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.mine_and_slash.database.data.MinMax;
import com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser.CustomSerializer;
import com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser.CustomSerializers;
import com.robertx22.mine_and_slash.database.data.currency.reworked.custom_ser.GsonCustomSer;
import com.robertx22.mine_and_slash.database.data.currency.reworked.item_mod.gear.AddQualityItemMod;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.itemstack.ExileStack;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocDesc;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;

// test if this loads correctly
public abstract class ItemModification implements JsonExileRegistry<ItemModification>, IAutoLocDesc, GsonCustomSer<ItemModification> {

    public static ItemModification SERIALIZER = new AddQualityItemMod("", new AddQualityItemMod.Data(new MinMax(1, 1)));

    public String serializer = "";

    public String id = "";

    public transient String locname = "";

    public int weight = 1000;


    public ItemModification(String serializer, String id) {
        this.serializer = serializer;
        this.id = id;
    }

    public enum OutcomeType {
        GOOD(ChatFormatting.GREEN),
        BAD(ChatFormatting.RED),
        NEUTRAL(ChatFormatting.GRAY);

        public ChatFormatting color;

        OutcomeType(ChatFormatting color) {
            this.color = color;
        }
    }

    public abstract OutcomeType getOutcomeType();

    public abstract void applyINTERNAL(ExileStack stack);


    public void applyMod(ExileStack stack) {
        applyINTERNAL(stack);
    }

    public abstract MutableComponent getDescWithParams();


    @Override
    public void addToSerializables() {
        getSerMap().register(this);
        Database.getRegistry(this.getExileRegistryType()).addSerializable(this);
    }

    @Override
    public CustomSerializer getSerMap() {
        return CustomSerializers.ITEM_MODIFICATION;
    }

    @Override
    public String getSer() {
        return serializer;
    }


    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.ITEM_MOD;
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
        return SlashRef.MODID + ".item_mods." + GUID();
    }

    @Override
    public int Weight() {
        return weight;
    }
}
