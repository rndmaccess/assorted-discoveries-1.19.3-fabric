package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import rndm_access.assorteddiscoveries.common.core.ADEntityTypeTags;
import rndm_access.assorteddiscoveries.common.core.ADItems;

public class ADBlueberryBushBlock extends ADAbstractBerryBushBlock {
    public ADBlueberryBushBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected Item berryItem() {
        return ADItems.BLUEBERRIES;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.getType().isIn(ADEntityTypeTags.BLUEBERRY_BUSH_IMMUNE_ENTITY_TYPES)) {
            entity.slowMovement(state, new Vec3d(0.8D, 0.75D, 0.8D));
        }
    }
}
