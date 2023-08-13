package com.robertx22.age_of_exile.database.data.spells.components.actions;

import com.robertx22.age_of_exile.database.data.spells.spell_classes.SpellCtx;
import net.minecraft.world.entity.Entity;

public enum PositionSource {
    CASTER() {
        @Override
        public Entity get(SpellCtx ctx) {
            return ctx.caster;
        }
    }, SOURCE_ENTITY {
        @Override
        public Entity get(SpellCtx ctx) {
            return ctx.sourceEntity;
        }
    }, TARGET() {
        @Override
        public Entity get(SpellCtx ctx) {
            return ctx.target;
        }
    };

    public abstract Entity get(SpellCtx ctx);
}
