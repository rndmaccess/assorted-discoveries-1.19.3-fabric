package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import rndm_access.assorteddiscoveries.common.block.state.ADProperties;
import rndm_access.assorteddiscoveries.common.core.ADEntityTypeTags;
import rndm_access.assorteddiscoveries.common.core.CBlockTags;

public class ADSnapdragonBlock extends FlowerBlock {
    public static final BooleanProperty CAN_TELEPORT = ADProperties.CAN_TELEPORT;

    public ADSnapdragonBlock(StatusEffect suspiciousStewEffect, int effectDuration, AbstractBlock.Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
        this.setDefaultState(this.getDefaultState().with(CAN_TELEPORT, true));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemInHand = player.getStackInHand(hand);

        if(itemInHand.isOf(Items.SHEARS) && state.get(CAN_TELEPORT)) {
            world.setBlockState(pos, state.with(CAN_TELEPORT, false), 2);
            world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            itemInHand.damage(1, player, (player2) -> player2.sendToolBreakStatus(player2.getActiveHand()));
            return ActionResult.success(world.isClient());
        }
        return ActionResult.PASS;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(CBlockTags.SNAPDRAGON_PLANTABLE_ON);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        double x = (double) pos.getX() + random.nextFloat();
        double y = (double) pos.getY() + random.nextFloat();
        double z = (double) pos.getZ() + random.nextFloat();

        world.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0D, 0.0D, 0.0D);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        EntityType<?> type = entity.getType();
        boolean isTeleportImmune = type.isIn(ADEntityTypeTags.SNAPDRAGON_TELEPORT_IMMUNE_ENTITY_TYPES);
        boolean isNotFalling = entity.getVelocity().y == 0.0D;

        if (entity instanceof LivingEntity livingEntity
                && !isTeleportImmune
                && !world.isClient()
                && !livingEntity.isSneaking()
                && isNotFalling
                && state.get(CAN_TELEPORT)) {
            double x = livingEntity.getX();
            double y = livingEntity.getY();
            double z = livingEntity.getZ();
            int bottomY = world.getBottomY();
            Random random = livingEntity.getRandom();

            livingEntity.setVelocity(0.0, 0.0, 0.0);

            for(int i = 0; i < 16; ++i) {
                double newX = x + (random.nextDouble() - 0.5D) * 16.0D;
                double newY = MathHelper.clamp(y + (random.nextInt(16) - 8), bottomY,
                        bottomY + ((ServerWorld)world).getLogicalHeight() - 1);
                double newZ = z + (random.nextDouble() - 0.5D) * 16.0D;

                if (livingEntity.hasVehicle()) {
                    livingEntity.stopRiding();
                }

                if (livingEntity.teleport(newX, newY, newZ, true)) {
                    SoundEvent teleportSound = livingEntity instanceof FoxEntity
                            ? SoundEvents.ENTITY_FOX_TELEPORT
                            : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;

                    world.playSound(null, x, y, z, teleportSound, SoundCategory.PLAYERS, 1.0F, 1.0F);
                    livingEntity.playSound(teleportSound, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(CAN_TELEPORT);
    }
}
