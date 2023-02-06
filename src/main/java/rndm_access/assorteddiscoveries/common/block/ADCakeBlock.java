package rndm_access.assorteddiscoveries.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CakeBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;

public class ADCakeBlock extends CakeBlock {
    public ADCakeBlock(Settings settings) {
        super(settings);
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getStackInHand(hand);

        if (state.get(BITES) == 0) {
            Item item = heldStack.getItem();
            Block block = Block.getBlockFromItem(item);

            if(ADCandleCakeBlock.containsCandleCake(this, block)) {
                return this.placeCandleCake(world, player, pos, heldStack, block, item);
            }
        }
        return this.eatCake(world, player, pos, heldStack, state);
    }

    private ActionResult placeCandleCake(World world, PlayerEntity player, BlockPos pos, ItemStack itemStack, Block block, Item item) {
        if (!player.isCreative()) {
            itemStack.decrement(1);
        }

        world.playSound(null, pos, SoundEvents.BLOCK_CAKE_ADD_CANDLE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.setBlockState(pos, ADCandleCakeBlock.getCandleCake(this, block));
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        player.incrementStat(Stats.USED.getOrCreateStat(item));
        return ActionResult.SUCCESS;
    }

    private ActionResult eatCake(World world, PlayerEntity player, BlockPos pos,
                                 ItemStack itemStack, BlockState state) {
        if (world.isClient()) {
            if (tryEatCake(world, pos, state, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }

            if (itemStack.isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return tryEatCake(world, pos, state, player);
    }

    public static ActionResult tryEatCake(WorldAccess world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        }
        else {
            int bites = state.get(BITES);

            player.incrementStat(Stats.EAT_CAKE_SLICE);
            player.getHungerManager().add(2, 0.1F);
            world.emitGameEvent(player, GameEvent.EAT, pos);

            if (bites < 6) {
                world.setBlockState(pos, state.with(BITES, bites + 1), 3);
            }
            else {
                world.removeBlock(pos, false);
                world.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }
            return ActionResult.SUCCESS;
        }
    }
}
