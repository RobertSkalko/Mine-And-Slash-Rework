package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.saveclasses.unit.ResourceType;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.world.entity.LivingEntity;

public class SpendResourceEvent extends EffectEvent {

    public static String ID = "on_spend_resource";

    @Override
    public String GUID() {
        return ID;
    }

    // spell is needed so support gems work
    public SpendResourceEvent(LivingEntity en, Spell spell, ResourceType resource, float amount) {
        super(amount, en, en);
        this.data.setString(EventData.RESOURCE_TYPE, resource.name());

        if (spell != null) {
            this.data.setString(EventData.SPELL, spell.GUID());
        }
    }

    @Override
    public String getName() {
        return "Spend Resource Event";
    }

    @Override
    protected void activate() {

        if (data.isCanceled()) {
            return;
        }

        float num = data.getNumber();

        this.targetData.getResources().spend(target, data.getResourceType(), num);

    }
}
