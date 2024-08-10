package com.robertx22.mine_and_slash.database.data.spell_school;

import com.robertx22.mine_and_slash.database.data.perks.Perk;
import com.robertx22.mine_and_slash.database.registry.ExileRegistryTypes;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.saveclasses.PointData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SpellSchool implements JsonExileRegistry<SpellSchool>, IAutoGson<SpellSchool>, IAutoLocName {
    public static SpellSchool SERIALIZER = new SpellSchool();

    public String id = "";
    public transient String locname = "";

    public static int MAX_Y_ROWS = 7;
    public static int MAX_X_ROWS = 10;

    public HashMap<String, PointData> perks = new HashMap<>();

    public List<Integer> lvl_reqs = Arrays.asList(1, 5, 10, 15, 20, 25, 30);
 
    public int getLevelNeededToAllocate(PointData point) {

        int req = lvl_reqs.get(point.y);

        return req;
    }

    public boolean isLevelEnoughFor(LivingEntity en, Perk perk) {
        return Load.Unit(en).getLevel() >= getLevelNeededToAllocate(perks.get(perk.GUID()));
    }


    public ResourceLocation getIconLoc() {
        return SlashRef.guiId("asc_classes/class/" + id);
    }

    public ResourceLocation getBackgroundLoc() {
        return SlashRef.guiId("asc_classes/background/" + id);
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.SPELL_SCHOOL;
    }

    @Override
    public Class<SpellSchool> getClassForSerialization() {
        return SpellSchool.class;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Spells;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".asc_class." + id;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }
}
