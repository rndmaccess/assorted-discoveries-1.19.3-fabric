package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;
import rndm_access.assorteddiscoveries.common.util.ADBlockStateUtil;

import java.util.Objects;

public class ADGrassSlabBlock extends SlabBlock {
    public static final BooleanProperty SNOWY = Properties.SNOWY;

    public ADGrassSlabBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(SNOWY, false));
    }

    public static boolean canBeGrass(BlockState state, WorldView world, BlockPos pos) {
        BlockPos abovePos = pos.up();
        BlockState aboveState = world.getBlockState(abovePos);

        if (aboveState.isIn(BlockTags.SNOW) || aboveState.isIn(CBlockTags.SNOW_STAIRS) || aboveState.isIn(CBlockTags.SNOW_SLABS)) {
            return true;
        } else if (aboveState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            return !aboveState.isSolidBlock(world, abovePos) || state.get(TYPE).equals(SlabType.BOTTOM);
        }
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos().up();
        BlockState blockState = world.getBlockState(blockPos);
        return Objects.requireNonNull(super.getPlacementState(context)).with(SNOWY, isSnow(world, blockPos, blockState));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP
                ? super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos).with(SNOWY, isSnow(world, neighborPos, neighborState))
                : state;
    }

    public static boolean isSnow(WorldAccess world, BlockPos pos, BlockState state) {

        if(ADBlockStateUtil.isSnowSlabOrStairs(world, pos, state)) {
            return true;
        }
        return state.isIn(BlockTags.SNOW);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canBeGrass(state, world, pos)) {
            BlockState stateAt = world.getBlockState(pos);
            world.setBlockState(pos, ADBlocks.DIRT_SLAB.getDefaultState().with(TYPE, stateAt.get(TYPE)));
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TYPE, WATERLOGGED, SNOWY);
    }
}
