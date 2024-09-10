package com.robertx22.mine_and_slash.vanilla_mc.items.misc;

import com.robertx22.mine_and_slash.a_libraries.jei.iHideJei;

public class ProjectileItem extends AutoItem implements iHideJei {
    String id;

    public ProjectileItem(String id) {
        super(new Properties());
        this.id = id;
    }

    @Override
    public String locNameForLangFile() {
        return "Projectile";
    }

    @Override
    public String GUID() {
        return "projectile/" + id;
    }
}
