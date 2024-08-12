package com.robertx22.mine_and_slash.mmorpg.registers.common;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.a_libraries.jei.LockRecipePacket;
import com.robertx22.mine_and_slash.capability.player.data.Backpacks;
import com.robertx22.mine_and_slash.characters.CreateCharPacket;
import com.robertx22.mine_and_slash.characters.LoadCharPacket;
import com.robertx22.mine_and_slash.gui.screens.stat_gui.RequestStatCalcInfoPacket;
import com.robertx22.mine_and_slash.mmorpg.MMORPG;
import com.robertx22.mine_and_slash.prophecy.AcceptProphecyAffixPacket;
import com.robertx22.mine_and_slash.prophecy.AcceptProphecyPacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.*;
import com.robertx22.mine_and_slash.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellServerToCancelSpellCast;
import com.robertx22.mine_and_slash.vanilla_mc.packets.spells.TellServerToCastSpellPacket;

public class C2SPacketRegister {

    public static void register() {

        int i = 100;
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new TellServerToCastSpellPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new PerkChangePacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new AllocateClassPointPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new AllocateStatPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new TellServerToCancelSpellCast(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new OpenContainerPacket(OpenContainerPacket.GuiType.SKILL_GEMS), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new OpenBackpackPacket(Backpacks.BackpackType.GEARS), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new InvGuiPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new OpenJewelsPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new CraftPacket(), i++);

        Packets.registerClientToServerPacket(MMORPG.NETWORK, new CreateCharPacket(""), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new LoadCharPacket(0), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new AcceptProphecyPacket(""), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new AcceptProphecyAffixPacket(""), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new LockTogglePacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new UnsummonPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new RequestStatCalcInfoPacket(), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new LockRecipePacket(""), i++);


        // Packets.registerClientToServerPacket(MMORPG.NETWORK, new SetupHotbarPacket(), i++);
    }

}


