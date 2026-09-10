package com.teamabnormals.endergetic.core.other;

import com.google.common.collect.Maps;
import com.teamabnormals.blueprint.common.world.storage.tracking.*;
import com.teamabnormals.endergetic.common.entity.bolloom.BalloonOrder;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.Map;
import java.util.UUID;

public final class EEDataProcessors {
	private static CompoundTag writeOrder(Map<UUID, BalloonOrder> map) {
		ListTag entries = new ListTag();
		map.forEach((uuid, balloonOrder) -> {
			CompoundTag entry = new CompoundTag();
			entry.putUUID("UUID", uuid);
			entry.putInt("Order", balloonOrder.ordinal());
			entries.add(entry);
		});
		CompoundTag compound = new CompoundTag();
		compound.put("Entries", entries);
		return compound;
	}

	private static Map<UUID, BalloonOrder> readOrder(CompoundTag compound) {
		Map<UUID, BalloonOrder> map = Maps.newHashMap();
		compound.getList("Entries", 10).forEach(nbt -> {
			CompoundTag entry = (CompoundTag) nbt;
			if (entry.contains("UUID", 11) && entry.contains("Order", 3)) {
				map.put(entry.getUUID("UUID"), BalloonOrder.byOrdinal(entry.getInt("Order")));
			}
		});
		return map;
	}


	public static final TrackedData<Map<UUID, BalloonOrder>> ORDER_DATA = TrackedData.Builder.create(ByteBufCodecs.COMPOUND_TAG.map(EEDataProcessors::readOrder, EEDataProcessors::writeOrder), Maps::newHashMap).build();
	public static final TrackedData<Integer> CATCHING_COOLDOWN = TrackedData.Builder.create(ByteBufCodecs.INT, () -> 0).setSyncType(SyncType.NOPE).build();

	public static void registerTrackedData() {
		TrackedDataManager.INSTANCE.registerData(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "ballooon_order"), ORDER_DATA);
		TrackedDataManager.INSTANCE.registerData(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "catching_cooldown"), CATCHING_COOLDOWN);
	}
}
