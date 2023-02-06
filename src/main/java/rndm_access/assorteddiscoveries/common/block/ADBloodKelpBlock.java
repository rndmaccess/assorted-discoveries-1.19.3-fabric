package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.KelpBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;

import java.util.Objects;

public class ADBloodKelpBlock extends KelpBlock implements ADBloodKelp {
    public ADBloodKelpBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }

    @Override
    protected Block getPlant() {
        return ADBlocks.BLOOD_KELP_PLANT;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return Objects.requireNonNull(super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos))
                .with(LIT, state.get(LIT));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos newStemPos = pos.offset(this.growthDirection);
        int age = state.get(AGE);

        if (age < 25 && this.chooseStemState(world.getBlockState(newStemPos))) {
            world.setBlockState(pos, this.growStemToPlant(state), 2);
            world.setBlockState(newStemPos, this.getStemState(random, age).cycle(AGE));
        }
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos newStemPos = pos.mutableCopy().offset(this.growthDirection);
        int age = Math.min(state.get(AGE) + 1, 25);

        if(this.chooseStemState(world.getBlockState(newStemPos))) {
            world.setBlockState(pos, this.growStemToPlant(state), 2);
            world.setBlockState(newStemPos, this.getStemState(random, age));
        }
    }

    public BlockState getStemState(Random random, int age) {
        return this.getDefaultState().with(LIT, ADBloodKelp.isLit(random)).with(AGE, age);
    }

    private BlockState growStemToPlant(BlockState stemState) {
        boolean isLit = stemState.get(LIT);

        return this.getPlant().getDefaultState().with(ADBloodKelpPlantBlock.LIT, isLit);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        ADBloodKelp.playParticles(world, state, pos, random);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        return ADBloodKelp.pickSeedCluster(world, player, state, pos);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return !state.get(LIT) && super.isFertilizable(world, pos, state, isClient);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, AGE);
    }
}
