package com.teamabnormals.endergetic.core.data.server.tags;

import com.teamabnormals.blueprint.core.data.server.tags.BlueprintBlockTagsProvider;
import com.teamabnormals.blueprint.core.other.tags.BlueprintBlockTags;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.other.tags.EEBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.teamabnormals.endergetic.core.registry.EEBlocks.*;

public final class EEBlockTagsProvider extends BlueprintBlockTagsProvider {

	public EEBlockTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(EndergeticExpansion.MOD_ID, output, provider, helper);
	}

	@Override
	protected void addTags(HolderLookup.Provider provider) {
		this.tag(EEBlockTags.POISE_STEMS).add(POISE_STEM.get(), POISE_WOOD.get(), STRIPPED_POISE_STEM.get(), STRIPPED_POISE_WOOD.get(), GLOWING_POISE_STEM.get(), GLOWING_POISE_WOOD.get());
		this.tag(EEBlockTags.END_CRYSTAL_PLACEABLE).add(Blocks.OBSIDIAN, Blocks.BEDROCK, CRYSTAL_HOLDER.get(), MYSTICAL_OBSIDIAN.get(), MYSTICAL_OBSIDIAN_RUNE.get(), MYSTICAL_OBSIDIAN_ACTIVATION_RUNE.get(), ACTIVATED_MYSTICAL_OBSIDIAN_ACTIVATION_RUNE.get());
		this.tag(EEBlockTags.CHORUS_PLANTABLE).add(Blocks.END_STONE, END_CORROCK_BLOCK.get(), NETHER_CORROCK_BLOCK.get(), OVERWORLD_CORROCK_BLOCK.get(), PETRIFIED_END_CORROCK_BLOCK.get(), PETRIFIED_NETHER_CORROCK_BLOCK.get(), PETRIFIED_OVERWORLD_CORROCK_BLOCK.get());
		this.tag(EEBlockTags.END_PLANTABLE).add(EUMUS.get());
		this.tag(EEBlockTags.POISE_PLANTABLE).add(POISMOSS.get(), EUMUS_POISMOSS.get());
		this.tag(EEBlockTags.ENDER_FIRE_BASE_BLOCKS).add(
				Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_STAIRS, Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICK_WALL, CRACKED_END_STONE_BRICKS.get(), CHISELED_END_STONE_BRICKS.get(),
				POISMOSS.get(), EUMUS.get(), EUMUS_POISMOSS.get(), EUMUS_BRICKS.get(), EUMUS_BRICK_STAIRS.get(), EUMUS_BRICK_SLAB.get(), EUMUS_BRICK_WALL.get(), CRACKED_EUMUS_BRICKS.get(), CHISELED_EUMUS_BRICKS.get(),
				CRYSTAL_HOLDER.get(), MYSTICAL_OBSIDIAN.get(), MYSTICAL_OBSIDIAN_WALL.get(), MYSTICAL_OBSIDIAN_RUNE.get(), MYSTICAL_OBSIDIAN_ACTIVATION_RUNE.get(), ACTIVATED_MYSTICAL_OBSIDIAN_ACTIVATION_RUNE.get()
		);

		this.tag(BlockTags.MINEABLE_WITH_AXE).add(ENDER_CAMPFIRE.get(), BOLLOOM_BUD.get(), BOLLOOM_CRATE.get());
		this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(EUMUS_POISMOSS.get(), EUMUS.get(), EUMUS_POISMOSS_PATH.get());
		this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
				SPECKLED_END_CORROCK.get(), SPECKLED_NETHER_CORROCK.get(), SPECKLED_OVERWORLD_CORROCK.get(),
				PETRIFIED_SPECKLED_END_CORROCK.get(), PETRIFIED_SPECKLED_NETHER_CORROCK.get(), PETRIFIED_SPECKLED_OVERWORLD_CORROCK.get(),
				END_CORROCK_BLOCK.get(), NETHER_CORROCK_BLOCK.get(), OVERWORLD_CORROCK_BLOCK.get(),
				PETRIFIED_END_CORROCK_BLOCK.get(), PETRIFIED_NETHER_CORROCK_BLOCK.get(), PETRIFIED_OVERWORLD_CORROCK_BLOCK.get(),
				INFESTED_CORROCK.get(), PETRIFIED_INFESTED_CORROCK.get(),
				PUFFBUG_HIVE.get(), POISMOSS.get(), POISMOSS_PATH.get(),
				EUMUS_BRICKS.get(), EUMUS_BRICK_STAIRS.get(), EUMUS_BRICK_SLAB.get(), EUMUS_BRICK_WALL.get(), CRACKED_EUMUS_BRICKS.get(), CHISELED_EUMUS_BRICKS.get(),
				ACIDIAN_LANTERN.get(), ENDER_LANTERN.get(),
				CHISELED_END_STONE_BRICKS.get(), CRACKED_END_STONE_BRICKS.get(), CRACKED_PURPUR_BLOCK.get()
		);

		this.tag(BlockTags.CAMPFIRES).add(ENDER_CAMPFIRE.get());
		this.tag(BlockTags.DRAGON_IMMUNE).add(POISMOSS.get(), EUMUS.get(), ENDER_FIRE.get(), ACIDIAN_LANTERN.get(), CRYSTAL_HOLDER.get(), MYSTICAL_OBSIDIAN.get(), MYSTICAL_OBSIDIAN_WALL.get(), MYSTICAL_OBSIDIAN_RUNE.get(), MYSTICAL_OBSIDIAN_ACTIVATION_RUNE.get(), ACTIVATED_MYSTICAL_OBSIDIAN_ACTIVATION_RUNE.get());
		this.tag(BlockTags.FIRE).add(ENDER_FIRE.get());
		this.tag(BlockTags.FLOWER_POTS).add(POTTED_POISE_BUSH.get(), POTTED_TALL_POISE_BUSH.get());
		this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ACIDIAN_LANTERN.get());
		this.tag(BlockTags.SLABS).add(EUMUS_BRICK_SLAB.get());
		this.tag(BlockTags.STAIRS).add(EUMUS_BRICK_STAIRS.get());
		this.tag(BlockTags.WALLS).add(EUMUS_BRICK_WALL.get(), MYSTICAL_OBSIDIAN_WALL.get());
		this.tag(BlockTags.WITHER_IMMUNE).add(ENDER_FIRE.get(), ACIDIAN_LANTERN.get(), CRYSTAL_HOLDER.get(), MYSTICAL_OBSIDIAN.get(), MYSTICAL_OBSIDIAN_WALL.get(), MYSTICAL_OBSIDIAN_RUNE.get(), MYSTICAL_OBSIDIAN_ACTIVATION_RUNE.get(), ACTIVATED_MYSTICAL_OBSIDIAN_ACTIVATION_RUNE.get());

		this.tag(BlockTags.PLANKS).add(POISE_PLANKS.get());
		this.tag(BlockTags.LOGS_THAT_BURN).addTag(EEBlockTags.POISE_STEMS);
		this.tag(BlockTags.WOODEN_SLABS).add(POISE_SLAB.get());
		this.tag(BlockTags.WOODEN_STAIRS).add(POISE_STAIRS.get());
		this.tag(BlockTags.WOODEN_FENCES).add(POISE_FENCE.get());
		this.tag(BlockTags.FENCE_GATES).add(POISE_FENCE_GATE.get());
		this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(POISE_FENCE_GATE.get());
		this.tag(BlockTags.WOODEN_DOORS).add(POISE_DOOR.get());
		this.tag(BlockTags.WOODEN_TRAPDOORS).add(POISE_TRAPDOOR.get());
		this.tag(BlockTags.WOODEN_BUTTONS).add(POISE_BUTTON.get());
		this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(POISE_PRESSURE_PLATE.get());
		this.tag(BlockTags.STANDING_SIGNS).add(POISE_SIGNS.getFirst().get());
		this.tag(BlockTags.WALL_SIGNS).add(POISE_SIGNS.getSecond().get());
		this.tag(BlockTags.CEILING_HANGING_SIGNS).add(POISE_HANGING_SIGNS.getFirst().get());
		this.tag(BlockTags.WALL_HANGING_SIGNS).add(POISE_HANGING_SIGNS.getSecond().get());

		this.tag(BlueprintBlockTags.WOODEN_BOARDS).add(POISE_BOARDS.get());
		this.tag(BlueprintBlockTags.WOODEN_CHESTS).add(POISE_CHEST.get());
		this.tag(BlueprintBlockTags.WOODEN_TRAPPED_CHESTS).add(TRAPPED_POISE_CHEST.get());
		this.tag(BlueprintBlockTags.WOODEN_BEEHIVES).add(POISE_BEEHIVE.get());
		this.tag(BlueprintBlockTags.WOODEN_LADDERS).add(POISE_LADDER.get());
		this.tag(BlueprintBlockTags.WOODEN_BOOKSHELVES).add(POISE_BOOKSHELF.get());
		this.tag(BlueprintBlockTags.WOODEN_CHISELED_BOOKSHELVES).add(CHISELED_POISE_BOOKSHELF.get());
	}
}
