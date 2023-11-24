package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.utilityclasses.OnScreenMessageUtils;
import com.robertx22.age_of_exile.vanilla_mc.packets.NoManaPacket;
import com.robertx22.library_of_exile.main.Packets;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpellCastingData {

    private HashMap<Integer, String> hotbar = new HashMap<>();

    public List<InsertedSpell> getAllHotbarSpells() {
        List<InsertedSpell> list = new ArrayList<>();
        for (Integer i : hotbar.keySet()) {
            list.add(getSpellData(i));
        }

        list.removeIf(x -> x == null || x.getData() == null);
        return list;

    }

    public List<InsertedSpell> spells = new ArrayList<>();


    public void setHotbar(int slot, String spell) {

        for (Map.Entry<Integer, String> en : hotbar.entrySet()) {
            if (en.getValue().equals(spell)) {
                hotbar.put(en.getKey(), "");
            }
        }

        hotbar.put(slot, spell);
    }

    public void resetSpells() {
        spells.clear();
    }

    public void addSpell(InsertedSpell spell) {
        spells.add(spell);
    }

    public InsertedSpell getSpellData(int slot) {
        String id = getSpellId(slot);
        return spells.stream().filter(x -> x.id.equals(id)).findAny().orElse(new InsertedSpell("", 0));
    }

    public InsertedSpell getSpellData(String id) {
        return spells.stream().filter(x -> x.id.equals(id)).findAny().orElse(new InsertedSpell("", 0));
    }


    public String getSpellId(int slot) {

        return hotbar.getOrDefault(slot, "");
    }


    public static class InsertedSpell {

        public String id;
        public int rank;

        public InsertedSpell(String id, int rank) {
            this.id = id;
            this.rank = rank;
        }

        public SkillGemData getData() {

            if (id.isEmpty()) {
                return null;
            }

            SkillGemData data = new SkillGemData();
            data.id = id;
            data.type = SkillGemData.SkillGemType.SKILL;

            data.links = 0;

            data.perc = (int) ((rank / (float) data.getSpell().max_lvl) * 100);

            if (rank > 1) {

                int total = rank - 1;

                while (total > 2) {
                    total -= 3;
                    data.links++;
                }

            }

            return data;
        }
    }

    public int castTickLeft = 0;
    public int castTicksDone = 0;
    public int spellTotalCastTicks = 0;
    public CalculatedSpellData calcSpell = null;
    public Boolean casting = false;
    public ChargeData charges = new ChargeData();


    public void cancelCast(LivingEntity entity) {
        try {
            if (isCasting()) {
                SpellCastContext ctx = new SpellCastContext(entity, 0, getSpellBeingCast());

                Spell spell = getSpellBeingCast();
                if (spell != null) {
                    int cd = ctx.spell.getCooldownTicks(ctx);
                    Load.Unit(entity)
                            .getCooldowns()
                            .setOnCooldown(spell.GUID(), cd);

                }

                this.calcSpell = null;
                castTickLeft = 0;
                spellTotalCastTicks = 0;
                castTicksDone = 0;
                this.casting = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isCasting() {
        return calcSpell != null && casting && ExileDB.Spells()
                .isRegistered(calcSpell.spell_id);
    }

    transient static Spell lastSpell = null;

    public void onTimePass(LivingEntity entity) {

        try {

            if (isCasting()) {
                Spell spell = this.calcSpell.getSpell();

                SpellCastContext ctx = new SpellCastContext(entity, castTicksDone, spell);

                if (spell != null && ExileDB.Spells()
                        .isRegistered(spell)) {
                    spell.onCastingTick(ctx);
                }

                tryCast(ctx);

                lastSpell = spell;

                castTickLeft--;
                castTicksDone++;

                if (castTickLeft < 0) {
                    this.calcSpell = null;
                }
            } else {

                lastSpell = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.cancelCast(entity);
            // cancel when error, cus this is called on tick, so it doesn't crash servers when 1 spell fails
        }
    }

    public List<String> getSpellsOnCooldown(LivingEntity en) {
        return Load.Unit(en)
                .getCooldowns()
                .getAllSpellsOnCooldown();
    }

    public void setToCast(SpellCastContext ctx) {

        this.calcSpell = ctx.calcData;
        this.castTickLeft = ctx.spell.getCastTimeTicks(ctx);
        this.spellTotalCastTicks = this.castTickLeft;
        this.castTicksDone = 0;
        this.casting = true;
    }

    public void tryCast(SpellCastContext ctx) {

        if (getSpellBeingCast() != null) {
            if (castTickLeft <= 0) {
                Spell spell = getSpellBeingCast();

                int timesToCast = ctx.spell.getConfig().times_to_cast;

                if (timesToCast == 1) {
                    spell.cast(ctx);
                }

                onSpellCast(ctx);
                this.calcSpell = null;

            }
        }

    }

    public Spell getSpellBeingCast() {

        if (calcSpell != null) {
            return calcSpell.getSpell();
        }


        return null;
    }

    public boolean canCast(Spell spell, Player player) {

        if (player.level().isClientSide) {
            return false;
        }

        if (isCasting()) {
            return false;
        }

        if (spell == null) {
            return false;
        }

        if (Load.Unit(player)
                .getCooldowns()
                .isOnCooldown(spell.GUID())) {
            return false;
        }

        if (player.isCreative()) {
            return true;
        }

        if (spell.GUID()
                .contains("test")) {
            if (!MMORPG.RUN_DEV_TOOLS) {
                return false;
            }
        }

        if (spell.config.charges > 0) {
            if (!charges.hasCharge(spell.config.charge_name)) {
                return false;
            }
        }

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);

        LivingEntity caster = ctx.caster;

        if (caster instanceof Player == false) {
            return true;
        }

        if (((Player) caster).isCreative()) {
            return true;
        }

        EntityData data = Load.Unit(caster);

        if (data != null) {

            if (!spell.isAllowedInDimension(caster.level())) {
                if (caster instanceof Player) {
                    ((Player) caster).displayClientMessage(Component.literal("You feel an entity watching you. [Spell can not be casted in this dimension]"), false);
                }
                return false;
            }

            SpendResourceEvent rctx = spell.getManaCostCtx(ctx);
            SpendResourceEvent ectx = spell.getManaCostCtx(ctx);

            if (data.getResources().hasEnough(rctx) && data.getResources().hasEnough(ectx)) {

                if (!spell.getConfig().castingWeapon.predicate.predicate.test(caster)) {
                    return false;
                }


                GearItemData wep = StackSaving.GEARS.loadFrom(ctx.caster.getMainHandItem());

                if (wep == null) {
                    return false;
                }

                if (!wep.canPlayerWear(ctx.data)) {
                    if (ctx.caster instanceof Player) {
                        OnScreenMessageUtils.sendMessage((ServerPlayer) ctx.caster, Component.literal(""), Component.literal("Weapon requirements not met"));
                    }
                    return false;
                }

                return true;
            } else {
                if (caster instanceof ServerPlayer) {
                    Packets.sendToClient((Player) caster, new NoManaPacket());
                }
            }
        }

        return false;

    }

    public void onSpellCast(SpellCastContext ctx) {

        int cd = ctx.spell.getCooldownTicks(ctx);

        ctx.data.getCooldowns()
                .setOnCooldown(ctx.spell.GUID(), cd);

        if (ctx.spell.config.charges > 0) {
            if (ctx.caster instanceof Player) {
                this.charges.spendCharge((Player) ctx.caster, ctx.spell.config.charge_name);
            }
        }

        if (ctx.caster instanceof Player) {
            Player p = (Player) ctx.caster;
            if (p.isCreative()) {
                if (cd > 20) {
                    ctx.data.getCooldowns()
                            .setOnCooldown(ctx.spell.GUID(), 20);

                }
            }
        }

        this.casting = false;

        if (ctx.caster instanceof ServerPlayer) {
            Load.Unit(ctx.caster)
                    .syncToClient((Player) ctx.caster);
        }
    }

}
