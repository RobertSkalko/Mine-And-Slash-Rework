package com.robertx22.mine_and_slash.database.data.stats;

import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.interfaces.IAutoLocName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public enum StatGuiGroup implements IAutoLocName {

    NONE("none", "None"),
    RESIST("resist", "Elemental Resists"),
    MAX_RESIST("max_resist", "Maximum Resists"),
    ELE_DAMAGE("ele_damage", "Elemental Damage"),
    ELE_PENE("ele_pene", "Elemental Penetration"),
    AILMENT_CHANCE("ailment_chance", "Ailment Chance"),
    AILMENT_DURATION("ailment_duration", "Ailment Duration"),
    AILMENT_DAMAGE("ailment_damage", "Ailment Damage"),
    AILMENT_PROC_CHANCE("ailment_proc-_chance", "Ailment Proc Chance"),
    ELE_SPELL_DAMAGE("ele_spell_damage", "Elemental Spell Damage");

    public String id;
    public String name;

    StatGuiGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public List<Stat> getSameGroupStats() {
        List<Stat> list = new ArrayList<>();
        int i = 0;
        for (Stat s : ExileDB.Stats().getFilterWrapped(x -> x.gui_group.isValid() && x.gui_group == this).list) {
            if (s.getElement().shouldShowInStatPanel()) {
                list.add(s);
                i++;
            }
        }
        list.sort(Comparator.comparingInt(x -> x.getElement().GUID().hashCode()));
        return list;
    }

    public boolean isValid() {
        return this != NONE;
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.StatGroup;
    }

    @Override
    public String locNameLangFileGUID() {
        return SlashRef.MODID + ".stat_group." + id;
    }

    @Override
    public String locNameForLangFile() {
        return name;
    }

    @Override
    public String GUID() {
        return id;
    }
}
