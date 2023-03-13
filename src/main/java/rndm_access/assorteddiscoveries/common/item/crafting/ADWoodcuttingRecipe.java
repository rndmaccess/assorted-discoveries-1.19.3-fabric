package rndm_access.assorteddiscoveries.common.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;
import rndm_access.assorteddiscoveries.common.core.ADRecipeSerializers;
import rndm_access.assorteddiscoveries.common.core.ADRecipeTypes;

import java.util.Objects;

public class ADWoodcuttingRecipe implements Recipe<Inventory> {
    private final Ingredient input;
    private final ItemStack output;
    private final RecipeType<?> type;
    private final RecipeSerializer<?> serializer;
    private final Identifier id;
    private final String group;

    public ADWoodcuttingRecipe(Identifier id, String group, Ingredient input, ItemStack output) {
        this.type = ADRecipeTypes.WOODCUTTING;
        this.serializer = ADRecipeSerializers.WOODCUTTING;
        this.id = id;
        this.group = group;
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return this.input.test(inventory.getStack(0));
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return this.serializer;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public ItemStack getOutput() {
        return this.output;
    }

    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.add(this.input);
        return defaultedList;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ADBlocks.WOODCUTTER);
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return this.output.copy();
    }
    
    public record Serializer<T extends ADWoodcuttingRecipe>(RecipeFactory<T> recipeFactory) implements RecipeSerializer<T> {

        @Override
        public T read(Identifier identifier, JsonObject jsonObject) {
            String group = JsonHelper.getString(jsonObject, "group", "");
            String result = JsonHelper.getString(jsonObject, "result");
            int count = JsonHelper.getInt(jsonObject, "count");
            ItemStack stack = new ItemStack(Registries.ITEM.get(new Identifier(result)), count);
            Ingredient ingredient = JsonHelper.hasArray(jsonObject, "ingredient")
                    ? Ingredient.fromJson(JsonHelper.getArray(jsonObject, "ingredient"))
                    : Ingredient.fromJson(JsonHelper.getObject(jsonObject, "ingredient"));

            return this.recipeFactory.create(identifier, group, ingredient, stack);
        }

        @Override
        public T read(Identifier identifier, PacketByteBuf packetByteBuf) {
            String string = packetByteBuf.readString();
            Ingredient ingredient = Ingredient.fromPacket(packetByteBuf);
            ItemStack stack = packetByteBuf.readItemStack();

            return this.recipeFactory.create(identifier, string, ingredient, stack);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, T cuttingRecipe) {
            packetByteBuf.writeString(cuttingRecipe.getGroup());
            cuttingRecipe.getInput().write(packetByteBuf);
            packetByteBuf.writeItemStack(cuttingRecipe.getOutput());
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.recipeFactory);
        }

        @Override
        public String toString() {
            return "Serializer[recipeFactory=" + this.recipeFactory + ']';
        }

        public interface RecipeFactory<T extends ADWoodcuttingRecipe> {
            T create(Identifier id, String group, Ingredient input, ItemStack output);
        }
    }
}
