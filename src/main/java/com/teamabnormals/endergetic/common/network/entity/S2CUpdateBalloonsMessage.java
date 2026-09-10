package com.teamabnormals.endergetic.common.network.entity;

import com.teamabnormals.endergetic.common.entity.bolloom.BolloomBalloon;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.interfaces.BalloonHolder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public final class S2CUpdateBalloonsMessage implements CustomPacketPayload {
	public static final Type<S2CUpdateBalloonsMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("endergetic", "update_balloons"));
	public static final StreamCodec<FriendlyByteBuf, S2CUpdateBalloonsMessage> STREAM_CODEC = StreamCodec.ofMember(S2CUpdateBalloonsMessage::serialize, S2CUpdateBalloonsMessage::deserialize);

	@Override
	public Type<S2CUpdateBalloonsMessage> type() {
		return TYPE;
	}

	private int entityId;
	private int[] balloonIds;

	private S2CUpdateBalloonsMessage(int entityId, int[] balloonIds) {
		this.entityId = entityId;
		this.balloonIds = balloonIds;
	}

	public S2CUpdateBalloonsMessage(Entity entity) {
		this.entityId = entity.getId();
		List<BolloomBalloon> balloons = ((BalloonHolder) entity).getBalloons();
		this.balloonIds = new int[balloons.size()];
		for (int i = 0; i < balloons.size(); i++) {
			this.balloonIds[i] = balloons.get(i).getId();
		}
	}

	public void serialize(FriendlyByteBuf buf) {
		buf.writeVarInt(this.entityId);
		buf.writeVarIntArray(this.balloonIds);
	}

	public static S2CUpdateBalloonsMessage deserialize(FriendlyByteBuf buf) {
		return new S2CUpdateBalloonsMessage(buf.readVarInt(), buf.readVarIntArray());
	}

	public static void handle(S2CUpdateBalloonsMessage message, IPayloadContext context) {
		if (context.flow() == PacketFlow.CLIENTBOUND) {
			context.enqueueWork(() -> {
				Level world = context.player().level();
				Entity entity = world.getEntity(message.entityId);
				if (entity == null) {
					EndergeticExpansion.LOGGER.warn("Received balloons for unknown entity!");
				} else {
					((BalloonHolder) entity).detachBalloons();
					for (int id : message.balloonIds) {
						Entity balloon = world.getEntity(id);
						if (balloon instanceof BolloomBalloon) {
							((BolloomBalloon) balloon).attachToEntity(entity);
						}
					}
				}
			});
		}
	}
}
