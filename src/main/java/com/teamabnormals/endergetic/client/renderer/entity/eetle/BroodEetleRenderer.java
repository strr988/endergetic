package com.teamabnormals.endergetic.client.renderer.entity.eetle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.teamabnormals.endergetic.client.model.eetle.BroodEetleModel;
import com.teamabnormals.endergetic.client.renderer.entity.layers.BroodEetleEmissiveLayer;
import com.teamabnormals.endergetic.common.entity.eetle.BroodEetle;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.other.EEModelLayers;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class BroodEetleRenderer extends MobRenderer<BroodEetle, BroodEetleModel> {
	private static final ResourceLocation[] TEXTURES = getStageTextures();

	public BroodEetleRenderer(EntityRendererProvider.Context context) {
		super(context, new BroodEetleModel(context.bakeLayer(EEModelLayers.BROOD_EETLE)), 1.0F);
		this.addLayer(new BroodEetleEmissiveLayer<>(this));
	}

	@Override
	protected void setupRotations(BroodEetle broodEetle, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks) {
		matrixStackIn.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
		if (broodEetle.hasCustomName()) {
			String name = ChatFormatting.stripFormatting(broodEetle.getName().getString());
			if (name.equals("Dinnerbone") || name.equals("Grum")) {
				matrixStackIn.translate(0.0D, broodEetle.getBbHeight() + 0.1D, 0.0D);
				matrixStackIn.mulPose(Axis.ZP.rotationDegrees(180.0F));
			}
		}
	}

	@Override
	public ResourceLocation getTextureLocation(BroodEetle entity) {
		return TEXTURES[entity.getHealthStage().ordinal()];
	}

	private static ResourceLocation[] getStageTextures() {
		List<ResourceLocation> textures = new ArrayList<>();
		for (BroodEetle.HealthStage stage : BroodEetle.HealthStage.values()) {
			textures.add(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/entity/eetle/brood/brood_eetle_" + stage.ordinal() + ".png"));
		}
		return textures.toArray(new ResourceLocation[0]);
	}
}
