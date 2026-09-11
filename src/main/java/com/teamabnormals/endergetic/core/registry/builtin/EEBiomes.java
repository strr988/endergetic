package com.teamabnormals.endergetic.core.registry.builtin;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.registry.EEEntityTypes;
import com.teamabnormals.endergetic.core.registry.EEFeatures.EEPlacedFeatures;
import com.teamabnormals.endergetic.core.registry.EESoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.EndPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = EndergeticExpansion.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class EEBiomes {
	public static final ResourceKey<Biome> POISE_FOREST = createKey("poise_forest");

	public static void bootstrap(BootstrapContext<Biome> context) {
		HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
		HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);

		context.register(POISE_FOREST, poiseForest(features, carvers));
	}

	public static ResourceKey<Biome> createKey(String name) {
		return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, name));
	}

	public static Biome poiseForest(HolderGetter<PlacedFeature> features, HolderGetter<ConfiguredWorldCarver<?>> carvers) {
		return new Biome.BiomeBuilder()
				.hasPrecipitation(false)
				.temperature(0.5F)
				.downfall(0.5F)
				.specialEffects(
						new BiomeSpecialEffects.Builder()
								.skyColor(0)
								.waterColor(4159204)
								.waterFogColor(329011)
								.fogColor(10518688)
								.backgroundMusic(Musics.createGameMusic(Holder.direct(EESoundEvents.POISE_FOREST_MUSIC.get())))
								.ambientLoopSound(Holder.direct(EESoundEvents.POISE_FOREST_LOOP.get()))
								.ambientAdditionsSound(new AmbientAdditionsSettings(Holder.direct(EESoundEvents.POISE_FOREST_ADDITIONS.get()), 0.01D))
								.ambientMoodSound(new AmbientMoodSettings(Holder.direct(EESoundEvents.POISE_FOREST_MOOD.get()), 6000, 8, 2.0D))
								.build()
				)
				.mobSpawnSettings(
						new MobSpawnSettings.Builder()
								.addSpawn(EEEntityTypes.END_CREATURE, new MobSpawnSettings.SpawnerData(EEEntityTypes.BOOFLO_ADOLESCENT.get(), 5, 1, 2))
								.addSpawn(EEEntityTypes.END_CREATURE, new MobSpawnSettings.SpawnerData(EEEntityTypes.BOOFLO.get(), 15, 1, 3))
								.addSpawn(EEEntityTypes.END_CREATURE, new MobSpawnSettings.SpawnerData(EEEntityTypes.PUFF_BUG.get(), 10, 2, 4))
								.creatureGenerationProbability(0.9F)
								.build()
				)
				.generationSettings(
						new BiomeGenerationSettings.Builder(features, carvers)
								.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, EEPlacedFeatures.POISE_DOME)
								.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, EEPlacedFeatures.POISE_TREE)
								.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, EndPlacements.END_GATEWAY_RETURN)
								.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EEPlacedFeatures.POISE_CLUSTER)
								.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EEPlacedFeatures.PUFF_BUG_HIVE)
								.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EEPlacedFeatures.BOLLOOM_BUD)
								.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EEPlacedFeatures.TALL_POISE_BUSH_PATCH)
								.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EEPlacedFeatures.POISE_BUSH_PATCH)
								.build()
				).build();
	}
}