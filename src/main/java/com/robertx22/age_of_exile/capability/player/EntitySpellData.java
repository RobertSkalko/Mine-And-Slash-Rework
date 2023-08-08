package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.database.data.game_balance_config.GameBalanceConfig;
import com.robertx22.age_of_exile.database.data.spell_school.SpellSchool;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.ExactStatData;
import com.robertx22.age_of_exile.saveclasses.gearitem.gear_bases.IApplyableStats;
import com.robertx22.age_of_exile.saveclasses.skill_gem.SkillGemData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellsData;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.MiscStatCtx;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.age_of_exile.uncommon.datasaving.StackSaving;
import com.robertx22.library_of_exile.components.ICap;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;


public class EntitySpellData {

    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "spells");
    public static Capability<SpellCap> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
    });


    public static SpellCap get(LivingEntity entity) {
        return entity.getCapability(INSTANCE)
                .orElse(null);
    }


    private static final String PLAYER_SPELL_DATA = "player_spells_data";
    private static final String GEMS = "gems";


    public abstract static class ISpellsCap implements ICap, IApplyableStats {

        public abstract Spell getSpellByNumber(int key);

        public abstract void reset();

        public abstract int getFreeSpellPoints();

        public abstract boolean canLearn(SpellSchool school, Spell spell);


        public abstract List<Spell> getSpells();

        public abstract SpellCastingData getCastingData();

        public abstract SpellsData getSpellsData();


        public abstract void onSpellHitTarget(Entity spellEntity, LivingEntity target);

        public abstract boolean alreadyHit(Entity spellEntity, LivingEntity target);

        public abstract int getLevelOf(String id);

    }

    public static class SpellCap extends ISpellsCap {

        final transient LazyOptional<SpellCap> supp = LazyOptional.of(() -> this);

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == INSTANCE) {
                return supp.cast();
            }
            return LazyOptional.empty();

        }

        SpellCastingData spellCastingData = new SpellCastingData();

        SpellsData spellData = new SpellsData();

        transient LivingEntity entity;

        public SpellCap(LivingEntity entity) {
            this.entity = entity;
        }

        @Override
        public CompoundTag serializeNBT() {

            CompoundTag nbt = new CompoundTag();

            try {
                LoadSave.Save(spellCastingData, nbt, PLAYER_SPELL_DATA);
                LoadSave.Save(spellData, nbt, GEMS);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return nbt;

        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {

            try {
                this.spellCastingData = LoadSave.Load(SpellCastingData.class, new SpellCastingData(), nbt, PLAYER_SPELL_DATA);

                if (spellCastingData == null) {
                    spellCastingData = new SpellCastingData();
                }

                this.spellData = LoadSave.Load(SpellsData.class, new SpellsData(), nbt, GEMS);

                if (spellData == null) {
                    spellData = new SpellsData();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public Spell getSpellByNumber(int key) {


            if (this.entity instanceof Player p) {
                ItemStack stack = Load.playerRPGData(p).getSkillGemInventory().getHotbarGem(key).getSkill();
                // todo eventually remove this
                SkillGemData data = StackSaving.SKILL_GEM.loadFrom(stack);
                if (data != null) {
                    return data.getSpell();
                }
            }

            return null;
        }

        @Override
        public void reset() {
            this.spellData = new SpellsData();
        }

        public int getSpentSpellPoints() {
            int total = 0;
            for (Integer x : this.spellData.allocated_lvls.values()) {
                total += x;
            }
            return total;
        }

        @Override
        public int getFreeSpellPoints() {
            return (int) (GameBalanceConfig.get().SPELL_POINTS_PER_LEVEL * Load.Unit(entity)
                    .getLevel()) - getSpentSpellPoints();
        }

        @Override
        public boolean canLearn(SpellSchool school, Spell spell) {
            if (getFreeSpellPoints() < 1) {
                return false;
            }
            if (!school.isLevelEnoughForSpell(entity, spell)) {
                return false;
            }
            if (spellData.schools.size() > 1) {
                if (!spellData.schools.contains(school.GUID())) {
                    return false;
                }
            }
            if (spellData.allocated_lvls.getOrDefault(spell.GUID(), 0) >= spell.getMaxLevel()) {
                return false;
            }

            return true;
        }


        @Override
        public List<Spell> getSpells() {
            if (entity instanceof Player p) {
                return Load.playerRPGData(p).getSkillGemInventory().getAllSkillGems().stream().map(x -> x.getSpell()).collect(Collectors.toList());
            }
            return Arrays.asList();
        }

        @Override
        public SpellCastingData getCastingData() {
            return this.spellCastingData;
        }

        @Override
        public SpellsData getSpellsData() {
            return this.spellData;
        }

        public HashMap<UUID, List<UUID>> mobsHit = new HashMap<>();

        @Override
        public void onSpellHitTarget(Entity spellEntity, LivingEntity target) {

            UUID id = target.getUUID();

            UUID key = spellEntity.getUUID();

            if (!mobsHit.containsKey(key)) {
                mobsHit.put(key, new ArrayList<>());
            }
            mobsHit.get(key)
                    .add(id);

            if (mobsHit.size() > 1000) {
                mobsHit.clear();
            }

        }

        @Override
        public boolean alreadyHit(Entity spellEntity, LivingEntity target) {
            // this makes sure piercing projectiles hit target only once and then pass through
            // i can replace this with an effect that tags them too

            UUID key = spellEntity.getUUID();

            if (!mobsHit.containsKey(key)) {
                return false;
            }
            return mobsHit.get(key)
                    .contains(target.getUUID());
        }

        @Override
        public int getLevelOf(String id) {

            if (entity instanceof Player p) {
                var gem = Load.playerRPGData(p).getSkillGemInventory().getSpellGem(ExileDB.Spells().get(id));
                if (gem != null && gem.getSkillData() != null) {
                    return (int) (gem.getSkillData().perc / 10F); // todo make sure this works
                }
            }

            return 1;
        }

        @Override
        public List<StatContext> getStatAndContext(LivingEntity en) {

            List<StatContext> ctxs = new ArrayList<>();

            List<ExactStatData> stats = new ArrayList<>();

            ctxs.add(new MiscStatCtx(stats));

            return ctxs;
        }

        @Override
        public String getCapIdForSyncing() {
            return "spells";
        }

    }

}
