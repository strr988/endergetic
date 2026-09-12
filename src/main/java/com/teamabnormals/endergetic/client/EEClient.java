package com.teamabnormals.endergetic.client;

import com.teamabnormals.endergetic.client.model.BoofBlockDispenserModel;
import com.teamabnormals.endergetic.client.model.BoofBlockModel;
import com.teamabnormals.endergetic.client.model.PoiseClusterModel;
import com.teamabnormals.endergetic.client.model.PuffBugModel;
import com.teamabnormals.endergetic.client.model.bolloom.BolloomBalloonModel;
import com.teamabnormals.endergetic.client.model.bolloom.BolloomBudModel;
import com.teamabnormals.endergetic.client.model.bolloom.BolloomFruitModel;
import com.teamabnormals.endergetic.client.model.bolloom.BolloomKnotModel;
import com.teamabnormals.endergetic.client.model.booflo.AdolescentBoofloModel;
import com.teamabnormals.endergetic.client.model.booflo.BoofloBabyModel;
import com.teamabnormals.endergetic.client.model.booflo.BoofloModel;
import com.teamabnormals.endergetic.client.model.corrock.CorrockCrownStandingModel;
import com.teamabnormals.endergetic.client.model.corrock.CorrockCrownWallModel;
import com.teamabnormals.endergetic.client.model.eetle.BroodEetleModel;
import com.teamabnormals.endergetic.client.model.eetle.ChargerEetleModel;
import com.teamabnormals.endergetic.client.model.eetle.GliderEetleModel;
import com.teamabnormals.endergetic.client.model.eetle.LeetleModel;
import com.teamabnormals.endergetic.client.model.eetle.eggs.LargeEetleEggModel;
import com.teamabnormals.endergetic.client.model.eetle.eggs.MediumEetleEggModel;
import com.teamabnormals.endergetic.client.model.eetle.eggs.SmallEetleEggModel;
import com.teamabnormals.endergetic.client.model.purpoid.PurpModel;
import com.teamabnormals.endergetic.client.model.purpoid.PurpazoidModel;
import com.teamabnormals.endergetic.client.model.purpoid.PurpoidGelModel;
import com.teamabnormals.endergetic.client.model.purpoid.PurpoidModel;
import com.teamabnormals.endergetic.client.renderer.block.BolloomBudTileEntityRenderer;
import com.teamabnormals.endergetic.client.renderer.block.CorrockCrownTileEntityRenderer;
import com.teamabnormals.endergetic.client.renderer.block.DispensedBoofBlockTileEntityRenderer;
import com.teamabnormals.endergetic.client.renderer.block.EetleEggTileEntityRenderer;
import com.teamabnormals.endergetic.client.renderer.entity.*;
import com.teamabnormals.endergetic.client.renderer.entity.booflo.BoofloAdolescentRenderer;
import com.teamabnormals.endergetic.client.renderer.entity.booflo.BoofloBabyRenderer;
import com.teamabnormals.endergetic.client.renderer.entity.booflo.BoofloRenderer;
import com.teamabnormals.endergetic.client.renderer.entity.eetle.*;
import com.teamabnormals.blueprint.core.util.BiomeUtil;
import com.teamabnormals.endergetic.core.keybinds.KeybindHandler;
import com.teamabnormals.endergetic.core.other.EEClientCompat;
import com.teamabnormals.endergetic.core.other.EEModelLayers;
import com.teamabnormals.endergetic.core.registry.*;
import com.teamabnormals.endergetic.core.registry.builtin.EEBiomes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import static com.teamabnormals.endergetic.core.EndergeticExpansion.MOD_ID;

public final class EEClient {
	public static void register(IEventBus bus) {
		EEItems.setupTabEditors();
		EEBlocks.setupTabEditors();
		bus.addListener(EventPriority.LOWEST, EEClient::clientSetup);
		bus.addListener(KeybindHandler::registerKeys);
		bus.addListener(EEClient::registerLayerDefinitions);
		bus.addListener(EEClient::registerRenderers);
		bus.addListener(EEClient::registerClientExtensions);
	}

