package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import rndm_access.assorteddiscoveries.ADReference;

public class CBiomeTags {
    public static final TagKey<Biome> ORE_SMOKY_QUARTZ = of("has_feature/ore_smoky_quartz");
    public static final TagKey<Biome> PATCH_BLUEBERRY_BUSH = of("has_feature/patch_blueberry_bush");
    public static final TagKey<Biome> PATCH_CATTAIL = of("has_feature/patch_cattail");
    public static final TagKey<Biome> PATCH_ENDER_PLANTS = of("has_feature/patch_ender_plants");
    public static final TagKey<Biome> PATCH_HUGE_PURPLE_MUSHROOM = of("has_feature/patch_huge_purple_mushroom");
    public static final TagKey<Biome> PATCH_WITCHS_CRADLE = of("has_feature/patch_witchs_cradle");
    public static final TagKey<Biome> BLOOD_KELP = of("has_feature/blood_kelp");
    public static final TagKey<Biome> ORE_BAUXITE = of("has_feature/ore_bauxite");
    public static final TagKey<Biome> WEEPING_HEART = of("has_feature/weeping_heart");

    private static TagKey<Biome> of(String path) {
        return TagKey.of(RegistryKeys.BIOME, ADReference.makeCommonId(path));
    }
}
