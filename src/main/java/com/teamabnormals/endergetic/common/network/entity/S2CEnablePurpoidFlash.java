package com.teamabnormals.endergetic.common.network.entity;

import com.teamabnormals.endergetic.client.events.OverlayEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public final class S2CEnablePurpoidFlash implements CustomPacketPayload {
	public static final Type<S2CEnablePurpoidFlash> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("endergetic", "enable_purpoid_flash"));
	public static final S2CEnablePurpoidFlash INSTANCE = new S2CEnablePurpoidFlash();
	public static final StreamCodec<FriendlyByteBuf, S2CEnablePurpoidFlash> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public Type<S2CEnablePurpoidFlash> type() {
		return TYPE;
	}

	public static void handle(S2CEnablePurpoidFlash message, IPayloadContext context) {
		if (context.flow() == PacketFlow.CLIENTBOUND) {
			context.enqueueWork(OverlayEvents::enablePurpoidFlash);
		}
	}

}
