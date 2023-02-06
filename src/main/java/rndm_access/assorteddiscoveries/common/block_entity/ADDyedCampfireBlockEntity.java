package rndm_access.assorteddiscoveries.common.block_entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.util.math.BlockPos;
import rndm_access.assorteddiscoveries.common.core.ADBlockEntityTypes;

public class ADDyedCampfireBlockEntity extends CampfireBlockEntity {
    public ADDyedCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return ADBlockEntityTypes.DYED_CAMPFIRE;
    }
}
