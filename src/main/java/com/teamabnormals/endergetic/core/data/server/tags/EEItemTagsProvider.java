package com.teamabnormals.endergetic.core.data.server.tags;

import com.teamabnormals.blueprint.core.data.server.tags.BlueprintItemTagsProvider;
import com.teamabnormals.blueprint.core.other.tags.BlueprintItemTags;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.registry.EEBlocks;
import com.teamabnormals.endergetic.core.other.tags.EEBlockTags;
import com.teamabnormals.endergetic.core.other.tags.EEItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.endergetic.core.registry.EEItems.*;

public final class EEItemTagsProvider extends BlueprintItemTagsProvider {

	public EEItemTagsProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> tagLookup, ExistingFileHelper helper) {
		super(EndergeticExpansion.MOD_ID, output, lookupProvider, tagLookup, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.copyWoodenTags();
		this.copyWoodworksTags();

		this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
		this.copy(BlockTags.SLABS, ItemTags.SLABS);
		this.copy(BlockTags.WALLS, ItemTags.WALLS);

		this.copy(EEBlockTags.POISE_STEMS, EEItemTags.POISE_STEMS);
		this.tag(EEItemTags.ENDER_FIRE_BASE_BLOCKS).add(Blocks.END_STONE.asItem(), EEBlocks.EUMUS.get().asItem(), EEBlocks.EUMUS_POISMOSS.get().asItem(), EEBlocks.POISMOSS.get().asItem());

		this.tag(Tags.Items.MUSIC_DISCS).add(MUSIC_DISC_KILOBYTE.get());

		this.tag(ItemTags.BOATS).add(POISE_BOAT.getFirst().get());
		this.tag(ItemTags.CHEST_BOATS).add(POISE_BOAT.getSecond().get());
		this.tag(BlueprintItemTags.FURNACE_BOATS).add(POISE_FURNACE_BOAT.get());
		this.tag(BlueprintItemTags.LARGE_BOATS).add(LARGE_POISE_BOAT.get());
	}
}
