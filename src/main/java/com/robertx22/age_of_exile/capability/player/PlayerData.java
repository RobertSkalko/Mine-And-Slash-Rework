package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.DirtySync;
import com.robertx22.age_of_exile.capability.player.data.*;
import com.robertx22.age_of_exile.capability.player.helper.GemInventoryHelper;
import com.robertx22.age_of_exile.capability.player.helper.JewelInvHelper;
import com.robertx22.age_of_exile.capability.player.helper.MyInventory;
import com.robertx22.age_of_exile.characters.CharStorageData;
import com.robertx22.age_of_exile.database.data.spells.components.Spell;
import com.robertx22.age_of_exile.gui.screens.skill_tree.buttons.PerkConnectionCache;
import com.robertx22.age_of_exile.gui.screens.stat_gui.StatCalcInfoData;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.prophecy.PlayerProphecies;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.saveclasses.perks.SchoolData;
import com.robertx22.age_of_exile.saveclasses.perks.TalentsData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellSchoolsData;
import com.robertx22.age_of_exile.saveclasses.unit.Unit;
import com.robertx22.age_of_exile.saveclasses.unit.stat_calc.StatCalculation;
import com.robertx22.age_of_exile.saveclasses.unit.stat_ctx.StatContext;
import com.robertx22.age_of_exile.uncommon.datasaving.Load;
import com.robertx22.library_of_exile.components.ICap;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.SyncPlayerCapToClient;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class PlayerData implements ICap {


    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "player_data");
    public static Capability<PlayerData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static PlayerData get(LivingEntity entity) {
        return entity.getCapability(INSTANCE)
                .orElse(null);
    }

    transient final LazyOptional<PlayerData> supp = LazyOptional.of(() -> this);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == INSTANCE) {
            return supp.cast();
        }
        return LazyOptional.empty();

    }


    @Override
    public void syncToClient(Player player) {

    }


    private static final String TEAM_DATA = "teams";
    private static final String PROPHECY = "proph";
    private static final String TALENTS_DATA = "tals";
    private static final String STAT_POINTS = "stats";
    private static final String DEATH_STATS = "death";
    private static final String GEMS = "gems";
    private static final String AURAS = "auras";
    private static final String MAP = "map";
    private static final String ASC = "asc";
    private static final String CAST = "casting";
    private static final String CONFIG = "config";
    private static final String JEWELS = "jewels";
    private static final String FAVOR = "favor";
    private static final String PROFESSIONS = "profs";
    private static final String BUFFS = "buffs";
    private static final String RESTED_XP = "rxp";
    private static final String NAME = "name";
    private static final String CHARACTERS = "chars";
    private static final String BONUS_TALENTS = "btal";

    public DirtySync playerDataSync = new DirtySync("playerdata_sync", x -> syncData());

    public transient Player player;


    public transient StatCalcInfoData ctxs = new StatCalcInfoData();

    // so players know where their stats come from in the future gui
    //ublic SavedStatCtxList ctxStats = new SavedStatCtxList();

    public TeamData team = new TeamData();
    public TalentsData talents = new TalentsData();
    public StatPointsData statPoints = new StatPointsData();
    public DeathStatsData deathStats = new DeathStatsData();
    public PlayerMapData map = new PlayerMapData();
    public SpellSchoolsData ascClass = new SpellSchoolsData();
    public PlayerProphecies prophecy = new PlayerProphecies();
    public SpellCastingData spellCastingData = new SpellCastingData();
    public PlayerConfigData config = new PlayerConfigData();
    public DeathFavorData favor = new DeathFavorData();
    public PlayerProfessionsData professions = new PlayerProfessionsData();
    public PlayerBuffData buff = new PlayerBuffData();
    public RestedExpData rested_xp = new RestedExpData();

    private MyInventory skillGemInv = new MyInventory(GemInventoryHelper.TOTAL_SLOTS);
    private MyInventory auraInv = new MyInventory(GemInventoryHelper.TOTAL_AURAS);
    private MyInventory jewelsInv = new MyInventory(9);

    public CharStorageData characters = new CharStorageData();

    public List<String> aurasOn = new ArrayList<>();

    public String name = "";

    public int bonusTalents = 0;

    public int emptyMapTicks = 0;

    public PlayerData(Player player) {
        this.player = player;
    }


    public JewelInvHelper getJewels() {
        return new JewelInvHelper(jewelsInv);
    }

    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();

        LoadSave.Save(team, nbt, TEAM_DATA);
        LoadSave.Save(talents, nbt, TALENTS_DATA);
        LoadSave.Save(prophecy, nbt, PROPHECY);
        LoadSave.Save(statPoints, nbt, STAT_POINTS);
        LoadSave.Save(deathStats, nbt, DEATH_STATS);
        LoadSave.Save(map, nbt, MAP);
        LoadSave.Save(ascClass, nbt, ASC);
        LoadSave.Save(spellCastingData, nbt, CAST);
        LoadSave.Save(config, nbt, CONFIG);
        LoadSave.Save(favor, nbt, FAVOR);
        LoadSave.Save(professions, nbt, PROFESSIONS);
        LoadSave.Save(buff, nbt, BUFFS);
        LoadSave.Save(rested_xp, nbt, RESTED_XP);
        LoadSave.Save(characters, nbt, CHARACTERS);
        // LoadSave.Save(ctxStats, nbt, "ctx");

        nbt.put(GEMS, skillGemInv.createTag());
        nbt.put(AURAS, auraInv.createTag());
        nbt.put(JEWELS, jewelsInv.createTag());

        nbt.putString(NAME, name);
        nbt.putInt(BONUS_TALENTS, bonusTalents);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.team = loadOrBlank(TeamData.class, new TeamData(), nbt, TEAM_DATA, new TeamData());
        this.prophecy = loadOrBlank(PlayerProphecies.class, new PlayerProphecies(), nbt, PROPHECY, new PlayerProphecies());
        TalentsData newTalentsData = loadOrBlank(TalentsData.class, new TalentsData(), nbt, TALENTS_DATA, new TalentsData());
        var newData = newTalentsData.getPerks().values().stream().map(SchoolData::getAllocatedPoints).collect(Collectors.toSet());
        var oldData = this.talents.getPerks().values().stream().map(SchoolData::getAllocatedPoints).collect(Collectors.toSet());
        this.talents = newTalentsData;
        //!oldData.isEmpty() to avoid the first sync when player join world, otherwise the first perk player allocated will bug
        if (!oldData.isEmpty() && !newData.equals(oldData)){
            PerkConnectionCache.canUpdate = true;

        }
        this.statPoints = loadOrBlank(StatPointsData.class, new StatPointsData(), nbt, STAT_POINTS, new StatPointsData());
        this.deathStats = loadOrBlank(DeathStatsData.class, new DeathStatsData(), nbt, DEATH_STATS, new DeathStatsData());
        this.map = loadOrBlank(PlayerMapData.class, new PlayerMapData(), nbt, MAP, new PlayerMapData());
        this.ascClass = loadOrBlank(SpellSchoolsData.class, new SpellSchoolsData(), nbt, ASC, new SpellSchoolsData());
        this.spellCastingData = loadOrBlank(SpellCastingData.class, new SpellCastingData(), nbt, CAST, new SpellCastingData());
        this.config = loadOrBlank(PlayerConfigData.class, new PlayerConfigData(), nbt, CONFIG, new PlayerConfigData());
        this.favor = loadOrBlank(DeathFavorData.class, new DeathFavorData(), nbt, FAVOR, new DeathFavorData());
        this.professions = loadOrBlank(PlayerProfessionsData.class, new PlayerProfessionsData(), nbt, PROFESSIONS, new PlayerProfessionsData());
        this.buff = loadOrBlank(PlayerBuffData.class, new PlayerBuffData(), nbt, BUFFS, new PlayerBuffData());
        this.rested_xp = loadOrBlank(RestedExpData.class, new RestedExpData(), nbt, RESTED_XP, new RestedExpData());
        this.characters = loadOrBlank(CharStorageData.class, new CharStorageData(), nbt, CHARACTERS, new CharStorageData());
        // this.ctxStats = loadOrBlank(SavedStatCtxList.class, new SavedStatCtxList(), nbt, "ctx", new SavedStatCtxList());

        skillGemInv.fromTag(nbt.getList(GEMS, 10)); // todo
        auraInv.fromTag(nbt.getList(AURAS, 10)); // todo
        jewelsInv.fromTag(nbt.getList(JEWELS, 10)); // todo

        this.name = nbt.getString(NAME);

        this.bonusTalents = nbt.getInt(BONUS_TALENTS);
        if (bonusTalents < 0) {
            bonusTalents = 0;
        }
    }

    private void syncData() {
        Packets.sendToClient(player, new SyncPlayerCapToClient(player, this.getCapIdForSyncing()));
    }

    transient HashMap<String, Unit> spellUnits = new HashMap<>();

    public Unit getSpellUnitStats(Player p, Spell spell) {
        if (!spellUnits.containsKey(spell.GUID())) {
            return Load.Unit(p).getUnit();
            // todo will this break anything
            //   spellUnits.put(spell.GUID(), getSpellStats(spell));
        }
        return spellUnits.get(spell.GUID());
    }

    public void calcSpellUnits(List<Spell> spells, List<StatContext> stats) {
        for (Spell spell : spells) {
            spellUnits.put(spell.GUID(), getSpellStats(spell, stats));
        }
    }


    private Unit getSpellStats(Spell spell, List<StatContext> stats) {
        int key = this.spellCastingData.keyOfSpell(spell.GUID());
        var unit = new Unit();
        StatCalculation.calc(unit, StatCalculation.getStatsWithoutSuppGems(this.player, Load.Unit(player), null), player, key, null);
        return unit;
    }

    public GemInventoryHelper getSkillGemInventory() {
        return new GemInventoryHelper(player, skillGemInv, auraInv);
    }

    public static <OBJ> OBJ loadOrBlank(Class theclass, OBJ newobj, CompoundTag nbt, String loc, OBJ blank) {
        try {
            OBJ data = LoadSave.Load(theclass, newobj, nbt, loc);
            if (data == null) {
                return blank;
            } else {
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blank;
    }

    @Override
    public String getCapIdForSyncing() {
        return "rpg_player_data";
    }

}
