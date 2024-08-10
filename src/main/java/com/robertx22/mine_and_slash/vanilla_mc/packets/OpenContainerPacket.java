package com.robertx22.mine_and_slash.vanilla_mc.packets;

import com.robertx22.mine_and_slash.capability.player.container.SkillGemsMenu;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;

public class OpenContainerPacket extends MyPacket<OpenContainerPacket> {

    public GuiType gui;

    public OpenContainerPacket(GuiType gui) {
        this.gui = gui;
    }

    public enum GuiType {
        SKILL_GEMS
    }

    @Override
    public ResourceLocation getIdentifier() {
        return SlashRef.id("open_gui_container");
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {
        this.gui = buf.readEnum(GuiType.class);


    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

        buf.writeEnum(gui);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        if (gui == GuiType.SKILL_GEMS) {
            ctx.getPlayer().openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.empty();
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
                    return new SkillGemsMenu(Load.player(pPlayer), pContainerId, pPlayerInventory);
                }
            });
        }

    }

    @Override
    public MyPacket<OpenContainerPacket> newInstance() {
        return new OpenContainerPacket(GuiType.SKILL_GEMS);
    }
}
