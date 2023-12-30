package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.capability.player.data.Backpacks;
import com.robertx22.age_of_exile.characters.CreateCharPacket;
import com.robertx22.age_of_exile.characters.LoadCharPacket;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.vanilla_mc.packets.*;
import com.robertx22.age_of_exile.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCancelSpellCast;
import com.robertx22.age_of_exile.vanilla_mc.packets.spells.TellServerToCastSpellPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.core.BlockPos;

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
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new CraftPacket("", BlockPos.ZERO), i++);

        Packets.registerClientToServerPacket(MMORPG.NETWORK, new CreateCharPacket(""), i++);
        Packets.registerClientToServerPacket(MMORPG.NETWORK, new LoadCharPacket(0), i++);


        // Packets.registerClientToServerPacket(MMORPG.NETWORK, new SetupHotbarPacket(), i++);
    }

}


