package rndm_access.assorteddiscoveries;

import net.minecraft.util.Identifier;

public class ADReference {
    public static final String MOD_ID = "assorteddiscoveries";

    public static Identifier makeId(String path) {
        return new Identifier(MOD_ID, path);
    }

    public static Identifier makeCommonId(String path) {
        return new Identifier("c", path);
    }
}
