package com.teamabnormals.endergetic.common.advancement;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger.TriggerInstance;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public final class EECriteriaTriggers {
	public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, EndergeticExpansion.MOD_ID);
	public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger> TAME_BOOFLO = TRIGGERS.register("tamed_booflo", PlayerTrigger::new);
	public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger> BRED_BOOFLO = TRIGGERS.register("bred_booflo", PlayerTrigger::new);
	public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger> UP_UP_AND_AWAY = TRIGGERS.register("up_up_and_away", PlayerTrigger::new);

	public static Criterion<TriggerInstance> tameBooflo() {
		return TAME_BOOFLO.get().createCriterion(new TriggerInstance(Optional.empty()));
	}

	public static Criterion<TriggerInstance> bredBooflo() {
		return BRED_BOOFLO.get().createCriterion(new TriggerInstance(Optional.empty()));
	}

	public static Criterion<TriggerInstance> upUpAndAway() {
		return UP_UP_AND_AWAY.get().createCriterion(new TriggerInstance(Optional.empty()));
	}
}
