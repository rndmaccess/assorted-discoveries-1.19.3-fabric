package rndm_access.assorteddiscoveries.common.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;
import rndm_access.assorteddiscoveries.common.util.ADVoxelShapeHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ADPlanterBoxBlock extends Block {
    public static final BooleanProperty NORTH = Properties.NORTH;
    public static final BooleanProperty SOUTH = Properties.SOUTH;
    public static final BooleanProperty WEST = Properties.WEST;
    public static final BooleanProperty EAST = Properties.EAST;
    private static final HashMap<List<Boolean>, VoxelShape> SHAPES = composeRotatedShapes();

    public ADPlanterBoxBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(NORTH, false).with(SOUTH, false)
                .with(WEST, false).with(EAST, false));
    }

    /**
     * A helper method that creates a hashmap that maps a list of booleans that represent each property,
     * (south, north, east, west), to their appropriate shape.
     */
    private static HashMap<List<Boolean>, VoxelShape> composeRotatedShapes() {
        VoxelShape bottomShape = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 15.0, 16.0);
        VoxelShape northBorderShape = Block.createCuboidShape(0.0, 15.0, 13.0, 16.0, 16.0, 16.0);
        List<VoxelShape> borderShapes = ADVoxelShapeHelper.makeShapeRotationList(northBorderShape);
        double borderNum = 4;
        double stateNum = Math.pow(2, borderNum);
        HashMap<List<Boolean>, VoxelShape> shapes = new HashMap<>();

        // Generate a map of shapes for each state that the planter box can be in.
        for (int i = 0; i < stateNum; i++) {
            ArrayList<Boolean> borders = new ArrayList<>(4);
            VoxelShape tempBorderShape = VoxelShapes.empty();

            for (int j = 0; j < borderNum; j++) {
                // When this bit is on there is a closed border here!!!
                if (((i >> j) & 0x01) == 1) {
                    borders.add(false);
                    tempBorderShape = VoxelShapes.union(tempBorderShape, borderShapes.get(j));
                } else {
                    borders.add(true);
                }
            }
            shapes.put(borders, VoxelShapes.union(tempBorderShape, bottomShape));
        }
        return shapes;
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        List<Boolean> stateProperties = ImmutableList.of(state.get(SOUTH), state.get(NORTH), state.get(EAST), state.get(WEST));

        return SHAPES.get(stateProperties);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getPlanterBoxState(this.getDefaultState(), context.getWorld(), context.getBlockPos());
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return getPlanterBoxState(state, world, pos);
    }

    private BlockState getPlanterBoxState(BlockState state, WorldAccess world, BlockPos pos) {
        return state
                .with(NORTH, world.getBlockState(pos.north()).isOf(this))
                .with(SOUTH, world.getBlockState(pos.south()).isOf(this))
                .with(WEST, world.getBlockState(pos.west()).isOf(this))
                .with(EAST, world.getBlockState(pos.east()).isOf(this));
    }

    /**
     * When a structure is rotated with this block in it. This method decides what
     * state each block should be in.
     */
    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        boolean north = state.get(NORTH);
        boolean south = state.get(SOUTH);
        boolean west = state.get(WEST);
        boolean east = state.get(EAST);

        switch (rotation) {
            case CLOCKWISE_180 -> {
                return state
                        .with(SOUTH, north)
                        .with(NORTH, south)
                        .with(EAST, west)
                        .with(WEST, east);
            }
            case CLOCKWISE_90 -> {
                return state
                        .with(EAST, north)
                        .with(WEST, south)
                        .with(NORTH, west)
                        .with(SOUTH, east);
            }
            case COUNTERCLOCKWISE_90 -> {
                return state
                        .with(WEST, north)
                        .with(EAST, south)
                        .with(SOUTH, west)
                        .with(NORTH, east);
            }
            default -> {
                return state;
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(NORTH, SOUTH, WEST, EAST);
    }
}
