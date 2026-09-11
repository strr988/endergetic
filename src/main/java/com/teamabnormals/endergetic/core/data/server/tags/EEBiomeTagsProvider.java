package com.teamabnormals.endergetic.core.data.server.tags;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.registry.builtin.EEBiomes;
import com.teamabnormals.endergetic.core.other.tags.EEBiomeTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public final class EEBiomeTagsProvider extends BiomeTagsProvider {

	public EEBiomeTagsProvider(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		super(output, provider, EndergeticExpansion.MOD_ID, helper);
	}

	@Override
	public void addTags(Provider provider) {
		this.tag(BiomeTags.IS_END).add(EEBiomes.POISE_FOREST);
		this.tag(Tags.Biomes.IS_COLD).add(EEBiomes.POISE_FOREST);
		this.tag(Tags.Biomes.IS_COLD_END).add(EEBiomes.POISE_FOREST);
		this.tag(Tags.Biomes.IS_SPARSE).add(EEBiomes.POISE_FOREST);
		this.tag(Tags.Biomes.IS_SPARSE_END).add(EEBiomes.POISE_FOREST);
		this.tag(Tags.Biomes.IS_MAGICAL).add(EEBiomes.POISE_FOREST);

		this.tag(EEBiomeTags.HAS_EETLE_NEST);//.add(Biomes.END_HIGHLANDS);
		this.tag(EEBiomeTags.HAS_EETLE);//.add(Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS);
		this.tag(EEBiomeTags.HAS_CORROCK);//.add(Biomes.END_HIGHLANDS, Biomes.END_MIDLANDS);
		this.tag(EEBiomeTags.HAS_SPARSE_CORROCK);//.add(Biomes.END_MIDLANDS);
	}
}
