package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.database.registry.ExileRegistryTypes;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockEntity;
import com.robertx22.library_of_exile.registry.ExileRegistry;
import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Random;

public abstract class LeagueMechanic implements ExileRegistry<LeagueMechanic> {


    public static LeagueMechanic getMechanicFromPosition(ServerLevel sw, BlockPos pos) {

        var list = ExileDB.LeagueMechanics().getFilterWrapped(x -> x.isInsideLeague(sw, pos)).list;

        if (!list.isEmpty()) {
            return list.get(0);
        }

        return LeagueMechanics.NONE;
    }

    public abstract void onTick(ServerLevel level, BlockPos pos, LeagueBlockEntity be, LeagueBlockData data);

    public abstract BlockPos getTeleportPos(BlockPos pos);


    public abstract LeagueStructurePieces getPieces();


    public abstract int startY();


    public abstract boolean isInsideLeague(ServerLevel level, BlockPos pos);


    public final void tryGenerate(ServerLevel level, ChunkPos pos, Random ran) {
        try {
            LeagueStructurePieces pieces = getPieces();
            var room = pieces.getRoomForChunk(pos);
            if (room != null) {
                generateStructure(level, room, pos);
            }
        } catch (Exception e) {
            //    throw new RuntimeException(e);
        }
    }


    protected boolean generateStructure(LevelAccessor world, ResourceLocation room, ChunkPos cpos) {


        var template = world.getServer().getStructureManager().get(room).get();
        StructurePlaceSettings settings = new StructurePlaceSettings().setMirror(Mirror.NONE)
                .setIgnoreEntities(false);

        settings.setBoundingBox(settings.getBoundingBox());

        BlockPos position = cpos.getBlockAt(0, startY(), 0);

        if (template == null) {
            System.out.println("FATAL ERROR: Structure does not exist (" + room.toString() + ")");
            return false;
        }
        settings.setRotation(Rotation.NONE);

        template.placeInWorld((ServerLevelAccessor) world, position, position, settings, world.getRandom(), 2);

        return true;
    }

    @Override
    public ExileRegistryType getExileRegistryType() {
        return ExileRegistryTypes.LEAGUE_MECHANIC;
    }
}
