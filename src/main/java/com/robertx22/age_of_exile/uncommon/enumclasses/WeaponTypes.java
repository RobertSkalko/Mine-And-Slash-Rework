package com.robertx22.age_of_exile.uncommon.enumclasses;

import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.uncommon.utilityclasses.StringUTIL;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import com.robertx22.library_of_exile.registry.IAutoGson;
import com.robertx22.library_of_exile.registry.JsonExileRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WeaponTypes implements JsonExileRegistry<WeaponTypes>, IAutoGson<WeaponTypes> {

    public static WeaponTypes SERIALIZER = new WeaponTypes();

    private static List<WeaponTypes> ALL = new ArrayList<>();


    public static WeaponTypes none = new WeaponTypes("none", PlayStyle.STR, WeaponRange.MELEE, false, DamageValidityData.meleeWeapon());
    public static WeaponTypes axe = new WeaponTypes("axe", PlayStyle.STR, WeaponRange.MELEE, false, DamageValidityData.meleeWeapon());
    public static WeaponTypes staff = new WeaponTypes("staff", PlayStyle.INT, WeaponRange.MELEE, false, DamageValidityData.meleeWeapon());
    public static WeaponTypes trident = new WeaponTypes("trident", PlayStyle.STR, WeaponRange.OPTIONALLY_RANGED, false, DamageValidityData.meleeWeapon());
    public static WeaponTypes sword = new WeaponTypes("sword", PlayStyle.STR, WeaponRange.MELEE, false, DamageValidityData.meleeWeapon()).setCanDualWield();
    public static WeaponTypes bow = new WeaponTypes("bow", PlayStyle.DEX, WeaponRange.RANGED, true, DamageValidityData.projectile());
    public static WeaponTypes crossbow = new WeaponTypes("crossbow", PlayStyle.DEX, WeaponRange.RANGED, true, DamageValidityData.projectile());

    static {
        init();
    }

    public WeaponTypes() {
    }

    static void init() {

    }

    public static void registerAll() {
        for (WeaponTypes wep : ALL) {
            wep.addToSerializables();
        }
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.WEAPON_TYPE;
    }

    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public Class<WeaponTypes> getClassForSerialization() {
        return WeaponTypes.class;
    }

    public enum SourceCheck {
        ANY() {
            @Override
            public boolean isValid(DamageSource source) {
                return true;
            }
        },
        RANGED_PROJECTILE() {
            @Override
            public boolean isValid(DamageSource source) {
                return source.is(DamageTypeTags.IS_PROJECTILE);
            }
        },
        DIRECT_ATTACK() {
            @Override
            public boolean isValid(DamageSource source) {
                return source.getDirectEntity() == source.getEntity();
            }
        };

        public abstract boolean isValid(DamageSource source);

    }

    public enum TagCheck {
        ANY() {
            @Override
            public boolean isValid(DamageSource source, DamageValidityData data) {
                return true;
            }
        },
        MATCH_ANY() {
            @Override
            public boolean isValid(DamageSource source, DamageValidityData data) {
                var dmgid = source.getEntity().level().registryAccess().registry(Registries.DAMAGE_TYPE).get().getKey(source.type());

                for (ResourceLocation id : data.valid_proj_dmg_id) {
                    if (id.equals(dmgid)) {
                        return true;
                    }
                }
                String id = source.getMsgId();
                for (String name : data.contains_in_dmg_id) {
                    if (id.contains(name)) {
                        return true;
                    }
                }
                return false;
            }
        };

        public abstract boolean isValid(DamageSource source, DamageValidityData data);

    }

    public static class DamageValidityData {

        private SourceCheck source_check = SourceCheck.ANY;
        private TagCheck tag_and_id_check = TagCheck.MATCH_ANY;

        private List<ResourceLocation> valid_proj_dmg_id = Arrays.asList(new ResourceLocation("testmodid", "name"));
        private List<String> contains_in_dmg_id = Arrays.asList("arrow", "bolt", "ammo", "bullet", "dart", "missile");

        public DamageValidityData(SourceCheck shortcheck, TagCheck tag_and_id_check, List<ResourceLocation> valid_proj_dmg_id, List<String> contains_in_dmg_id) {
            this.tag_and_id_check = tag_and_id_check;
            this.source_check = shortcheck;
            this.valid_proj_dmg_id = valid_proj_dmg_id;
            this.contains_in_dmg_id = contains_in_dmg_id;
        }

        public static DamageValidityData projectile() {
            return new DamageValidityData(
                    SourceCheck.RANGED_PROJECTILE,
                    TagCheck.MATCH_ANY,
                    Arrays.asList(),
                    Arrays.asList("arrow", "bolt", "ammo", "bullet", "dart", "missile")
            );
        }

        public static DamageValidityData meleeWeapon() {
            return new DamageValidityData(
                    SourceCheck.DIRECT_ATTACK,
                    TagCheck.ANY,
                    Arrays.asList(),
                    Arrays.asList()
            );
        }

        public DamageValidityData() {
        }

        public boolean isValid(DamageSource source) {

            if (!source_check.isValid(source)) {
                return false;
            }
            if (!tag_and_id_check.isValid(source, this)) {
                return false;
            }

            return true;
        }
    }

    WeaponTypes(String id, PlayStyle style, WeaponRange range, boolean isProjectile, DamageValidityData valid) {

        this.id = id;
        this.style = style;
        this.range = range;
        this.isProjectile = isProjectile;
        this.damage_validity_check = valid;

        ALL.add(this);
    }

    public boolean can_dual_wield = false;
    public PlayStyle style;
    WeaponRange range;
    public String id;
    public boolean isProjectile;

    public DamageValidityData damage_validity_check = new DamageValidityData();


    public WeaponTypes setCanDualWield() {
        this.can_dual_wield = true;
        return this;
    }

    public String locName() {
        return StringUTIL.capitalise(id);
    }

    public boolean isMelee() {
        return this.range == WeaponRange.MELEE;
    }

    public static List<WeaponTypes> getAll() {
        return ALL.stream().filter(x -> x != none).collect(Collectors.toList());

    }

    @Override
    public String GUID() {
        return this.id;
    }
}
