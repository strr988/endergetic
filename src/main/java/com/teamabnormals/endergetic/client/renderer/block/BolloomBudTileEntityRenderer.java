package com.teamabnormals.endergetic.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.teamabnormals.endergetic.client.model.bolloom.BolloomBudModel;
import com.teamabnormals.endergetic.common.block.entity.BolloomBudTileEntity;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.other.EEModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;

public class BolloomBudTileEntityRenderer implements BlockEntityRenderer<BolloomBudTileEntity> {
	private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/tile/bolloom_bud.png");
	private BolloomBudModel budModel;

	public BolloomBudTileEntityRenderer(BlockEntityRendererProvider.Context context) {
		this.budModel = new BolloomBudModel(context.bakeLayer(EEModelLayers.BOLLOOM_BUD));
	}

	@Override
	public void render(BolloomBudTileEntity bud, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
		matrixStack.pushPose();
		matrixStack.translate(0.5D, 1.5D, 0.5D);
		matrixStack.scale(1.0F, -1.0F, -1.0F);

		VertexConsumer ivertexbuilder = bufferIn.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
		this.budModel.renderAll(bud, matrixStack, ivertexbuilder, combinedLightIn, combinedOverlayIn);

		matrixStack.popPose();
	}

	@Override
	public int getViewDistance() {
		return 256;
	}

	@Override
	public AABB getRenderBoundingBox(BolloomBudTileEntity blockEntity) {
		return AABB.INFINITE;
	}
}
