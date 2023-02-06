package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import rndm_access.assorteddiscoveries.common.core.ADItems;
import rndm_access.assorteddiscoveries.common.core.ADParticleTypes;

public interface ADBloodKelp {
    BooleanProperty LIT = Properties.LIT;

    static boolean isLit(Random random) {
        float numPicked = random.nextFloat();

        return numPicked < 0.3F;
    }

    static ActionResult pickSeedCluster(World world, PlayerEntity player, BlockState state, BlockPos pos) {
        Random random = Random.create();

        if (state.get(LIT)) {
            player.giveItemStack(new ItemStack(ADItems.BLOOD_KELP_SEED_CLUSTER, random.nextInt(3) + 1));
            world.setBlockState(pos, state.with(LIT, false));
            return ActionResult.success(world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    static void playParticles(World world, BlockState state, BlockPos pos, Random random) {
        double x = pos.getX() + (random.nextDouble() / 2.0);
        double y = pos.getY() + (random.nextDouble() / 2.0);
        double z = pos.getZ() + (random.nextDouble() / 2.0);

        if (state.get(LIT)) {
            world.addParticle(ADParticleTypes.BLOOD_KELP_SPORE, x, y, z, 0.0D, 0.0D, 0.0D);
        }
    }
}
