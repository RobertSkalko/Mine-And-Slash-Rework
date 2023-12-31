package com.robertx22.age_of_exile.database.data.spells.spell_fx;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FXInfoHolder {

    public static Map<UUID, PositionEffect> clientPlayerEntityFXHolder = new HashMap<>();
    private static Map<UUID, Boolean> playerFXEnableMap = new HashMap<>();

    public static void writeFXConfigValue(Player player){
        UUID UUID = player.getUUID();
        Boolean ifFXEnable = ClientConfigs.getConfig().ENABLE_PHOTON_FX.get();
        playerFXEnableMap.put(UUID, ifFXEnable);
    }

    public static void writeFXConfigValueFromPacket(Player player, Boolean b){
        UUID UUID = player.getUUID();
        playerFXEnableMap.put(UUID, b);
    }

    public static Boolean readFXConfigValue(Player player){
        UUID UUID = player.getUUID();
        return playerFXEnableMap.getOrDefault(UUID, false);
    }

    public static void init(){}

}
