package rndm_access.assorteddiscoveries.common.item;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class ADFoodComponents {
    public static final FoodComponent FRIED_EGG = (new FoodComponent.Builder()).hunger(2).saturationModifier(0.3F).build();
    public static final FoodComponent GARLIC = new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).build();
    public static final FoodComponent GREEN_ONION = new FoodComponent.Builder().hunger(1).saturationModifier(0.3F).build();
    public static final FoodComponent BOK_CHOY = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build();
    public static final FoodComponent NOODLE_SOUP = stew(10);
    public static final FoodComponent PUDDING = stew(6);
    public static final FoodComponent BERRY_PUDDING = stew(8);
    public static final FoodComponent CARAMEL_APPLE = new FoodComponent.Builder().hunger(5).saturationModifier(0.5F).build();
    public static final FoodComponent WITCHS_CRADLE_BRANCH = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build();
    public static final FoodComponent WITCHS_CRADLE_SOUP = new FoodComponent.Builder().hunger(6).saturationModifier(0.6F)
            .statusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000, 0, true, true), 1.0F)
            .alwaysEdible().build();
    public static final FoodComponent WEEPING_HEART_NECTAR_BUCKET = new FoodComponent.Builder().hunger(3).saturationModifier(0.3F).build();
    public static final FoodComponent BLUEBERRIES = new FoodComponent.Builder().hunger(3).saturationModifier(0.2F).build();
    public static final FoodComponent SWEET_BERRY_JUICE = new FoodComponent.Builder().hunger(5).saturationModifier(0.6F).build();
    public static final FoodComponent BLUEBERRY_JUICE = new FoodComponent.Builder().hunger(5).saturationModifier(0.6F).build();
    public static final FoodComponent SPRUCE_CONE = new FoodComponent.Builder().hunger(2).saturationModifier(0.3F).build();
    public static final FoodComponent FORESTS_BOUNTY = stew(5);
    public static final FoodComponent HOGLIN_STEW = stew(8);

    private static FoodComponent stew(int hunger) {
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(0.6F).build();
    }
}
