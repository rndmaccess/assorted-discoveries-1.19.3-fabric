package rndm_access.assorteddiscoveries.common.core;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;

public class ADVillagerOffers {

    /**
     * Called during mod initialization to register
     * every villager trade for the new professions.
     */
    public static void registerVillagerTradeOffers() {
        TradeOfferHelper.registerVillagerOffers(ADVillagerProfessions.LUMBERJACK, 1, factories -> {
            factories.add(new ItemForEmeraldFactory(new ItemStack(Items.APPLE), 16, 2));
            factories.add(new ItemForEmeraldFactory(new ItemStack(ADItems.SPRUCE_CONE), 10, 2));
        });
        TradeOfferHelper.registerVillagerOffers(ADVillagerProfessions.LUMBERJACK, 2, factories -> {
            factories.add(new EmeraldForItemFactory(2, new ItemStack(Items.GOLDEN_AXE), 6, 10, 0.02F));
            factories.add(new EmeraldForItemFactory(3, new ItemStack(Items.IRON_AXE), 3, 15, 0.02F));
        });
        TradeOfferHelper.registerVillagerOffers(ADVillagerProfessions.LUMBERJACK, 3, factories -> {
            factories.add(new SellItemFactory(new ItemStack(Blocks.OAK_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new ItemStack(Blocks.BIRCH_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new ItemStack(Blocks.JUNGLE_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new ItemStack(Blocks.ACACIA_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15));
            factories.add(new SellItemFactory(new ItemStack(Blocks.DARK_OAK_LOG, 5), new ItemStack(Items.CHARCOAL, 5), 5, 15));
        });
        TradeOfferHelper.registerVillagerOffers(ADVillagerProfessions.LUMBERJACK, 4, factories -> {
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.OAK_SAPLING, 3), 5, 20, 0.05F));
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.BIRCH_SAPLING, 3), 5, 20, 0.05F));
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.JUNGLE_SAPLING, 5), 5, 20, 0.05F));
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.ACACIA_SAPLING, 5), 5, 20, 0.05F));
            factories.add(new EmeraldForItemFactory(1, new ItemStack(Blocks.DARK_OAK_SAPLING, 10), 5, 20, 0.05F));
        });
        TradeOfferHelper.registerVillagerOffers(ADVillagerProfessions.LUMBERJACK, 5, factories -> {
            factories.add(new MasterFactory(1, new ItemStack(Blocks.NOTE_BLOCK), 10, 0.05F));
            factories.add(new MasterFactory(5, new ItemStack(Blocks.JUKEBOX), 5, 0.05F));
        });
    }

    private record EmeraldForItemFactory(int emeralds, ItemStack sellItem, int maxTrades, int xp,
                                         float priceMultiplier) implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(Items.EMERALD, emeralds), sellItem, maxTrades, xp, priceMultiplier);
        }
    }

    private record ItemForEmeraldFactory(ItemStack buyItem, int maxTrades,
                                         int xp) implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(buyItem, new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
        }
    }

    private record SellItemFactory(ItemStack buyItem, ItemStack forSale,
                                   int maxTrades, int xp) implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(buyItem, new ItemStack(Items.EMERALD), forSale, maxTrades, xp, 0.05F);
        }
    }

    private record MasterFactory(int emeralds, ItemStack forSale, int maxTrades,
                                 float priceMultiplier) implements TradeOffers.Factory {
        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(Items.EMERALD, emeralds), forSale, maxTrades, 0, priceMultiplier);
        }
    }
}