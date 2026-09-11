package com.teamabnormals.endergetic.core.data.server.modifiers;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public final class EELootModifierProvider extends GlobalLootModifierProvider {
	public static final ResourceKey<LootTable> KILOBYTE_BONUS = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "chests/kilobyte_bonus"));

	public EELootModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
		super(output, provider, EndergeticExpansion.MOD_ID);
	}

	@Override
	protected void start() {
		this.add("end_city_kilobyte", new AddTableLootModifier(
				new LootItemCondition[]{LootTableIdCondition.builder(BuiltInLootTables.END_CITY_TREASURE.location()).build()},
				KILOBYTE_BONUS));
	}
}
