package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.bases.ICommonPlayerCap;
import com.robertx22.age_of_exile.capability.player.data.StatPointsData;
import com.robertx22.age_of_exile.capability.player.data.TeamData;
import com.robertx22.age_of_exile.mmorpg.SlashRef;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.saveclasses.perks.TalentsData;
import com.robertx22.library_of_exile.components.forge.BaseProvider;
import com.robertx22.library_of_exile.components.forge.BaseStorage;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class RPGPlayerData implements ICommonPlayerCap {

    public static final ResourceLocation RESOURCE = new ResourceLocation(SlashRef.MODID, "player_data");

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof PlayerEntity) {
                event.addCapability(RESOURCE, new Provider((PlayerEntity) event.getObject()));
            }
        }
    }

    public static class Storage implements BaseStorage<RPGPlayerData> {

    }

    public static class Provider extends BaseProvider<RPGPlayerData, PlayerEntity> {
        public Provider(PlayerEntity owner) {
            super(owner);
        }

        @Override
        public RPGPlayerData newDefaultImpl(PlayerEntity owner) {
            return new RPGPlayerData(owner);
        }

        @Override
        public Capability<RPGPlayerData> dataInstance() {
            return Data;
        }
    }

    @CapabilityInject(RPGPlayerData.class)
    public static final Capability<RPGPlayerData> Data = null;
    // stupid bloatware

    private static final String TEAM_DATA = "teams";
    private static final String TALENTS_DATA = "talents";
    private static final String STAT_POINTS = "stat_points";
    private static final String DEATH_STATS = "death_stats";
  
    PlayerEntity player;

    public TeamData team = new TeamData();
    public TalentsData talents = new TalentsData();
    public StatPointsData statPoints = new StatPointsData();
    public DeathStatsData deathStats = new DeathStatsData();

    public RPGPlayerData(PlayerEntity player) {
        this.player = player;
    }


    @Override
    public CompoundNBT saveToNBT() {

        CompoundNBT nbt = new CompoundNBT();

        LoadSave.Save(team, nbt, TEAM_DATA);
        LoadSave.Save(talents, nbt, TALENTS_DATA);
        LoadSave.Save(statPoints, nbt, STAT_POINTS);
        LoadSave.Save(deathStats, nbt, DEATH_STATS);

        return nbt;
    }

    @Override
    public void loadFromNBT(CompoundNBT nbt) {

        this.team = loadOrBlank(TeamData.class, new TeamData(), nbt, TEAM_DATA, new TeamData());
        this.talents = loadOrBlank(TalentsData.class, new TalentsData(), nbt, TALENTS_DATA, new TalentsData());
        this.statPoints = loadOrBlank(StatPointsData.class, new StatPointsData(), nbt, STAT_POINTS, new StatPointsData());
        this.deathStats = loadOrBlank(DeathStatsData.class, new DeathStatsData(), nbt, DEATH_STATS, new DeathStatsData());

    }

    public static <OBJ> OBJ loadOrBlank(Class theclass, OBJ newobj, CompoundNBT nbt, String loc, OBJ blank) {
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
