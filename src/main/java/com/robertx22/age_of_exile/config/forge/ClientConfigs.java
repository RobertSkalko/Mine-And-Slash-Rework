package com.robertx22.age_of_exile.config.forge;

import com.robertx22.age_of_exile.gui.overlays.GuiPosition;
import com.robertx22.age_of_exile.uncommon.enumclasses.PlayerGUIs;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ClientConfigs {

    public static final ForgeConfigSpec clientSpec;
    public static final ClientConfigs CLIENT;

    static {
        final Pair<ClientConfigs, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfigs::new);
        clientSpec = specPair.getRight();
        CLIENT = specPair.getLeft();
    }

    ClientConfigs(ForgeConfigSpec.Builder b) {
        b.comment("Client Configs")
                .push("general");


        SHOW_LOW_ENERGY_MANA_WARNING = b.define("show_low_mana_warning", true);
        RENDER_SIMPLE_MOB_BAR = b.define("render_mob_bar", true);
        RENDER_DEATH_STATISTICS = b.define("render_death_stats", true);
        RENDER_ITEM_RARITY_BACKGROUND = b.define("render_item_rarity_background", true);
        SHOW_DURABILITY = b.define("show_durability", true);
        RENDER_MOB_HEALTH_GUI = b.define("render_mob_hp_gui", true);
        USE_HOTBAR_TOGGLE = b.define("use_hotbar_toggle", true);
        ONLY_RENDER_MOB_LOOKED_AT = b.define("only_render_mob_looked_at", true);
        SHOW_DURABILITY = b.define("show_durability", true);
        ENABLE_FLOATING_DMG = b.define("render_floating_damage", true);
        ALIGN_STAT_TOOLTIPS = b.define("ALIGN_STAT_TOOLTIPS", true);
        ENABLE_PHOTON_FX = b.define("enable_photon_fx", false);
        MODIFY_TOOLTIP_LENGTH = b.define("MODIFY_TOOLTIP_LENGTH", true);

        ITEM_RARITY_OPACITY = b.defineInRange("ITEM_RARITY_OPACITY", 0.75F, 0, 1F);
        HEALTH_BAR_GUI_SCALE = b.defineInRange("health_bar_gui_scale", 1.25F, 0, 10F);

        GUI_POSITION = b.defineEnum("GUI_POSITION", GuiPosition.TOP_LEFT);
        ITEM_RARITY_BACKGROUND_TYPE = b.defineEnum("ITEM_RARITY_BACKGROUND_TYPE", GlintType.FULL);
        PLAYER_GUI_TYPE = b.defineEnum("PLAYER_GUI_TYPE", PlayerGUIs.RPG);
        SKILL_TREE_ZOOM_SPEED = b.defineInRange("SKILL_TREE_ZOOM_SPEED", 0.15D, 0.000001D, 1D);

        REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES = b.defineInRange("REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES", 30, 0, 1000);

        b.pop();
    }

    public ForgeConfigSpec.BooleanValue SHOW_LOW_ENERGY_MANA_WARNING;
    public ForgeConfigSpec.BooleanValue ENABLE_FLOATING_DMG;
    public ForgeConfigSpec.BooleanValue RENDER_SIMPLE_MOB_BAR;
    public ForgeConfigSpec.BooleanValue RENDER_DEATH_STATISTICS;
    public ForgeConfigSpec.BooleanValue RENDER_ITEM_RARITY_BACKGROUND;
    public ForgeConfigSpec.BooleanValue SHOW_DURABILITY;
    public ForgeConfigSpec.BooleanValue RENDER_MOB_HEALTH_GUI;
    public ForgeConfigSpec.BooleanValue USE_HOTBAR_TOGGLE;
    public ForgeConfigSpec.BooleanValue ONLY_RENDER_MOB_LOOKED_AT;
    public ForgeConfigSpec.BooleanValue ALIGN_STAT_TOOLTIPS;
    public ForgeConfigSpec.BooleanValue MODIFY_TOOLTIP_LENGTH;

    public ForgeConfigSpec.BooleanValue ENABLE_PHOTON_FX;

    public ForgeConfigSpec.EnumValue<GlintType> ITEM_RARITY_BACKGROUND_TYPE;
    public ForgeConfigSpec.EnumValue<GuiPosition> GUI_POSITION;
    public ForgeConfigSpec.EnumValue<PlayerGUIs> PLAYER_GUI_TYPE;
    public ForgeConfigSpec.DoubleValue ITEM_RARITY_OPACITY;
    public ForgeConfigSpec.DoubleValue HEALTH_BAR_GUI_SCALE;
    public ForgeConfigSpec.DoubleValue SKILL_TREE_ZOOM_SPEED;

    public ForgeConfigSpec.IntValue REMOVE_EMPTY_TOOLTIP_LINES_IF_MORE_THAN_X_LINES;


    public enum GlintType {
        BORDER, FULL;
    }

    public static ClientConfigs getConfig() {
        return CLIENT;
    }

}
