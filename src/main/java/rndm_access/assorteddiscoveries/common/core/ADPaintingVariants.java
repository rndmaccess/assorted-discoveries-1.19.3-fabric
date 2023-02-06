package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

public class ADPaintingVariants {
    public static final PaintingVariant STARRY_NIGHT = new PaintingVariant(32, 32);

    private static void register(String path, PaintingVariant variant) {
        Registry.register(Registries.PAINTING_VARIANT, ADReference.makeId(path), variant);
    }

    public static void registerPaintingVariants() {
        register("starry_night", STARRY_NIGHT);

        AssortedDiscoveries.LOGGER.info("Registered painting variants");
    }
}
