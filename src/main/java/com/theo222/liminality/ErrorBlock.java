package com.theo222.liminality;

import net.minecraft.core.BlockPos;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ErrorBlock extends Block {

    public ErrorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        entity.teleportTo(entity.xOld, entity.yOld, entity.zOld);
        super.fallOn(level, state, pos, entity, fallDistance);
    }
    private void SpreadToBlock(BlockPos pos, BlockState state, ServerLevel level) {
        if (!level.getBlockState(pos).is(Blocks.AIR) && !level.getBlockState(pos).is(this.asBlock())) {
            level.setBlock(pos, state, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        SpreadToBlock(pos.above(), state, level);
        SpreadToBlock(pos.below(), state, level);
        SpreadToBlock(pos.east(), state, level);
        SpreadToBlock(pos.west(), state, level);
        SpreadToBlock(pos.north(), state, level);
        SpreadToBlock(pos.south(), state, level);

        //level.getServer().getPlayerList().getPlayers().forEach(serverPlayer -> serverPlayer.setHealth(0));
        super.randomTick(state, level, pos, random);
    }
}
