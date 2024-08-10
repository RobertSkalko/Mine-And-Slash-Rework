package com.robertx22.mine_and_slash.aoe_data.database.perks;

import com.robertx22.mine_and_slash.aoe_data.database.stats.base.AutoHashClass;
import com.robertx22.mine_and_slash.database.OptScaleExactStat;
import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.library_of_exile.registry.IGUID;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public class AscendancyKey extends AutoHashClass implements IGUID {

    private String id;
    public String name;

    public AscendancyKey(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getStartPerkId() {
        return id + "_class";
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String GUID() {
        return id;
    }

    public void of(Consumer<AscendancyKey> c) {
        c.accept(this);
    }

    public void createPerk(int num, String name, OptScaleExactStat... stats) {
        var id = getStartPerkId() + "_" + num;
        Perk perk = PerkBuilder.bigStat(id, name, stats);
        perk.type = Perk.PerkType.MAJOR;
        perk.icon = new ResourceLocation(SlashRef.MODID, "textures/gui/talent_icons/asc/" + this.id + "/" + num + ".png").toString();
    }
}
