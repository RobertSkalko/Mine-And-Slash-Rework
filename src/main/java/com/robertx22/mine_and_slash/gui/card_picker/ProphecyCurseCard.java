package com.robertx22.mine_and_slash.gui.card_picker;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.mine_and_slash.database.data.map_affix.MapAffix;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.prophecy.AcceptProphecyAffixPacket;
import com.robertx22.mine_and_slash.saveclasses.ExactStatData;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ProphecyCurseCard implements ICard {


    MapAffix affix;


    public ProphecyCurseCard(MapAffix affix) {
        this.affix = affix;
    }

    @Override
    public ResourceLocation getIcon() {
        return SlashRef.guiId("prophecy/curse/" + affix.prophecy_type);
    }

    @Override
    public void onClick(Player p) {
        Packets.sendToServer(new AcceptProphecyAffixPacket(affix.GUID()));
    }

    @Override
    public List<MutableComponent> getTooltip(Player p) {
        List<MutableComponent> list = new ArrayList<>();
        // todo

        for (ExactStatData stat : affix.getStats(100, Load.Unit(p).getLevel())) {
            list.addAll(stat.GetTooltipString());
        }
        return list;
    }

    @Override
    public MutableComponent getName() {
        return Component.literal("Pick Curse");
    }
}
