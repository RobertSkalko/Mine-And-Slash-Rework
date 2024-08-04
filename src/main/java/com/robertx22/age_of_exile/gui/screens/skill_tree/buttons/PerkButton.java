package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons;

import com.mojang.blaze3d.systems.RenderSystem;
import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.database.data.perks.Perk;
import com.robertx22.age_of_exile.database.data.perks.PerkStatus;
import com.robertx22.age_of_exile.database.data.stats.types.UnknownStat;
import com.robertx22.age_of_exile.database.data.talent_tree.TalentTree;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer.ButtonIdentifier;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer.PerkButtonPainter;
import com.robertx22.age_of_exile.gui.screens.skill_tree.connections.PerkConnectionCache;
import com.robertx22.age_of_exile.gui.screens.skill_tree.connections.PerkConnectionRenderer;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.ButtonStatusUpdateTask;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.IDelayCheckTask;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.IDelayChecker;
import com.robertx22.age_of_exile.gui.screens.skill_tree.delaycheck.RendererUpdateTask;
import com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.OpacityController;
import com.robertx22.age_of_exile.gui.screens.skill_tree.opacity.states.NonSearching;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.PointData;

import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.ModRange;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.StatRangeInfo;
import com.robertx22.age_of_exile.saveclasses.perks.TalentsData;

import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.utilityclasses.ClientOnly;
import com.robertx22.age_of_exile.vanilla_mc.packets.perks.PerkChangePacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.utils.GuiUtils;
import com.robertx22.library_of_exile.utils.TextUTIL;
import it.unimi.dsi.fastutil.ints.Int2ReferenceOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.*;
import java.util.stream.Collectors;


public class PerkButton extends ImageButton implements IDelayChecker {

    private PerkButtonState currentState;
    private VanillaState vanillaState;
    private OptimizedState optimizedState;
    public static int SPACING = 26;
    public static int BIGGEST = 33;
    public static ResourceLocation LOCKED_TEX = new ResourceLocation(SlashRef.MODID, "textures/gui/locked.png");
    static ResourceLocation ID = new ResourceLocation(SlashRef.MODID, "textures/gui/skill_tree/perk_buttons.png");

    private final SkillTreeScreen screen;
    public Perk perk;
    public PointData point;
    public TalentTree school;
    public PlayerData playerData;
    public int originalWidth;
    public int originalHeight;
    public int origX;
    public int origY;
    public String perkid = "";
    //not a real status, it will only sync to real status by using some method.

    public PerkButton(SkillTreeScreen screen, PlayerData playerData, TalentTree school, PointData point, Perk perk, int x, int y) {
        super(x, y, perk.getType().size, perk.getType().size, 0, 0, 1, ID, (action) -> {
        });
        this.perk = perk;
        this.point = point;
        this.school = school;
        this.playerData = playerData;

        this.origX = x;
        this.origY = y;
        this.originalWidth = this.width;
        this.originalHeight = this.height;
        this.screen = screen;

        this.vanillaState = new VanillaState(this);
        this.optimizedState = new OptimizedState(this);
        this.currentState = TreeOptimizationHandler.isOptEnable() ? optimizedState : vanillaState;

    }

    public SkillTreeScreen getScreen() {
        return screen;
    }

    public PerkButtonState getCurrentState() {
        return currentState;
    }

    public VanillaState getVanillaState() {
        return vanillaState;
    }

    public OptimizedState getOptimizedState() {
        return optimizedState;
    }

    public boolean isInside(int x, int y) {

        float scale = 2 - screen.zoom;
        return GuiUtils.isInRect((int) (this.getX() - ((width / 4) * scale)), (int) (this.getY() - ((height / 4) * scale)), (int) (width * scale), (int) (height * scale), x, y);
    }

