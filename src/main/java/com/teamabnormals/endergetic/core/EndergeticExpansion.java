package com.teamabnormals.endergetic.core;

import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.endergetic.common.network.C2SInflateBoofloVestMessage;
import com.teamabnormals.endergetic.common.advancement.EECriteriaTriggers;
import com.teamabnormals.endergetic.common.network.entity.S2CEnablePurpoidFlash;
import com.teamabnormals.endergetic.common.network.entity.S2CUpdateBalloonsMessage;
import com.teamabnormals.endergetic.common.network.entity.booflo.C2SBoostMessage;
import com.teamabnormals.endergetic.common.network.entity.booflo.C2SInflateMessage;
import com.teamabnormals.endergetic.common.network.entity.booflo.C2SSlamMessage;
import com.teamabnormals.endergetic.common.network.entity.puffbug.RotateMessage;
import com.teamabnormals.endergetic.core.data.client.EEBlockStateProvider;
import com.teamabnormals.endergetic.core.data.client.EEEndimationProvider;
import com.teamabnormals.endergetic.core.data.server.EEAdvancementProvider;
import com.teamabnormals.endergetic.core.data.server.EEDataMapProvider;
import com.teamabnormals.endergetic.core.data.server.EEDatapackBuiltinEntriesProvider;
import com.teamabnormals.endergetic.core.data.server.EELootTableProvider;
import com.teamabnormals.endergetic.core.data.server.EERecipeProvider;
import com.teamabnormals.endergetic.core.data.server.modifiers.EEAdvancementModifierProvider;
import com.teamabnormals.endergetic.core.data.server.modifiers.EEChunkGeneratorModifierProvider;
import com.teamabnormals.endergetic.core.data.server.modifiers.EELootModifierProvider;
import com.teamabnormals.endergetic.core.data.server.tags.EEBiomeTagsProvider;
import com.teamabnormals.endergetic.core.data.server.tags.EEBlockTagsProvider;
import com.teamabnormals.endergetic.core.data.server.tags.EEEntityTypeTagsProvider;
import com.teamabnormals.endergetic.core.data.server.tags.EEItemTagsProvider;
import com.teamabnormals.endergetic.core.other.*;
import com.teamabnormals.endergetic.core.registry.*;
import com.teamabnormals.endergetic.core.registry.EEStructureTypes.EEStructurePieceTypes;
import com.teamabnormals.endergetic.core.registry.util.EndergeticBlockSubRegistryHelper;
import com.teamabnormals.endergetic.core.registry.util.EndergeticEntitySubRegistryHelper;
import com.teamabnormals.endergetic.core.registry.util.EndergeticItemSubRegistryHelper;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.minecraft.core.registries.Registries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(value = EndergeticExpansion.MOD_ID)
public class EndergeticExpansion {
	public static final String MOD_ID = "endergetic";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID.toUpperCase());
	public static final String NETWORK_PROTOCOL = "EE1";
	public static EndergeticExpansion instance;
	public static final RegistryHelper REGISTRY_HELPER = RegistryHelper.create(MOD_ID, helper -> {
		helper.putSubHelper(Registries.ITEM, new EndergeticItemSubRegistryHelper(helper));
		helper.putSubHelper(Registries.BLOCK, new EndergeticBlockSubRegistryHelper(helper));
		helper.putSubHelper(Registries.ENTITY_TYPE, new EndergeticEntitySubRegistryHelper(helper));
	});

	public EndergeticExpansion(IEventBus bus, ModContainer context) {
		NeoForge.EVENT_BUS.addListener(EEMobEffects::registerBrewingRecipes);

		instance = this;

		bus.addListener(this::registerPayloadHandlers);
		EEDataProcessors.registerTrackedData();
		EEPlayableEndimations.bootstrap();

		EEBlocks.bootstrap();
		EEEntityTypes.bootstrap();
		EEItems.bootstrap();
		EEBlockEntityTypes.bootstrap();
		EESoundEvents.bootstrap();
		REGISTRY_HELPER.register(bus);
		EECriteriaTriggers.TRIGGERS.register(bus);
		EEArmorMaterials.ARMOR_MATERIALS.register(bus);
		EEParticleTypes.PARTICLES.register(bus);
		EEMobEffects.MOB_EFFECTS.register(bus);
		EEMobEffects.POTIONS.register(bus);
		EESurfaceRules.RULES.register(bus);
		EEPlacementModifierTypes.PLACEMENT_MODIFIER_TYPES.register(bus);
		EEFeatures.FEATURES.register(bus);
		EEStructureTypes.STRUCTURE_TYPES.register(bus);
		EEStructurePieceTypes.STRUCTURE_PIECE_TYPES.register(bus);
		EEDataSerializers.SERIALIZERS.register(bus);
		EEBiomeModifierSerializers.SERIALIZERS.register(bus);

		if (FMLEnvironment.dist == Dist.CLIENT) {
			com.teamabnormals.endergetic.client.EEClient.register(bus);
		}

		bus.addListener(EventPriority.LOWEST, this::commonSetup);
		bus.addListener(this::dataSetup);

		context.registerConfig(ModConfig.Type.COMMON, EEConfig.COMMON_SPEC);
	}

	private void commonSetup(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EECompat.registerCompat();
		});
	}

	private void dataSetup(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		PackOutput output = generator.getPackOutput();
		CompletableFuture<Provider> provider = event.getLookupProvider();
		ExistingFileHelper helper = event.getExistingFileHelper();

		boolean server = event.includeServer();
		EEDatapackBuiltinEntriesProvider datapackEntries = new EEDatapackBuiltinEntriesProvider(output, provider);
		generator.addProvider(server, datapackEntries);
		provider = datapackEntries.getRegistryProvider();
		generator.addProvider(server, new EEChunkGeneratorModifierProvider(output, provider));
		generator.addProvider(server, new EERecipeProvider(output, provider));
		generator.addProvider(server, new EELootTableProvider(output, provider));
		generator.addProvider(server, new EEAdvancementModifierProvider(output, provider));
		generator.addProvider(server, new EELootModifierProvider(output, provider));
		generator.addProvider(server, new EEDataMapProvider(output, provider));
		EEBlockTagsProvider blockTags = new EEBlockTagsProvider(output, provider, helper);
		generator.addProvider(server, blockTags);
		generator.addProvider(server, new EEItemTagsProvider(output, provider, blockTags.contentsGetter(), helper));
		generator.addProvider(server, new EEBiomeTagsProvider(output, provider, helper));
		generator.addProvider(server, new EEEntityTypeTagsProvider(output, provider, helper));
		generator.addProvider(server, EEAdvancementProvider.create(output, provider, helper));

		boolean client = event.includeClient();
		generator.addProvider(client, new EEEndimationProvider(output));
		generator.addProvider(client, new EEBlockStateProvider(output, helper));
	}

	private void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar(NETWORK_PROTOCOL);
		registrar.playToServer(C2SInflateMessage.TYPE, C2SInflateMessage.STREAM_CODEC, C2SInflateMessage::handle);
		registrar.playToServer(C2SBoostMessage.TYPE, C2SBoostMessage.STREAM_CODEC, C2SBoostMessage::handle);
		registrar.playToServer(C2SSlamMessage.TYPE, C2SSlamMessage.STREAM_CODEC, C2SSlamMessage::handle);
		registrar.playToClient(RotateMessage.TYPE, RotateMessage.STREAM_CODEC, RotateMessage::handle);
		registrar.playToClient(S2CUpdateBalloonsMessage.TYPE, S2CUpdateBalloonsMessage.STREAM_CODEC, S2CUpdateBalloonsMessage::handle);
		registrar.playToServer(C2SInflateBoofloVestMessage.TYPE, C2SInflateBoofloVestMessage.STREAM_CODEC, C2SInflateBoofloVestMessage::handle);
		registrar.playToClient(S2CEnablePurpoidFlash.TYPE, S2CEnablePurpoidFlash.STREAM_CODEC, S2CEnablePurpoidFlash::handle);
	}
}
