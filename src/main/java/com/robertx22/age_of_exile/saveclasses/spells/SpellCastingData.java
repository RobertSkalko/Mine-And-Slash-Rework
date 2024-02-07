package com.robertx22.age_of_exile.saveclasses.spells;

import com.robertx22.age_of_exile.capability.entity.EntityData;
import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.profession.ExplainedResult;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.database.data.spells.spell_classes.bases.SpellCastContext;
import com.robertx22.age_of_exile.database.data.stats.types.LearnSpellStat;
import com.robertx22.age_of_exile.database.data.stats.types.MaxAllSpellLevels;
import com.robertx22.age_of_exile.database.data.stats.types.MaxSpellLevel;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.MMORPG;
import com.robertx22.age_of_exile.saveclasses.item_classes.GearItemData;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.uncommon.MathHelper;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.age_of_exile.uncommon.effectdatas.SpendResourceEvent;
import com.robertx22.age_of_exile.uncommon.localization.Chats;
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

    public HashMap<Integer, String> hotbar = new HashMap<>();


    public static class HotbarSpellData {
        public Spell spell;
        public int hotbarkey;

        public HotbarSpellData(Spell spell, int hotbarkey) {
            this.spell = spell;
            this.hotbarkey = hotbarkey;
        }
    }

    public int keyOfSpell(String spell) {

        for (Map.Entry<Integer, String> en : hotbar.entrySet()) {
            if (en.getValue().equals(spell)) {
                return en.getKey();
            }
        }
        return -1;
    }

    public List<HotbarSpellData> getAllHotbarSpellsInfo() {
        List<HotbarSpellData> list = new ArrayList<>();
        for (Integer i : hotbar.keySet()) {
            String spell = hotbar.getOrDefault(i, "");
            if (ExileDB.Spells().isRegistered(spell)) {
                list.add(new HotbarSpellData(ExileDB.Spells().get(spell), i));
            }
        }
        return list;
    }

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

    public void calcSpellLevels(Unit unit) {
        resetSpells();


        unit.getStats().stats.values()
                .forEach(x -> {
                    if (x.GetStat() instanceof LearnSpellStat learn) {
                        addSpell(new SpellCastingData.InsertedSpell(learn.spell.GUID(), (int) x.getValue()));
                    }
                });

        // todo this might be a bit perf heavy?
        unit.getStats().stats.values().forEach(x -> {
            if (x.GetStat() instanceof MaxSpellLevel max) {
                for (InsertedSpell spell : this.spells) {
                    if (spell.getSpell().config.tags.contains(max.tag)) {
                        spell.bonus_ranks += x.getValue();
                    }
                }
            } else if (x.GetStat() instanceof MaxAllSpellLevels) {
                for (InsertedSpell spell : this.spells) {
                    spell.bonus_ranks += x.getValue();
                }
            }
        });
        // caps the bonus ranks to config value max
        for (InsertedSpell spell : this.spells) {
            spell.bonus_ranks = MathHelper.clamp(spell.bonus_ranks, 0, GameBalanceConfig.get().MAX_BONUS_SPELL_LEVELS);
            spell.rank += spell.bonus_ranks;
        }

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
        public int rankBeforePlusSkills = 0;
        public int rank;
        public int bonus_ranks = 0;

        public Spell getSpell() {
            return ExileDB.Spells().get(id);
        }

        public InsertedSpell(String id, int rank) {
            this.id = id;
            this.rank = rank;
            this.rankBeforePlusSkills = rank;
        }

        public SkillGemData getData() {

            if (id.isEmpty()) {
                return null;
            }

            SkillGemData data = new SkillGemData();
            data.id = id;
            data.type = SkillGemData.SkillGemType.SKILL;

            data.links = 0;

            data.perc = (int) ((rankBeforePlusSkills / (float) data.getSpell().max_lvl) * 100);

            if (rankBeforePlusSkills > 1) {

                int total = rankBeforePlusSkills - 1;

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

    public ExplainedResult canCast(Spell spell, Player player) {

        if (player.level().isClientSide) {
            return ExplainedResult.failure(Component.literal("Client side"));
        }
        if (isCasting()) {
            return ExplainedResult.failure(Chats.ALREADY_CASTING.locName());
        }

        if (spell == null) {
            return ExplainedResult.failure(Component.literal("Trying to cast NULL Spell, this shouldn't happen"));
        }

        if (Load.Unit(player).getCooldowns().isOnCooldown(spell.GUID())) {
            // dont spam chat with no cd msgs for stuff like fireball
            if (Load.Unit(player).getCooldowns().getCooldownTicks(spell.GUID()) > 40) {
                return ExplainedResult.failure(Chats.SPELL_IS_ON_CD.locName());
            }
            return ExplainedResult.silentlyFail();
        }

        if (player.isCreative()) {
            return ExplainedResult.success();
        }

        if (spell.GUID().contains("test")) {
            if (!MMORPG.RUN_DEV_TOOLS) {
                return ExplainedResult.failure(Chats.USING_TEST_SPELL.locName());
            }
        }

        if (spell.config.charges > 0) {
            if (!charges.hasCharge(spell.config.charge_name)) {
                return ExplainedResult.failure(Chats.NO_CHARGES.locName());
            }
        }

        SpellCastContext ctx = new SpellCastContext(player, 0, spell);


        EntityData data = Load.Unit(player);

        if (data != null) {

            if (!spell.isAllowedInDimension(player.level())) {
                return ExplainedResult.failure(Chats.NOT_IN_THIS_DIMENSION.locName());
            }

            SpendResourceEvent rctx = spell.getManaCostCtx(ctx);
            SpendResourceEvent ectx = spell.getManaCostCtx(ctx);


            if (data.getResources().hasEnough(rctx) && data.getResources().hasEnough(ectx)) {

                GearItemData wep = StackSaving.GEARS.loadFrom(ctx.caster.getMainHandItem());

                if (wep == null) {
                    return ExplainedResult.failure(Chats.NOT_MNS_WEAPON.locName());
                }

                if (!spell.getConfig().castingWeapon.predicate.predicate.test(player)) {
                    return ExplainedResult.failure(Chats.WRONG_CASTING_WEAPON.locName());
                }

                if (!wep.canPlayerWear(ctx.data)) {
                    return ExplainedResult.failure(Chats.WEAPON_REQ_NOT_MET.locName());
                }

                return ExplainedResult.success();
            } else {
                if (player instanceof ServerPlayer) {
                    Packets.sendToClient((Player) player, new NoManaPacket());
                    return ExplainedResult.failure(Chats.NO_MANA.locName());
                }
            }
        }
        return ExplainedResult.silentlyFail();

    }

    public void setCooldownOnCasted(SpellCastContext ctx) {

        int cd = ctx.spell.getCooldownTicks(ctx);

        ctx.data.getCooldowns().setOnCooldown(ctx.spell.GUID(), cd);

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

    }

    public void onSpellCast(SpellCastContext ctx) {

        setCooldownOnCasted(ctx);

        this.casting = false;

        if (ctx.caster instanceof ServerPlayer) {
            Load.Unit(ctx.caster).sync.setDirty();
        }
    }

}
