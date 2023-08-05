package com.robertx22.age_of_exile.capability.player;

import com.robertx22.age_of_exile.capability.player.data.StatPointsData;
import com.robertx22.age_of_exile.capability.player.data.TeamData;
import com.robertx22.age_of_exile.saveclasses.DeathStatsData;
import com.robertx22.age_of_exile.saveclasses.perks.TalentsData;
import com.robertx22.library_of_exile.components.ICap;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber
public class RPGPlayerData implements ICap {


    public static final ResourceLocation RESOURCE = new ResourceLocation(Ref.MODID, "player_data");
    public static Capability<RPGPlayerData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static RPGPlayerData get(LivingEntity entity) {
        return entity.getCapability(INSTANCE)
                .orElse(null);
    }

    final LazyOptional<RPGPlayerData> supp = LazyOptional.of(() -> this);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == INSTANCE) {
            return supp.cast();
        }
        return LazyOptional.empty();

    }


    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void onEntityConstruct(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player p) {
                event.addCapability(RESOURCE, new RPGPlayerData(p));
            }
        }
    }


    private static final String TEAM_DATA = "teams";
    private static final String TALENTS_DATA = "talents";
    private static final String STAT_POINTS = "stat_points";
    private static final String DEATH_STATS = "death_stats";

    Player player;

    public TeamData team = new TeamData();
    public TalentsData talents = new TalentsData();
    public StatPointsData statPoints = new StatPointsData();
    public DeathStatsData deathStats = new DeathStatsData();

    public RPGPlayerData(Player player) {
        this.player = player;
    }


    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();

        LoadSave.Save(team, nbt, TEAM_DATA);
        LoadSave.Save(talents, nbt, TALENTS_DATA);
        LoadSave.Save(statPoints, nbt, STAT_POINTS);
        LoadSave.Save(deathStats, nbt, DEATH_STATS);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        this.team = loadOrBlank(TeamData.class, new TeamData(), nbt, TEAM_DATA, new TeamData());
        this.talents = loadOrBlank(TalentsData.class, new TalentsData(), nbt, TALENTS_DATA, new TalentsData());
        this.statPoints = loadOrBlank(StatPointsData.class, new StatPointsData(), nbt, STAT_POINTS, new StatPointsData());
        this.deathStats = loadOrBlank(DeathStatsData.class, new DeathStatsData(), nbt, DEATH_STATS, new DeathStatsData());

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
