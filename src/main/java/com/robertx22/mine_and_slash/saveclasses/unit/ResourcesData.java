package com.robertx22.mine_and_slash.saveclasses.unit;

import com.robertx22.mine_and_slash.capability.entity.EntityData;
import com.robertx22.mine_and_slash.config.forge.ServerContainer;
import com.robertx22.mine_and_slash.database.data.stats.types.resources.energy.Energy;
import com.robertx22.mine_and_slash.uncommon.datasaving.Load;
import com.robertx22.mine_and_slash.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.mine_and_slash.uncommon.enumclasses.ModType;
import com.robertx22.mine_and_slash.uncommon.utilityclasses.HealthUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ResourcesData {

    public ResourcesData() {

    }

    private enum Use {
        SPEND,
        RESTORE
    }

    private float mana = 0;
    private float magic_shield = 0;
    private float energy = 0;
    private float blood = 0;


    public float getMana() {
        return mana;
    }

    public float getEnergy() {
        return energy;
    }

    public float getBlood() {
        return blood;
    }

    public float getMagicShield() {
        return magic_shield;
    }

    public void onTickBlock(Player player, int ticks) {
        if (player.isBlocking()) {
            float cost = Energy.getInstance().scale(ModType.FLAT, ServerContainer.get().BLOCK_COST.get().floatValue(), Load.Unit(player).getLevel());
            SpendResourceEvent event = new SpendResourceEvent(player, null, ResourceType.energy, cost * ticks);
            event.calculateEffects();
            event.Activate();
            this.cap(player, ResourceType.energy);
        }

    }


    public float getModifiedValue(LivingEntity en, ResourceType type, Use use, float amount) {
        if (use == Use.RESTORE) {
            return get(en, type) + amount;
        } else {
            return get(en, type) - amount;
        }

    }

    public float get(LivingEntity en, ResourceType type) {
        if (type == ResourceType.mana) {
            return mana;
        }
        if (type == ResourceType.blood) {
            return blood;
        } else if (type == ResourceType.energy) {
            return energy;
        } else if (type == ResourceType.magic_shield) {
            return magic_shield;
        } else if (type == ResourceType.health) {
            return HealthUtils.getCurrentHealth(en);
        }
        return 0;

    }

    public float getMax(LivingEntity en, ResourceType type) {
        EntityData data = Load.Unit(en);

        if (type == ResourceType.mana) {
            return data.getUnit()
                    .manaData()
                    .getValue();

        } else if (type == ResourceType.blood) {
            return data.getUnit()
                    .bloodData()
                    .getValue();
        } else if (type == ResourceType.energy) {
            return data.getUnit()
                    .energyData()
                    .getValue();
        } else if (type == ResourceType.magic_shield) {
            return data.getUnit()
                    .magicShieldData()
                    .getValue();
        } else if (type == ResourceType.health) {
            return HealthUtils.getMaxHealth(en);
        }
        return 0;

    }

    public void spend(LivingEntity en, ResourceType type, float amount) {
        modify(en, Use.SPEND, type, amount);
    }

    public void restore(LivingEntity en, ResourceType type, float amount) {
        modify(en, Use.RESTORE, type, amount);
    }

    public void modify(LivingEntity en, Use use, ResourceType type, float amount) {
        if (amount == 0) {
            return;
        }
        if (type == ResourceType.mana) {
            mana = getModifiedValue(en, type, use, amount);
        } else if (type == ResourceType.blood) {
            blood = getModifiedValue(en, type, use, amount);
        } else if (type == ResourceType.energy) {
            energy = getModifiedValue(en, type, use, amount);
        } else if (type == ResourceType.magic_shield) {
            magic_shield = getModifiedValue(en, type, use, amount);
        } else if (type == ResourceType.health) {
            if (use == Use.RESTORE) {
                HealthUtils.heal(en, amount);
            }
        }
        cap(en, type);
        sync(en);
    }

    private void cap(LivingEntity en, ResourceType type) {
        if (type == ResourceType.mana) {
            mana = Mth.clamp(mana, 0, Load.Unit(en)
                    .getMaximumResource(type));
        } else if (type == ResourceType.energy) {
            energy = Mth.clamp(energy, 0, Load.Unit(en)
                    .getMaximumResource(type));
        } else if (type == ResourceType.magic_shield) {
            magic_shield = Mth.clamp(magic_shield, 0, Load.Unit(en)
                    .getMaximumResource(type));
        } else if (type == ResourceType.blood) {
            blood = Mth.clamp(blood, 0, Load.Unit(en)
                    .getMaximumResource(type));
        }

    }

    private void sync(LivingEntity en) {
        if (en instanceof ServerPlayer) {
            Load.Unit(en).sync.setDirty();
        }
    }

    public boolean hasEnough(SpendResourceEvent ctx) {
        if (ctx.data.getNumber() <= 0) {
            return true;
        }
        return get(ctx.target, ctx.data.getResourceType()) >= ctx.data.getNumber();
    }

}
