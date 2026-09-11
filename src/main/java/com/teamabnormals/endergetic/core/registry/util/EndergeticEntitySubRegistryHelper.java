package com.teamabnormals.endergetic.core.registry.util;

import com.teamabnormals.blueprint.core.util.registry.EntitySubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

public class EndergeticEntitySubRegistryHelper extends EntitySubRegistryHelper {

	public EndergeticEntitySubRegistryHelper(RegistryHelper parent) {
		super(parent);
	}

	public <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> createUnsummonableEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
		return this.deferredRegister.register(name, () -> EntityType.Builder.of(factory, entityClassification).sized(width, height).noSummon().setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(1).build(this.parent.prefix(name).toString()));
	}

	public <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> createManuallyUpdatedEntity(String name, EntityType.EntityFactory<E> factory, MobCategory entityClassification, float width, float height) {
		return this.deferredRegister.register(name, () -> EntityType.Builder.of(factory, entityClassification).sized(width, height).setTrackingRange(64).setShouldReceiveVelocityUpdates(true).setUpdateInterval(Integer.MAX_VALUE).build(this.parent.prefix(name).toString()));
	}

}
