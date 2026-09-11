package com.teamabnormals.endergetic.core.registry.builtin;

import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.common.world.modification.ModdedBiomeSlice;
import com.teamabnormals.blueprint.core.registry.BlueprintDataPackRegistries;
import com.teamabnormals.blueprint.core.util.BiomeUtil.OverlayModdedBiomeProvider;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.LevelStem;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EEBiomeSlices {
	public static final ResourceKey<ModdedBiomeSlice> POISE_FOREST = createKey("poise_forest");

	public static void bootstrap(BootstrapContext<ModdedBiomeSlice> context) {
		HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
		context.register(POISE_FOREST, new ModdedBiomeSlice(40,
				new OverlayModdedBiomeProvider(List.of(
						Pair.of(HolderSet.direct(Stream.of(Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS, Biomes.END_BARRENS).map(biomes::getOrThrow
						).collect(Collectors.toList())), new FixedBiomeSource(Holder.direct(biomes.getOrThrow(EEBiomes.POISE_FOREST)).get()))
				)), LevelStem.END));
	}

	public static ResourceKey<ModdedBiomeSlice> createKey(String name) {
		return ResourceKey.create(BlueprintDataPackRegistries.MODDED_BIOME_SLICES, ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, name));
	}
}