    private void setTooltipMOD(GuiGraphics gui, int mouseX, int mouseY) {

        int MmouseX = (int) (1F / screen.zoom * mouseX);
        int MmouseY = (int) (1F / screen.zoom * mouseY);

        if (this.isInside(MmouseX, MmouseY)) {

            List<Component> tooltip = perk.GetTooltipString(new StatRangeInfo(ModRange.hide()));

            if (perk.stats.stream().anyMatch(x -> x.stat.equals(new UnknownStat().GUID()))) {
                tooltip.add(Component.literal("No Perk of this ID found: " + perkid));
            } else {
                if (MMORPG.RUN_DEV_TOOLS || Screen.hasShiftDown()) {
                    tooltip.add(Component.literal("Perk ID: " + perkid));
                }
            }

            setTooltip(Tooltip.create(TextUTIL.mergeList(tooltip)));

            Screen screen = Minecraft.getInstance().screen;
            if (screen != null) {
                screen.setTooltipForNextRenderPass(this.getTooltip(), this.createTooltipPositioner(), true);
            }
            //GuiUtils.renderTooltip(gui, tooltip, mouseX, mouseY);
        } else {
            setTooltip(null);
        }
    }

    int xPos(float offset, float multi) {
        return (int) ((this.getX() * multi) + offset);

    }

    int yPos(float offset, float multi) {
        return (int) ((getY() * multi) + offset);
    }

    private void changeState(PerkButtonState state) {
        this.currentState = state;
    }

    // copied from abstractbutton
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.currentState.onMouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void renderWidget(GuiGraphics gui, int mouseX, int mouseY, float pPartialTick) {
        this.currentState.onRenderWidget(gui, mouseX, mouseY, pPartialTick);
    }



    @Override
    public ArrayList<ButtonStatusUpdateTask> getTasksContainer() {
        return this.optimizedState.updateList;
    }


    abstract class PerkButtonState {
        public final PerkButton button;

        public PerkButtonState(PerkButton button) {
            this.button = button;
        }

        public abstract void onRenderWidget(GuiGraphics gui, int mouseX, int mouseY, float pPartialTick);

        public abstract boolean onMouseClicked(double mouseX, double mouseY, int button);
    }

    class VanillaState extends PerkButtonState {
        public VanillaState(PerkButton button) {
            super(button);
        }

        @Override
        public void onRenderWidget(GuiGraphics gui, int mouseX, int mouseY, float pPartialTick) {
            if (!screen.shouldRender(getX(), getY(), screen.ctx)) {
                return;
            }

            setTooltipMOD(gui, mouseX, mouseY);


            gui.pose().pushPose();

            float scale = 2 - screen.zoom;

            float posMulti = 1F / scale;


            float add = MathHelper.clamp(scale - 1, 0, 2);
            float off = width / -2F * add;
            gui.pose().translate(off, off, 0);
            gui.pose().scale(scale, scale, scale);


            PerkStatus status = playerData.talents.getStatus(Minecraft.getInstance().player, school, point);

            float offset = perk.getType().getOffset();

            // background

            RenderSystem.enableDepthTest();

            var search = SkillTreeScreen.SEARCH.getValue();

            boolean containsSearchStat = perk.stats.stream()
                    .anyMatch(item -> item.getStat().locName().getString().toLowerCase().contains(search.toLowerCase()));

            boolean containsName = perk.locName().getString().toLowerCase().contains(search.toLowerCase());


            float opacity = search.isEmpty() || containsSearchStat || containsName ? 1F : 0.2f;


            if (!search.isEmpty()) {
                if (search.equals("all")) {
                    if (status != PerkStatus.CONNECTED) {
                        opacity = 0.2F;
                    } else {
                        opacity = 1;
                    }
                }
            } else {
                opacity = status.getOpacity();
            }

            // if newbie, show only the starter perks he can pick
            if (playerData.talents.getAllocatedPoints(TalentTree.SchoolType.TALENTS) < 1) {
                opacity = this.button.perk.getType() == Perk.PerkType.START ? 1 : 0.2F;
            }

            var type = perk.type;

            //gui.blit(ID, xPos(0, posMulti), yPos(0, posMulti), perk.getType().getXOffset(), status.getYOffset(), this.width, this.height);

            int offcolor = (int) ((perk.getType().size - 20) / 2F);

            gui.setColor(1.0F, 1.0F, 1.0F, opacity);


            // if i can merge these 2 icons into 1, i can remove 20% of the lag todo
            gui.blit(perk.getType().getColorTexture(status), xPos(offcolor, posMulti), yPos(offcolor, posMulti), 20, 20, 0, 0, 20, 20, 20, 20);
            gui.blit(perk.getType().getBorderTexture(status), (int) xPos(0, posMulti), (int) yPos(0, posMulti), 0, 0, this.button.width, this.button.height, this.button.width, this.button.height);

            if (search.isEmpty()) {
                opacity += 0.2F;
            }


            gui.setColor(1.0F, 1.0F, 1.0F, MathHelper.clamp(opacity, 0, 1));

            gui.blit(perk.getIcon(), (int) xPos(offset, posMulti), (int) yPos(offset, posMulti), 0, 0, type.iconSize, type.iconSize, type.iconSize, type.iconSize);


            //   gui.pose().scale(1F / scale, 1F / scale, 1F / scale);
            gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            gui.pose().popPose();


        }

