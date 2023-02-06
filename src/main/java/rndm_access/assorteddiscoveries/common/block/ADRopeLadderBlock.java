package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.block.state.ADProperties;

public class ADRopeLadderBlock extends LadderBlock {
    public static final IntProperty LENGTH = ADProperties.LENGTH;
    public static final BooleanProperty DOWN = Properties.DOWN;

    public ADRopeLadderBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH)
                .with(WATERLOGGED, false).with(LENGTH, 0).with(DOWN, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState aboveState = world.getBlockState(pos.up());
        BlockState belowState = world.getBlockState(pos.down());
        boolean isInWater = world.getFluidState(pos).isOf(Fluids.WATER);
        BlockState placedState = this.getDefaultState().with(WATERLOGGED, isInWater)
                .with(DOWN, belowState.isOf(this));

        // Place hanging ladders when they're supporting blocks up to 16.
        if (aboveState.isOf(this)) {
            Direction facing = aboveState.get(FACING);
            int length = aboveState.get(LENGTH);

            if (length < 16) {
                BlockPos behindPos = pos.offset(facing.getOpposite());
                BlockState behindState = world.getBlockState(behindPos);

                if (!behindState.isSideSolidFullSquare(world, behindPos, facing)) {
                    return placedState.with(LENGTH, length + 1).with(FACING, facing);
                }
                return placedState.with(FACING, facing);
            }
            return null;
        }

        // Place ladder on block facing the correct way.
        for (Direction direction : context.getPlacementDirections()) {
            if (direction.getAxis().isHorizontal()) {
                return placedState.with(FACING, direction.getOpposite());
            }
        }
        return null;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        Direction facing = state.get(FACING);
        BlockPos posBehind = pos.offset(facing.getOpposite());
        BlockState stateAbove = world.getBlockState(pos.up());
        BlockState stateBelow = world.getBlockState(pos.down());
        BlockState stateBehind = world.getBlockState(posBehind);

        if (canPlaceAt(state, world, pos)) {

            // Set the ladder's length to 0 when a block is placed behind it.
            if (stateBehind.isSideSolidFullSquare(world, posBehind, facing)) {
                return state.with(LENGTH, 0).with(DOWN, stateBelow.isOf(this));
            }

            // Update each ladders length to keep the ladder's length consistent.
            if (stateAbove.isOf(this)) {
                return state.with(LENGTH, stateAbove.get(LENGTH) + 1).with(DOWN, stateBelow.isOf(this));
            }
        }
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction facing = state.get(FACING);
        BlockPos behindPos = pos.offset(facing.getOpposite());
        BlockState aboveState = world.getBlockState(pos.up());
        BlockState behindState = world.getBlockState(behindPos);
        boolean hasSupport = behindState.isSideSolidFullSquare(world, behindPos, facing);

        // When the last ladder supported is less than or equal to 16 allow new ladders to be placed.
        if (aboveState.isOf(this)) {
            int length = aboveState.get(LENGTH) + 1;

            return length <= 16;
        }
        return hasSupport;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LENGTH, DOWN, FACING, WATERLOGGED);
    }
}
