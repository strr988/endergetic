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
 * Message that requests the server to slam the Booflo the sending player is riding.
 *
 * @author SmellyModder (Luke Tonon)
 */
public final class C2SSlamMessage implements CustomPacketPayload {
	public static final Type<C2SSlamMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("endergetic", "slam_booflo"));
	public static final C2SSlamMessage INSTANCE = new C2SSlamMessage();
	public static final StreamCodec<FriendlyByteBuf, C2SSlamMessage> STREAM_CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public Type<C2SSlamMessage> type() {
		return TYPE;
	}

	public static void handle(C2SSlamMessage message, IPayloadContext context) {
		if (context.flow() == PacketFlow.SERVERBOUND) {
			context.enqueueWork(() -> {
				Player player = context.player();
				if (player != null && !player.isSpectator()) {
					Entity ridingEntity = player.getVehicle();
					if (ridingEntity instanceof Booflo) {
						Booflo booflo = (Booflo) ridingEntity;
						if (booflo.getControllingPassenger() != player) return;
						if (booflo.isBoofed() && booflo.getBoostPower() <= 0 && booflo.isNoEndimationPlaying()) {
							NetworkUtil.setPlayingAnimation(booflo, EEPlayableEndimations.BOOFLO_CHARGE);
							booflo.setBoostExpanding(true);
							booflo.setBoostLocked(true);
						}
					}
				}
			});
		}
	}

}
