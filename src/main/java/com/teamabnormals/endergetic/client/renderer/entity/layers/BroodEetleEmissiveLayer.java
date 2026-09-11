package com.teamabnormals.endergetic.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.blueprint.client.BlueprintRenderTypes;
import com.teamabnormals.endergetic.common.entity.eetle.BroodEetle;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BroodEetleEmissiveLayer<E extends BroodEetle, M extends EntityModel<E>> extends RenderLayer<E, M> {
	private static final ResourceLocation EMISSIVE_TEXTURE = ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/entity/eetle/brood/brood_eetle_emissive.png");

	public BroodEetleEmissiveLayer(RenderLayerParent<E, M> entityRendererIn) {
		super(entityRendererIn);
	}

	@Override
	public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, E brood, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		M model = this.getParentModel();
		model.setupAnim(brood, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		VertexConsumer ivertexbuilder = buffer.getBuffer(BlueprintRenderTypes.getUnshadedCutoutEntity(EMISSIVE_TEXTURE, true));
		int pulseAnimation = (int) ((0.5F * Mth.cos(Mth.PI * 0.1F * (brood.deathTime + partialTicks)) + 0.5F) * 240);
		model.renderToBuffer(matrixStack, ivertexbuilder, pulseAnimation, OverlayTexture.NO_OVERLAY, -1);
	}
}
