package rndm_access.assorteddiscoveries.common.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class ADFoodContainerItem extends Item {
    private final Item returnItem;

    public ADFoodContainerItem(Item.Settings settings) {
        super(settings);
        this.returnItem = Items.BOWL;
    }

    public ADFoodContainerItem(Item.Settings settings, Item returnItem) {
        super(settings);
        this.returnItem = returnItem;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        boolean flag = user instanceof PlayerEntity && ((PlayerEntity)user).isCreative();

        user.eatFood(world, stack);
        return flag ? stack : new ItemStack(this.returnItem);
    }
}
