package com.robertx22.mine_and_slash.aoe_data.database.unique_gears;

import com.robertx22.mine_and_slash.database.data.StatMod;
import com.robertx22.mine_and_slash.database.data.stats.types.offense.WeaponDamage;

import java.util.Arrays;
import java.util.List;

// todo replace all uniques with this to standardize
public enum UniquePower {
    NEWBIE_FRIENDLY() {
        @Override
        public List<StatMod> getWeaponDamageMods() {
            return Arrays.asList(
                    WeaponDamage.getInstance().mod(50, 75).percent(),
                    WeaponDamage.getInstance().mod(1, 1)
            );
        }
    },
    MEDIUM() {
        @Override
        public List<StatMod> getWeaponDamageMods() {
            return Arrays.asList(
                    WeaponDamage.getInstance().mod(40, 100).percent(),
                    WeaponDamage.getInstance().mod(1, 2)
            );
        }
    },
    ENDGAME() {
        @Override
        public List<StatMod> getWeaponDamageMods() {
            return Arrays.asList(
                    WeaponDamage.getInstance().mod(75, 150).percent(),
                    WeaponDamage.getInstance().mod(1, 3)
            );
        }
    },
    RNG_ENDGAME() {
        @Override
        public List<StatMod> getWeaponDamageMods() {
            return Arrays.asList(
                    WeaponDamage.getInstance().mod(40, 200).percent(),
                    WeaponDamage.getInstance().mod(1, 3)
            );
        }
    };

    public abstract List<StatMod> getWeaponDamageMods();
}
