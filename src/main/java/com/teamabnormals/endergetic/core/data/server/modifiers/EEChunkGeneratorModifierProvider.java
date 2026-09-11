package com.teamabnormals.endergetic.core.data.server.modifiers;

import com.teamabnormals.blueprint.common.world.modification.chunk.ChunkGeneratorModifierProvider;
import com.teamabnormals.blueprint.common.world.modification.chunk.modifiers.SurfaceRuleModifier;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.registry.EEBlocks;
import com.teamabnormals.endergetic.core.registry.builtin.EEBiomes;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public final class EEChunkGeneratorModifierProvider extends ChunkGeneratorModifierProvider {

	public EEChunkGeneratorModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(EndergeticExpansion.MOD_ID, output, provider);
	}

	@Override
	protected void registerEntries(Provider provider) {
		// TODO: Do I have to worry about ModdednessSliceConditionSource?
		//ConditionSource inSlice = new BlueprintSurfaceRules.ModdednessSliceConditionSource(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "poise_forest"));
		ConditionSource inPoiseForest = isBiome(EEBiomes.POISE_FOREST);
		RuleSource poismoss = ifTrue(noiseCondition(Noises.SURFACE, 0.43F, Double.MAX_VALUE), sequence(ifTrue(ON_FLOOR, state(EEBlocks.POISMOSS.get().defaultBlockState())), state(Blocks.END_STONE.defaultBlockState())));
		RuleSource eumus = ifTrue(noiseCondition(Noises.SURFACE, 0.35F, 0.43F), ifTrue(UNDER_FLOOR, state(EEBlocks.EUMUS.get().defaultBlockState())));
		RuleSource poismossAndEumus = sequence(poismoss, eumus);
		RuleSource poiseForest = ifTrue(inPoiseForest, poismossAndEumus); // ifTrue(inSlice, ifTrue(inPoiseForest, poismossAndEumus));
		this.entry("poise_forest_surface_rule")
				.selects("the_end")
				.addModifier(new SurfaceRuleModifier(poiseForest, false));

		// TODO: Eetle Update
//		RuleSource corrockPlains = SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS), EESurfaceRules.CorrockRuleSource.INSTANCE);
//		this.entry("corrock_plains_surface")
//				.selects("the_end")
//				.addModifier(new SurfaceRuleModifier(corrockPlains, false));
	}

}
