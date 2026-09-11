package com.teamabnormals.endergetic.client.renderer.entity.booflo;

import com.teamabnormals.endergetic.client.model.booflo.BoofloBabyModel;
import com.teamabnormals.endergetic.client.renderer.entity.layers.EmissiveLayerRenderer;
import com.teamabnormals.endergetic.common.entity.booflo.BoofloBaby;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.other.EEModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BoofloBabyRenderer extends MobRenderer<BoofloBaby, BoofloBabyModel<BoofloBaby>> {

	public BoofloBabyRenderer(EntityRendererProvider.Context context) {
		super(context, new BoofloBabyModel<>(context.bakeLayer(EEModelLayers.BOOFLO_BABY)), 0.3F);
		this.addLayer(new EmissiveLayerRenderer<>(this, ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/entity/booflo/booflo_baby_emissive.png")));
	}

	@Override
	public ResourceLocation getTextureLocation(BoofloBaby entity) {
		return ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/entity/booflo/booflo_baby.png");
	}

}