package com.teamabnormals.endergetic.core.other;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.registry.EEItems;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

/**
 * @author - SmellyModder(Luke Tonon)
 */
public final class EEArmorMaterials {
	public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, EndergeticExpansion.MOD_ID);
	public static final DeferredHolder<ArmorMaterial, ArmorMaterial> BOOFLO_VEST = ARMOR_MATERIALS.register("booflo_vest", () -> new ArmorMaterial(
			Util.make(new EnumMap<>(ArmorItem.Type.class), defense -> {
				for (ArmorItem.Type type : ArmorItem.Type.values()) defense.put(type, 3);
			}),
			8,
			SoundEvents.ARMOR_EQUIP_LEATHER,
			() -> Ingredient.of(EEItems.BOOFLO_HIDE.get()),
			List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "booflo_vest"))),
			0.0F,
			0.0F));
}
