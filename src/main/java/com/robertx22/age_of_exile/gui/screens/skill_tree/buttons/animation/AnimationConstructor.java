package com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.animation;

import com.robertx22.age_of_exile.gui.screens.skill_tree.SkillTreeScreen;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.painter.AllPerkButtonPainter;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

public class AnimationConstructor {
    private final ArrayList<IOrderExecuteAnimationPiece<?>> animationList = new ArrayList<>();
    private SkillTreeScreen screen;
    public boolean isLoaded = false;

    public AnimationConstructor(SkillTreeScreen screen) {
        this.screen = screen;
    }

    public AnimationConstructor load(List<AllPerkButtonPainter.ResourceLocationAndSize> materials, GuiGraphics gui, int startX, int startY) {

        RightFadeInPiece animation = null;
        for (AllPerkButtonPainter.ResourceLocationAndSize material : materials) {
            if (animation == null){
                animation = new RightFadeInPiece(material, gui, startX, startY, materials.indexOf(material), screen);
            } else {
                animation = animation.append(new RightFadeInPiece(material, gui, startX, startY, materials.indexOf(material), screen));
            }
        }
        animationList.add(animation.goBack());
        this.isLoaded = true;
        return this;
    }

    public boolean isAllDone(){
        Boolean b = animationList.stream().map(IOrderExecuteAnimationPiece::isAllDone).reduce((x, y) -> x && y).orElse(false);
        return b;
    }

    public void play(){
        if (this.animationList.isEmpty()) return;
        for (IOrderExecuteAnimationPiece<?> iOrderExecuteAnimationPiece : animationList) {
            iOrderExecuteAnimationPiece.executeThis();
        }
    }
}
