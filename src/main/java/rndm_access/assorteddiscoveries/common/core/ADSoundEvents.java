package rndm_access.assorteddiscoveries.common.core;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.AssortedDiscoveries;
public class ADSoundEvents {
    public static final SoundEvent BLOCK_MUSHROOM_BOUNCE = register("block.mushroom_bounce");
    public static final SoundEvent UI_WOODCUTTER_TAKE_RESULT = register("ui.woodcutter.take_result");

    public static void registerSoundEvents() {
        AssortedDiscoveries.LOGGER.info("Registered sound events.");
    }

    /**
     * @param name The name for the sound event.
     * @return new sound event.
     */
    private static SoundEvent register(String name) {
        return Registry.register(Registries.SOUND_EVENT, ADReference.makeId(name), SoundEvent.of(ADReference.makeId(name)));
    }
}
