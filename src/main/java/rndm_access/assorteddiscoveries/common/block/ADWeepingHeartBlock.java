package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADParticleTypes;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

public class ADWeepingHeartBlock extends Block implements Fertilizable {
    private static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 13.0, 2.0, 14.0, 16.0, 14.0);

    public ADWeepingHeartBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int xOrigin = pos.getX();
        int yOrigin = pos.getY();
        int zOrigin = pos.getZ();
        double fallingX = xOrigin + random.nextDouble();
        double fallingY = yOrigin + 0.7D;
        double fallingZ = zOrigin + random.nextDouble();

        // Play dripping particles under the plant.
        world.addParticle(ADParticleTypes.FALLING_WEEPING_HEART_NECTAR, fallingX, fallingY, fallingZ,
                0.0D, 0.0D, 0.0D);
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        // Play air particles that float around the plant.
        for(int l = 0; l < 14; ++l) {
            int floatingXOrigin = xOrigin + MathHelper.nextInt(random, -10, 10);
            int floatingYOrigin = yOrigin - random.nextInt(10);
            int floatingZOrigin = zOrigin + MathHelper.nextInt(random, -10, 10);

            mutable.set(floatingXOrigin, floatingYOrigin, floatingZOrigin);
            BlockState blockState = world.getBlockState(mutable);

            if (!blockState.isFullCube(world, mutable)) {
                double floatingX = mutable.getX() + random.nextDouble();
                double floatingY = mutable.getY() + random.nextDouble();
                double floatingZ = mutable.getZ() + random.nextDouble();

                world.addParticle(ADParticleTypes.WEEPING_HEART_AIR_NECTAR, floatingX, floatingY, floatingZ,
                        0.0D, 0.0D, 0.0D);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState supportingBlock = world.getBlockState(pos.up());

        return supportingBlock.isIn(CBlockTags.WEEPING_HEART_PLANTABLE_ON) && !world.isWater(pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState,
                                                WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP && !this.canPlaceAt(state, world, pos)
                ? Blocks.AIR.getDefaultState()
                : state;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        dropStack(world, pos, new ItemStack(this));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
