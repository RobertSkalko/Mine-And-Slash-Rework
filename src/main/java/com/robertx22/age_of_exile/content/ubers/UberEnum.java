package com.robertx22.age_of_exile.content.ubers;

import com.robertx22.age_of_exile.database.data.league.LeaguePiecesList;
import com.robertx22.age_of_exile.mmorpg.registers.common.items.SlashItems;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Consumer;

public enum UberEnum {
    WITHER("uber1", "Realm of Atrophy and Decay", "Withering Fragment", "A living mortal, in MY Realm?"),
    UNUSED2("uber2", "unamed", "unamed", "unamed"),
    UNUSED3("uber3", "unamed", "unamed", "unamed"),
    UNUSED4("uber4", "unamed", "unamed", "unamed"),
    UNUSED5("uber5", "unamed", "unamed", "unamed"),
    UNUSED6("uber6", "unamed", "unamed", "unamed"),
    UNUSED7("uber7", "unamed", "unamed", "unamed"),
    UNUSED8("uber8", "unamed", "unamed", "unamed"),
    UNUSED9("uber9", "unamed", "unamed", "unamed");

    public String id;
    public String arenaName;
    public String desc;
    public String fragmentName;

    UberEnum(String id, String arenaName, String fragmentName, String desc) {
        this.id = id;
        this.desc = desc;
        this.arenaName = arenaName;
        this.fragmentName = fragmentName;
    }

    public UberFragmentItem getFragment(int tier) {
        return SlashItems.UBER_FRAGS.get(this).get(tier).get();
    }

    public UberBossMapItem getMap(int tier) {
        return SlashItems.UBER_MAPS.get(this).get(tier).get();
    }

    public static UberEnum of(String id) {

        for (UberEnum v : UberEnum.values()) {
            if (v.id.equals(id)) {
                return v;
            }
        }
        return null;
    }

    public void createBoss(EntityType bossEntity, LeaguePiecesList struc, Consumer<UberBossArena> c) {
        UberBossArena boss = new UberBossArena();

        boss.id = id;
        boss.name = this.arenaName;
        boss.desc = this.desc;

        boss.possible_bosses.add(ForgeRegistries.ENTITY_TYPES.getKey(bossEntity).toString());

        boss.structure = struc;

        boss.addToSerializables();

        c.accept(boss);
    }
}
