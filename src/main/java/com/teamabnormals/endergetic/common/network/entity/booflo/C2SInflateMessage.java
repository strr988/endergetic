package com.teamabnormals.endergetic.common.network.entity.booflo;

import com.teamabnormals.blueprint.core.util.NetworkUtil;
import com.teamabnormals.endergetic.common.entity.booflo.Booflo;
import com.teamabnormals.endergetic.core.other.EEPlayableEndimations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.network.protocol.PacketFlow;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Message that requests the server to begin charging boost power for the Booflo the sending player is riding.
 *
 * @author SmellyModder (Luke Tonon)
 */
public final class C2SInflateMessage implements CustomPacketPayload {
	public static final Type<C2SInflateMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("endergetic", "inflate_booflo"));
	public static final StreamCodec<FriendlyByteBuf, C2SInflateMessage> STREAM_CODEC = StreamCodec.unit(new C2SInflateMessage());

	@Override
	public Type<C2SInflateMessage> type() {
		return TYPE;
	}

	public static void handle(C2SInflateMessage message, IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				Player player = context.player();
				if (player != null && !player.isSpectator()) {
					Entity entity = player.getVehicle();
					if (entity instanceof Booflo) {
						Booflo booflo = (Booflo) entity;
						if (booflo.getControllingPassenger() != player) return;
						if (!booflo.onGround() && !booflo.isBoostLocked() && booflo.getBoostPower() <= 0) {
							if (!booflo.isBoofed()) {
								booflo.setBoostLocked(true);
								booflo.setBoofed(true);
								NetworkUtil.setPlayingAnimation(booflo, EEPlayableEndimations.BOOFLO_INFLATE);
							}
							booflo.setBoostExpanding(true);
						}
					}
				}
			});
		}
	}

}