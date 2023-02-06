package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import rndm_access.assorteddiscoveries.ADReference;

public class ADConfiguredFeatureKeys {
    public static final RegistryKey<ConfiguredFeature<?,?>> HUGE_PURPLE_MUSHROOM = of("huge_purple_mushroom");

    public static RegistryKey<ConfiguredFeature<?,?>> of(String path) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, ADReference.makeId(path));
    }
}
