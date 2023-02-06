package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import rndm_access.assorteddiscoveries.common.block.state.ADProperties;
import rndm_access.assorteddiscoveries.common.util.ADVoxelShapeHelper;

import java.util.HashMap;

public class ADCubePlushBlock extends ADPlushBlock {
    public static final IntProperty STACK_SIZE = ADProperties.STACK_SIZE;
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape NORTH_BOTTOM_SHAPE = Block.createCuboidShape(2.5D, 0.0D, 2.5D, 13.5D, 9.5D, 13.5D);
    private static final HashMap<Direction, VoxelShape> BOTTOM_SHAPES = ADVoxelShapeHelper.makeShapeRotationMap(NORTH_BOTTOM_SHAPE);

    private static final VoxelShape NORTH_TEMP_MIDDLE_SHAPE = Block.createCuboidShape(3.5D, 7.0D, 3.5D, 12.5D, 16.5D, 12.5D);
    private static final VoxelShape NORTH_MIDDLE_SHAPE = VoxelShapes.union(NORTH_BOTTOM_SHAPE, NORTH_TEMP_MIDDLE_SHAPE);
    private static final HashMap<Direction, VoxelShape> MIDDLE_SHAPES = ADVoxelShapeHelper.makeShapeRotationMap(NORTH_MIDDLE_SHAPE);

    private static final VoxelShape NORTH_TOP_SHAPE = Block.createCuboidShape(5.5D, 0.0D, 5.5D, 10.5D, 4.5D, 10.5D);
    private static final HashMap<Direction, VoxelShape> TOP_SHAPES = ADVoxelShapeHelper.makeShapeRotationMap(NORTH_TOP_SHAPE);

    public ADCubePlushBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(STACK_SIZE, 1)
                .with(WATERLOGGED, false));
    }

    private static void placeTop(World world, BlockPos pos, BlockState state) {
        FluidState fluidState = world.getFluidState(pos);
        boolean isWaterSource = fluidState.isIn(FluidTags.WATER) && fluidState.isStill();
        boolean isDoubleStacked = state.get(STACK_SIZE) == 2;

        if (isDoubleStacked) {
            world.setBlockState(pos, state.with(HALF, DoubleBlockHalf.UPPER).with(STACK_SIZE, 3)
                    .with(WATERLOGGED, isWaterSource), 3);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        BlockState state = world.getBlockState(pos);

        if (state.isOf(this)) {
            placeTop(world, pos.up(), state);
            return state.with(STACK_SIZE, Math.min(3, state.get(STACK_SIZE) + 1));
        }
        return super.getPlacementState(context);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        BlockPos topPos = context.getBlockPos().up();
        BlockState topState = context.getWorld().getBlockState(topPos);
        boolean isStackSame = context.getStack().isOf(this.asItem());
        boolean isTopStateReplaceable = topState.getMaterial().isReplaceable();
        boolean isStackNotFull = state.get(STACK_SIZE) < 3;

        return isStackSame && isStackNotFull && isTopStateReplaceable;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        boolean isTripleStacked = state.get(STACK_SIZE) == 3;

        if(isTripleStacked) {
            boolean hasPlushBelow = world.getBlockState(pos.down()).isOf(this);
            boolean hasPlushAbove = world.getBlockState(pos.up()).isOf(this);

            return hasPlushBelow || hasPlushAbove ? state : Blocks.AIR.getDefaultState();
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = state.get(FACING);
        VoxelShape bottomShape = BOTTOM_SHAPES.get(direction);
        VoxelShape middleShape = MIDDLE_SHAPES.get(direction);
        VoxelShape topShape = TOP_SHAPES.get(direction);

        return switch (state.get(STACK_SIZE)) {
            case 1 -> bottomShape;
            case 2 -> middleShape;
            default -> ((state.get(HALF) == DoubleBlockHalf.UPPER) ? topShape : middleShape);
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(STACK_SIZE, HALF, WATERLOGGED, FACING);
    }
}
