package com.teamabnormals.endergetic.client.renderer.entity.eetle;

import com.teamabnormals.endergetic.client.model.eetle.GliderEetleModel;
import com.teamabnormals.endergetic.common.entity.eetle.GliderEetle;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.other.EEModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;

public class GliderEetleRenderer extends AbstractEetleRenderer<GliderEetle> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/entity/eetle/glider_eetle.png");
	private static final ResourceLocation EMISSIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/entity/eetle/glider_eetle_emissive.png");

	public GliderEetleRenderer(EntityRendererProvider.Context context) {
		super(context, new GliderEetleModel(context.bakeLayer(EEModelLayers.GLIDER_EETLE)), EMISSIVE_TEXTURE, 0.75F);
	}

	@Nonnull
	@Override
	protected ResourceLocation getAdultTexture() {
		return TEXTURE;
	}
}
