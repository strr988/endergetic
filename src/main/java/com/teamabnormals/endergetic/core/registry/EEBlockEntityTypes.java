package com.teamabnormals.endergetic.core.registry;

import com.teamabnormals.blueprint.core.util.registry.BlockEntitySubRegistryHelper;
import com.teamabnormals.endergetic.common.block.entity.*;
import com.teamabnormals.endergetic.common.block.entity.boof.BoofBlockTileEntity;
import com.teamabnormals.endergetic.common.block.entity.boof.DispensedBlockBoofTileEntity;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;

public final class EEBlockEntityTypes {
	private static final BlockEntitySubRegistryHelper HELPER = EndergeticExpansion.REGISTRY_HELPER.getBlockEntitySubHelper();

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CorrockCrownTileEntity>> CORROCK_CROWN = HELPER.createBlockEntity("corrock_crown", CorrockCrownTileEntity::new, () -> Set.of(
			EEBlocks.OVERWORLD_CORROCK_CROWN.get(), EEBlocks.OVERWORLD_WALL_CORROCK_CROWN.get(), EEBlocks.NETHER_CORROCK_CROWN.get(),
			EEBlocks.NETHER_WALL_CORROCK_CROWN.get(), EEBlocks.END_CORROCK_CROWN.get(), EEBlocks.END_WALL_CORROCK_CROWN.get(),
			EEBlocks.PETRIFIED_OVERWORLD_CORROCK_CROWN.get(), EEBlocks.PETRIFIED_OVERWORLD_WALL_CORROCK_CROWN.get(), EEBlocks.PETRIFIED_NETHER_CORROCK_CROWN.get(),
			EEBlocks.PETRIFIED_NETHER_WALL_CORROCK_CROWN.get(), EEBlocks.PETRIFIED_END_CORROCK_CROWN.get(), EEBlocks.PETRIFIED_END_WALL_CORROCK_CROWN.get()
	));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EnderCampfireTileEntity>> ENDER_CAMPFIRE = HELPER.createBlockEntity("ender_campfire", EnderCampfireTileEntity::new, () -> Set.of(EEBlocks.ENDER_CAMPFIRE.get()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EetleEggTileEntity>> EETLE_EGG = HELPER.createBlockEntity("eetle_egg", EetleEggTileEntity::new, () -> Set.of(EEBlocks.EETLE_EGG.get()));

	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BolloomBudTileEntity>> BOLLOOM_BUD = HELPER.createBlockEntity("bolloom_bud", BolloomBudTileEntity::new, () -> Set.of(EEBlocks.BOLLOOM_BUD.get()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PuffBugHiveTileEntity>> PUFFBUG_HIVE = HELPER.createBlockEntity("puffbug_hive", PuffBugHiveTileEntity::new, () -> Set.of(EEBlocks.PUFFBUG_HIVE.get()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BoofBlockTileEntity>> BOOF_BLOCK = HELPER.createBlockEntity("boof_block", BoofBlockTileEntity::new, () -> Set.of(EEBlocks.BOOF_BLOCK.get()));
	public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DispensedBlockBoofTileEntity>> BOOF_BLOCK_DISPENSED = HELPER.createBlockEntity("boof_block_dispensed", DispensedBlockBoofTileEntity::new, () -> Set.of(EEBlocks.DISPENSED_BOOF_BLOCK.get()));
}
