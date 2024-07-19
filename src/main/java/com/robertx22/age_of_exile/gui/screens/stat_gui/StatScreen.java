package com.robertx22.age_of_exile.gui.screens.stat_gui;

import com.robertx22.age_of_exile.database.data.stats.Stat;
import com.robertx22.age_of_exile.database.data.stats.StatGuiGroup;
import com.robertx22.age_of_exile.database.data.stats.types.defense.Armor;
import com.robertx22.age_of_exile.database.data.stats.types.defense.DodgeRating;
import com.robertx22.age_of_exile.database.data.stats.types.generated.ElementalResist;
import com.robertx22.age_of_exile.database.data.stats.types.resources.health.Health;
import com.robertx22.age_of_exile.database.data.stats.types.resources.mana.Mana;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.unit.StatData;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.localization.Words;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class StatScreen extends BaseScreen implements INamedScreen {
    static ResourceLocation BG = SlashRef.guiId("stat_gui/background");

    public StatScreen() {
        super(199, 222);
    }


    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        gui.blit(BG, mc.getWindow().getGuiScaledWidth() / 2 - sizeX / 2, mc.getWindow().getGuiScaledHeight() / 2 - sizeY / 2, 0, 0, sizeX, sizeY);
        super.render(gui, x, y, ticks);


        SEARCH.setX(this.guiLeft - (SEARCH_WIDTH / 2) + sizeX / 2);
        SEARCH.setY(this.guiTop - SEARCH_HEIGHT - 5);
        SEARCH.render(gui, 0, 0, 0);

    }

    private static int SEARCH_WIDTH = 100;
    private static int SEARCH_HEIGHT = 14;
    public static EditBox SEARCH = new EditBox(Minecraft.getInstance().font, 0, 0, SEARCH_WIDTH, SEARCH_HEIGHT,
            Component.translatable("fml.menu.mods.search"));


    int currentElement = 0;
    public List<Stat> stats = new ArrayList<>();
    public List<Stat> searched = new ArrayList<>();
    //   int elementsAmount = 1;


    public void setupStatButtons() {
        this.renderables.removeIf(x -> x instanceof EditBox == false);
        this.children().removeIf(x -> x instanceof EditBox == false);

        //    this.children().clear();
        //  this.renderables.clear();

        int secX = guiLeft + 9;
        int secY = guiTop + 18;

        for (StatGuiGroupSection sec : StatGuiGroupSection.values()) {
            this.publicAddButton(new StatSectionButton(this, sec, secX, secY));
            secY += StatSectionButton.ySize + 2;
        }


        //  this.children().removeIf(x -> x instanceof StatPanelButton || x instanceof StatIconAndNumberButton);

        int x = this.guiLeft + 30;
        int y = this.guiTop + 16;


        int spaceleft = 143;

        var data = Load.Unit(ClientOnly.getPlayer());


        for (int i = currentElement; i < currentElement + 15; i++) {
            if (i >= this.stats.size()) {
                continue;
            } else {
                if (searched.size() > i) {
                    Stat entry = searched.get(i);
                    int ysize = entry.getStatGuiPanelButtonYSize() + 3;

                    if (spaceleft >= ysize) {
                        var stat = data.getUnit().getCalculatedStat(entry);
                        if (stat.GetStat() != null) {
                            this.publicAddButton(new StatPanelButton(this, stat, x, y));
                            y += ysize;
                            spaceleft -= ysize;
                        }
                    }
                }

            }

        }


    }

    @Override
    public boolean mouseScrolled(double num1, double num2, double num3) {

        this.setCurrentElement((int) (currentElement - num3));

        return super.mouseScrolled(num1, num2, num3);

    }

    public void setCurrentElement(int element) {
        this.currentElement = MathHelper.clamp(element, 0, searched.size());
        setupStatButtons();
    }


    // todo

    public void setInfo(StatData stat) {

        this.renderables.removeIf(x -> x instanceof IStatInfoButton);
        this.children().removeIf(x -> x instanceof IStatInfoButton);


        int x = guiLeft + 38;
        int y = guiTop + 172;

        for (StatInfoButton.StatInfoType type : StatInfoButton.StatInfoType.values()) {
            if (type.shouldShow(stat)) {
                this.publicAddButton(new StatInfoButton(type, stat, x, y));
                x += StatInfoButton.xSize + 12;
            }
        }

    }

    @Override
    public void tick() {
        SEARCH.tick();

    }

    public void showStats(List<Stat> stats, boolean replaceSaved) {

        if (replaceSaved) {
            this.stats = stats;
        }
        this.searched = stats;
        this.currentElement = 0;

        setupStatButtons();


    }

    public List<Stat> getAllStats() {

        if (true) {

            var stats = Load.Unit(ClientOnly.getPlayer()).getUnit().getStats().stats.values().stream().filter(x -> x.GetStat().show_in_gui).map(x -> x.GetStat()).collect(Collectors.toList());

            var ungrouped = stats.stream().filter(x -> !x.gui_group.isValid()).collect(Collectors.toList());
            List<Stat> grouped = new ArrayList<>();
            for (StatGuiGroup group : StatGuiGroup.values()) {
                if (group.isValid()) {
                    stats.stream().filter(x -> x.gui_group == group).findFirst().ifPresent(x -> grouped.add(x));
                }
            }

            List<Stat> all = new ArrayList<>();
            all.addAll(grouped);

            all.addAll(ungrouped);
            return all;
        }

        return Arrays.asList(new ElementalResist(Elements.Physical), DodgeRating.getInstance(), Armor.getInstance(), Health.getInstance(), Mana.getInstance());
    }

    @Override
    protected void init() {
        super.init();


        SEARCH.setFocused(false);
        SEARCH.setCanLoseFocus(true);
        publicAddButton(SEARCH);

        SEARCH.setResponder(x -> {
            showStats(stats.stream().filter(s -> {
                String name = s.locName().getString();
                return name.toLowerCase(Locale.ROOT).contains(x.toLowerCase(Locale.ROOT));
            }).collect(Collectors.toList()), false);
        });


        showStats(StatGuiGroupSection.CORE.getStats(mc.player), true);

    }

    @Override
    public ResourceLocation iconLocation() {
        return SlashRef.guiId("main_hub/icons/stats");
    }

    @Override
    public Words screenName() {
        return Words.Stats;
    }
}
