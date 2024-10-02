package com.robertx22.mine_and_slash.characters;

import com.robertx22.mine_and_slash.capability.entity.CooldownsData;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.WorldUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;
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

    public CharacterData getCurrent() {
        return this.map.getOrDefault(current, new CharacterData());
    }

    public boolean canChangeCharactersRightNow(Player p) {
        if (Load.Unit(p).getCooldowns().isOnCooldown(CooldownsData.IN_COMBAT)) {
            p.sendSystemMessage(Chats.CANT_CHANGE_CHAR_IN_COMBAT.locName().withStyle(ChatFormatting.RED));
            return false;
        }
        if (WorldUtils.isMapWorldClass(p.level())) {
            p.sendSystemMessage(Chats.CANT_CHANGE_CHAR_IN_MAP.locName().withStyle(ChatFormatting.RED));
            return false;
        }
        return true;
    }


    public int tryAddNewCharacter(Player p, String name) {

        if (!nameIsValid(p, name)) {
            p.sendSystemMessage(Chats.CREATE_ERROR_NAME.locName().withStyle(ChatFormatting.RED));
            return -1;
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
                    return i;
                }
            }
        } else {
            p.sendSystemMessage(Chats.CREATE_ERROR_CHAR_LIMIT.locName().withStyle(ChatFormatting.RED));
        }

        return -1;
    }

    public boolean nameIsValid(Player p, String name) {

        if (name.isEmpty()) {
            p.sendSystemMessage(Chats.NAME_EMPTY.locName().withStyle(ChatFormatting.RED));
            return false;
        }
        if (getAllCharacters().stream().anyMatch(x -> x.name.equals(name))) {
            p.sendSystemMessage(Chats.NAME_SAME.locName().withStyle(ChatFormatting.RED));
            return false;
        }
        if (name.length() > 20) {
            p.sendSystemMessage(Chats.NAME_TOO_LONG.locName().withStyle(ChatFormatting.RED));
            return false;
        }
        return true;
    }


    public List<CharacterData> getAllCharacters() {
        return map.values().stream().filter(x -> x != null).collect(Collectors.toList());
    }


}

