package rndm_access.assorteddiscoveries.common.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

import java.util.Objects;

/**
 * This class provides a public constructor for cutting recipe serializers which is required for making
 * custom serializers.
 *
 * @author rndm_access
 */
public abstract class ADCuttingRecipe extends CuttingRecipe {
    public ADCuttingRecipe(RecipeType<?> type, RecipeSerializer<?> serializer, Identifier id, String group, Ingredient input, ItemStack output) {
        super(type, serializer, id, group, input, output);
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    public record Serializer<T extends ADCuttingRecipe>(
            ADCuttingRecipe.Serializer.RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

        public T read(Identifier identifier, JsonObject jsonObject) {
            String string = JsonHelper.getString(jsonObject, "group", "");
            Ingredient ingredient;
            if (JsonHelper.hasArray(jsonObject, "ingredient")) {
                ingredient = Ingredient.fromJson(JsonHelper.getArray(jsonObject, "ingredient"));
            } else {
                ingredient = Ingredient.fromJson(JsonHelper.getObject(jsonObject, "ingredient"));
            }

            String string2 = JsonHelper.getString(jsonObject, "result");
            int i = JsonHelper.getInt(jsonObject, "count");
            ItemStack itemStack = new ItemStack(Registries.ITEM.get(new Identifier(string2)), i);
            return this.recipeFactory.create(identifier, string, ingredient, itemStack);
        }

        public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString();
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            ItemStack itemStack = packetByteBuf.readItemStack();
            return this.recipeFactory.create(identifier, string, ingredient, itemStack);
        }

        public void write(PacketByteBuf packetByteBuf, T cuttingRecipe) {
            packetByteBuf.writeString(cuttingRecipe.group);
            cuttingRecipe.input.write(packetByteBuf);
            packetByteBuf.writeItemStack(cuttingRecipe.output);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Serializer<ADCuttingRecipe> that = (Serializer<ADCuttingRecipe>) obj;
            return Objects.equals(this.recipeFactory, that.recipeFactory);
        }

        @Override
        public int hashCode() {
            return Objects.hash(recipeFactory);
        }

        @Override
        public String toString() {
            return "Serializer[" +
                    "recipeFactory=" + recipeFactory + ']';
        }

        public interface RecipeFactory<T extends CuttingRecipe> {
            T create(Identifier id, String group, Ingredient input, ItemStack output);
        }
    }
}