        @Override
        public boolean onMouseClicked(double mouseX, double mouseY, int button) {

            screen.getVanillaState().mouseRecentlyClickedTicks = 25;
            screen.pointClicked = super.button.point;

            mouseX = 1F / screen.zoom * mouseX;
            mouseY = 1F / screen.zoom * mouseY;

            if (super.button.active && super.button.visible) {
                boolean bl = super.button.clicked(mouseX, mouseY);
                if (bl) {
//                System.out.println(this.getX() + "_" + getY() + " : " + perk.GUID());


                    super.button.playDownSound(Minecraft.getInstance()
                            .getSoundManager());

                    if (button == 0) {
                        Packets.sendToServer(new PerkChangePacket(school, point, PerkChangePacket.ACTION.ALLOCATE));
                    }
                    if (button == 1) {
                        Packets.sendToServer(new PerkChangePacket(school, point, PerkChangePacket.ACTION.REMOVE));
                    }
                    super.button.onClick(mouseX, mouseY);

                    return true;
                }
                return false;
            } else {
                return false;
            }

        }
    }

    public class OptimizedState extends PerkButtonState {

        private PerkStatus lazyStatus;
        private ResourceLocation wholeTexture;
        private final OpacityController opacityController;
        public final ButtonIdentifier buttonIdentifier;
        public final ArrayList<ButtonStatusUpdateTask> updateList = new ArrayList<>();
        public final List<String> matchStrings;

        public OptimizedState(PerkButton button) {
            super(button);
            this.buttonIdentifier = new ButtonIdentifier(button.school, button.point, button.perk);
            this.opacityController = new OpacityController(button);
            this.matchStrings = perk.stats.stream()
                    .map(item -> item.getStat().locName().getString().toLowerCase()).toList();
            this.lazyStatus = Load.player(ClientOnly.getPlayer()).talents.getStatus(ClientOnly.getPlayer(), button.school, button.point);
            updateTexture();
        }

        @Override
        public void onRenderWidget(GuiGraphics gui, int mouseX, int mouseY, float pPartialTick) {
            if (!screen.shouldRender(getX(), getY(), screen.ctx)) return;


            setTooltipMOD(gui, mouseX, mouseY);

            opacityController.detectCurrentState(playerData);
            if (screen.getOptimizedState().painter.isAllowedToPaint()) {
                int MmouseX = (int) (1F / screen.zoom * mouseX);
                int MmouseY = (int) (1F / screen.zoom * mouseY);
                handleTaskList();
                boolean inside = isInside(MmouseX, MmouseY);

                // the principle of this whole image render is keep the whole image at low opacity, and check if any button needs to use the normal render system.
                // opacityController can detect the current situation, and highlight the necessary buttons.
                if (opacityController.getSingleButtonWhenWholeImage() != OpacityController.HIGHLIGHT && !inside && !screen.getOptimizedState().painter.isThisButtonIsUpdating(this.button) && (this.lazyStatus != PerkStatus.POSSIBLE && opacityController.isSearching()))
                    return;
                if (opacityController.isSearching()) {
                    if (opacityController.getSingleButtonWhenWholeImage() == OpacityController.HIGHLIGHT) {
                        gui.pose().pushPose();
                        normalRender(gui, mouseX, mouseY, true);
                        gui.pose().popPose();

                    }
                } else {
                    if (inside || screen.getOptimizedState().painter.isThisButtonIsUpdating(this.button) || this.lazyStatus == PerkStatus.POSSIBLE) {
                        gui.pose().pushPose();
                        normalRender(gui, mouseX, mouseY, true);
                        gui.pose().popPose();
                    }
                }


            } else {
                gui.pose().pushPose();
                normalRender(gui, mouseX, mouseY, false);
                gui.pose().popPose();
            }
        }

