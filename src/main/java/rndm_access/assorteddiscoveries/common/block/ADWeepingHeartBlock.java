package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADItems;
import rndm_access.assorteddiscoveries.common.core.ADParticleTypes;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

public class ADWeepingHeartBlock extends Block implements Fertilizable {
    public static final IntProperty AGE = Properties.AGE_2;

    public ADWeepingHeartBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ADItems.WEEPING_HEART_SEEDS.getDefaultStack();
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        this.playNectarDripParticles(world, random, x, y, z);
        this.playAirNectarParticles(world, state, random, x, y, z);
    }

    private void playNectarDripParticles(World world, Random random, int x, int y, int z) {
        double fallingX = x + random.nextDouble();
        double fallingY = y + 0.7D;
        double fallingZ = z + random.nextDouble();

        world.addParticle(ADParticleTypes.FALLING_WEEPING_HEART_NECTAR, fallingX, fallingY, fallingZ,
                0.0D, 0.0D, 0.0D);
    }

    private void playAirNectarParticles(World world, BlockState state, Random random, int x, int y, int z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int floatingCount = getParticleAmount(state);
        int floatingArea = getParticleArea(state);

        for(int l = 0; l < floatingCount; ++l) {
            int floatingXOrigin = x + MathHelper.nextInt(random, -floatingArea, floatingArea);
            int floatingYOrigin = y - random.nextInt(floatingArea);
            int floatingZOrigin = z + MathHelper.nextInt(random, -floatingArea, floatingArea);

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

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getStackInHand(hand);

        // Fill the held bucket with nectar when the player clicks the plant.
        if (heldStack.isOf(Items.BUCKET) && this.isMature(state)) {
            this.fillBucketWithNectar(player, hand);
            world.setBlockState(pos, state.with(AGE, 0));
            return ActionResult.success(world.isClient);
        }
        return ActionResult.PASS;
    }

    private void fillBucketWithNectar(PlayerEntity player, Hand hand) {
        ItemStack nectarBucket = new ItemStack(ADItems.WEEPING_HEART_NECTAR_BUCKET);

        player.playSound(SoundEvents.BLOCK_BEEHIVE_DRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);

        if (player.isCreative()) {
            PlayerInventory playerInventory = player.getInventory();
            if (!playerInventory.contains(nectarBucket)) {
                player.giveItemStack(nectarBucket);
            }
        } else {
            player.getStackInHand(hand).decrement(1);

            if (player.getStackInHand(hand).isEmpty()) {
                player.setStackInHand(hand, nectarBucket);
            } else {
                player.giveItemStack(nectarBucket);
            }
        }
    }

    private int getParticleAmount(BlockState state) {
        int i = state.get(AGE);

        return (i + 1) * 5 - 1;
    }

    private int getParticleArea(BlockState state) {
        int i = state.get(AGE);

        return (i + 1) * 3 + 1;
    }

    private int getMaxAge() {
        return 2;
    }

    private boolean isMature(BlockState state) {
        return state.get(AGE) >= this.getMaxAge();
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return !this.isMature(state);
    }

    public boolean hasRandomTicks(BlockState state) {
        return !this.isMature(state);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state.with(AGE, Math.min(3, state.get(AGE) + 1)), 2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int i = state.get(AGE);

        if (!this.isMature(state) && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= 9) {
            BlockState blockState = state.with(AGE, i + 1);
            world.setBlockState(pos, blockState, 2);
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
