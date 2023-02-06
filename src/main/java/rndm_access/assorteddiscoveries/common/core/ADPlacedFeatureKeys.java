package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.feature.PlacedFeature;
import rndm_access.assorteddiscoveries.ADReference;

public class ADPlacedFeatureKeys {
    public static final RegistryKey<PlacedFeature> PATCH_CATTAIL = of("patch_cattail");
    public static final RegistryKey<PlacedFeature> ORE_SMOKY_QUARTZ = of("ore_smoky_quartz");
    public static final RegistryKey<PlacedFeature> PATCH_HUGE_PURPLE_MUSHROOM = of("patch_huge_purple_mushroom");
    public static final RegistryKey<PlacedFeature> PATCH_BLUEBERRY_COMMON = of("patch_blueberry_bush_common");
    public static final RegistryKey<PlacedFeature> PATCH_BLUEBERRY_RARE = of("patch_blueberry_bush_rare");
    public static final RegistryKey<PlacedFeature> PATCH_WITCHS_CRADLE_COMMON = of("patch_witchs_cradle_common");
    public static final RegistryKey<PlacedFeature> PATCH_WITCHS_CRADLE_RARE = of("patch_witchs_cradle_rare");
    public static final RegistryKey<PlacedFeature> PATCH_ENDER_PLANTS = of("patch_ender_plants");
    public static final RegistryKey<PlacedFeature> BLOOD_KELP = of("blood_kelp");
    public static final RegistryKey<PlacedFeature> ORE_BAUXITE_LOWER = of("ore_bauxite_lower");
    public static final RegistryKey<PlacedFeature> ORE_BAUXITE_UPPER = of("ore_bauxite_upper");
    public static final RegistryKey<PlacedFeature> WEEPING_HEART = of("weeping_heart");

    public static RegistryKey<PlacedFeature> of(String path) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, ADReference.makeId(path));
    }
}
