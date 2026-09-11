package com.teamabnormals.endergetic.core.registry;

import com.teamabnormals.endergetic.common.levelgen.structure.EetleNestStructure;
import com.teamabnormals.endergetic.common.levelgen.structure.structures.EetleNestPieces.EetleNestPiece;
import com.teamabnormals.endergetic.core.EndergeticExpansion;
import com.teamabnormals.endergetic.core.other.tags.EEBiomeTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType.StructureTemplateType;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Map;

public class EEStructureTypes {
	public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, EndergeticExpansion.MOD_ID);

	public static final DeferredHolder<StructureType<?>, StructureType<EetleNestStructure>> EETLE_NEST_TYPE = STRUCTURE_TYPES.register("eetle_nest", () -> () -> EetleNestStructure.CODEC);

	public static class EEStructurePieceTypes {
		public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES = DeferredRegister.create(Registries.STRUCTURE_PIECE, EndergeticExpansion.MOD_ID);

		public static final DeferredHolder<StructurePieceType, StructurePieceType> EETLE_NEST = STRUCTURE_PIECE_TYPES.register("eetle_nest", () -> (StructureTemplateType) EetleNestPiece::new);
	}

	public static class EEStructures {
		public static final ResourceKey<Structure> EETLE_NEST = createKey("eetle_nest");

		public static void bootstrap(BootstrapContext<Structure> context) {
			HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

			context.register(EETLE_NEST, new EetleNestStructure(new Structure.StructureSettings(
					biomes.getOrThrow(EEBiomeTags.HAS_EETLE_NEST),
					Map.of(MobCategory.MONSTER, new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.STRUCTURE, WeightedRandomList.create(
							new MobSpawnSettings.SpawnerData(EEEntityTypes.CHARGER_EETLE.get(), 12, 3, 7),
							new MobSpawnSettings.SpawnerData(EEEntityTypes.GLIDER_EETLE.get(), 8, 3, 6)))),
					GenerationStep.Decoration.RAW_GENERATION, TerrainAdjustment.NONE))
			);
		}

		public static ResourceKey<Structure> createKey(String name) {
			return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, name));
		}
	}

	public static class EEStructureSets {
		public static final ResourceKey<StructureSet> EETLE_NESTS = createKey("eetle_nests");

		public static void bootstrap(BootstrapContext<StructureSet> context) {
			HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);

			context.register(EETLE_NESTS, new StructureSet(structures.getOrThrow(EEStructures.EETLE_NEST), new RandomSpreadStructurePlacement(18, 9, RandomSpreadType.TRIANGULAR, 5193657)));
		}

		public static ResourceKey<StructureSet> createKey(String name) {
			return ResourceKey.create(Registries.STRUCTURE_SET, ResourceLocation.fromNamespaceAndPath(EndergeticExpansion.MOD_ID, name));
		}
	}
}
