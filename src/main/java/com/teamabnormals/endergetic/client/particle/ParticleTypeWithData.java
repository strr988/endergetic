package com.teamabnormals.endergetic.client.particle;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.function.Function;

public class ParticleTypeWithData<T extends ParticleOptions> extends ParticleType<T> {
	private final Function<ParticleType<T>, MapCodec<T>> codecFactory;
	private final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodecFactory;

	public ParticleTypeWithData(Function<ParticleType<T>, MapCodec<T>> codecFactory, Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodecFactory) {
		super(false);
		this.codecFactory = codecFactory;
		this.streamCodecFactory = streamCodecFactory;
	}

	@Override
	public MapCodec<T> codec() {
		return this.codecFactory.apply(this);
	}

	@Override
	public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
		return this.streamCodecFactory.apply(this);
	}
}
