package rndm_access.assorteddiscoveries.common.block.state;

import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;

public class ADProperties {
    public static final IntProperty STACK_SIZE = IntProperty.of("stack_size", 1, 3);
    public static final IntProperty PUFFED = IntProperty.of("puffed", 0, 2);
    public static final IntProperty LENGTH = IntProperty.of("length", 0, 16);
    public static final BooleanProperty CAN_TELEPORT = BooleanProperty.of("can_teleport");
}
