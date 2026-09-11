package com.teamabnormals.endergetic.core.registry;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.endergetic.common.levelgen.biome.modifiers.SmallEndIslandsAmbienceBiomeModifier;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class EEBiomeModifierSerializers {
	public static final DeferredRegister<MapCodec<? extends BiomeModifier>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, EndergeticExpansion.MOD_ID);

	public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<? extends BiomeModifier>> SMALL_END_ISLANDS_AMBIENCE = SERIALIZERS.register("small_end_islands_ambience", () -> SmallEndIslandsAmbienceBiomeModifier.CODEC);
}
