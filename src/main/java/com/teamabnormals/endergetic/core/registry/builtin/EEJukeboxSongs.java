package com.teamabnormals.endergetic.core.registry.builtin;

import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.registry.EESoundEvents;
import net.minecraft.Util;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.JukeboxSong;

public final class EEJukeboxSongs {
	public static final ResourceKey<JukeboxSong> KILOBYTE = ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, "kilobyte"));

	public static void bootstrap(BootstrapContext<JukeboxSong> context) {
		context.register(KILOBYTE, new JukeboxSong(EESoundEvents.KILOBYTE, Component.translatable(Util.makeDescriptionId("jukebox_song", KILOBYTE.location())), 163.0F, 14));
	}
}
