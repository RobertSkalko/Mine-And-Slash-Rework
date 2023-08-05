package com.robertx22.age_of_exile.gui.screens.skill_tree;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.robertx22.age_of_exile.capability.player.RPGPlayerData;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree.SchoolType;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.gui.bases.BaseScreen;
import com.robertx22.age_of_exile.gui.bases.INamedScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.ConnectionButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.SelectTreeButton;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.TalentTreeButton;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.library_of_exile.utils.GuiUtils.PointF;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.awt.*;
import java.util.List;
import java.util.*;

public abstract class SkillTreeScreen extends BaseScreen implements INamedScreen {
    static ResourceLocation BIG_PANEL = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/background.png");
    static ResourceLocation SMALL_PANEL = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/small_panel.png");

    public SchoolType schoolType;

    public SkillTreeScreen(SchoolType type) {
        super(Minecraft.getInstance()
                .getWindow()
                .getGuiScaledWidth(), Minecraft.getInstance()
                .getWindow()
                .getGuiScaledHeight());
        this.schoolType = type;

    }

    public PointData pointClicked = new PointData(0, 0);
    public int mouseRecentlyClickedTicks = 0;

    @Override
    public boolean mouseReleased(double x, double y, int ticks) {

        mouseRecentlyClickedTicks = 25;

        return super.mouseReleased(x, y, ticks);

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 32) { // space
            this.goToCenter();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);

    }

    int tick_count = 0;
    int scrollX = 0;
    int scrollY = 0;

    HashMap<AbstractWidget, PointData> originalButtonLocMap = new HashMap<>();
    HashMap<PointData, PerkButton> pointPerkButtonMap = new HashMap<>();

    public List<TalentTree> schoolsInOrder;

    public TalentTree getSchoolByIndexAllowsOutOfBounds(int i) {
        if (i >= schoolsInOrder.size()) {
            return schoolsInOrder.get(i - schoolsInOrder.size());
        }
        if (i < 0) {
            return schoolsInOrder.get(schoolsInOrder.size() + i);
        }
        return schoolsInOrder.get(i);

    }

    public Minecraft mc = Minecraft.getInstance();

    RPGPlayerData playerData = Load.playerRPGData(mc.player);

    public TalentTree school;

    @Override
    protected void init() {
        super.init();

        try {

            schoolsInOrder = ExileDB.TalentTrees()
                    .getFiltered(x -> {
                        return x.getSchool_type() == this.schoolType;
                    });
            schoolsInOrder.sort(Comparator.comparingInt(x -> x.order));

            this.school = schoolsInOrder.get(0);

            refreshButtons();

            goToCenter();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addButtonPublic(AbstractWidget b) {
        this.addWidget(b);
    }

    private void addConnections() {

        HashSet<PointData> def = new HashSet();

        Set<Set<PointData>> cons = new HashSet<>();

        new ArrayList<>(children()).forEach(b -> {
            if (b instanceof PerkButton) {
                PerkButton pb = (PerkButton) b;

                Set<PointData> connections = this.school.calcData.connections.getOrDefault(pb.point, def);

                int x1 = pb.getX() + pb.getWidth() / 2;
                int y1 = pb.getY() + pb.getHeight() / 2;

                int size = 6;
                float spacing = size + size / 2F;

                for (PointData p : connections) {

                    if (cons.stream()
                            .anyMatch(x -> x.contains(p) && x.contains(pb.point))) {
                        continue;
                    }

                    cons.add(Sets.newHashSet(p, pb.point));

                    PerkButton sb = this.pointPerkButtonMap.get(p);

                    if (sb == null) {
                        continue;

                    }

                    int x2 = sb.getX() + sb.getWidth() / 2;
                    int y2 = sb.getY() + sb.getHeight() / 2;

                    List<PointF> points = GuiUtils.generateCurve(new PointF(x1, y1), new PointF(x2, y2), 360f, spacing + 2, true);

                    for (PointF point : points) {

                        int x = (int) (point.x - ((float) size / 2));
                        int y = (int) (point.y - ((float) size / 2));

                        newButton(new ConnectionButton(this, school, p, pb.point, x, y));

                    }

                }
            }

        });

    }

    public void refreshButtons() {

        //Watch watch = new Watch();

        originalButtonLocMap.clear();
        pointPerkButtonMap.clear();

        // this.buttons.clear();
        this.children().clear();

        this.scrollX = 0;
        this.scrollY = 0;

        for (Map.Entry<PointData, String> e : school.calcData.perks.entrySet()) {
            Perk perk = ExileDB.Perks()
                    .get(e.getValue());

            if (perk == null) {
                continue;
            }

            try {
                // centers them if they are smaller than the biggest one
                int addx = (PerkButton.BIGGEST) / 2 - perk
                        .getType().width / 2;
                int addy = (PerkButton.BIGGEST) / 2 - perk
                        .getType().height / 2;

                int subx = PerkButton.BIGGEST / 2;
                int suby = PerkButton.BIGGEST / 2;

                int x = getPosForPoint(e.getKey()).x + addx - subx + TalentTreeButton.XSIZE / 2;
                int y = getPosForPoint(e.getKey()).y + addy - suby + TalentTreeButton.YSIZE / 2;

                this.newButton(new PerkButton(this, playerData, school, e.getKey(), perk, x, y));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        int sx = mc.getWindow()
                .getGuiScaledWidth() / 2 - TalentTreeButton.XSIZE / 2;
        int sy = 0;

        if (this.schoolsInOrder.size() > 1) {

            this.addButtonPublic(new TalentTreeButton(this, school, sx, sy));

            int place = this.schoolsInOrder.indexOf(school);

            for (int i = 0; i < 2; i++) {
                int xadd = TalentTreeButton.XSIZE * (i + 1) + i + 1;

                int index1 = i + 1;
                int index2 = -i - 1;

                TalentTree school1 = getSchoolByIndexAllowsOutOfBounds(place + index1);
                TalentTree school2 = getSchoolByIndexAllowsOutOfBounds(place + index2);

                this.addButtonPublic(new TalentTreeButton(this, school1, sx + xadd, sy));
                this.addButtonPublic(new TalentTreeButton(this, school2, sx - xadd, sy));

                if (i == 1) {
                    this.addButtonPublic(new SelectTreeButton(this, SelectTreeButton.LeftOrRight.LEFT, sx - xadd - 15, sy + TalentTreeButton.YSIZE / 2 - SelectTreeButton.YSIZE / 2));
                    this.addButtonPublic(new SelectTreeButton(this, SelectTreeButton.LeftOrRight.RIGHT, sx + xadd + TalentTreeButton.XSIZE + 1, sy + TalentTreeButton.YSIZE / 2 - SelectTreeButton.YSIZE / 2));
                }
            }
        }

        addConnections();

        //        watch.print(" Setting up buttons ");

    }

    private Point getPosForPoint(PointData point) {

        float halfx = mc.getWindow()
                .getGuiScaledWidth() / 2F;

        float halfy = mc.getWindow()
                .getGuiScaledHeight() / 2F;

        float x = (point.x - school.calcData.center.x) * PerkButton.SPACING + 2;
        float y = (point.y - school.calcData.center.y) * PerkButton.SPACING + 2;

        x -= TalentTreeButton.XSIZE / 2F;
        y -= TalentTreeButton.YSIZE / 2F;

        int tx = (int) (halfx + x);
        int ty = (int) (halfy + y);

        return new Point(tx, ty);

    }

    public void goToCenter() {
        this.scrollX = 0;
        this.scrollY = 0;
    }

    private void newButton(AbstractWidget b) {
        this.addButtonPublic(b);
        originalButtonLocMap.put(b, new PointData(b.getX(), b.getY()));
        if (b instanceof PerkButton) {
            this.pointPerkButtonMap.put(((PerkButton) b).point, (PerkButton) b);
        }
    }

    public float zoom = 0.3F;

    public static boolean RETURN_ZOOM = false;

    @Override
    public void onClose() {

        super.onClose();

        resetZoom();

    }

    @Override
    public void removed() {
        super.removed();

        resetZoom();

    }

    private void resetZoom() {
        zoom = 1;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (scroll < 0) {
            zoom -= 0.1F;
        }
        if (scroll > 0) {
            zoom += 0.1F;
        }

        this.zoom = Mth.clamp(zoom, 0.08F, 1);

        return true;
    }

    public PointData getZoomedPosition(PointData pos) {
        return new PointData((int) this.zoom / pos.x, (int) this.zoom / pos.y);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.scrollX += 1F / zoom * deltaX;
        this.scrollY += 1F / zoom * deltaY;

        scrollY = Mth.clamp(scrollY, -3333, 3333);

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    private void renderButton(AbstractWidget b) {
        if (originalButtonLocMap.containsKey(b)) {

            b.setX(this.originalButtonLocMap.get(b).x);
            b.setY(this.originalButtonLocMap.get(b).y);

            float addx = (1F / zoom - 1) * this.width / 2F;
            float addy = (1F / zoom - 1) * this.height / 2F;

            b.setX((int) (b.getX() + addx));
            b.setY((int) (b.getY() + addy));

            b.setX(b.getX() + scrollX);
            b.setY(b.getY() + scrollY);

        }
    }

    @Override
    public void render(GuiGraphics gui, int x, int y, float ticks) {

        // Watch watch = new Watch();
        mouseRecentlyClickedTicks--;

        gui.pose().scale(zoom, zoom, zoom);

        try {

            for (GuiEventListener e : this.children()) {
                if (e instanceof ImageButton b)
                    if (originalButtonLocMap.containsKey(b)) {
                        b.setX((this.originalButtonLocMap.get(b).
                                x));
                        b.setY((this.originalButtonLocMap.get(b)
                                .y));

                        float addx = (1F / zoom - 1) * this.width / 2F;
                        float addy = (1F / zoom - 1) * this.height / 2F;

                        b.setX((int) (b.getX() + addx));
                        b.setY((int) (b.getY() + addy));

                        b.setX(b.getX() + scrollX);
                        b.setY(b.getY() + scrollY);

                    }
            }


            renderBackgroundDirt(gui, this, 0);

            for (GuiEventListener e : this.children()) {
                if (e instanceof ConnectionButton c) {
                    c.renderButtonForReal(gui, x, y, ticks);
                }

            }


            super.render(gui, x, y, ticks);

            for (GuiEventListener abstractButtonWidget : children()) {
                if (abstractButtonWidget instanceof PerkButton p) {
                    p.render(gui, x, y, ticks);
                }
            }

            // we order them here so school buttons are on top, and perks are on top of connection buttons..
            // probably a better way to do it exists?

            for (GuiEventListener button : children()) {
                if (button instanceof IMarkOnTop t && button instanceof ImageButton b) {
                    b.render(gui, x, y, ticks);
                }

            }

            this.tick_count++;

        } catch (Exception e) {
            e.printStackTrace();
        }

        gui.pose().scale(1F / zoom, 1F / zoom, 1F / zoom);

        renderPanels(gui);

        for (GuiEventListener e : children()) {
            if (e instanceof PerkButton p) { // todo
                p.renderToolTip(gui, x, y);
            }
            if (e instanceof TalentTreeButton p) { // todo
                p.renderToolTip(gui, x, y);
            }
        }
        //watch.print(" rendering ");
    }

    private void renderPanels(GuiGraphics gui) {

        int BG_HEIGHT = 38;
        Minecraft mc = Minecraft.getInstance();

        RenderSystem.enableDepthTest();

        int BG_WIDTH = 237;
        int xp = mc.getWindow()
                .getGuiScaledWidth() / 2 - BG_WIDTH / 2;
        int yp = 0;

        if (this.schoolsInOrder.size() > 1) {
            gui.blit(BIG_PANEL, xp, yp, 0, 0, BG_WIDTH, 39);
        }

        //  mc.getTextureManager()
        //        .bind(SMALL_PANEL);
        RenderSystem.enableDepthTest();

        int savedx = xp;
        int savedy = yp;

        // LEFT
        xp = savedx;
        yp = savedy;

        String text = "Points: " + playerData.talents.getFreePoints(Load.Unit(mc.player), this.schoolType);

        int tx = xp - mc.font.width(text) - 10;
        int yx = yp + BG_HEIGHT / 2 - mc.font.lineHeight / 2;


        gui.drawString(mc.font, text, tx, yx, ChatFormatting.GREEN.getColor());

        text = "Reset Points: " + playerData.talents.reset_points;

        tx = savedx + 10 + BG_WIDTH;

        gui.drawString(mc.font, text, tx, yx, ChatFormatting.GREEN.getColor());

    }

    static ResourceLocation BACKGROUND = SlashRef.guiId("skill_tree/background");

    public static void renderBackgroundDirt(GuiGraphics gui, Screen screen, int vOffset) {
        //copied from Scree


        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();

        RenderSystem.setShaderTexture(0, BACKGROUND);

        Minecraft mc = Minecraft.getInstance();

        // todo test
        gui.setColor(0.25F, 0.25F, 0.25F, 1.0F);
        int i = 32;
        gui.blit(BACKGROUND, 0, 0, 0, 0.0F, 0.0F, mc.screen.width, mc.screen.height, 32, 32);
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

/*
        gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        bufferBuilder.begin(7, DefaultVertexFormat.POSITION_TEX_COLOR);
        bufferBuilder.vertex(0.0D, (double) screen.height, 0.0D)
                .uv(0.0F, (float) screen.height / 32.0F + (float) vOffset)
                .color(64, 64, 64, 255)
                .endVertex();
        bufferBuilder.vertex((double) screen.width, (double) screen.height, 0.0D)
                .uv((float) screen.width / 32.0F, (float) screen.height / 32.0F + (float) vOffset)
                .color(64, 64, 64, 255)
                .endVertex();
        bufferBuilder.vertex((double) screen.width, 0.0D, 0.0D)
                .uv((float) screen.width / 32.0F, (float) vOffset)
                .color(64, 64, 64, 255)
                .endVertex();
        bufferBuilder.vertex(0.0D, 0.0D, 0.0D)
                .uv(0.0F, (float) vOffset)
                .color(64, 64, 64, 255)
                .endVertex();
        tessellator.end();

 */
    }

}
