package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.player.data.PlayerConfigData;
import com.robertx22.age_of_exile.capability.player.data.PlayerMapData;
import com.robertx22.age_of_exile.capability.player.data.StatPointsData;
import com.robertx22.age_of_exile.capability.player.data.TeamData;
import com.robertx22.age_of_exile.capability.player.helper.GemInventoryHelper;
import com.robertx22.age_of_exile.capability.player.helper.JewelInvHelper;
import com.robertx22.age_of_exile.capability.player.helper.MyInventory;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.saveclasses.perks.TalentsData;
import com.robertx22.age_of_exile.saveclasses.spells.AscendancyClassesData;
import com.robertx22.age_of_exile.saveclasses.spells.SpellCastingData;
import com.robertx22.library_of_exile.components.ICap;
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


    private static final String TEAM_DATA = "teams";
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

    transient Player player;

    public TeamData team = new TeamData();
    public TalentsData talents = new TalentsData();
    public StatPointsData statPoints = new StatPointsData();
    public DeathStatsData deathStats = new DeathStatsData();
    public PlayerMapData map = new PlayerMapData();
    public AscendancyClassesData ascClass = new AscendancyClassesData();
    public SpellCastingData spellCastingData = new SpellCastingData();
    public PlayerConfigData config = new PlayerConfigData();

    private MyInventory skillGemInv = new MyInventory(GemInventoryHelper.TOTAL_SLOTS);
    private MyInventory auraInv = new MyInventory(GemInventoryHelper.TOTAL_AURAS);
    private MyInventory jewelsInv = new MyInventory(9);


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
        LoadSave.Save(statPoints, nbt, STAT_POINTS);
        LoadSave.Save(deathStats, nbt, DEATH_STATS);
        LoadSave.Save(map, nbt, MAP);
        LoadSave.Save(ascClass, nbt, ASC);
        LoadSave.Save(spellCastingData, nbt, CAST);
        LoadSave.Save(config, nbt, CONFIG);

        nbt.put(GEMS, skillGemInv.createTag());
        nbt.put(AURAS, auraInv.createTag());
        nbt.put(JEWELS, jewelsInv.createTag());

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.team = loadOrBlank(TeamData.class, new TeamData(), nbt, TEAM_DATA, new TeamData());
        this.talents = loadOrBlank(TalentsData.class, new TalentsData(), nbt, TALENTS_DATA, new TalentsData());
        this.statPoints = loadOrBlank(StatPointsData.class, new StatPointsData(), nbt, STAT_POINTS, new StatPointsData());
        this.deathStats = loadOrBlank(DeathStatsData.class, new DeathStatsData(), nbt, DEATH_STATS, new DeathStatsData());
        this.map = loadOrBlank(PlayerMapData.class, new PlayerMapData(), nbt, MAP, new PlayerMapData());
        this.ascClass = loadOrBlank(AscendancyClassesData.class, new AscendancyClassesData(), nbt, ASC, new AscendancyClassesData());
        this.spellCastingData = loadOrBlank(SpellCastingData.class, new SpellCastingData(), nbt, CAST, new SpellCastingData());
        this.config = loadOrBlank(PlayerConfigData.class, new PlayerConfigData(), nbt, CONFIG, new PlayerConfigData());

        skillGemInv.fromTag(nbt.getList(GEMS, 10)); // todo
        auraInv.fromTag(nbt.getList(AURAS, 10)); // todo
        jewelsInv.fromTag(nbt.getList(JEWELS, 10)); // todo

    }

    public GemInventoryHelper getSkillGemInventory() {
        return new GemInventoryHelper(skillGemInv, auraInv);
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
