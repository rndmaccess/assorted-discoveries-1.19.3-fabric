package rndm_access.assorteddiscoveries.common.core;

import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.poi.PointOfInterestType;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

public class ADPointOfInterestTypes {
    public static final RegistryKey<PointOfInterestType> LUMBERJACK = of("lumberjack");

    public static void registerPointOfInterestTypes() {
        PointOfInterestHelper.register(ADReference.makeId("lumberjack"), 1, 1, ADBlocks.WOODCUTTER);

        AssortedDiscoveries.LOGGER.info("Registered point of interest types.");
    }

    private static RegistryKey<PointOfInterestType> of(String path) {
        return RegistryKey.of(RegistryKeys.POINT_OF_INTEREST_TYPE, ADReference.makeId(path));
    }
}