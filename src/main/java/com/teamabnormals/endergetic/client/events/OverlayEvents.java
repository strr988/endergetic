package com.teamabnormals.endergetic.client.events;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamabnormals.endergetic.common.entity.booflo.Booflo;
import com.teamabnormals.endergetic.common.entity.eetle.GliderEetle;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = EndergeticExpansion.MOD_ID, value = Dist.CLIENT)
public final class OverlayEvents {
	private static final Minecraft MC = Minecraft.getInstance();
	private static boolean purpoidFlash;
	private static int prevPurpoidFlashTime = 0, purpoidFlashTime = 0;

	@SubscribeEvent
	public static void onClientTick(ClientTickEvent.Pre event) {
		prevPurpoidFlashTime = purpoidFlashTime;
		if (purpoidFlash) {
			if (++purpoidFlashTime >= 5) {
				purpoidFlash = false;
			}
		} else if (purpoidFlashTime > 0) {
			purpoidFlashTime--;
		}
	}

	@SubscribeEvent
	public static void renderOverlays(RenderGuiLayerEvent.Pre event) {
		LocalPlayer player = MC.player;
		if (player != null) {
			if (!MC.options.hideGui) {
				ResourceLocation overlayID = event.getName();
				if (overlayID == VanillaGuiLayers.EXPERIENCE_BAR) {
					if (player.isPassenger() && player.getVehicle() instanceof Booflo) {
						event.setCanceled(true);

						int scaledWidth = MC.getWindow().getGuiScaledWidth();
						int scaledHeight = MC.getWindow().getGuiScaledHeight();
						int top = scaledHeight - 32 + 3;
						int left = scaledWidth / 2 - 91;
						int progress = ((Booflo) player.getVehicle()).getBoostPower();

						PoseStack stack = event.getGuiGraphics().pose();
						stack.pushPose();
						ResourceLocation texture = ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/gui/booflo_bar.png");
						drawTexture(event.getGuiGraphics(), texture, left, top, 0, 0, 182, 5);
						if (progress > 0) {
							drawTexture(event.getGuiGraphics(), texture, left, top, 0, 5, progress, 10);
						}

						stack.popPose();
					}
				} else if (overlayID == VanillaGuiLayers.VEHICLE_HEALTH && player.level().getDifficulty() != Difficulty.PEACEFUL && !player.isSpectator() && !player.isCreative() && player.isPassenger() && player.getVehicle() instanceof GliderEetle) {
					event.setCanceled(true);
				} else if (overlayID == VanillaGuiLayers.CAMERA_OVERLAYS && MC.options.getCameraType() == CameraType.FIRST_PERSON) {
					float purpoidFlashProgress = Mth.lerp(event.getPartialTick().getGameTimeDeltaPartialTick(true), prevPurpoidFlashTime, purpoidFlashTime) * 0.2F;
					if (purpoidFlashProgress > 0.0F) {
						PoseStack stack = event.getGuiGraphics().pose();
						stack.pushPose();
						RenderSystem.disableDepthTest();
						RenderSystem.depthMask(false);
						RenderSystem.enableBlend();
						RenderSystem.defaultBlendFunc();
						RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, purpoidFlashProgress);
						int scaledWidth = MC.getWindow().getGuiScaledWidth();
						int scaledHeight = MC.getWindow().getGuiScaledHeight();
						event.getGuiGraphics().blit(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/gui/overlay/purpoid_flash.png"), 0, 0, scaledWidth, scaledHeight, 0.0F, 0.0F, 256, 256, 256, 256);
						RenderSystem.depthMask(true);
						RenderSystem.enableDepthTest();
						RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
						stack.popPose();
					}
				}
			}
		}
	}

	private static void drawTexture(GuiGraphics guiGraphics, ResourceLocation location, int posX, int posY, int p_blit_3_, int p_blit_4_, int p_blit_5_, int p_blit_6_) {
		guiGraphics.blit(location, posX, posY, -90, (float) p_blit_3_, (float) p_blit_4_, p_blit_5_, p_blit_6_, 256, 256);
	}

	public static void enablePurpoidFlash() {
		OverlayEvents.purpoidFlash = true;
	}
}
