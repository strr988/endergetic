package com.teamabnormals.endergetic.common.item;

import com.teamabnormals.endergetic.core.registry.EEEntityTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

public final class EetleSpawnEggItem extends DeferredSpawnEggItem {

	public EetleSpawnEggItem(int primaryColor, int secondaryColor, Properties properties) {
		super(EEEntityTypes.CHARGER_EETLE, primaryColor, secondaryColor, properties);
	}

	@Override
	public EntityType<?> getType(ItemStack stack) {
		if (!stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY).isEmpty()) {
			return super.getType(stack);
		}
		return Math.random() < 0.6F ? EEEntityTypes.CHARGER_EETLE.get() : EEEntityTypes.GLIDER_EETLE.get();
	}

}
