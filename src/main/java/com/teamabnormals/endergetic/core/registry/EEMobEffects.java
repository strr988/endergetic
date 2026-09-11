package com.teamabnormals.endergetic.core.registry;

import com.teamabnormals.blueprint.core.util.DataUtil;
import com.teamabnormals.endergetic.common.effect.InstabilityMobEffect;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class EEMobEffects {
	public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, EndergeticExpansion.MOD_ID);
	public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(BuiltInRegistries.POTION, EndergeticExpansion.MOD_ID);

	public static final DeferredHolder<?, MobEffect> INSTABILITY = MOB_EFFECTS.register("instability", InstabilityMobEffect::new);

	public static final DeferredHolder<?, Potion> INSTABILITY_NORMAL = POTIONS.register("instability", () -> new Potion("instability", new MobEffectInstance(INSTABILITY.get(), 1800)));
	public static final DeferredHolder<?, Potion> STRONG_INSTABILITY = POTIONS.register("strong_instability", () -> new Potion("instability", new MobEffectInstance(INSTABILITY.get(), 432, 1)));

	public static void registerBrewingRecipes() {
		DataUtil.addMix(Potions.AWKWARD, EEItems.PORTAPLASM.get(), INSTABILITY_NORMAL.get());
		DataUtil.addMix(INSTABILITY_NORMAL.get(), Items.GLOWSTONE_DUST, STRONG_INSTABILITY.get());
	}
}
