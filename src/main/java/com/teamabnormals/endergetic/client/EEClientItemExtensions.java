package com.teamabnormals.endergetic.client;

import com.teamabnormals.blueprint.client.MemoizedBEWLR;
import com.teamabnormals.blueprint.client.renderer.block.TypedBlockEntityWithoutLevelRenderer;
import com.teamabnormals.endergetic.common.block.entity.BolloomBudTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Supplier;

public final class EEClientItemExtensions {
	private EEClientItemExtensions() {
	}

	public static IClientItemExtensions bolloomBud(Supplier<? extends Block> block) {
		return MemoizedBEWLR.asCustomItemRenderer((dispatcher, modelSet) ->
				new TypedBlockEntityWithoutLevelRenderer<>(dispatcher, modelSet, new BolloomBudTileEntity(BlockPos.ZERO, block.get().defaultBlockState())));
	}
}
