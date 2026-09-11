package com.teamabnormals.endergetic.common.levelgen.biome.modifiers;

import com.mojang.serialization.MapCodec;
import com.teamabnormals.endergetic.core.registry.EESoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public enum SmallEndIslandsAmbienceBiomeModifier implements BiomeModifier {
	INSTANCE;

	public static final MapCodec<SmallEndIslandsAmbienceBiomeModifier> CODEC = MapCodec.unit(INSTANCE);

	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.MODIFY && biome.is(Biomes.SMALL_END_ISLANDS)) {
			builder.getSpecialEffects()
					.ambientLoopSound(Holder.direct(EESoundEvents.SMALL_END_ISLANDS_LOOP.get()))
					.ambientAdditionsSound(new AmbientAdditionsSettings(Holder.direct(EESoundEvents.SMALL_END_ISLANDS_ADDITIONS.get()), 0.0111D));
		}
	}

	@Override
	public MapCodec<? extends BiomeModifier> codec() {
		return CODEC;
	}
}
