package com.robertx22.mine_and_slash.a_libraries.player_animations;


import com.robertx22.mine_and_slash.mmorpg.SlashRef;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class AnimationHolder {
    public String id;
    // private final RawAnimation geckoAnimation;
    private final ResourceLocation playerAnimation;
    public final boolean isPass;
    public final boolean animatesLegs;

    public AnimationHolder(String path, boolean animatesLegs) {
        this.id = path;
        this.playerAnimation = path.contains(":") ? new ResourceLocation(path) : SlashRef.id(path);
        // playonce is only needed for geckolib
        // this.geckoAnimation = RawAnimation.begin().then(playerAnimation.getPath(), playOnce ? Animation.LoopType.PLAY_ONCE : Animation.LoopType.HOLD_ON_LAST_FRAME);
        this.isPass = false;
        this.animatesLegs = animatesLegs;
    }

    public AnimationHolder(String path) {
        this(path, false);
    }

    private AnimationHolder(boolean isPass) {
        this.playerAnimation = null;
        //this.geckoAnimation = null;
        this.isPass = isPass;
        this.animatesLegs = false;
    }

    private static final AnimationHolder empty = new AnimationHolder(false);
    private static final AnimationHolder pass = new AnimationHolder(true);

    /**
     * Represents an empty animation, making the player immediately stop animating at the end of a cast
     */
    public static AnimationHolder none() {
        return empty;
    }

    /**
     * Represents the lack of an animation, letting the previous animation (the cast start animation) continue to play after the spell ends, so long as the spell wasn't cancelled
     */
    public static AnimationHolder pass() {
        return pass;
    }

    /*
    public Optional<RawAnimation> getForMob() {
        return geckoAnimation == null ? Optional.empty() : Optional.of(geckoAnimation);
    }

     */

    public Optional<ResourceLocation> getForPlayer() {
        return playerAnimation == null ? Optional.empty() : Optional.of(playerAnimation);

    }
}
