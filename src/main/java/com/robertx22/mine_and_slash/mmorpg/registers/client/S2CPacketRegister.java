package com.robertx22.mine_and_slash.mmorpg.registers.client;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.database.data.profession.StationPacket;
import com.robertx22.mine_and_slash.database.data.profession.StationSyncData;
import com.robertx22.mine_and_slash.database.data.spells.components.packets.ParticlesPacket;
import com.robertx22.mine_and_slash.gui.screens.stat_gui.SendStatCalcInfoToClientPacket;
import com.robertx22.mine_and_slash.gui.screens.stat_gui.StatCalcInfoData;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.vanilla_mc.packets.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellClientEntityCastingSpell;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellClientEntityIsCastingSpellPacket;

public class S2CPacketRegister {

    public static void register() {
        int i = 1000;

        Packets.registerServerToClient(MMORPG.NETWORK, new DmgNumPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new EfficientMobUnitPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new EntityUnitPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new NoManaPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new OpenGuiPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new TellClientEntityCastingSpell(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new SyncAreaLevelPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new TellClientEntityIsCastingSpellPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new TotemAnimationPacket(), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new ParticlesPacket(new ParticlesPacket.Data()), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new StationPacket(new StationSyncData()), i++);
        Packets.registerServerToClient(MMORPG.NETWORK, new SendStatCalcInfoToClientPacket(new StatCalcInfoData()), i++);


    }
}
