package com.robertx22.mine_and_slash.gui.screens.map;

import com.robertx22.library_of_exile.utils.TextUTIL;
import com.robertx22.mine_and_slash.database.data.profession.screen.ItemButton;
import com.robertx22.mine_and_slash.gui.bases.BaseScreen;
import com.robertx22.mine_and_slash.gui.bases.IAlertScreen;
import com.robertx22.mine_and_slash.gui.bases.INamedScreen;
import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import com.robertx22.mine_and_slash.mmorpg.registers.common.items.SlashItems;
import com.robertx22.mine_and_slash.uncommon.localization.Itemtips;
import com.robertx22.mine_and_slash.uncommon.localization.Words;
import com.robertx22.mine_and_slash.vanilla_mc.packets.MapCompletePacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MapScreen extends BaseScreen implements INamedScreen, IAlertScreen {
    private static final ResourceLocation BACKGROUND = new ResourceLocation(SlashRef.MODID, "textures/gui/map/background.png");

    static int sizeX = 250;
    static int sizeY = 233;

    Minecraft mc = Minecraft.getInstance();


    public MapScreen() {
        super(sizeX, sizeY);
    }

    @Override
    public ResourceLocation iconLocation() {
        return new ResourceLocation(SlashRef.MODID, "textures/gui/main_hub/icons/map.png");
    }

    @Override
    public Words screenName() {
        return Words.Map;
    }

    public MapSyncData getData() {
        return MapCompletePacket.SYNCED_DATA;
    }

    @Override
    public void init() {
        super.init();
        this.clearWidgets();

        try {


            var p = mc.player;

            ItemStack stack = SlashItems.MAP.get().getDefaultInstance();
            getData().data.map.saveToStack(stack);

            var rar = getData().data.map.getRarity();

            var canBoss = getData().data.canTeleportToArena(p).can;

            addRenderableWidget(new MapRarityButton(getData(), guiLeft + sizeX / 2 - MapRarityButton.SIZE / 2, guiTop + 5));
            addRenderableWidget(new ItemButton(stack, guiLeft + 56, guiTop + 58));
            addRenderableWidget(new MapBarButton(MapCompletePacket.SYNCED_DATA, guiLeft + 11, guiTop + 207));

            if (canBoss) {
                addRenderableWidget(new TeleportBossButton(guiLeft + (sizeX / 2) - TeleportBossButton.WIDTH / 2, guiTop + sizeY + 3));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void drawSplit(Minecraft mc, GuiGraphics gui, List<MutableComponent> text, int x, int y, int maxWidth, ChatFormatting format) {

        var tip = TextUTIL.mergeList(text);

        var split = mc.font.split(tip, maxWidth);

        int max = 0;
        for (FormattedCharSequence c : split) {
            int size = mc.font.width(c);
            if (size > max) {
                max = size;
            }
        }
        gui.drawWordWrap(mc.font, tip, x - max / 2, y, maxWidth, ChatFormatting.RED.getColor());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // so 1 guy can mixin to replace it
    public void mnsRenderBG(GuiGraphics gui) {
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(BACKGROUND, mc.getWindow()
                        .getGuiScaledWidth() / 2 - sizeX / 2,
                mc.getWindow()
                        .getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY
        );
    }


    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        try {
            mnsRenderBG(gui);

            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            // background
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            // gui.blit(currentSchool().getBackgroundLoc(), guiLeft + 7, guiTop + 8, 93, 36, 93, 36, 93, 36);
            // gui.blit(currentSchool().getBackgroundLoc(), guiLeft + 150, guiTop + 8, 93, 36, 93, 36, 93, 36);

            super.render(gui, x, y, ticks);

            List<MutableComponent> leftTip = new ArrayList<>();
            leftTip.add(Itemtips.TIER_INFLUENCE.locName().withStyle(ChatFormatting.BLUE));
            getData().data.map.getTierStats().forEach(exactStatData -> leftTip.addAll(exactStatData.GetTooltipString()));

            drawSplit(mc, gui, leftTip, guiLeft + 64, guiTop + 90, 100, ChatFormatting.BOLD);

            /*
            String txt = Gui.SPELL_POINTS.locName().append(String.valueOf(PlayerPointsType.SPELLS.getFreePoints(mc.player))).getString();
            GuiUtils.renderScaledText(gui, guiLeft + 50, guiTop + 215, 1, txt, ChatFormatting.WHITE);

            String tx2 = Gui.PASSIVE_POINTS.locName().append(String.valueOf(PlayerPointsType.PASSIVES.getFreePoints(mc.player))).getString();
            GuiUtils.renderScaledText(gui, guiLeft + 195, guiTop + 215, 1, tx2, ChatFormatting.WHITE);

             */
            //buttons.forEach(b -> b.renderToolTip(matrix, x, y));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public boolean shouldAlert() {
        return false;
    }
}