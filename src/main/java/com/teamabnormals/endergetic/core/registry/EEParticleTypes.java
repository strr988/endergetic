package com.teamabnormals.endergetic.core.registry;

import com.google.common.base.Function;
import com.mojang.serialization.MapCodec;
import com.teamabnormals.endergetic.client.particle.CorrockCrownParticle;
import com.teamabnormals.endergetic.client.particle.FastBlockParticle.Factory;
import com.teamabnormals.endergetic.client.particle.ParticleTypeWithData;
import com.teamabnormals.endergetic.client.particle.PoiseBubbleParticle;
import com.teamabnormals.endergetic.client.particle.data.CorrockCrownParticleData;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EEParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, EndergeticExpansion.MOD_ID);

	public static final DeferredHolder<?, SimpleParticleType> POISE_BUBBLE = createBasicParticleType("poise_bubble", true);
	public static final DeferredHolder<?, SimpleParticleType> SHORT_POISE_BUBBLE = createBasicParticleType("short_poise_bubble", true);
	public static final DeferredHolder<?, SimpleParticleType> ENDER_FIRE_FLAME = createBasicParticleType("ender_fire_flame", true);
	public static final DeferredHolder<?, SimpleParticleType> SMALL_ENDER_FIRE_FLAME = createBasicParticleType("small_ender_fire_flame", false);
	public static final DeferredHolder<?, ParticleType<BlockParticleOption>> FAST_BLOCK = createParticleType("fast_block", BlockParticleOption::codec, BlockParticleOption::streamCodec);
	public static final DeferredHolder<?, ParticleType<CorrockCrownParticleData>> OVERWORLD_CROWN = createParticleType("overworld_crown", CorrockCrownParticleData::codec, CorrockCrownParticleData::streamCodec);
	public static final DeferredHolder<?, ParticleType<CorrockCrownParticleData>> NETHER_CROWN = createParticleType("nether_crown", CorrockCrownParticleData::codec, CorrockCrownParticleData::streamCodec);
	public static final DeferredHolder<?, ParticleType<CorrockCrownParticleData>> END_CROWN = createParticleType("end_crown", CorrockCrownParticleData::codec, CorrockCrownParticleData::streamCodec);

	private static DeferredHolder<?, SimpleParticleType> createBasicParticleType(String name, boolean alwaysShow) {
		return PARTICLES.register(name, () -> new SimpleParticleType(alwaysShow));
	}

	private static <T extends ParticleOptions> DeferredHolder<?, ParticleType<T>> createParticleType(String name, Function<ParticleType<T>, MapCodec<T>> codecFactory, Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodecFactory) {
		return PARTICLES.register(name, () -> new ParticleTypeWithData<>(codecFactory, streamCodecFactory));
	}

	@OnlyIn(Dist.CLIENT)
	@EventBusSubscriber(modid = EndergeticExpansion.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class RegisterParticleFactories {

		@SubscribeEvent(priority = EventPriority.LOWEST)
		public static void registerParticleTypes(RegisterParticleProvidersEvent event) {
			event.registerSpriteSet(ENDER_FIRE_FLAME.get(), FlameParticle.Provider::new);
			event.registerSpriteSet(SMALL_ENDER_FIRE_FLAME.get(), FlameParticle.SmallFlameProvider::new);
			event.registerSpriteSet(POISE_BUBBLE.get(), PoiseBubbleParticle.Factory::new);
			event.registerSpriteSet(SHORT_POISE_BUBBLE.get(), PoiseBubbleParticle.ShortFactory::new);
			event.registerSpecial(FAST_BLOCK.get(), new Factory());
			event.registerSpriteSet(OVERWORLD_CROWN.get(), CorrockCrownParticle.Factory::new);
			event.registerSpriteSet(NETHER_CROWN.get(), CorrockCrownParticle.Factory::new);
			event.registerSpriteSet(END_CROWN.get(), CorrockCrownParticle.Factory::new);
		}
	}
}
