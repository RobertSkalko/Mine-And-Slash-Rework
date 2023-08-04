package com.robertx22.age_of_exile.mmorpg.registers.common;

import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.Def;
import com.robertx22.age_of_exile.mmorpg.registers.deferred_wrapper.RegObj;
import com.robertx22.age_of_exile.vanilla_mc.blocks.runeword_station.RuneWordStationContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;

public class SlashContainers {

    public static void init() {

    }

    public static RegObj<ContainerType<RuneWordStationContainer>> RUNEWORD = Def.container("runeword", () -> IForgeContainerType.create((int n, PlayerInventory pinv, PacketBuffer buf) -> new RuneWordStationContainer(n, pinv, buf)));
   
}