        @Override
        public boolean onMouseClicked(double mouseX, double mouseY, int button) {

            screen.pointClicked = this.button.point;

            mouseX = 1F / screen.zoom * mouseX;
            mouseY = 1F / screen.zoom * mouseY;

            if (this.button.active && this.button.visible) {
                boolean bl = this.button.clicked(mouseX, mouseY);
                if (bl) {

                    super.button.playDownSound(Minecraft.getInstance()
                            .getSoundManager());

                    if (button == 0) {
                        Packets.sendToServer(new PerkChangePacket(school, point, PerkChangePacket.ACTION.ALLOCATE));
                    }
                    if (button == 1) {
                        Packets.sendToServer(new PerkChangePacket(school, point, PerkChangePacket.ACTION.REMOVE));
                    }
                    super.button.onClick(mouseX, mouseY);
                    if (this.buttonIdentifier.getCurrentStatus() == PerkStatus.POSSIBLE){
                        updateRelatedButtonStatus();
                        screen.getOptimizedState().painter.addToUpdate(this.buttonIdentifier);
                        updateRelatedRenderer();
                    }

                    return true;
                }
                return false;
            } else {
                return false;
            }

        }

        private void normalRender(GuiGraphics gui, int mouseX, int mouseY, boolean isPaintBackup) {
            float scale = this.getScale(mouseX, mouseY);

            float posMulti = 1F / scale;
            handleTaskList();

            float add = MathHelper.clamp(scale - 1, 0, 2);
            float off = width / -2F * add;

            gui.pose().translate(off, off, 0);
            gui.pose().scale(scale, scale, scale);

            Perk.PerkType type = perk.getType();


            float offset = type.getOffset();

            // background

            RenderSystem.enableDepthTest();

            int offcolor = (int) ((type.size - 20) / 2F);

            gui.setColor(1.0F, 1.0F, 1.0F, opacityController.getSingleButton());


            if (this.wholeTexture == null) {
                updateTexture();
            }

            if (PerkButtonPainter.handledLocation.contains(this.wholeTexture)) {
                //have to render it with 1.0 opacity, otherwise the icon can't fully cover the whole image when some button use normal render way.
                if (isPaintBackup) {
                    gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                    gui.blit(this.wholeTexture, (int) xPos(0, posMulti), (int) yPos(0, posMulti), 0, 0, this.button.width, this.button.height, this.button.width, this.button.height);
                } else {
                    gui.blit(this.wholeTexture, (int) xPos(0, posMulti), (int) yPos(0, posMulti), 0, 0, this.button.width, this.button.height, this.button.width, this.button.height);
                    gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);
                }
            } else {
                PerkButtonPainter.addToWait(buttonIdentifier);

                ResourceLocation colorTexture = type.getColorTexture(lazyStatus);
                ResourceLocation borderTexture = type.getBorderTexture(lazyStatus);
                ResourceLocation perkIcon = perk.getIcon();

                gui.blit(colorTexture, xPos(offcolor, posMulti), yPos(offcolor, posMulti), 20, 20, 0, 0, 20, 20, 20, 20);
                gui.blit(borderTexture, (int) xPos(0, posMulti), (int) yPos(0, posMulti), 0, 0, this.button.width, this.button.height, this.button.width, this.button.height);


                gui.setColor(1.0F, 1.0F, 1.0F, opacityController.getSingleButton() + (opacityController.getState() instanceof NonSearching ? 0.2f : 0.0F));

                gui.blit(perkIcon, (int) xPos(offset, posMulti), (int) yPos(offset, posMulti), 0, 0, type.iconSize, type.iconSize, type.iconSize, type.iconSize);

                gui.setColor(1.0F, 1.0F, 1.0F, 1.0F);

            }
        }

