package com.teamabnormals.endergetic.common.item;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.other.EEArmorMaterials;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

/**
 * @author - SmellyModder(Luke Tonon)
 */
public class BoofloVestItem extends ArmorItem {
	private static final ResourceLocation DEFAULT_TEXTURE = ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/models/armor/booflo_vest.png");
	private static final ResourceLocation BOOFED_TEXTURE = ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "textures/models/armor/booflo_vest_boofed.png");
	public static final String TICKS_BOOFED_TAG = "ticksBoofed";
	public static final String BOOFED_TAG = "boofed";
	public static final String TIMES_BOOFED_TAG = "timesBoofed";

	public BoofloVestItem(Properties properties) {
		super(EEArmorMaterials.BOOFLO_VEST, Type.CHESTPLATE, properties);
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slotId, boolean isSelected) {
		if (!(entity instanceof Player player) || player.getItemBySlot(EquipmentSlot.CHEST) != stack) return;
		CompoundTag tag = getTag(stack);
		int ticksBoofed = tag.getInt(TICKS_BOOFED_TAG);
		if (tag.getBoolean(BOOFED_TAG)) {
			ticksBoofed++;
			tag.putInt(TICKS_BOOFED_TAG, ticksBoofed);
		} else {
			tag.putInt(TICKS_BOOFED_TAG, 0);
		}

		if (ticksBoofed >= 10) {
			tag.putBoolean(BOOFED_TAG, false);
		}

		if (tag.getInt(TICKS_BOOFED_TAG) == 10) {
			player.getItemBySlot(EquipmentSlot.CHEST).hurtAndBreak(2, player, EquipmentSlot.CHEST);
		}

		if (player.onGround() || (player.isPassenger() && player.getVehicle().onGround())) {
			tag.putInt(TIMES_BOOFED_TAG, 0);
		}
		setTag(stack, tag);
	}

	public static boolean canBoof(ItemStack stack, Player player) {
		return !player.getCooldowns().isOnCooldown(stack.getItem()) && !getTag(stack).getBoolean(BOOFED_TAG);
	}

	@Override
	public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
		return getTag(stack).getBoolean(BOOFED_TAG) ? BOOFED_TEXTURE : DEFAULT_TEXTURE;
	}

	public static CompoundTag getTag(ItemStack stack) {
		return stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag();
	}

	public static void setTag(ItemStack stack, CompoundTag tag) {
		CustomData.set(DataComponents.CUSTOM_DATA, stack, tag);
	}

}
