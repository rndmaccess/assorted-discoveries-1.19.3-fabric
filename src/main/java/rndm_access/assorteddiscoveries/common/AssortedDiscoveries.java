package rndm_access.assorteddiscoveries.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.TableBonusLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rndm_access.assorteddiscoveries.ADReference;
import rndm_access.assorteddiscoveries.common.core.*;

public class AssortedDiscoveries implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger(ADReference.MOD_ID);

	@Override
	public void onInitialize() {
		// General Registries
		ADBlocks.registerBlocks();
		ADItems.registerItems();
		addAndPopulateItemGroups();
		ADBlockEntityTypes.registerBlockEntityTypes();
		ADParticleTypes.registerParticleTypes();
		ADScreenHandlerTypes.registerScreenHandlerTypes();
		ADRecipeTypes.registerRecipeTypes();
		ADRecipeSerializers.registerSerializers();
		ADPaintingVariants.registerPaintingVariants();
		ADSoundEvents.registerSoundEvents();
		AssortedDiscoveries.registerFuel();
		AssortedDiscoveries.registerCompostables();
		AssortedDiscoveries.modifyLootTables();

		// Entity Registries
		ADVillagerTypes.registerVillagerTypes();
		ADPointOfInterestTypes.registerPointOfInterestTypes();
		ADVillagerProfessions.registerVillagerProfessions();
		ADVillagerOffers.registerVillagerTradeOffers();

		// World Generation Registries
		ADFeature.registerFeatures();
		AssortedDiscoveries.addFeaturesToBiomes();
	}

	private static void addFeaturesToBiomes() {
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.PATCH_CATTAIL),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_CATTAIL);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.ORE_SMOKY_QUARTZ),
				GenerationStep.Feature.UNDERGROUND_ORES, ADPlacedFeatureKeys.ORE_SMOKY_QUARTZ);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.PATCH_HUGE_PURPLE_MUSHROOM),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_HUGE_PURPLE_MUSHROOM);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.PATCH_BLUEBERRY_BUSH),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_BLUEBERRY_COMMON);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.PATCH_BLUEBERRY_BUSH),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_BLUEBERRY_RARE);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.PATCH_WITCHS_CRADLE),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_WITCHS_CRADLE_COMMON);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.PATCH_WITCHS_CRADLE),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_WITCHS_CRADLE_RARE);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.PATCH_ENDER_PLANTS),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.PATCH_ENDER_PLANTS);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.BLOOD_KELP),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.BLOOD_KELP);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.ORE_BAUXITE),
				GenerationStep.Feature.UNDERGROUND_ORES, ADPlacedFeatureKeys.ORE_BAUXITE_LOWER);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.ORE_BAUXITE),
				GenerationStep.Feature.UNDERGROUND_ORES, ADPlacedFeatureKeys.ORE_BAUXITE_UPPER);
		BiomeModifications.addFeature(BiomeSelectors.tag(CBiomeTags.WEEPING_HEART),
				GenerationStep.Feature.VEGETAL_DECORATION, ADPlacedFeatureKeys.WEEPING_HEART);
	}

	private static void registerFuel() {
		FuelRegistry.INSTANCE.add(ADItems.DRIED_BLOOD_KELP_BLOCK, 4000);
	}

	private static void registerCompostables() {
		CompostingChanceRegistry.INSTANCE.add(ADItems.BLUEBERRIES, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.WITCHS_CRADLE_BRANCH, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.BLOOD_KELP_SEED_CLUSTER, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.BLOOD_KELP, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.DRIED_BLOOD_KELP, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.ENDER_GRASS, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.BOK_CHOY_SEEDS, 0.3F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.WEEPING_HEART_SEEDS, 0.3F);

		CompostingChanceRegistry.INSTANCE.add(ADItems.CATTAIL, 0.5F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.DRIED_BLOOD_KELP_BLOCK, 0.5F);

		CompostingChanceRegistry.INSTANCE.add(ADItems.SNAPDRAGON, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.GARLIC, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.GREEN_ONION, 0.65F);
		CompostingChanceRegistry.INSTANCE.add(ADItems.BOK_CHOY, 0.65F);
	}

	private static void modifyLootTables() {
		Identifier spruceLeavesLootTableId = Blocks.SPRUCE_LEAVES.getLootTableId();

		LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
			if(source.isBuiltin() && spruceLeavesLootTableId.equals(id)) {
				LootPool.Builder poolBuilder = LootPool.builder()
						.rolls(ConstantLootNumberProvider.create(1))
						.conditionally(TableBonusLootCondition.builder(Enchantments.FORTUNE, 0.02F, 0.023F, 0.025F, 0.035F, 0.1F))
						.with(ItemEntry.builder(ADItems.SPRUCE_CONE))
						.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)));

				tableBuilder.pool(poolBuilder);
			}
		});
	}

	private static void addAndPopulateItemGroups() {
		FabricItemGroup.builder(ADReference.makeId("mod_group"))
				.displayName(Text.literal("Vanilla Expansions"))
				.icon(() -> new ItemStack(ADItems.ENDERMAN_PLUSH))
				.entries((enabledFeatures, entries, operatorEnabled) -> {
					entries.add(ADItems.BAT_PLUSH);
					entries.add(ADItems.BLAZE_PLUSH);
					entries.add(ADItems.CHICKEN_PLUSH);
					entries.add(ADItems.COW_PLUSH);
					entries.add(ADItems.CREEPER_PLUSH);
					entries.add(ADItems.ENDERMAN_PLUSH);
					entries.add(ADItems.GHAST_PLUSH);
					entries.add(ADItems.GUARDIAN_PLUSH);
					entries.add(ADItems.WHITE_HORSE_PLUSH);
					entries.add(ADItems.GRAY_HORSE_PLUSH);
					entries.add(ADItems.LIGHT_GRAY_HORSE_PLUSH);
					entries.add(ADItems.BROWN_HORSE_PLUSH);
					entries.add(ADItems.BLACK_HORSE_PLUSH);
					entries.add(ADItems.RED_MOOSHROOM_PLUSH);
					entries.add(ADItems.BROWN_MOOSHROOM_PLUSH);
					entries.add(ADItems.OCELOT_PLUSH);
					entries.add(ADItems.TABBY_CAT_PLUSH);
					entries.add(ADItems.TUXEDO_CAT_PLUSH);
					entries.add(ADItems.RED_CAT_PLUSH);
					entries.add(ADItems.SIAMESE_CAT_PLUSH);
					entries.add(ADItems.BRITISH_SHORTHAIR_CAT_PLUSH);
					entries.add(ADItems.CALICO_CAT_PLUSH);
					entries.add(ADItems.PERSIAN_CAT_PLUSH);
					entries.add(ADItems.RAGDOLL_CAT_PLUSH);
					entries.add(ADItems.WHITE_CAT_PLUSH);
					entries.add(ADItems.BLACK_CAT_PLUSH);
					entries.add(ADItems.JELLIE_CAT_PLUSH);
					entries.add(ADItems.PIG_PLUSH);
					entries.add(ADItems.BROWN_RABBIT_PLUSH);
					entries.add(ADItems.WHITE_RABBIT_PLUSH);
					entries.add(ADItems.BLACK_RABBIT_PLUSH);
					entries.add(ADItems.WHITE_SPLOTCHED_RABBIT_PLUSH);
					entries.add(ADItems.GOLD_RABBIT_PLUSH);
					entries.add(ADItems.TOAST_RABBIT_PLUSH);
					entries.add(ADItems.SALT_RABBIT_PLUSH);
					entries.add(ADItems.WHITE_SHEEP_PLUSH);
					entries.add(ADItems.ORANGE_SHEEP_PLUSH);
					entries.add(ADItems.MAGENTA_SHEEP_PLUSH);
					entries.add(ADItems.LIGHT_BLUE_SHEEP_PLUSH);
					entries.add(ADItems.YELLOW_SHEEP_PLUSH);
					entries.add(ADItems.LIME_SHEEP_PLUSH);
					entries.add(ADItems.PINK_SHEEP_PLUSH);
					entries.add(ADItems.GRAY_SHEEP_PLUSH);
					entries.add(ADItems.LIGHT_GRAY_SHEEP_PLUSH);
					entries.add(ADItems.CYAN_SHEEP_PLUSH);
					entries.add(ADItems.PURPLE_SHEEP_PLUSH);
					entries.add(ADItems.BLUE_SHEEP_PLUSH);
					entries.add(ADItems.BROWN_SHEEP_PLUSH);
					entries.add(ADItems.GREEN_SHEEP_PLUSH);
					entries.add(ADItems.RED_SHEEP_PLUSH);
					entries.add(ADItems.BLACK_SHEEP_PLUSH);
					entries.add(ADItems.MAROON_SHEEP_PLUSH);
					entries.add(ADItems.SKELETON_PLUSH);
					entries.add(ADItems.SLIME_PLUSH);
					entries.add(ADItems.MAGMA_CUBE_PLUSH);
					entries.add(ADItems.SPIDER_PLUSH);
					entries.add(ADItems.CAVE_SPIDER_PLUSH);
					entries.add(ADItems.SQUID_PLUSH);
					entries.add(ADItems.GLOW_SQUID_PLUSH);
					entries.add(ADItems.BEE_PLUSH);
					entries.add(ADItems.PLAINS_VILLAGER_PLUSH);
					entries.add(ADItems.DESERT_VILLAGER_PLUSH);
					entries.add(ADItems.JUNGLE_VILLAGER_PLUSH);
					entries.add(ADItems.SAVANNA_VILLAGER_PLUSH);
					entries.add(ADItems.SNOW_VILLAGER_PLUSH);
					entries.add(ADItems.SWAMP_VILLAGER_PLUSH);
					entries.add(ADItems.TAIGA_VILLAGER_PLUSH);
					entries.add(ADItems.CRIMSON_VILLAGER_PLUSH);
					entries.add(ADItems.WARPED_VILLAGER_PLUSH);
					entries.add(ADItems.WANDERING_TRADER_PLUSH);
					entries.add(ADItems.PLAINS_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.DESERT_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.JUNGLE_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.SAVANNA_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.SNOW_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.SWAMP_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.TAIGA_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.CRIMSON_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.WARPED_ZOMBIE_VILLAGER_PLUSH);
					entries.add(ADItems.WITCH_PLUSH);
					entries.add(ADItems.WOLF_PLUSH);
					entries.add(ADItems.ZOMBIE_PLUSH);
					entries.add(ADItems.PIGLIN_PLUSH);
					entries.add(ADItems.ZOMBIFIED_PIGLIN_PLUSH);
					entries.add(ADItems.HOGLIN_PLUSH);
					entries.add(ADItems.ZOGLIN_PLUSH);
					entries.add(ADItems.PUFFERFISH_PLUSH);
					entries.add(ADItems.WITHER_PLUSH);
					entries.add(ADItems.STRIDER_PLUSH);
					entries.add(ADItems.SHIVERING_STRIDER_PLUSH);
					entries.add(ADItems.PHANTOM_PLUSH);
					entries.add(ADItems.POLAR_BEAR_PLUSH);
					entries.add(ADItems.ALLAY_PLUSH);
					entries.add(ADItems.VEX_PLUSH);
					entries.add(ADItems.PILLAGER_PLUSH);
					entries.add(ADItems.VINDICATOR_PLUSH);
					entries.add(ADItems.EVOKER_PLUSH);
					entries.add(ADItems.RAVAGER_PLUSH);
					entries.add(ADItems.SHULKER_PLUSH);
					entries.add(ADItems.CAMEL_PLUSH);

					entries.add(ADItems.NETHER_SMOKY_QUARTZ_ORE);
					entries.add(ADItems.SMOKY_QUARTZ_BLOCK);
					entries.add(ADItems.SMOKY_QUARTZ_STAIRS);
					entries.add(ADItems.SMOKY_QUARTZ_SLAB);
					entries.add(ADItems.SMOKY_QUARTZ_WALL);
					entries.add(ADItems.SMOOTH_SMOKY_QUARTZ);
					entries.add(ADItems.SMOOTH_SMOKY_QUARTZ_STAIRS);
					entries.add(ADItems.SMOOTH_SMOKY_QUARTZ_SLAB);
					entries.add(ADItems.SMOOTH_SMOKY_QUARTZ_WALL);
					entries.add(ADItems.SMOKY_QUARTZ_BRICKS);
					entries.add(ADItems.SMOKY_QUARTZ_BRICK_STAIRS);
					entries.add(ADItems.SMOKY_QUARTZ_BRICK_SLAB);
					entries.add(ADItems.SMOKY_QUARTZ_BRICK_WALL);
					entries.add(ADItems.CHISELED_SMOKY_QUARTZ_BLOCK);
					entries.add(ADItems.SMOKY_QUARTZ_PILLAR);

					entries.add(ADItems.QUARTZ_WALL);
					entries.add(ADItems.SMOOTH_QUARTZ_WALL);
					entries.add(ADItems.STONE_WALL);

					entries.add(ADItems.RED_GLASS);
					entries.add(ADItems.RED_GLASS_PANE);

					entries.add(ADItems.OAK_PLANTER_BOX);
					entries.add(ADItems.SPRUCE_PLANTER_BOX);
					entries.add(ADItems.BIRCH_PLANTER_BOX);
					entries.add(ADItems.JUNGLE_PLANTER_BOX);
					entries.add(ADItems.ACACIA_PLANTER_BOX);
					entries.add(ADItems.DARK_OAK_PLANTER_BOX);
					entries.add(ADItems.MANGROVE_PLANTER_BOX);
					entries.add(ADItems.CRIMSON_PLANTER_BOX);
					entries.add(ADItems.WARPED_PLANTER_BOX);

					entries.add(ADItems.OAK_WALL);
					entries.add(ADItems.SPRUCE_WALL);
					entries.add(ADItems.BIRCH_WALL);
					entries.add(ADItems.JUNGLE_WALL);
					entries.add(ADItems.ACACIA_WALL);
					entries.add(ADItems.DARK_OAK_WALL);
					entries.add(ADItems.MANGROVE_WALL);
					entries.add(ADItems.CRIMSON_WALL);
					entries.add(ADItems.WARPED_WALL);

					entries.add(ADItems.STRIPPED_OAK_WALL);
					entries.add(ADItems.STRIPPED_SPRUCE_WALL);
					entries.add(ADItems.STRIPPED_BIRCH_WALL);
					entries.add(ADItems.STRIPPED_JUNGLE_WALL);
					entries.add(ADItems.STRIPPED_ACACIA_WALL);
					entries.add(ADItems.STRIPPED_DARK_OAK_WALL);
					entries.add(ADItems.STRIPPED_MANGROVE_WALL);
					entries.add(ADItems.STRIPPED_CRIMSON_WALL);
					entries.add(ADItems.STRIPPED_WARPED_WALL);

					entries.add(ADItems.OAK_ROPE_LADDER);
					entries.add(ADItems.SPRUCE_ROPE_LADDER);
					entries.add(ADItems.BIRCH_ROPE_LADDER);
					entries.add(ADItems.JUNGLE_ROPE_LADDER);
					entries.add(ADItems.ACACIA_ROPE_LADDER);
					entries.add(ADItems.DARK_OAK_ROPE_LADDER);
					entries.add(ADItems.CRIMSON_ROPE_LADDER);
					entries.add(ADItems.WARPED_ROPE_LADDER);
					entries.add(ADItems.MANGROVE_ROPE_LADDER);

					entries.add(ADItems.IRON_LADDER);

					entries.add(ADItems.DIRT_SLAB);
					entries.add(ADItems.COARSE_DIRT_SLAB);
					entries.add(ADItems.DIRT_PATH_SLAB);
					entries.add(ADItems.GRASS_SLAB);

					entries.add(ADItems.SNOW_BRICKS);
					entries.add(ADItems.SNOW_BRICK_STAIRS);
					entries.add(ADItems.SNOW_BRICK_SLAB);
					entries.add(ADItems.SNOW_BRICK_WALL);

					entries.add(ADItems.PACKED_SNOW);
					entries.add(ADItems.PACKED_SNOW_STAIRS);
					entries.add(ADItems.PACKED_SNOW_SLAB);
					entries.add(ADItems.PACKED_SNOW_WALL);

					entries.add(ADItems.SNOW_SLAB);
					entries.add(ADItems.SNOW_STAIRS);
					entries.add(ADItems.SNOW_WALL);

					entries.add(ADItems.PURPLE_MUSHROOM_BLOCK);
					entries.add(ADItems.PURPLE_MUSHROOM);

					entries.add(ADItems.WOODCUTTER);

					entries.add(ADItems.FRESH_BAMBOO_FENCE);
					entries.add(ADItems.FRESH_BAMBOO_FENCE_GATE);

					entries.add(ADItems.DRIED_BAMBOO_FENCE);
					entries.add(ADItems.DRIED_BAMBOO_FENCE_GATE);

					entries.add(ADItems.BAUXITE);
					entries.add(ADItems.BAUXITE_SLAB);
					entries.add(ADItems.BAUXITE_STAIRS);
					entries.add(ADItems.BAUXITE_WALL);

					entries.add(ADItems.BAUXITE_BRICKS);
					entries.add(ADItems.BAUXITE_BRICK_STAIRS);
					entries.add(ADItems.BAUXITE_BRICK_SLAB);
					entries.add(ADItems.BAUXITE_BRICK_WALL);
					entries.add(ADItems.MOSSY_BAUXITE_BRICKS);
					entries.add(ADItems.MOSSY_BAUXITE_BRICK_STAIRS);
					entries.add(ADItems.MOSSY_BAUXITE_BRICK_SLAB);
					entries.add(ADItems.MOSSY_BAUXITE_BRICK_WALL);
					entries.add(ADItems.CRACKED_BAUXITE_BRICKS);
					entries.add(ADItems.CRACKED_BAUXITE_BRICK_STAIRS);
					entries.add(ADItems.CRACKED_BAUXITE_BRICK_SLAB);
					entries.add(ADItems.CRACKED_BAUXITE_BRICK_WALL);

					entries.add(ADItems.TWISTED_NETHER_BRICKS);
					entries.add(ADItems.TWISTED_NETHER_BRICK_STAIRS);
					entries.add(ADItems.TWISTED_NETHER_BRICK_SLAB);
					entries.add(ADItems.TWISTED_NETHER_BRICK_WALL);

					entries.add(ADItems.TWISTED_NETHERRACK);
					entries.add(ADItems.TWISTED_NETHERRACK_STAIRS);
					entries.add(ADItems.TWISTED_NETHERRACK_SLAB);
					entries.add(ADItems.TWISTED_NETHERRACK_WALL);

					entries.add(ADItems.WEEPING_NETHER_BRICKS);
					entries.add(ADItems.WEEPING_NETHER_BRICK_STAIRS);
					entries.add(ADItems.WEEPING_NETHER_BRICK_SLAB);
					entries.add(ADItems.WEEPING_NETHER_BRICK_WALL);

					entries.add(ADItems.WEEPING_NETHERRACK);
					entries.add(ADItems.WEEPING_NETHERRACK_STAIRS);
					entries.add(ADItems.WEEPING_NETHERRACK_SLAB);
					entries.add(ADItems.WEEPING_NETHERRACK_WALL);

					entries.add(ADItems.SNAPDRAGON);
					entries.add(ADItems.ENDER_GRASS);

					entries.add(ADItems.ICICLE);

					entries.add(ADItems.STONE_TILES);
					entries.add(ADItems.STONE_TILE_SLAB);
					entries.add(ADItems.STONE_TILE_STAIRS);
					entries.add(ADItems.STONE_TILE_WALL);

					entries.add(ADItems.MOSSY_STONE_TILES);
					entries.add(ADItems.MOSSY_STONE_TILE_SLAB);
					entries.add(ADItems.MOSSY_STONE_TILE_STAIRS);
					entries.add(ADItems.MOSSY_STONE_TILE_WALL);

					entries.add(ADItems.CRACKED_STONE_TILES);
					entries.add(ADItems.CRACKED_STONE_TILE_SLAB);
					entries.add(ADItems.CRACKED_STONE_TILE_STAIRS);
					entries.add(ADItems.CRACKED_STONE_TILE_WALL);

					entries.add(ADItems.CRACKED_STONE_BRICK_STAIRS);
					entries.add(ADItems.CRACKED_STONE_BRICK_SLAB);
					entries.add(ADItems.CRACKED_STONE_BRICK_WALL);

					entries.add(ADItems.BLACKSTONE_TILES);
					entries.add(ADItems.BLACKSTONE_TILE_STAIRS);
					entries.add(ADItems.BLACKSTONE_TILE_SLAB);
					entries.add(ADItems.BLACKSTONE_TILE_WALL);

					entries.add(ADItems.TWISTED_BLACKSTONE_TILES);
					entries.add(ADItems.TWISTED_BLACKSTONE_TILE_STAIRS);
					entries.add(ADItems.TWISTED_BLACKSTONE_TILE_SLAB);
					entries.add(ADItems.TWISTED_BLACKSTONE_TILE_WALL);

					entries.add(ADItems.WEEPING_BLACKSTONE_TILES);
					entries.add(ADItems.WEEPING_BLACKSTONE_TILE_STAIRS);
					entries.add(ADItems.WEEPING_BLACKSTONE_TILE_SLAB);
					entries.add(ADItems.WEEPING_BLACKSTONE_TILE_WALL);

					entries.add(ADItems.TWISTED_POLISHED_BLACKSTONE_BRICKS);
					entries.add(ADItems.TWISTED_POLISHED_BLACKSTONE_BRICK_STAIRS);
					entries.add(ADItems.TWISTED_POLISHED_BLACKSTONE_BRICK_SLAB);
					entries.add(ADItems.TWISTED_POLISHED_BLACKSTONE_BRICK_WALL);

					entries.add(ADItems.WEEPING_POLISHED_BLACKSTONE_BRICKS);
					entries.add(ADItems.WEEPING_POLISHED_BLACKSTONE_BRICK_STAIRS);
					entries.add(ADItems.WEEPING_POLISHED_BLACKSTONE_BRICK_SLAB);
					entries.add(ADItems.WEEPING_POLISHED_BLACKSTONE_BRICK_WALL);

					entries.add(ADItems.TWISTED_BLACKSTONE);
					entries.add(ADItems.TWISTED_BLACKSTONE_STAIRS);
					entries.add(ADItems.TWISTED_BLACKSTONE_SLAB);
					entries.add(ADItems.TWISTED_BLACKSTONE_WALL);

					entries.add(ADItems.WEEPING_BLACKSTONE);
					entries.add(ADItems.WEEPING_BLACKSTONE_STAIRS);
					entries.add(ADItems.WEEPING_BLACKSTONE_SLAB);
					entries.add(ADItems.WEEPING_BLACKSTONE_WALL);

					entries.add(ADItems.QUARTZ_TILES);
					entries.add(ADItems.QUARTZ_TILE_STAIRS);
					entries.add(ADItems.QUARTZ_TILE_SLAB);
					entries.add(ADItems.QUARTZ_TILE_WALL);

					entries.add(ADItems.CALCITE_BRICKS);
					entries.add(ADItems.CALCITE_BRICK_STAIRS);
					entries.add(ADItems.CALCITE_BRICK_SLAB);
					entries.add(ADItems.CALCITE_BRICK_WALL);
					entries.add(ADItems.MOSSY_CALCITE_BRICKS);
					entries.add(ADItems.MOSSY_CALCITE_BRICK_STAIRS);
					entries.add(ADItems.MOSSY_CALCITE_BRICK_SLAB);
					entries.add(ADItems.MOSSY_CALCITE_BRICK_WALL);
					entries.add(ADItems.CRACKED_CALCITE_BRICKS);
					entries.add(ADItems.CRACKED_CALCITE_BRICK_STAIRS);
					entries.add(ADItems.CRACKED_CALCITE_BRICK_SLAB);
					entries.add(ADItems.CRACKED_CALCITE_BRICK_WALL);
					entries.add(ADItems.CHISELED_CALCITE_BRICKS);

					entries.add(ADItems.TUFF_BRICKS);
					entries.add(ADItems.TUFF_BRICK_STAIRS);
					entries.add(ADItems.TUFF_BRICK_SLAB);
					entries.add(ADItems.TUFF_BRICK_WALL);
					entries.add(ADItems.MOSSY_TUFF_BRICKS);
					entries.add(ADItems.MOSSY_TUFF_BRICK_STAIRS);
					entries.add(ADItems.MOSSY_TUFF_BRICK_SLAB);
					entries.add(ADItems.MOSSY_TUFF_BRICK_WALL);
					entries.add(ADItems.CRACKED_TUFF_BRICKS);
					entries.add(ADItems.CRACKED_TUFF_BRICK_STAIRS);
					entries.add(ADItems.CRACKED_TUFF_BRICK_SLAB);
					entries.add(ADItems.CRACKED_TUFF_BRICK_WALL);
					entries.add(ADItems.CHISELED_TUFF_BRICKS);

					entries.add(ADItems.DRIPSTONE_BRICKS);
					entries.add(ADItems.DRIPSTONE_BRICK_STAIRS);
					entries.add(ADItems.DRIPSTONE_BRICK_SLAB);
					entries.add(ADItems.DRIPSTONE_BRICK_WALL);
					entries.add(ADItems.MOSSY_DRIPSTONE_BRICKS);
					entries.add(ADItems.MOSSY_DRIPSTONE_BRICK_STAIRS);
					entries.add(ADItems.MOSSY_DRIPSTONE_BRICK_SLAB);
					entries.add(ADItems.MOSSY_DRIPSTONE_BRICK_WALL);
					entries.add(ADItems.CRACKED_DRIPSTONE_BRICKS);
					entries.add(ADItems.CRACKED_DRIPSTONE_BRICK_STAIRS);
					entries.add(ADItems.CRACKED_DRIPSTONE_BRICK_SLAB);
					entries.add(ADItems.CRACKED_DRIPSTONE_BRICK_WALL);
					entries.add(ADItems.CHISELED_DRIPSTONE_BRICKS);

					entries.add(ADItems.WHITE_CAMPFIRE);
					entries.add(ADItems.ORANGE_CAMPFIRE);
					entries.add(ADItems.MAGENTA_CAMPFIRE);
					entries.add(ADItems.LIGHT_BLUE_CAMPFIRE);
					entries.add(ADItems.YELLOW_CAMPFIRE);
					entries.add(ADItems.LIME_CAMPFIRE);
					entries.add(ADItems.PINK_CAMPFIRE);
					entries.add(ADItems.GRAY_CAMPFIRE);
					entries.add(ADItems.LIGHT_GRAY_CAMPFIRE);
					entries.add(ADItems.CYAN_CAMPFIRE);
					entries.add(ADItems.PURPLE_CAMPFIRE);
					entries.add(ADItems.BLUE_CAMPFIRE);
					entries.add(ADItems.BROWN_CAMPFIRE);
					entries.add(ADItems.GREEN_CAMPFIRE);
					entries.add(ADItems.RED_CAMPFIRE);
					entries.add(ADItems.BLACK_CAMPFIRE);
					entries.add(ADItems.MAROON_CAMPFIRE);

					entries.add(ADItems.WHITE_LANTERN);
					entries.add(ADItems.ORANGE_LANTERN);
					entries.add(ADItems.MAGENTA_LANTERN);
					entries.add(ADItems.LIGHT_BLUE_LANTERN);
					entries.add(ADItems.YELLOW_LANTERN);
					entries.add(ADItems.LIME_LANTERN);
					entries.add(ADItems.PINK_LANTERN);
					entries.add(ADItems.GRAY_LANTERN);
					entries.add(ADItems.LIGHT_GRAY_LANTERN);
					entries.add(ADItems.CYAN_LANTERN);
					entries.add(ADItems.PURPLE_LANTERN);
					entries.add(ADItems.BLUE_LANTERN);
					entries.add(ADItems.BROWN_LANTERN);
					entries.add(ADItems.GREEN_LANTERN);
					entries.add(ADItems.RED_LANTERN);
					entries.add(ADItems.BLACK_LANTERN);
					entries.add(ADItems.MAROON_LANTERN);

					entries.add(ADItems.WHITE_TORCH);
					entries.add(ADItems.ORANGE_TORCH);
					entries.add(ADItems.MAGENTA_TORCH);
					entries.add(ADItems.LIGHT_BLUE_TORCH);
					entries.add(ADItems.YELLOW_TORCH);
					entries.add(ADItems.LIME_TORCH);
					entries.add(ADItems.PINK_TORCH);
					entries.add(ADItems.GRAY_TORCH);
					entries.add(ADItems.LIGHT_GRAY_TORCH);
					entries.add(ADItems.CYAN_TORCH);
					entries.add(ADItems.PURPLE_TORCH);
					entries.add(ADItems.BLUE_TORCH);
					entries.add(ADItems.BROWN_TORCH);
					entries.add(ADItems.GREEN_TORCH);
					entries.add(ADItems.RED_TORCH);
					entries.add(ADItems.BLACK_TORCH);
					entries.add(ADItems.MAROON_TORCH);

					entries.add(ADItems.CHOCOLATE_CAKE);
					entries.add(ADItems.RED_VELVET_CAKE);

					entries.add(ADItems.SWEET_BERRY_PIE);
					entries.add(ADItems.BLUEBERRY_PIE);

					entries.add(ADItems.MIXED_SEED_PACKET);
					entries.add(ADItems.BOK_CHOY);
					entries.add(ADItems.BOK_CHOY_SEEDS);
					entries.add(ADItems.GARLIC);
					entries.add(ADItems.GREEN_ONION);
					entries.add(ADItems.BLUEBERRIES);
					entries.add(ADItems.SWEET_BERRY_JUICE);
					entries.add(ADItems.BLUEBERRY_JUICE);
					entries.add(ADItems.NOODLES);
					entries.add(ADItems.NOODLE_SOUP);
					entries.add(ADItems.PUDDING);
					entries.add(ADItems.BERRY_PUDDING);
					entries.add(ADItems.CARAMEL_APPLE);
					entries.add(ADItems.CARAMEL);
					entries.add(ADItems.SPRUCE_CONE);
					entries.add(ADItems.FORESTS_BOUNTY);
					entries.add(ADItems.WITCHS_CRADLE_BRANCH);
					entries.add(ADItems.WITCHS_CRADLE_SOUP);
					entries.add(ADItems.CATTAIL);
					entries.add(ADItems.DRIED_BAMBOO);
					entries.add(ADItems.FRIED_EGG);
					entries.add(ADItems.BLOOD_KELP_SEED_CLUSTER);
					entries.add(ADItems.BLOOD_KELP);
					entries.add(ADItems.DRIED_BLOOD_KELP_BLOCK);
					entries.add(ADItems.DRIED_BLOOD_KELP);
					entries.add(ADItems.BLOOD_KELP_LANTERN);
					entries.add(ADItems.MAROON_WOOL);
					entries.add(ADItems.MAROON_STAINED_GLASS);
					entries.add(ADItems.MAROON_STAINED_GLASS_PANE);
					entries.add(ADItems.MAROON_CANDLE);
					entries.add(ADItems.MAROON_CONCRETE);
					entries.add(ADItems.MAROON_CONCRETE_POWDER);
					entries.add(ADItems.MAROON_DYE);
					entries.add(ADItems.HOGLIN_STEW);
					entries.add(ADItems.WEEPING_HEART_NECTAR_BUCKET);
					entries.add(ADItems.WEEPING_HEART_SEEDS);
					entries.add(ADItems.SMOKY_QUARTZ);
				}).build();
	}
}
