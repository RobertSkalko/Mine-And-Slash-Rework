package com.robertx22.age_of_exile.database.data.profession.all.misc_drops;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ProfessionDropItems {
    String folder;
    
    public List<RegObj<Item>> ALL = new ArrayList<>();

    public ProfessionDropItems(String folder) {
        this.folder = folder;
    }

    protected RegObj<Item> drop(String id, Supplier<Item> object) {
        var o = Def.item(folder + "/" + id, object);
        ALL.add(o);
        return o;
    }
}
