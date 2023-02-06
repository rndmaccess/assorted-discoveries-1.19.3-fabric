package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import rndm_access.assorteddiscoveries.common.core.ADItems;

public class ADBokChoyCropBlock extends CropBlock {
    public ADBokChoyCropBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ADItems.BOK_CHOY_SEEDS;
    }
}
