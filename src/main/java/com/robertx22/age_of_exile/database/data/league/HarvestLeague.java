package com.robertx22.age_of_exile.database.data.league;

import com.robertx22.age_of_exile.database.registry.ExileDB;
import com.robertx22.age_of_exile.loot.LootInfo;
import com.robertx22.age_of_exile.loot.blueprints.LootChestBlueprint;
import com.robertx22.age_of_exile.maps.MapData;
import com.robertx22.age_of_exile.maps.processors.helpers.MobBuilder;
import com.robertx22.age_of_exile.mechanics.base.LeagueBlockData;
import com.robertx22.age_of_exile.mechanics.base.LeagueControlBlockEntity;
import com.robertx22.age_of_exile.mechanics.harvest.HarvestItems;
import com.robertx22.age_of_exile.mmorpg.registers.common.SlashBlocks;
import com.robertx22.library_of_exile.utils.RandomUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class HarvestLeague extends LeagueMechanic {

    // todo make a timer, maybe a kill count..?

    public int ticksLast = 20 * 60 * 3;

    @Override
    public void onKillMob(LootInfo info) {
        if (RandomUtils.roll(1)) { // todo test
            for (ItemStack stack : generateMobLoot(info)) {
                info.mobKilled.spawnAtLocation(stack, 1);
            }
        }
    }

    private List<ItemStack> generateMobLoot(LootInfo info) {

        LootChestBlueprint b = new LootChestBlueprint(info);
        b.type.set(ExileDB.LootChests().getFilterWrapped(x -> x.getDropReq().isFromLeague(LeagueMechanics.HARVEST)).random());

        ItemStack stack = b.createStack();

        Item seeditem = RandomUtils.randomFromList(Arrays.asList(HarvestItems.BLUE_PLANT_SEED, HarvestItems.GREEN_PLANT_SEED, HarvestItems.PURPLE_PLANT_SEED)).get();

        ItemStack seeds = new ItemStack(seeditem, 4);

        return Arrays.asList(stack, seeds);
    }

    @Override
    public void spawnTeleportInMap(ServerLevel level, BlockPos pos) {

        level.setBlock(pos, SlashBlocks.HARVEST_TELEPORT.get().defaultBlockState(), 2);
    }

    @Override
    public float chanceToSpawnMechanicAfterKillingMob() {
        return 1;
    }

    @Override
    public void onTick(ServerLevel level, BlockPos pos, LeagueControlBlockEntity be, LeagueBlockData data) {

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

                if (mobs < 30) {

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

    public EntityType getRandomMobToSpawn() {
        if (RandomUtils.roll(5)) {
            return EntityType.CAVE_SPIDER;
        }
        return EntityType.SPIDER;
    }


    @Override
    public BlockPos getTeleportPos(BlockPos pos) {
        BlockPos p = MapData.getStartChunk(pos).getBlockAt(0, 0, 0);
        p = new BlockPos(p.getX() + 20, startY() + 5 + 3, p.getZ() + 20);
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
        return pos.getY() >= startY() && pos.getY() <= (startY() + 20);
    }

    @Override
    public String GUID() {
        return LeagueMechanics.HARVEST_ID;
    }

    @Override
    public int Weight() {
        return 1000;
    }
}
