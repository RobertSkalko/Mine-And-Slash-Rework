package com.robertx22.mine_and_slash.database.data.gems;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import com.robertx22.library_of_exile.vanilla_util.main.VanillaUTIL;
import com.robertx22.mine_and_slash.database.data.BaseGem;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.uncommon.interfaces.data_items.IRarity;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.List;

public class Gem extends BaseGem implements IAutoGson<Gem>, JsonExileRegistry<Gem>, IRarity {

    public static Gem SERIALIZER = new Gem();

    public String gem_type = "";
    public String text_format = "";

    public String rar = IRarity.COMMON_ID;

    public int perc_upgrade_chance = 0;

    public ChatFormatting getFormat() {
        try {
            return ChatFormatting.valueOf(text_format);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return ChatFormatting.GRAY;
    }

    public Gem getHigherTierGem() {
        List<Gem> list = ExileDB.Gems()
                .getFiltered(x -> x.tier == tier + 1 && x.gem_type.equals(gem_type));

        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public boolean hasHigherTierGem() {
        return getHigherTierGem() != null && getHigherTierGem().getItem() != null;
    }

    public Item getItem() {
        return VanillaUTIL.REGISTRY.items().get(new ResourceLocation(item_id));
    }

    @Override
    public Class<Gem> getClassForSerialization() {
        return Gem.class;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.GEM;
    }

    @Override
    public String getRarityId() {
        return rar;
    }
}
