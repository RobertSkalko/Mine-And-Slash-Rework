package com.robertx22.age_of_exile.gui.inv_gui.actions;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.TooltipInfo;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.vanilla_mc.packets.OpenContainerPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class PickSpellAction extends GuiAction {

    Spell spell;

    public static int SLOT = 0;

    public PickSpellAction(Spell spell) {
        this.spell = spell;
    }


    @Override
    public ResourceLocation getIcon() {
        return spell.getIconLoc();
    }

    @Override
    public void saveExtraData(FriendlyByteBuf buf) {
        buf.writeInt(SLOT);
    }

    @Override
    public Object loadExtraData(FriendlyByteBuf buf) {
        return buf.readInt();
    }

    @Override
    public List<Component> getTooltip(Player p) {
        return spell.GetTooltipString(new TooltipInfo(p));
    }

    @Override
    public void doAction(Player p, Object data) {
        int slot = (int) data;

        Load.player(p).spellCastingData.setHotbar(slot, spell.GUID());

    }

    @Override
    public void clientAction(Player p, Object obj) {
        Packets.sendToServer(new OpenContainerPacket(OpenContainerPacket.GuiType.SKILL_GEMS));
    }

    @Override
    public String GUID() {
        return spell.GUID();
    }
}
