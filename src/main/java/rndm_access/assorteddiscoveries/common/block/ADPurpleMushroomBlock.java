package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.MushroomBlock;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import rndm_access.assorteddiscoveries.common.core.ADSoundEvents;

public class ADPurpleMushroomBlock extends MushroomBlock {
    public ADPurpleMushroomBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        float strength = 0.5F;
        float height = fallDistance * strength;
        SoundEvent sound = ADSoundEvents.BLOCK_MUSHROOM_BOUNCE;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        if (entity.isSneaking()) {
            world.playSound(x, y, z, sound, SoundCategory.BLOCKS,
                    1.0F, 0.8F + world.random.nextFloat() / 0.4F, true);
            entity.setOnGround(true);

        } else {
            Vec3d vector3d = entity.getVelocity();

            // Set a maximum height of 10.
            if (height >= 10) {
                height = 10;
            }
            // Set a minimum height of 2.
            if (height <= 2) {
                height = 2;
            }

            // Update the entities motion and play the bounce sound.
            entity.setVelocity(vector3d.x, -vector3d.y + Math.sqrt(0.2 * (height + 0.2F)), vector3d.z);
            world.playSound(x, y, z, sound, SoundCategory.BLOCKS, 1.0F, 0.8F + world.getRandom().nextFloat() * 0.4F, true);
            entity.setOnGround(false);
        }
        entity.fallDistance = 0;
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {}
}
