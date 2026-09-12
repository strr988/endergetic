package com.teamabnormals.endergetic.core.data.server;

import com.teamabnormals.endergetic.core.registry.EEBlocks;
import com.teamabnormals.endergetic.core.registry.EEItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public final class EEDataMapProvider extends DataMapProvider {
	public EEDataMapProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
		super(output, lookupProvider);
	}

	@Override
	protected void gather(HolderLookup.Provider provider) {
		var compostables = builder(NeoForgeDataMaps.COMPOSTABLES);
		compostables.add(EEBlocks.POISE_BUSH.getId(), new Compostable(0.3F), false);
		compostables.add(EEBlocks.TALL_POISE_BUSH.getId(), new Compostable(0.5F), false);
		compostables.add(EEBlocks.POISE_CLUSTER.getId(), new Compostable(0.85F), false);
		compostables.add(EEBlocks.BOLLOOM_BUD.getId(), new Compostable(1.0F), false);
		compostables.add(EEItems.BOLLOOM_FRUIT.getId(), new Compostable(0.65F), false);
		compostables.add(EEBlocks.BOLLOOM_CRATE.getId(), new Compostable(1.0F), false);

		var fuels = builder(NeoForgeDataMaps.FURNACE_FUELS);
		fuels.add(EEBlocks.POISE_FENCE.getId(), new FurnaceFuel(300), false);
		fuels.add(EEBlocks.POISE_FENCE_GATE.getId(), new FurnaceFuel(300), false);
		fuels.add(EEBlocks.POISE_BOARDS.getId(), new FurnaceFuel(300), false);
		fuels.add(EEBlocks.POISE_BOOKSHELF.getId(), new FurnaceFuel(300), false);
		fuels.add(EEBlocks.CHISELED_POISE_BOOKSHELF.getId(), new FurnaceFuel(300), false);
		fuels.add(EEBlocks.POISE_LADDER.getId(), new FurnaceFuel(300), false);
		fuels.add(EEBlocks.BOLLOOM_CRATE.getId(), new FurnaceFuel(300), false);
	}
}
