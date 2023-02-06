package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import rndm_access.assorteddiscoveries.common.util.ADVoxelShapeHelper;

import java.util.HashMap;

public abstract class ADAbstractDirectionalPlushBlock extends ADPlushBlock {
    private final HashMap<Direction, VoxelShape> shapes;

    protected ADAbstractDirectionalPlushBlock(Settings settings, VoxelShape northShape) {
        super(settings);
        this.shapes = ADVoxelShapeHelper.makeShapeRotationMap(northShape);
    }

    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shapes.get(state.get(FACING));
    }
}
