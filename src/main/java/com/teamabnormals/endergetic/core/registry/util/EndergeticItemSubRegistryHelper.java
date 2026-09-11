package com.teamabnormals.endergetic.core.registry.util;

import com.teamabnormals.blueprint.core.util.registry.ItemSubRegistryHelper;
import com.teamabnormals.blueprint.core.util.registry.RegistryHelper;
import com.teamabnormals.endergetic.common.item.EetleSpawnEggItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public final class EndergeticItemSubRegistryHelper extends ItemSubRegistryHelper {

	public EndergeticItemSubRegistryHelper(RegistryHelper parent) {
		super(parent);
	}

	public DeferredItem<EetleSpawnEggItem> createEetleSpawnEgg() {
		return this.deferredRegister.register("eetle_spawn_egg", () -> new EetleSpawnEggItem(7964867, 3943508, new Item.Properties()));
	}
}
