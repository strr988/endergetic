package com.teamabnormals.endergetic.client;

import com.teamabnormals.blueprint.client.MemoizedBEWLR;
import com.teamabnormals.blueprint.client.renderer.block.TypedBlockEntityWithoutLevelRenderer;
import com.teamabnormals.endergetic.common.block.entity.BolloomBudTileEntity;
import com.teamabnormals.endergetic.common.item.BoofloVestItem;
import com.teamabnormals.endergetic.client.model.armor.BoofloVestModel;
import net.minecraft.core.BlockPos;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public final class EEClientItemExtensions {
	private EEClientItemExtensions() {
	}

	public static IClientItemExtensions bolloomBud(Supplier<? extends Block> block) {
		return MemoizedBEWLR.asCustomItemRenderer((dispatcher, modelSet) ->
				new TypedBlockEntityWithoutLevelRenderer<>(dispatcher, modelSet, new BolloomBudTileEntity(BlockPos.ZERO, block.get().defaultBlockState())));
	}

	public static IClientItemExtensions boofloVest() {
		return new IClientItemExtensions() {
			@Override
			@NotNull
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack stack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				return BoofloVestItem.getTag(stack).getBoolean(BoofloVestItem.BOOFED_TAG) ? BoofloVestModel.INSTANCE : original;
			}
		};
	}
}
