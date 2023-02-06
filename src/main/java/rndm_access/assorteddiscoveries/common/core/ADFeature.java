package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;
import rndm_access.assorteddiscoveries.common.worldgen.feature.ADBloodKelpFeature;
import rndm_access.assorteddiscoveries.common.worldgen.feature.ADCattailFeature;

public class ADFeature {
    public static final Feature<ProbabilityConfig> CATTAIL = new ADCattailFeature(ProbabilityConfig.CODEC);
    public static final Feature<DefaultFeatureConfig> BLOOD_KELP = new ADBloodKelpFeature(DefaultFeatureConfig.CODEC);

    private static <C extends FeatureConfig, F extends Feature<C>> void register(String path, F feature) {
        Registry.register(Registries.FEATURE, ADReference.makeId(path), feature);
    }

    /**
     * Called during initialization to register every feature.
     */
    public static void registerFeatures() {
        register("cattail", CATTAIL);
        register("blood_kelp", BLOOD_KELP);

        AssortedDiscoveries.LOGGER.info("Registered features");
    }
}
