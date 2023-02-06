package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import rndm_access.assorteddiscoveries.common.core.ADItems;

public class ADCattailBlock extends TallFlowerBlock {
    public ADCattailBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP) && !floor.isOf(Blocks.MAGMA_BLOCK);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState topFluidState = world.getFluidState(pos.up());

        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return fluidState.isOf(Fluids.EMPTY) && super.canPlaceAt(state, world, pos);
        }
        return fluidState.isOf(Fluids.WATER) && topFluidState.isOf(Fluids.EMPTY)
                && super.canPlaceAt(state, world, pos);
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(ADItems.CATTAIL);
    }

    @SuppressWarnings("depreciation")
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER ? Fluids.WATER.getStill(false)
                : Fluids.EMPTY.getDefaultState();
    }
}
