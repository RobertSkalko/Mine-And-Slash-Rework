package com.robertx22.age_of_exile.vanilla_mc.new_commands.parts;

import com.robertx22.age_of_exile.capability.player.data.PlayerProfessionsData;
import com.robertx22.age_of_exile.capability.player.data.RestedExpData;
import com.robertx22.age_of_exile.capability.player.data.StatPointsData;
import com.robertx22.age_of_exile.characters.CharStorageData;
import com.robertx22.age_of_exile.saveclasses.perks.TalentsData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellSchoolsData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import net.minecraft.world.entity.player.Player;

public enum ResetPlayerData {
    LEVEL() {
        @Override
        public void reset(Player p) {
            Load.Unit(p).setLevel(1);
        }
    },
    SPELL_COOLDOWNS() {
        @Override
        public void reset(Player p) {
            Load.Unit(p).getCooldowns().onTicksPass(555555);
            for (int i = 0; i < 10; i++) {
                Load.player(p).spellCastingData.charges.onTicks(p, 500000);
            }
        }
    },
    SPELLS() {
        @Override
        public void reset(Player p) {
            Load.player(p).ascClass = new SpellSchoolsData();
        }
    },
    PROFESSIONS() {
        @Override
        public void reset(Player p) {
            Load.player(p).professions = new PlayerProfessionsData();
        }
    },
    RESTED_EXP() {
        @Override
        public void reset(Player p) {
            Load.player(p).rested_xp = new RestedExpData();
        }
    },
    CHARACTERS() {
        @Override
        public void reset(Player p) {
            Load.player(p).characters = new CharStorageData();
        }
    },
    BONUS_TALENTS() {
        @Override
        public void reset(Player p) {
            Load.player(p).bonusTalents = 0;
        }
    },
    STATS() {
        @Override
        public void reset(Player p) {
            Load.player(p).statPoints = new StatPointsData();
        }
    },
    TALENTS() {
        @Override
        public void reset(Player p) {
            Load.player(p).talents = new TalentsData();
        }
    };

    public abstract void reset(Player p);
}
