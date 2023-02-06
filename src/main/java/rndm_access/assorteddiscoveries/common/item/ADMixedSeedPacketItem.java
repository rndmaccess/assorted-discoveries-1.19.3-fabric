package rndm_access.assorteddiscoveries.common.item;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import rndm_access.assorteddiscoveries.common.core.ADItems;

import java.util.Random;

public class ADMixedSeedPacketItem extends Item {
    protected static final ImmutableList<Item> SEEDS = ImmutableList.of(ADItems.GARLIC, ADItems.GREEN_ONION, ADItems.BOK_CHOY_SEEDS);

    public ADMixedSeedPacketItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Random random = new Random();
        ItemStack heldStack = user.getStackInHand(hand);

        if (!world.isClient()) {
            for (int i = 0; i < 3; i++) {
                Item randomSeed = SEEDS.get(random.nextInt(SEEDS.size()));
                int randomCount = random.nextInt(3) + 1;

                user.giveItemStack(new ItemStack(randomSeed, randomCount));
            }
        }
        user.playSound(SoundEvents.BLOCK_BAMBOO_STEP, 1.0F, 1.0F);

        if (user.isCreative()) {
            return TypedActionResult.success(heldStack);
        } else {
            return TypedActionResult.consume(new ItemStack(heldStack.getItem(), heldStack.getCount() - 1));
        }
    }
}
