package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import rndm_access.assorteddiscoveries.ADReference;

public class ADBlockTags {
    public static final TagKey<Block> OVERWORLD_PLANTER_BOXES = of("overworld_planter_boxes");
    public static final TagKey<Block> NETHER_PLANTER_BOXES = of("nether_planter_boxes");

    /**
     * @param path The name of the tag.
     * @return The tag from the location or an empty tag if none exists.
     */
    private static TagKey<Block> of(String path) {
        return TagKey.of(RegistryKeys.BLOCK, ADReference.makeId(path));
    }
}
