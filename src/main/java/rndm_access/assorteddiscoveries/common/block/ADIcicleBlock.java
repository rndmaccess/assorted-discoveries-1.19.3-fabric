package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class ADIcicleBlock extends FallingBlock {
    private static final VoxelShape ICICLE_SHAPE = Block.createCuboidShape(4.0, 1.0, 4.0, 12.0,
            16.0, 12.0);

    public ADIcicleBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return ICICLE_SHAPE;
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        if(!canStay(world, pos)) {
            super.onBlockAdded(state, world, pos, oldState, notify);
        }
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        // Used to keep the player from placing icicle's without supporting blocks.
        return canStay(world, pos) ? this.getDefaultState() : null;
    }

    private boolean canStay(WorldAccess world, BlockPos pos) {
        Direction direction = Direction.DOWN;
        BlockPos blockpos = pos.offset(direction.getOpposite());
        BlockState blockstate = world.getBlockState(blockpos);
        return blockstate.isSideSolidFullSquare(world, blockpos, direction);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (canStay(world, pos)) {
            return state;
        } else {
            boolean isBlockBelow = world.getBlockState(pos.down()) != Blocks.AIR.getDefaultState();

            return isBlockBelow ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState,
                    world, pos, neighborPos);
        }
    }

    /**
     * Called when a falling block hits the ground.
     */
    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos,
                          FallingBlockEntity fallingBlockEntity) {
        PlayerEntity player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 1.1, false);
        float blocksFallen = fallingBlockEntity.timeFalling / 3.0F;
        float fallMultiplier = blocksFallen / 10.0F; // When the block has fallen more than 10 blocks the damage grows.

        // Damages the player when underneath the icicle.
        if (player != null) {
            player.damage(DamageSource.GENERIC, blocksFallen * fallMultiplier);
        }
        world.removeBlock(pos, false);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.damage(DamageSource.GENERIC, 0.5F);
    }
}
