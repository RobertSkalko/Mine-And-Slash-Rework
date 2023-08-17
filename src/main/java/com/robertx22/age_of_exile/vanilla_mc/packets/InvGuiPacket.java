package com.robertx22.age_of_exile.vanilla_mc.packets;

import com.robertx22.age_of_exile.gui.inv_gui.GuiItemData;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.packets.ExilePacketContext;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class InvGuiPacket extends MyPacket<OpenGuiPacket> {


    GuiItemData data;

    public InvGuiPacket() {

    }

    public InvGuiPacket(GuiItemData data) {
        this.data = data;
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {

        data = LoadSave.Load(GuiItemData.class, new GuiItemData(), buf.readNbt(), "inv");

    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {
        CompoundTag nbt = new CompoundTag();
        LoadSave.Save(data, nbt, "inv");
        buf.writeNbt(nbt);
    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        data.onServer(ctx.getPlayer());

    }

    @Override
    public MyPacket<OpenGuiPacket> newInstance() {
        return new InvGuiPacket();
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(SlashRef.MODID, "invgui");
    }

}