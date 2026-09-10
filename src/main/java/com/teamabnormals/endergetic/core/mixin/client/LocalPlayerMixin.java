package com.teamabnormals.endergetic.core.mixin.client;

import com.mojang.authlib.GameProfile;
import com.teamabnormals.endergetic.common.entity.booflo.Booflo;
import com.teamabnormals.endergetic.common.network.entity.booflo.C2SBoostMessage;
import com.teamabnormals.endergetic.common.network.entity.booflo.C2SInflateMessage;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LocalPlayer.class)
public final class LocalPlayerMixin extends AbstractClientPlayer {
	@Shadow
	public Input input;

	private LocalPlayerMixin(ClientLevel level, GameProfile profile) {
		super(level, profile);
	}

	/**
	 * Forge's input event gets fired after the previous input values are updated making it difficult to tell if a key was being pressed prior to the event being fired.
	 *
	 * @param flag wasJumping boolean.
	 */
	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/Input;tick(ZF)V", shift = At.Shift.AFTER), method = "aiStep", locals = LocalCapture.CAPTURE_FAILSOFT)
	private void onTickMovementInput(CallbackInfo info, boolean flag) {
		Entity ridingEntity = this.getVehicle();
		if (ridingEntity instanceof Booflo booflo) {
			if (!booflo.onGround()) {
				if (!flag && this.input.jumping) {
					PacketDistributor.sendToServer(new C2SInflateMessage());
				} else if (!this.input.jumping) {
					if (booflo.isBoostExpanding() && booflo.isBoofed() && !booflo.isBoostLocked() && booflo.getBoostPower() > 0) {
						PacketDistributor.sendToServer(new C2SBoostMessage());
					}
				}
			}
		}
	}
}
