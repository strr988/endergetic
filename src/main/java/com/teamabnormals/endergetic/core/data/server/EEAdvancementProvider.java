package com.teamabnormals.endergetic.core.data.server;

import com.teamabnormals.endergetic.common.advancement.EECriteriaTriggers;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.registry.EEBlocks;
import com.teamabnormals.endergetic.core.registry.EEEntityTypes;
import com.teamabnormals.endergetic.core.registry.EEItems;
import com.teamabnormals.endergetic.core.registry.EEStructureTypes.EEStructures;
import com.teamabnormals.endergetic.core.registry.builtin.EEBiomes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.AdvancementProvider.AdvancementGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class EEAdvancementProvider implements AdvancementGenerator {

	public static AdvancementProvider create(PackOutput output, CompletableFuture<Provider> provider, ExistingFileHelper helper) {
		return new AdvancementProvider(output, provider, helper, List.of(new EEAdvancementProvider()));
	}

	@Override
	public void generate(Provider provider, Consumer<AdvancementHolder> consumer, ExistingFileHelper helper) {
		AdvancementHolder poiseForest = createAdvancement("find_poise_forest", "end", ResourceLocation.parse("end/enter_end_gateway"), EEBlocks.POISMOSS.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("in_poise_forest", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inBiome(provider.lookupOrThrow(Registries.BIOME).getOrThrow(EEBiomes.POISE_FOREST))))
				.save(consumer, EndergeticExpansion.MOD_ID + ":end/find_poise_forest");

		AdvancementHolder eatBolloomFruit = createAdvancement("eat_bolloom_fruit", "end", poiseForest, EEItems.BOLLOOM_FRUIT.get(), AdvancementType.TASK, true, true, false)
				.addCriterion("bolloom_fruit", ConsumeItemTrigger.TriggerInstance.usedItem(EEItems.BOLLOOM_FRUIT.get()))
				.save(consumer, EndergeticExpansion.MOD_ID + ":end/eat_bolloom_fruit");

		createAdvancement("up_up_and_away", "end", eatBolloomFruit, EEItems.BOLLOOM_BALLOON.get(), AdvancementType.GOAL, true, true, false)
				.addCriterion("up_up_and_away", EECriteriaTriggers.upUpAndAway())
				.save(consumer, EndergeticExpansion.MOD_ID + ":end/up_up_and_away");

		createAdvancement("tame_booflo", "end", poiseForest, EEItems.BOOFLO_HIDE.get(), AdvancementType.GOAL, true, true, false)
				.addCriterion("tamed_booflo", EECriteriaTriggers.tameBooflo())
				.save(consumer, EndergeticExpansion.MOD_ID + ":end/tame_booflo");

		createAdvancement("obtain_booflo_vest", "end", poiseForest, EEItems.BOOFLO_VEST.get(), AdvancementType.GOAL, true, true, false)
				.addCriterion("booflo_vest", InventoryChangeTrigger.TriggerInstance.hasItems(EEItems.BOOFLO_VEST.get()))
				.save(consumer, EndergeticExpansion.MOD_ID + ":end/obtain_booflo_vest");

		createAdvancement("catch_puff_bug", "end", poiseForest, EEItems.PUFF_BUG_BOTTLE.get(), AdvancementType.GOAL, true, true, false)
				.addCriterion("puff_bug_bottle", InventoryChangeTrigger.TriggerInstance.hasItems(EEItems.PUFF_BUG_BOTTLE.get()))
				.save(consumer, EndergeticExpansion.MOD_ID + ":end/catch_puff_bug");

		// TODO: Eetle Update
		AdvancementHolder findEetleNest = createAdvancement("find_eetle_nest", "end", ResourceLocation.parse("end/enter_end_gateway"), EEBlocks.END_CORROCK_BLOCK.get(), AdvancementType.TASK, true, true, true)
				.addCriterion("in_eetle_nest", PlayerTrigger.TriggerInstance.located(LocationPredicate.Builder.inStructure(provider.lookupOrThrow(Registries.STRUCTURE).getOrThrow(EEStructures.EETLE_NEST))))
				.save(consumer, EndergeticExpansion.MOD_ID + ":end/find_eetle_nest");

		 createAdvancement("kill_brood_eetle", "end", findEetleNest, EEBlocks.EETLE_EGG.get(), AdvancementType.TASK, true, true, true)
				.addCriterion("killed_brood_eetle", KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity().of(EEEntityTypes.BROOD_EETLE.get())))
				.save(consumer, EndergeticExpansion.MOD_ID + ":end/kill_brood_eetle");
	}

	private static Advancement.Builder createAdvancement(String name, String category, ResourceLocation parent, ItemLike icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return Advancement.Builder.advancement().parent(parent).display(icon,
				Component.translatable("advancements." + EndergeticExpansion.MOD_ID + "." + category + "." + name + ".title"),
				Component.translatable("advancements." + EndergeticExpansion.MOD_ID + "." + category + "." + name + ".description"),
				null, frame, showToast, announceToChat, hidden);
	}

	private static Advancement.Builder createAdvancement(String name, String category, AdvancementHolder parent, ItemLike icon, AdvancementType frame, boolean showToast, boolean announceToChat, boolean hidden) {
		return Advancement.Builder.advancement().parent(parent).display(icon,
				Component.translatable("advancements." + EndergeticExpansion.MOD_ID + "." + category + "." + name + ".title"),
				Component.translatable("advancements." + EndergeticExpansion.MOD_ID + "." + category + "." + name + ".description"),
				null, frame, showToast, announceToChat, hidden);
	}
}
