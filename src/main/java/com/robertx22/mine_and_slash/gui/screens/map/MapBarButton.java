package com.robertx22.mine_and_slash.gui.screens.map;

import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarity;
import com.robertx22.mine_and_slash.database.data.rarities.GearRarityType;
import com.robertx22.mine_and_slash.database.registry.ExileDB;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.UNICODE;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.localization.Chats;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.ClientOnly;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MapCompletePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.robertx22.mine_and_slash.saveclasses.gearitem.gear_bases.StatRequirement.CHECK_YES_ICON;

public class MapBarButton extends ImageButton {

    MapSyncData data;
    static int BAR_WIDTH = 228;
    static int BAR_HEIGHT = 13;
    static ResourceLocation BAR = SlashRef.guiId("map/map_bar");

    public MapBarButton(MapSyncData data, int xPos, int yPos) {
        super(xPos, yPos, BAR_WIDTH, BAR_HEIGHT, 0, 0, 0, SlashRef.guiId(""), (button) -> {
        });
        this.data = data;
    }

    @Override
    public void onPress() {

    }

    @Override
    protected ClientTooltipPositioner createTooltipPositioner() {
        return DefaultTooltipPositioner.INSTANCE;
    }


    @Override
    public void renderWidget(GuiGraphics gui, int pMouseX, int pMouseY, float pPartialTick) {

        float multi = MapCompletePacket.SYNCED_DATA.data.rooms.getMapCompletePercent() / 100F;
        int barWidthMultiplied = (int) (multi * BAR_WIDTH);
        gui.blit(BAR, this.getX(), this.getY(), barWidthMultiplied, BAR_HEIGHT, 0, 0, barWidthMultiplied, BAR_HEIGHT, BAR_WIDTH, BAR_HEIGHT);

        var p = ClientOnly.getPlayer();
        this.setTooltip(Tooltip.create(TextUTIL.mergeList(getBarTooltip())));
    }


    public List<Component> getBarTooltip() {
        var p = ClientOnly.getPlayer();


        List<Component> all = new ArrayList<>();

        var map = data.data;

        var killedBoss = Load.player(p).map.killed_boss;

        all.add(map.canTeleportToArena(p).answer);
        if (killedBoss) {
            all.add(Chats.BOSS_KILLED.locName());
        }
        int perc = map.rooms.getMapCompletePercent();

        all.add(Chats.CURRENT_MAP_EXPLORATION_PERCENT.locName(perc + "%").withStyle(ChatFormatting.YELLOW));

        all.add(Component.empty());


        var rarities = ExileDB.GearRarities().getFilterWrapped(x -> x.type == GearRarityType.NORMAL).list.stream()
                .sorted(Comparator.comparingInt(x -> x.item_tier)).collect(Collectors.toList());

        for (GearRarity rar : rarities) {
            MutableComponent tick = Component.literal(UNICODE.NO_ICON).withStyle(ChatFormatting.RED, ChatFormatting.BOLD);

            if (perc >= rar.map_reward.perc_to_unlock) {
                tick = Component.literal(CHECK_YES_ICON).withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD);
            }
            tick.append(" : " + rar.map_reward.perc_to_unlock + "%");

            all.add(Chats.MAP_EXPLORATION_RARITY.locName(rar.coloredName(), tick).withStyle(ChatFormatting.YELLOW));
        }

        all.add(Component.empty());
        if (map.rooms.isDoneGenerating()) {
            all.add(Chats.MAP_FINISHED_SPAWNING.locName().withStyle(ChatFormatting.DARK_PURPLE));
            var color = ChatFormatting.LIGHT_PURPLE;
            all.add(Chats.TOTAL_MOBS.locName(map.rooms.mobs.done, map.rooms.mobs.total).withStyle(color));
            all.add(Chats.TOTAL_CHESTS.locName(map.rooms.chests.done, map.rooms.chests.total).withStyle(color));
        } else {
            all.add(Chats.MAP_NOT_SCOUTED.locName().withStyle(ChatFormatting.RED));
        }
        return all;
    }

}