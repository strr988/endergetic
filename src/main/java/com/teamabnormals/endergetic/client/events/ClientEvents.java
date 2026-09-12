package com.teamabnormals.endergetic.client.events;

import com.teamabnormals.blueprint.client.ClientInfo;
import com.teamabnormals.blueprint.core.util.EntityUtil;
import com.teamabnormals.endergetic.common.entity.bolloom.BolloomBalloon;
import com.teamabnormals.endergetic.common.item.BolloomBalloonItem;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.interfaces.BalloonHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

import java.util.List;

@EventBusSubscriber(modid = EndergeticExpansion.MOD_ID, value = Dist.CLIENT)
public final class ClientEvents {
	@SubscribeEvent
	public static void onPlayerSwing(InputEvent.InteractionKeyMappingTriggered event) {
		if (event.isAttack()) {
			LocalPlayer player = ClientInfo.getClientPlayer();
			if (player.getXRot() > -25.0F) return;
			Entity ridingEntity = player.getVehicle();
			if (ridingEntity instanceof Boat && BolloomBalloonItem.hasNoEntityTarget(player) && EntityUtil.rayTrace(player, BolloomBalloonItem.getPlayerReach(player), 1.0F).getType() == Type.MISS) {
				List<BolloomBalloon> balloons = ((BalloonHolder) ridingEntity).getBalloons();
				if (!balloons.isEmpty()) {
					Minecraft.getInstance().gameMode.attack(player, balloons.get(player.getRandom().nextInt(balloons.size())));
					event.setSwingHand(true);
				}
			}
		}
	}
}
