package com.teamabnormals.endergetic.core.mixin;

import com.teamabnormals.endergetic.core.registry.EEBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public final class SwordItemMixin {

	@Inject(at = @At(value = "HEAD"), method = "getDestroySpeed", cancellable = true)
	private void modifyDestroySpeed(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> info) {
		if ((Object) this instanceof SwordItem && state.is(EEBlocks.EETLE_EGG.get())) {
			info.setReturnValue(15.0F);
		}
	}
}
