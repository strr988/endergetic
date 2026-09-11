package com.teamabnormals.endergetic.core.other;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EEModelLayers {
	public static final ModelLayerLocation CORROCK_CROWN_STANDING = register("corrock_crown_standing");
	public static final ModelLayerLocation CORROCK_CROWN_WALL = register("corrock_crown_wall");
	public static final ModelLayerLocation BOLLOOM_BUD = register("bolloom_bud");
	public static final ModelLayerLocation BOOF_BLOCK_DISPENSED = register("boof_block_dispensed");
	public static final ModelLayerLocation SMALL_EETLE_EGG = register("small_eetle_egg");
	public static final ModelLayerLocation MEDIUM_EETLE_EGG = register("medium_eetle_egg");
	public static final ModelLayerLocation LARGE_EETLE_EGG = register("large_eetle_egg");
	public static final ModelLayerLocation BOLLOOM_FRUIT = register("bolloom_fruit");
	public static final ModelLayerLocation POISE_CLUSTER = register("poise_cluster");
	public static final ModelLayerLocation BOOF_BLOCK = register("boof_block");
	public static final ModelLayerLocation BOLLOOM_KNOT = register("bolloom_knot");
	public static final ModelLayerLocation BOLLOOM_BALLOON = register("bolloom_balloon");
	public static final ModelLayerLocation PUFF_BUG = register("puff_bug");
	public static final ModelLayerLocation BOOFLO_BABY = register("booflo_baby");
	public static final ModelLayerLocation ADOLESCENT_BOOFLO = register("adolescent_booflo");
	public static final ModelLayerLocation BOOFLO = register("booflo");
	public static final ModelLayerLocation LEETLE = register("leetle");
	public static final ModelLayerLocation CHARGER_EETLE = register("charger_eetle");
	public static final ModelLayerLocation GLIDER_EETLE = register("glider_eetle");
	public static final ModelLayerLocation BROOD_EETLE = register("brood_eetle");
	public static final ModelLayerLocation PURPOID_GEL = register("purpoid_gel");
	public static final ModelLayerLocation PURP_GEL = register("purp_gel");
	public static final ModelLayerLocation PURPAZOID_GEL = register("purpazoid_gel");
	public static final ModelLayerLocation PURPOID = register("purpoid");
	public static final ModelLayerLocation PURP = register("purp");
	public static final ModelLayerLocation PURPAZOID = register("purpazoid");

	public static ModelLayerLocation register(String name) {
		return register(name, "main");
	}

	public static ModelLayerLocation register(String name, String layer) {
		return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, name), layer);
	}
}
