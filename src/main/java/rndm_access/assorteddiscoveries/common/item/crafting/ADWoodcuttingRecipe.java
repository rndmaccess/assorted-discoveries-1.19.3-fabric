package rndm_access.assorteddiscoveries.common.item.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;
import rndm_access.assorteddiscoveries.common.core.ADRecipeSerializers;
import rndm_access.assorteddiscoveries.common.core.ADRecipeTypes;

public class ADWoodcuttingRecipe extends ADCuttingRecipe {
    public ADWoodcuttingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
        super(ADRecipeTypes.WOODCUTTING, ADRecipeSerializers.WOODCUTTING, id, group, input, output);
    }

    public ItemStack createIcon() {
        return new ItemStack(ADBlocks.WOODCUTTER);
    }
}
