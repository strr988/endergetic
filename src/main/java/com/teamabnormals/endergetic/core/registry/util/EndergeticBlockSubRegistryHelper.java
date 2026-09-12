package com.teamabnormals.endergetic.core.registry.util;

import com.teamabnormals.blueprint.core.util.registry.BlockSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.endergetic.client.EEClientItemExtensions;
import com.teamabnormals.endergetic.common.item.CorrockCrownBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

public final class EndergeticBlockSubRegistryHelper extends BlockSubRegistryHelper {

	public EndergeticBlockSubRegistryHelper(RegistryHelper parent) {
		super(parent);
	}

	public <B extends Block> DeferredBlock<B> createCorrockStandingBlock(String name, Supplier<? extends B> standingSupplier, Supplier<? extends B> wallSupplier) {
		DeferredBlock<B> standingBlock = this.deferredRegister.register(name, standingSupplier);
		this.itemRegister.register(name, () -> new CorrockCrownBlockItem(standingBlock.get(), wallSupplier::get, new Item.Properties()));
		return standingBlock;
	}

	public <B extends Block> DeferredBlock<B> createBolloomBudBlock(String name, Supplier<? extends B> supplier) {
		DeferredBlock<B> block = this.deferredRegister.register(name, supplier);
		var item = this.itemRegister.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
		if (FMLEnvironment.dist == Dist.CLIENT) {
			this.clientItemExtensions.put(item, EEClientItemExtensions.bolloomBud(block));
		}
		return block;
	}

}
