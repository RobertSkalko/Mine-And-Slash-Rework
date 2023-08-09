package com.robertx22.age_of_exile.database.data.stats.types.totem;

import com.robertx22.age_of_exile.database.data.spells.SpellTag;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;

public class ProjectileTotem extends Stat {


    public ProjectileTotem() {
        this.is_perc = false;
    }

    public static boolean shouldUse(Unit en, Spell spell) {
        return en.getCalculatedStat(ProjectileTotem.getInstance()).getValue() > 0 && spell.config.tags.contains(SpellTag.projectile);

    }

    public static ProjectileTotem getInstance() {
        return ProjectileTotem.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ProjectileTotem INSTANCE = new ProjectileTotem();
    }

    @Override
    public Elements getElement() {
        return Elements.All;
    }

    @Override
    public String locDescForLangFile() {
        return "Instead of casting the spell yourself, you summon a totem that uses the spell";
    }

    @Override
    public String locNameForLangFile() {
        return "Projectile Totem";
    }

    @Override
    public String GUID() {
        return "projectile_totem";
    }
}
