package com.robertx22.age_of_exile.aoe_data.database.spells.builders;

import com.robertx22.age_of_exile.aoe_data.database.stats.base.EffectCtx;
import com.robertx22.age_of_exile.database.data.spells.components.ComponentPart;
import com.robertx22.age_of_exile.database.data.spells.components.actions.SpellAction;
import com.robertx22.age_of_exile.database.data.spells.components.conditions.EffectCondition;
import com.robertx22.age_of_exile.database.data.spells.components.selectors.BaseTargetSelector;
import com.robertx22.age_of_exile.database.data.value_calc.ValueCalculation;
import com.robertx22.age_of_exile.uncommon.enumclasses.Elements;
import com.robertx22.age_of_exile.uncommon.utilityclasses.AllyOrEnemy;
import com.robertx22.age_of_exile.uncommon.utilityclasses.EntityFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

// todo replace all spells with these builders
public class DamageBuilder {

    private EntityFinder.SelectionType selection = EntityFinder.SelectionType.RADIUS;
    private Elements element;

    private ValueCalculation calc;
    private AllyOrEnemy pred = AllyOrEnemy.enemies;
    private float radius = 1;
    private float distance = 1;

    private List<Consumer<ComponentPart>> edits = new ArrayList<>();

    public static DamageBuilder target(Elements ele, ValueCalculation calc) {
        DamageBuilder b = new DamageBuilder();
        b.element = ele;
        b.selection = EntityFinder.SelectionType.TARGET;
        b.calc = calc;
        return b;
    }

    public static DamageBuilder radius(Elements ele, float radius, ValueCalculation calc) {
        DamageBuilder b = new DamageBuilder();
        b.element = ele;
        b.radius = radius;
        b.selection = EntityFinder.SelectionType.RADIUS;
        b.calc = calc;
        return b;
    }

    public static DamageBuilder front(Elements ele, float radius, float distance, ValueCalculation calc) {
        DamageBuilder b = new DamageBuilder();
        b.element = ele;
        b.distance = distance;
        b.radius = radius;
        b.selection = EntityFinder.SelectionType.IN_FRONT;
        b.calc = calc;
        return b;
    }

    public DamageBuilder onAllies() {
        this.pred = AllyOrEnemy.allies;
        return this;
    }

    public DamageBuilder onEnemies() {
        this.pred = AllyOrEnemy.enemies;
        return this;
    }

    public DamageBuilder onEntitiesWithMnsEffect(EffectCtx eff) {
        this.edits.add(x -> x.en_preds.add(EffectCondition.HAS_MNS_EFFECT.create(eff)));
        return this;
    }

    public ComponentPart build() {
        ComponentPart c = new ComponentPart();

        c.acts.add(SpellAction.DEAL_DAMAGE.create(calc, element));

        if (selection == EntityFinder.SelectionType.TARGET) {
            c.targets.add(BaseTargetSelector.TARGET.create());
        }
        if (selection == EntityFinder.SelectionType.RADIUS) {
            c.targets.add(BaseTargetSelector.AOE.create((double) radius, this.selection, pred));
        }
        if (selection == EntityFinder.SelectionType.IN_FRONT) {
            c.targets.add(BaseTargetSelector.IN_FRONT.create((double) radius, (double) this.distance, pred));
        }

        for (Consumer<ComponentPart> edit : this.edits) {
            edit.accept(c);
        }
        return c;
    }
}

