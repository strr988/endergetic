package com.teamabnormals.endergetic.core.registry.builtin;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class EEDamageTypes {
	public static final ResourceKey<DamageType> MUNCH = createKey("munch");

	public static void bootstrap(BootstrapContext<DamageType> context) {
		context.register(MUNCH, new DamageType(EndergeticExpansion.MOD_ID + ".munch", 0.1F));
	}

	public static DamageSource munch(Level level, Entity entity) {
		return level.damageSources().source(MUNCH, entity);
	}

	public static ResourceKey<DamageType> createKey(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, name));
	}
}
