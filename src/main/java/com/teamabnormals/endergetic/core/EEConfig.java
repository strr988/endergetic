package com.teamabnormals.endergetic.core;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author SmellyModder(Luke Tonon)
 */
public final class EEConfig {

	public static class Common {
		public final ConfigValue<Boolean> debugDragonFightManager;

		Common(ModConfigSpec.Builder builder) {
			builder.push("debug");
			debugDragonFightManager = builder.comment("If the Dragon Fight Manager should debug its portal values").define("Debug Dragon Fight Manager", false);
			builder.pop();
		}
	}

	public static final ModConfigSpec COMMON_SPEC;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = specPair.getRight();
		COMMON = specPair.getLeft();
	}
}