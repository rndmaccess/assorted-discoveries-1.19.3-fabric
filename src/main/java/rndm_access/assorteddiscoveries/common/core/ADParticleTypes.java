package rndm_access.assorteddiscoveries.common.core;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

public class ADParticleTypes {
    public static final DefaultParticleType WHITE_SPARK = register("white_spark");
    public static final DefaultParticleType ORANGE_SPARK = register("orange_spark");
    public static final DefaultParticleType MAGENTA_SPARK = register("magenta_spark");
    public static final DefaultParticleType LIGHT_BLUE_SPARK = register("light_blue_spark");
    public static final DefaultParticleType YELLOW_SPARK = register("yellow_spark");
    public static final DefaultParticleType LIME_SPARK = register("lime_spark");
    public static final DefaultParticleType PINK_SPARK = register("pink_spark");
    public static final DefaultParticleType GRAY_SPARK = register("gray_spark");
    public static final DefaultParticleType LIGHT_GRAY_SPARK = register("light_gray_spark");
    public static final DefaultParticleType CYAN_SPARK = register("cyan_spark");
    public static final DefaultParticleType PURPLE_SPARK = register("purple_spark");
    public static final DefaultParticleType BLUE_SPARK = register("blue_spark");
    public static final DefaultParticleType BROWN_SPARK = register("brown_spark");
    public static final DefaultParticleType GREEN_SPARK = register("green_spark");
    public static final DefaultParticleType RED_SPARK = register("red_spark");
    public static final DefaultParticleType BLACK_SPARK = register("black_spark");
    public static final DefaultParticleType MAROON_SPARK = register("maroon_spark");
    public static final DefaultParticleType WHITE_FLAME = register("white_flame");
    public static final DefaultParticleType ORANGE_FLAME = register("orange_flame");
    public static final DefaultParticleType MAGENTA_FLAME = register("magenta_flame");
    public static final DefaultParticleType LIGHT_BLUE_FLAME = register("light_blue_flame");
    public static final DefaultParticleType YELLOW_FLAME = register("yellow_flame");
    public static final DefaultParticleType LIME_FLAME = register("lime_flame");
    public static final DefaultParticleType PINK_FLAME = register("pink_flame");
    public static final DefaultParticleType GRAY_FLAME = register("gray_flame");
    public static final DefaultParticleType LIGHT_GRAY_FLAME = register("light_gray_flame");
    public static final DefaultParticleType CYAN_FLAME = register("cyan_flame");
    public static final DefaultParticleType PURPLE_FLAME = register("purple_flame");
    public static final DefaultParticleType BLUE_FLAME = register("blue_flame");
    public static final DefaultParticleType BROWN_FLAME = register("brown_flame");
    public static final DefaultParticleType GREEN_FLAME = register("green_flame");
    public static final DefaultParticleType RED_FLAME = register("red_flame");
    public static final DefaultParticleType BLACK_FLAME = register("black_flame");
    public static final DefaultParticleType MAROON_FLAME = register("maroon_flame");
    public static final DefaultParticleType BLOOD_KELP_SPORE = register("blood_kelp_spore");
    public static final DefaultParticleType WITCHS_CRADLE_SPORE = register("witchs_cradle_spore");
    public static final DefaultParticleType FALLING_WEEPING_HEART_NECTAR = register("falling_weeping_heart_nectar");
    public static final DefaultParticleType WEEPING_HEART_AIR_NECTAR = register("weeping_heart_air_nectar");

    private static DefaultParticleType register(String path) {
        return Registry.register(Registries.PARTICLE_TYPE, ADReference.makeId(path), FabricParticleTypes.simple());
    }

    /**
     * Called during mod initialization to register every particle type.
     */
    public static void registerParticleTypes() {
        AssortedDiscoveries.LOGGER.info("Registered particle types");
    }
}