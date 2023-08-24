package com.robertx22.age_of_exile.uncommon.effectdatas;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.data.spells.entities.CalculatedSpellData;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.effectdatas.rework.EventData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class SpellStatsCalculationEvent extends EffectEvent {
    public static String ID = "on_spell_stat_calc";

    @Override
    public String GUID() {
        return ID;
    }

    int lvl;

    public CalculatedSpellData savedData;

    public SpellStatsCalculationEvent(LivingEntity caster, String spellid) {
        super(caster, caster);

        Spell spell = ExileDB.Spells()
                .get(spellid);


        this.savedData = create(Load.Unit(caster).getLevel(), caster, spell);
        this.lvl = Load.Unit(caster).getLevel();


        this.data.setString(EventData.STYLE, spell.config.getStyle().id);

        this.data.setString(EventData.SUMMON_TYPE, spell.config.summonType.id);

        this.data.setString(EventData.SPELL, spellid);

        float manamultilvl = GameBalanceConfig.get().MANA_COST_SCALING.getMultiFor(lvl);
        if (caster instanceof Player p) {
            var gem = Load.player(p).getSkillGemInventory().getSpellGem(spell);
            if (gem != null) {
                manamultilvl *= gem.getManaCostMulti();
            }
        }
        this.data.setupNumber(EventData.CAST_TICKS, spell.config.getCastTimeTicks());
        this.data.setupNumber(EventData.MANA_COST, manamultilvl * spell.config.mana_cost.getValue(caster, spell));
        this.data.setupNumber(EventData.COOLDOWN_TICKS, spell.config.cooldown_ticks);
        this.data.setupNumber(EventData.PROJECTILE_SPEED_MULTI, 1F);
        this.data.setupNumber(EventData.DURATION_MULTI, 1F);
        this.data.setupNumber(EventData.AREA_MULTI, 1);

        // todo test spells like summon duration multi etc
    }


    @Override
    protected void activate() {

        int cd = (int) Mth.clamp(data.getNumber(EventData.COOLDOWN_TICKS).number, getSpell().config.cooldown_ticks * 0.2D, 1000000);
        this.data.getNumber(EventData.COOLDOWN_TICKS).number = cd; // cap it to 80% cooldown

        this.savedData.data = data;
    }

    private CalculatedSpellData create(int lvl, LivingEntity caster, Spell spell) {
        Objects.requireNonNull(caster);

        CalculatedSpellData data = new CalculatedSpellData(this);
        data.spell_id = spell.GUID();
        data.lvl = lvl;
        data.caster_uuid = caster.getUUID().toString();

        return data;
    }


}
