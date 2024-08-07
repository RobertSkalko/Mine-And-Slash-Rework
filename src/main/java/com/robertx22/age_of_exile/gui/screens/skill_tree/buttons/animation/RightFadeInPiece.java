package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.animation;

import com.robertx22.age_of_exile.config.forge.ClientConfigs;
import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.drawer.AllPerkButtonPainter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import javax.annotation.Nullable;

public class RightFadeInPiece implements IOrderExecuteAnimationPiece {

    private final int startX;
    private final int startY;
    private final int connectionX;
    private final int connectionY;
    private final int order;
    public boolean allDone = false;
    private int frameInterval = 1;
    private int animationFrames = 5;
    private int currentFrame = 0;
    @Nullable
    public RightFadeInPiece last;
    @Nullable
    private RightFadeInPiece next;
    private AllPerkButtonPainter.ResourceLocationAndSize thisLocation;
    private GuiGraphics gui;
    private SkillTreeScreen screen;


    public RightFadeInPiece(AllPerkButtonPainter.ResourceLocationAndSize thisLocation, GuiGraphics gui, int startX, int startY, int connectionX, int connectionY, int order, SkillTreeScreen screen) {
        this.thisLocation = thisLocation;
        this.gui = gui;
        this.startX = startX;
        this.startY = startY;
        this.connectionX = connectionX;
        this.connectionY = connectionY;
        this.order = order;
        this.screen = screen;
    }

    @Override
    public void executeThis() {
        if (getTimeInterval() > 0) {
            this.minusOneTimeInterval();
        } else {
            executeNext();
        }

        float v = currentFrame * 1.0f / animationFrames;
        gui.pose().pushPose();
        gui.pose().scale(screen.zoom, screen.zoom, screen.zoom);
        //handleZoom();
        gui.setColor(1.0f, 1.0f, 1.0f, 0.0f + v);
        gui.blit(thisLocation.location(), startX + order * thisLocation.width() + (int) ((1F / screen.zoom - 1) * this.screen.width / 2F + screen.scrollX), startY + (int) ((1F / screen.zoom - 1) * this.screen.height / 2F + screen.scrollY) + (int) (20 - 20 * v), 0, 0, thisLocation.width(), thisLocation.height(), thisLocation.width(), thisLocation.height());
        gui.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        gui.pose().popPose();
        if (currentFrame < animationFrames) {
            currentFrame++;
        }
    }

    @Override
    public void executeNext() {
        if (next == null) {
            allDone = true;
        } else {
            next.executeThis();
        }

    }

    @Override
    public <T extends IOrderExecuteAnimationPiece> T append(T next) {
        this.next = (RightFadeInPiece)next;
        this.next.last = this;
        return next;
    }

    @Override
    public <T extends IOrderExecuteAnimationPiece> T goBack() {
        if (last == null){
            return (T)this;
        } else {
            return last.goBack();
        }
    }

    @Override
    public int getTimeInterval() {
        return frameInterval;
    }

    @Override
    public void minusOneTimeInterval() {
        frameInterval--;
    }

    @Override
    public boolean isAllDone() {
        if (next == null) {
            return allDone;
        } else {
            return next.isAllDone();
        }
    }

}
