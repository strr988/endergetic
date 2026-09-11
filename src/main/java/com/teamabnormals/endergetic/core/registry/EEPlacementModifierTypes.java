package com.teamabnormals.endergetic.core.registry;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.endergetic.common.levelgen.placement.HeightmapSpreadDoublePlacement;
import com.teamabnormals.endergetic.common.levelgen.placement.HeightmapSpreadLowerPlacement;
import com.teamabnormals.endergetic.common.levelgen.placement.NoiseHeightmap32Placement;
import com.teamabnormals.endergetic.common.levelgen.placement.NoiseRarityFilter;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class EEPlacementModifierTypes {
	public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.PLACEMENT_MODIFIER_TYPE, EndergeticExpansion.MOD_ID);

	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<HeightmapSpreadDoublePlacement>> HEIGHTMAP_SPREAD_DOUBLE = register("heightmap_spread_double", HeightmapSpreadDoublePlacement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<NoiseHeightmap32Placement>> NOISE_HEIGHTMAP_32 = register("noise_heightmap_32", NoiseHeightmap32Placement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<HeightmapSpreadLowerPlacement>> HEIGHTMAP_SPREAD_LOWER = register("heightmap_spread_lower", HeightmapSpreadLowerPlacement.CODEC);
	public static final DeferredHolder<PlacementModifierType<?>, PlacementModifierType<NoiseRarityFilter>> NOISE_RARITY_FILTER = register("noise_rarity_filter", NoiseRarityFilter.CODEC);

	private static <P extends PlacementModifier> DeferredHolder<PlacementModifierType<?>, PlacementModifierType<P>> register(String name, MapCodec<P> codec) {
		return PLACEMENT_MODIFIER_TYPES.register(name, () -> () -> codec);
	}
}