	private static void registerClientExtensions(RegisterClientExtensionsEvent event) {
		event.registerItem(EEClientItemExtensions.boofloVest(), EEItems.BOOFLO_VEST);
	}

	private static void clientSetup(FMLClientSetupEvent event) {
		EEClientCompat.registerClientCompat();
		EndCrystalRenderer.RENDER_TYPE = RenderType.entityCutoutNoCull(ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/entity/end_crystal.png"));
		BiomeUtil.markEndBiomeCustomMusic(EEBiomes.POISE_FOREST);
	}

	private static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(EEModelLayers.CORROCK_CROWN_STANDING, CorrockCrownStandingModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.CORROCK_CROWN_WALL, CorrockCrownWallModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.BOLLOOM_BUD, BolloomBudModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.BOOF_BLOCK_DISPENSED, BoofBlockDispenserModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.SMALL_EETLE_EGG, SmallEetleEggModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.MEDIUM_EETLE_EGG, MediumEetleEggModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.LARGE_EETLE_EGG, LargeEetleEggModel::createLayerDefinition);

		event.registerLayerDefinition(EEModelLayers.BOLLOOM_FRUIT, BolloomFruitModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.POISE_CLUSTER, PoiseClusterModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.BOOF_BLOCK, BoofBlockModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.BOLLOOM_KNOT, BolloomKnotModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.BOLLOOM_BALLOON, BolloomBalloonModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.PUFF_BUG, PuffBugModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.BOOFLO_BABY, BoofloBabyModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.ADOLESCENT_BOOFLO, AdolescentBoofloModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.BOOFLO, BoofloModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.LEETLE, LeetleModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.CHARGER_EETLE, ChargerEetleModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.GLIDER_EETLE, GliderEetleModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.BROOD_EETLE, BroodEetleModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.PURPOID_GEL, PurpoidGelModel::createPurpoidLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.PURP_GEL, PurpoidGelModel::createPurpLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.PURPAZOID_GEL, PurpoidGelModel::createPurpazoidLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.PURPOID, PurpoidModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.PURP, PurpModel::createLayerDefinition);
		event.registerLayerDefinition(EEModelLayers.PURPAZOID, PurpazoidModel::createLayerDefinition);
	}

	private static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerBlockEntityRenderer(EEBlockEntityTypes.CORROCK_CROWN.get(), CorrockCrownTileEntityRenderer::new);
		event.registerBlockEntityRenderer(EEBlockEntityTypes.BOLLOOM_BUD.get(), BolloomBudTileEntityRenderer::new);
		event.registerBlockEntityRenderer(EEBlockEntityTypes.BOOF_BLOCK_DISPENSED.get(), DispensedBoofBlockTileEntityRenderer::new);
		event.registerBlockEntityRenderer(EEBlockEntityTypes.ENDER_CAMPFIRE.get(), CampfireRenderer::new);
		event.registerBlockEntityRenderer(EEBlockEntityTypes.EETLE_EGG.get(), EetleEggTileEntityRenderer::new);

		event.registerEntityRenderer(EEEntityTypes.BOLLOOM_FRUIT.get(), BolloomFruitRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.POISE_CLUSTER.get(), PoiseClusterRender::new);
		event.registerEntityRenderer(EEEntityTypes.BOOF_BLOCK.get(), BoofBlockRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.BOLLOOM_KNOT.get(), BolloomKnotRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.BOLLOOM_BALLOON.get(), BolloomBalloonRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.PUFF_BUG.get(), PuffBugRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.BOOFLO_BABY.get(), BoofloBabyRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.BOOFLO_ADOLESCENT.get(), BoofloAdolescentRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.BOOFLO.get(), BoofloRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.CHARGER_EETLE.get(), ChargerEetleRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.GLIDER_EETLE.get(), GliderEetleRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.BROOD_EETLE.get(), BroodEetleRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.EETLE_EGG.get(), EetleEggRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.BROOD_EGG_SACK.get(), BroodEggSackRenderer::new);
		event.registerEntityRenderer(EEEntityTypes.PURPOID.get(), PurpoidRenderer::new);
	}

}
