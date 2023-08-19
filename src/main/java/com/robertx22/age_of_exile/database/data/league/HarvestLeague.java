package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.processors.helpers.MobBuilder;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

public class HarvestLeague extends LeagueMechanic {

    public int ticksLast = 20 * 60 * 3;

    @Override
    public void onTick(ServerLevel level, BlockPos pos, LeagueBlockEntity be, LeagueBlockData data) {

        data.ticks++;

        if (data.ticks > ticksLast) {
            data.finished = true;

            for (Player p : be.getPlayers()) {
                p.sendSystemMessage(Component.literal("The Vines appear to shrink, for now...").withStyle(ChatFormatting.GREEN));
            }
            return;
        } else {

            if (data.ticks % 20 == 0) {

                int mobs = be.getMobs().size();

                if (mobs < 20) {

                    int tospawn = 5;

                    for (int i = 0; i < tospawn; i++) {

                        var type = getRandomMobToSpawn();
                        var spawnpos = be.getRandomMobSpawnPos(type);

                        if (spawnpos != null) {
                            for (Mob mob : MobBuilder.of(getRandomMobToSpawn(), x -> {
                                x.amount = 1;
                            }).summonMobs(level, spawnpos)) {

                            }
                        }

                    }
                }

            }

        }

    }

    // todo
    public EntityType getRandomMobToSpawn() {
        return EntityType.SPIDER;
    }


    @Override
    public BlockPos getTeleportPos(BlockPos pos) {
        BlockPos p = MapData.getStartChunk(pos).getBlockAt(0, 0, 0);
        p = new BlockPos(p.getX() + 8, startY() + 5 + 2, p.getZ() + 8);
        return p;
    }


    @Override
    public LeagueStructurePieces getPieces() {
        return new LeagueStructurePieces(1, "harvest");
    }

    @Override
    public int startY() {
        return 70;
    }


    @Override
    public boolean isInsideLeague(ServerLevel level, BlockPos pos) {
        return pos.getY() > startY() && pos.getY() < (startY() + 20);
    }

    @Override
    public String GUID() {
        return "harvest";
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