        public void updateTexture() {
            Perk.PerkType type = this.button.perk.type;
            ResourceLocation colorTexture = type.getColorTexture(this.lazyStatus);
            ResourceLocation borderTexture = type.getBorderTexture(this.lazyStatus);
            ResourceLocation perkIcon = perk.getIcon();
            this.wholeTexture = PerkButtonPainter.getNewLocation(school, colorTexture, borderTexture, perkIcon);
        }

        private float getScale(int mouseX, int mouseY) {
            float scale = 2 - screen.zoom;
            if (isInside((int) (1F / screen.zoom * mouseX), (int) (1F / screen.zoom * mouseY))) {
                scale = MathHelper.clamp(scale * 1.3f, 1.7f, 2.0f);
            }

            return MathHelper.clamp(scale, 1.5f, 2.0f);
        }

        public void updateLazyStatus(PerkStatus lazyStatus) {
            this.lazyStatus = lazyStatus;
            updateTexture();
        }

        public PerkStatus getLazyStatus() {
            return lazyStatus;
        }

        // use getStatus in a per-tick situation will affect performance a lot, this is a lazy version replacement.
        // invoke this when clicking a button
        public void updateRelatedButtonStatus() {
            TalentTree school = this.button.school;
            TalentTree.SchoolType schoolType = school.getSchool_type();
            Perk perk = school.calcData.getPerk(this.button.point);
            //wait for the playerData update.
            ButtonStatusUpdateTask waitingStatusUpdate = new ButtonStatusUpdateTask(x -> {
                return x.optimizedState.getLazyStatus() != playerData.talents.getStatus(ClientOnly.getPlayer(), x.school, x.point);
            }, x -> {
                x.optimizedState.updateLazyStatus(playerData.talents.getStatus(ClientOnly.getPlayer(), x.school, x.point));
            }, 1000);

            //if this perk is a one-kind perk, send the self-check task to their checklist.
            if (perk.one_kind != null && !perk.one_kind.isEmpty()) {

                Set<String> qualifiedPerk = playerData.talents.getAllAllocatedPerks(schoolType).values().stream().filter(x -> x.one_kind != null && x.one_kind.equals(perk.one_kind)).map(x -> x.id).collect(Collectors.toSet());
                Iterator<PerkButton> iterator = new ArrayList<>(this.button.getScreen().pointPerkButtonMap.values()).iterator();
                while (iterator.hasNext()) {
                    PerkButton next = iterator.next();
                    if (qualifiedPerk.contains(next.perk.id)) {
                        next.receive(waitingStatusUpdate);
                    }
                }
            }

            Set<PointData> con = school.calcData.connections.get(this.button.point);
            // todo if player don't have free points, and it closes the skill screen, this task will lose forever because each time open the screen, screen will re-create all button. it means that if a player closes the screen, and reopen it with no free point, and they get a new free point when this reopened screen is on, the button's status won't change at all, player must reopen the screen again to get the right status.

            con.stream().filter(x -> x != null && !playerData.talents.getSchool(schoolType).isAllocated(x)).forEach(x -> this.button.getScreen().pointPerkButtonMap.get(x).receive(waitingStatusUpdate));
            this.button.receive(waitingStatusUpdate);

        }

        public void updateRelatedRenderer() {
            Set<PointData> connections = this.button.school.calcData.connections.getOrDefault(this.button.point, Collections.EMPTY_SET);
            Int2ReferenceOpenHashMap<PerkConnectionRenderer> perkConnectionRendererInt2ReferenceOpenHashMap = PerkConnectionCache.renderersCache.get(this.button.school.getSchool_type().toString().hashCode());
            RendererUpdateTask rendererUpdateTask = new RendererUpdateTask(x -> x.connection() != Load.player(ClientOnly.getPlayer()).talents.getConnection(this.button.school, x.pair().perk1(), x.pair().perk2()), x -> x.mutateConnectionTo(Load.player(ClientOnly.getPlayer()).talents.getConnection(this.button.school, x.pair().perk1(), x.pair().perk2())), 1000);

            for (PointData p : connections) {

                PerkButton sb = this.button.screen.pointPerkButtonMap.get(p);
                PerkPointPair pair = new PerkPointPair(this.button.point, sb.point);

                perkConnectionRendererInt2ReferenceOpenHashMap.get(pair.hashCode()).receive(rendererUpdateTask);
            }

        }
    }
}






