package rndm_access.assorteddiscoveries.common.core;

import com.google.common.collect.ImmutableSet;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.jetbrains.annotations.Nullable;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;

public class ADVillagerProfessions {
    public static final VillagerProfession LUMBERJACK = register(ADReference.makeId("lumberjack"),
            ADPointOfInterestTypes.LUMBERJACK, ADSoundEvents.UI_WOODCUTTER_TAKE_RESULT);

    private static VillagerProfession register(Identifier id, RegistryKey<PointOfInterestType> workstation,
                                               @Nullable SoundEvent workSound) {
        return Registry.register(Registries.VILLAGER_PROFESSION, id, create(id, workstation, workSound));
    }

    private static VillagerProfession create(Identifier id, RegistryKey<PointOfInterestType> workstation,
                                               @Nullable SoundEvent workSound) {
        return new VillagerProfession(id.toString(), (entry) -> entry.matchesKey(workstation),
                (entry) -> entry.matchesKey(workstation), ImmutableSet.of(), ImmutableSet.of(), workSound);
    }

    public static void registerVillagerProfessions() {
        AssortedDiscoveries.LOGGER.info("Registered villager professions");
    }
}