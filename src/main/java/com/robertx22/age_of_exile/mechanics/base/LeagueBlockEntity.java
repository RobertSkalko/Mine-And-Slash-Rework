package com.robertx22.age_of_exile.mechanics.base;

import com.robertx22.age_of_exile.capability.player.PlayerData;
import com.robertx22.age_of_exile.capability.player.data.TeamData;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlockEntities;
import com.robertx22.library_of_exile.utils.LoadSave;
import com.robertx22.library_of_exile.utils.RandomUtils;
import com.robertx22.library_of_exile.utils.geometry.MyPosition;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LeagueBlockEntity extends BlockEntity {

    public LeagueBlockData data = new LeagueBlockData();


    public LeagueBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(SlashBlockEntities.MAP.get(), pPos, pBlockState);

    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        LoadSave.Save(data, pTag, "league_data");

    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.data = PlayerData.loadOrBlank(TeamData.class, new LeagueBlockData(), pTag, "league_data", new LeagueBlockData());
    }


    public BlockPos getRandomMobSpawnPos(EntityType type) {

        int tries = 0;

        while (tries < 50) {
            tries++;

            var box = getBox(getBlockPos());
            int x = RandomUtils.RandomRange((int) box.minX, (int) box.maxX);
            int y = RandomUtils.RandomRange((int) box.minY, (int) box.maxY);
            int z = RandomUtils.RandomRange((int) box.minZ, (int) box.maxZ);

            // var pos = new BlockPos(x, y, z);

            if (getLevel().noCollision(type.getDimensions().makeBoundingBox(new MyPosition(x, y, z)))) {

                return new BlockPos(x, y, z);
            }
        }

        return null;


    }

    public List<Player> getPlayers() {
        return this.getLevel().getEntitiesOfClass(Player.class, getBox(getBlockPos()), x -> x.isAlive());
    }

    public List<LivingEntity> getEntitiesInside() {
        return this.getLevel().getEntitiesOfClass(LivingEntity.class, getBox(getBlockPos()), x -> x.isAlive());
    }

    public List<LivingEntity> getMobs() {
        return this.getLevel().getEntitiesOfClass(LivingEntity.class, getBox(getBlockPos()), x -> x.isAlive() && x instanceof Player == false);
    }

    public AABB getBox(BlockPos pos) {
        return createBoxOfRadius(pos, 1).expandTowards(data.size.xRadius, data.size.yHeight / 2F, data.size.zRadius).move(0, data.size.yHeight, 0);
    }

    public AABB createBoxOfRadius(BlockPos pos, int radius) {
        int num = radius;

        AABB box = new AABB(
                pos.getX() - num,
                pos.getY() - num,
                pos.getZ() - num,
                pos.getX() + num,
                pos.getY() + num,
                pos.getZ() + num);
        return box;

    }


}

