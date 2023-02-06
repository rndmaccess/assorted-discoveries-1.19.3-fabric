package rndm_access.assorteddiscoveries.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rndm_access.assorteddiscoveries.common.core.ADBlocks;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

import java.util.Random;

@Mixin(BoneMealItem.class)
public abstract class ADBoneMealItemMixin {
    @Shadow
    public static void createParticles(WorldAccess world, BlockPos pos, int count) {}

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void assorteddiscoveries_boneMealEndBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> info) {
        BlockPos blockPos = context.getBlockPos();
        World world = context.getWorld();
        ItemStack boneMealStack = context.getStack();
        Random random = new Random();
        BlockState blockBoneMealed = world.getBlockState(blockPos);

        // Grow snapdragons and ender grass on blocks in the END_BONE_MEALABLE_BLOCKS
        // when using bone meal.
        if (blockBoneMealed.isIn(CBlockTags.END_BONE_MEALABLE_BLOCKS) && world.getBlockState(blockPos.up()).isAir()) {
            if (!world.isClient()) {
                growEnderPlants(blockPos, random, world);
            }
            boneMealStack.decrement(1);
            createParticles(world, blockPos, random.nextInt(10));
            info.setReturnValue(ActionResult.success(world.isClient()));
        }
    }

    private static void growEnderPlants(BlockPos blockPos, Random random, World world) {
        for (int i = 0; i < 128; ++i) {
            // Re-center the position on the block bone mealed.
            BlockPos.Mutable mutablePos = blockPos.mutableCopy();

            for (int j = 0; j < i / 16; ++j) {
                mutablePos.move(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2,
                        random.nextInt(3) - 1);
                BlockState state = world.getBlockState(mutablePos);
                BlockState soilState = world.getBlockState(mutablePos.down());

                if (soilState.isIn(CBlockTags.END_BONE_MEALABLE_BLOCKS) && state.isAir()) {

                    // There is a 40% chance to get a snapdragon and a 60% chance to get some ender grass.
                    if(random.nextFloat() <= 0.4) {
                        world.setBlockState(mutablePos, ADBlocks.SNAPDRAGON.getDefaultState());
                    }
                    else {
                        world.setBlockState(mutablePos, ADBlocks.ENDER_GRASS.getDefaultState());
                    }
                }
            }
        }
    }
}
