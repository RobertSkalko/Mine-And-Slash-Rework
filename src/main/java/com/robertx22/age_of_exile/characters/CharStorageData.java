package com.robertx22.age_of_exile.characters;

import com.robertx22.age_of_exile.capability.entity.CooldownsData;
import com.robertx22.age_of_exile.config.forge.ServerContainer;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.WorldUtils;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CharStorageData {

    public int current = 0;

    public HashMap<Integer, CharacterData> map = new HashMap<>();

    public void load(int num, Player p) {

        var data = map.get(num);

        if (data != null) {
            map.put(current, CharacterData.from(p));
            data.load(p);
            this.current = num;
        }

    }

    public boolean canChangeCharactersRightNow(Player p) {
        if (Load.Unit(p).getCooldowns().isOnCooldown(CooldownsData.IN_COMBAT)) {
            return false;
        }
        if (WorldUtils.isMapWorldClass(p.level())) {
            return false;
        }
        return true;
    }

    public Optional<CharacterData> getByName(String name) {
        return map.values().stream().filter(x -> x.name.equals(name)).findAny();
    }

    public int getSlotOf(CharacterData data) {
        return map.entrySet().stream().filter(x -> x.getValue() == data).map(x -> x.getKey()).findFirst().orElse(-1);
    }

    public void tryAddNewCharacter(Player p, String name) {

        if (name.isEmpty()) {
            return;
        }
        if (getAllCharacters().stream().anyMatch(x -> x.name.equals(name))) {
            return;
        }
        if (name.length() > 20) {
            return;
        }
        int amount = getAllCharacters().size();

        if (amount < ServerContainer.get().MAX_CHARACTERS.get()) {

            var data = new CharacterData();

            if (map.get(current) == null) {
                data = CharacterData.from(p); // if it's first time making character, let it just set name instead of deleting everything
            }

            data.name = name;

            for (int i = 0; i < ServerContainer.get().MAX_CHARACTERS.get(); i++) {
                if (map.get(i) == null) {
                    map.put(i, data);
                    break;
                }
            }
        }

    }


    public List<CharacterData> getAllCharacters() {
        return map.values().stream().filter(x -> x != null).collect(Collectors.toList());
    }


}

