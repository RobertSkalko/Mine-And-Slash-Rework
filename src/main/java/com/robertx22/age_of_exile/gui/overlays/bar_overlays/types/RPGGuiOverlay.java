package com.robertx22.age_of_exile.gui.overlays.bar_overlays.types;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.mmorpg.SyncedToClientValues;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class RPGGuiOverlay {

    static ResourceLocation BASETEX = new ResourceLocation(SlashRef.MODID, "textures/gui/overlay/base.png");
    static ResourceLocation MANA_RESERVE = new ResourceLocation(SlashRef.MODID, "textures/gui/overlay/mana_reserve.png");

    public RPGGuiOverlay() {
        super();
    }

    static int BAR_HEIGHT = 9;
    public static int INNER_BAR_WIDTH = 78;
    static int INNER_BAR_HEIGHT = 5;

    Minecraft mc = Minecraft.getInstance();

    int areaLvlTicks = 0;
    int currentAreaLvl = 0;

    public static int BUTTON_SIZE_X = 30;
    public static int BUTTON_SIZE_Y = 30;

    public enum IconRenderType {
        SCREEN, OVERLAY;
    }


    public void onHudRender(GuiGraphics gui) {

        try {

            if (mc.player == null) {
                return;
            }
            if (mc.options.renderDebug || mc.player.isCreative() || mc.player.isSpectator()) {
                return;
            }

            if (ClientConfigs.getConfig().PLAYER_GUI_TYPE.get() == PlayerGUIs.NONE) {
                return;
            }


            Player en = mc.player;
            EntityData data = Load.Unit(en);

            if (data == null) {
                return;
            }

            if (currentAreaLvl != SyncedToClientValues.areaLevel) {
                currentAreaLvl = SyncedToClientValues.areaLevel;
                areaLvlTicks = 200;
            }

            ClientConfigs.getConfig().GUI_POSITION.get().getGuiConfig(data, en)
                    .forEach(c -> {

                        if (c.type.shouldRender(data, mc.player)) {
                            MineAndSlashBars.renderMineAndSlashBar(c, c.type,
                                    gui,
                                    c.getPosition(),
                                    c.type.getText(data, mc.player),
                                    false);
                        }

                        if (c.type.shouldRender(data, mc.player)) {
                            MineAndSlashBars.renderMineAndSlashBar(c, c.type,
                                    gui,
                                    c.getPosition(),
                                    c.type.getText(data, mc.player),
                                    true);
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}