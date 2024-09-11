package com.robertx22.mine_and_slash.database.data.spells.components;

import com.robertx22.mine_and_slash.a_libraries.player_animations.AnimationHolder;

public class SpellAnimationData {

    public String id = "";

    public SpellAnimationData(String id) {
        this.id = id;
    }

    public SpellAnimationData(AnimationHolder anim) {
        this.id = anim.id;
    }

    public AnimationHolder getAnim() {
        if (id == null || id.isEmpty()) {
            return AnimationHolder.none();
        }
        return new AnimationHolder(id);
    }
}
