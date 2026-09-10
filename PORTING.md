# NeoForge 1.21.1 port

Status: in progress; not a working release. The Java sources and generated data
still require migration. A successful build and runtime validation are required
before this status can change.

## Baseline and build

- Working branch: `port/neoforge-1.21.1`.
- Baseline: Minecraft 1.20.1, Forge 47.1.3, Java 17, ForgeGradle 5.1, Gradle 7.4.
- Target: Minecraft 1.21.1, NeoForge 21.1.160, Java 21, NeoGradle 7.0.182,
  Gradle 8.12, Parchment 2024.11.17.
- Build configuration follows Team Abnormals' Neapolitan and Blueprint 1.21.x
  branches, both explicitly targeting Minecraft 1.21.1.
- Blueprint 8.1.0 is required. Boatload 6.0.0, Woodworks 4.0.0 and Clayworks
  4.0.3 are development dependencies for optional integration. JEI 19.21.0.247
  uses the NeoForge API and runtime artifacts.
- Mixin configuration is declared in `neoforge.mods.toml`. NeoForge supplies
  MixinExtras; the Forge-specific jar-in-jar dependency is no longer appropriate.

## Dependency map and migration checkpoints

The baseline has 329 Java files: api 9, client 64, common 178, core 76,
integration 2. Preserve registry and resource identifiers throughout migration.

| Area | Entry points | Required work |
| --- | --- | --- |
| Startup | `core/EndergeticExpansion`, `EEConfig` | Inject mod bus and ModContainer; NeoForge bus and ModConfigSpec; isolate client registration |
| Registries | `core/registry`, custom subhelpers | Blueprint registry keys; DeferredBlock/DeferredItem/DeferredHolder; preserve names |
| Network | seven classes in `common/network` | Typed payloads, StreamCodec, fixed directions, main-thread handling; verify controlling rider |
| Tracked entity state | `EEDataProcessors`, balloon holder mixins | Blueprint StreamCodec/MapCodec API; retain synchronization and lifecycle semantics |
| Synched entity data | `EEDataSerializers`, entity classes | Serializer codecs, SynchedEntityData.Builder, entity dimensions |
| Item data | `BoofloVestItem`, `PuffBugBottleItem`, `PuffBug` | Components for persistent item state and captured entity data; update tooltips and armor extensions |
| Entities and AI | Booflo family, PuffBug, Eetles, Purpoids, Bolloom, PoiseCluster | Spawn packet lifecycle, attributes, navigation, damage, interactions, animation synchronization |
| Worldgen | `EEBiomeSlices`, `EEFeatures`, `EEStructureTypes`, `EESurfaceRules` | Blueprint biome API, bootstrap and holder APIs, MapCodec registrations, generated data |
| Dragon fight | `EndergeticDragonFightManager`, End/ServerLevel mixins | Validate EndDragonFight members and lifecycle against 1.21.1; preserve portal and respawn behavior |
| Client | `client`, `EEClientCompat`, `EERenderTypes`, overlays | Vertex API, packed color, model rendering, GUI layers, frame timing, client-only class loading |
| Datagen | `core/data` | RecipeOutput, lookup providers, loot/advancement codecs, conditions and tag conventions |
| Resources | main and generated resources | Singular datapack directories, updated JSON schemas, pack formats, optional recipe conditions |

No direct custom Forge Capability or SavedData implementation was found by the
initial source searches. Blueprint tracked data, entity NBT and block entity NBT
must still be checked for persistence and synchronization changes.

## Integrations

- Boatload has runtime boat item/type references in `EEBoatTypes`, guarded
  selection in `EEItems`, and recipe generation in `EERecipeProvider`. Validate
  class loading with Boatload absent, not only with the development classpath.
- Woodworks supplies recipe helpers, sawmill recipes and optional creative-tab
  placement. Validate behavior with and without Woodworks.
- Clayworks supplies four baking recipes through its datagen helper. Preserve
  conditional recipes; do not turn it into a required runtime dependency.
- JEI integration is in `integration/jei/EEPlugin`.

## Mixin and access-transformer review

All 18 common and 4 client mixins need target and injection-point validation.
Especially sensitive targets include ServerLevel construction, PlayerList login,
LocalPlayer input processing, SwordItem destroy speed, ChorusPlantBlock placement,
and jump/ordinal injections. Retain functionality rather than disabling mixins.

The existing access transformer contains obsolete pre-1.20 class names as well as
SRG members. Validate actual 1.21.1 names and access before replacing entries;
the comments alone are not reliable mappings.

## Performance review candidates

These are candidates, not measured regressions or completed optimizations:

- ResourceLocation allocation in BoofBlockRenderer, PoiseClusterRender and
  Booflo bracelet rendering.
- Entity searches in Purpoid revenge propagation, PoiseCluster, PuffBug hive
  behavior and Booflo fear/attack logic; inspect invocation frequency first.
- Dragon-fight debug portal scan runs during tick when debug is enabled.
- Armor item state currently mutates NBT each tick; component updates should
  avoid unnecessary writes and preserve the ten-tick durability behavior.

Optimize after functional parity; do not add unbounded caches of entities/levels.

## Validation still required

- Java compilation and clean build.
- Datagen and generated-output review.
- Dedicated server startup without optional mods, then with integrations.
- Client menu and world creation, Overworld and End travel, biome/features and
  structure generation, dragon fight, entity AI, rendering and particles.
- Network actions and item persistence across save/reload.
- Feature comparison with the original Forge implementation and performance review.

## References

- https://github.com/team-abnormals/blueprint/tree/1.21.x
- https://github.com/team-abnormals/neapolitan/tree/1.21.x
- https://github.com/team-abnormals/boatload/tree/1.21.1
- https://github.com/team-abnormals/woodworks/tree/1.21.1
- https://github.com/team-abnormals/clayworks/tree/1.21.1
- https://github.com/neoforged/NeoForge/tree/1.21.1
- https://docs.neoforged.net/docs/1.21.1/networking/payload/
- https://docs.neoforged.net/primer/docs/1.21/
