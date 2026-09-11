package com.teamabnormals.endergetic.core.other;

import com.teamabnormals.endergetic.common.entity.bolloom.BalloonColor;
import com.teamabnormals.endergetic.common.entity.eetle.BroodEetle.HealthStage;
import com.teamabnormals.endergetic.common.entity.eetle.flying.TargetFlyingRotations;
import com.teamabnormals.endergetic.common.entity.purpoid.PurpoidSize;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Optional;

public final class EEDataSerializers {
	public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, EndergeticExpansion.MOD_ID);

	public static final EntityDataSerializer<Optional<Vec3>> OPTIONAL_VEC3D = serializer(StreamCodec.of((buf, value) -> {
		buf.writeBoolean(value.isPresent());
		value.ifPresent(vec -> {
			buf.writeDouble(vec.x());
			buf.writeDouble(vec.y());
			buf.writeDouble(vec.z());
		});
	}, buf -> buf.readBoolean() ? Optional.of(new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble())) : Optional.empty()));

	public static final EntityDataSerializer<BalloonColor> BALLOON_COLOR = serializer(enumCodec(BalloonColor.class));
	public static final EntityDataSerializer<TargetFlyingRotations> TARGET_FLYING_ROTATIONS = serializer(StreamCodec.of(
			(buf, value) -> {
				buf.writeFloat(value.getTargetFlyPitch());
				buf.writeFloat(value.getTargetFlyRoll());
			},
			buf -> new TargetFlyingRotations(buf.readFloat(), buf.readFloat())
	));
	public static final EntityDataSerializer<EntityDimensions> ENTITY_SIZE = serializer(StreamCodec.of(
			(buf, value) -> {
				buf.writeFloat(value.width());
				buf.writeFloat(value.height());
				buf.writeBoolean(value.fixed());
			},
			buf -> {
				float width = buf.readFloat();
				float height = buf.readFloat();
				return buf.readBoolean() ? EntityDimensions.fixed(width, height) : EntityDimensions.scalable(width, height);
			}
	));
	public static final EntityDataSerializer<HealthStage> BROOD_HEALTH_STAGE = serializer(enumCodec(HealthStage.class));
	public static final EntityDataSerializer<PurpoidSize> PURPOID_SIZE = serializer(enumCodec(PurpoidSize.class));

	private static <T> EntityDataSerializer<T> serializer(StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
		return EntityDataSerializer.forValueType(codec);
	}

	private static <E extends Enum<E>> StreamCodec<RegistryFriendlyByteBuf, E> enumCodec(Class<E> enumClass) {
		return StreamCodec.of((buf, value) -> buf.writeEnum(value), buf -> buf.readEnum(enumClass));
	}

	static {
		SERIALIZERS.register("optional_vec3d", () -> OPTIONAL_VEC3D);
		SERIALIZERS.register("balloon_color", () -> BALLOON_COLOR);
		SERIALIZERS.register("target_flying_rotations", () -> TARGET_FLYING_ROTATIONS);
		SERIALIZERS.register("entity_size", () -> ENTITY_SIZE);
		SERIALIZERS.register("brood_health_stage", () -> BROOD_HEALTH_STAGE);
		SERIALIZERS.register("purpoid_size", () -> PURPOID_SIZE);
	}
}
