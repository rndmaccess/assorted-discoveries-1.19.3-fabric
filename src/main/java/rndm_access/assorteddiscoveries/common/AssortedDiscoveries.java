package rndm_access.assorteddiscoveries.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
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
		FabricItemGroup.builder(ADReference.makeId("plushies"))
				.displayName(Text.literal("Plushies"))
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
				}).build();
	}
}
