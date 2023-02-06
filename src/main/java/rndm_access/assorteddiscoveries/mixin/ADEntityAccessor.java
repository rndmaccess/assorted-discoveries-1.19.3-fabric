package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface ADEntityAccessor {
    @Accessor
    EntityType getType();
}
