package com.teamabnormals.endergetic.common.network.entity.puffbug;

import com.teamabnormals.endergetic.common.entity.puffbug.PuffBug;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;


/**
 * Message that tells the client to rotate a Puff Bug
 *
 * @author - SmellyModder(Luke Tonon)
 */
public class RotateMessage implements CustomPacketPayload {
	public static final Type<RotateMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("endergetic", "rotate_puffbug"));
	public static final StreamCodec<FriendlyByteBuf, RotateMessage> STREAM_CODEC = StreamCodec.ofMember(RotateMessage::serialize, RotateMessage::deserialize);

	@Override
	public Type<RotateMessage> type() {
		return TYPE;
	}

	private int entityId;
	private int tickLength;
	private float yaw;
	private float pitch;
	private float roll;

	public RotateMessage(int entityId, int tickLength, float yaw, float pitch, float roll) {
		this.entityId = entityId;
		this.tickLength = tickLength;
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
	}

	public void serialize(FriendlyByteBuf buf) {
		buf.writeInt(this.entityId);
		buf.writeInt(this.tickLength);
		buf.writeFloat(this.yaw);
		buf.writeFloat(this.pitch);
		buf.writeFloat(this.roll);
	}

	public static RotateMessage deserialize(FriendlyByteBuf buf) {
		return new RotateMessage(buf.readInt(), buf.readInt(), buf.readFloat(), buf.readFloat(), buf.readFloat());
	}

	public static void handle(RotateMessage message, IPayloadContext context) {
		if (context.flow() == PacketFlow.CLIENTBOUND) {
			context.enqueueWork(() -> {
				Entity entity = context.player().level().getEntity(message.entityId);
				if (entity instanceof PuffBug) {
					((PuffBug) entity).getRotationController().rotate(message.yaw, message.pitch, message.roll, message.tickLength);
				}
			});
		}
	}
}