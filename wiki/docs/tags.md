---
title: 📋 Datapack Tags
---

# Datapack Tags

This page describes the various datapack tags used by Frostiful. It includes both new tags added by Frostiful, and tags
from Thermoo that Frostiful appends to.

!!! tip
    Items that are most relevant to mod pack authors have been marked with :star:

!!! warning
    This page reflects the latest set of tags available for Minecraft 1.21.1. Items documented here may not necessarily reflect what appears in an older version of Frostiful. Starting from Frostiful 2.2, removed options will be documented in [Removed Tags](#removed-tags).

## Armor Materials (deprecated)

Location: `data/frostiful/tags/armor_material`.

| Tag ID                            | Description                                          | Default values (summarized)  |
|-----------------------------------|------------------------------------------------------|------------------------------|
| `#thermoo:resistant_to_cold`      | Armour materials that are somewhat resistant to cold | Netherite                    |
| `#thermoo:very_resistant_to_cold` | Armour materials that are very resistant to cold     | Fur and Fur Padded Chainmail |

These tags are defined in Thermoo, which you may want to read more
about [here](https://thermoo.thedeathlycow.com/mods/armor_materials/).

These tags will apply a [`frostiful:frost_resistance`](./components.md#frost-resistance) component to any new stacks created with these tags that does not already have this component. Editing these tags and reloading will not update existing stacks.

!!! warning
    These tags are deprecated, it is preferred that you use a the [`frostiful:frost_resistance`](./components.md#frost-resistance) component to modify attributes of custom armours instead.

## Banner Patterns

Location: `data/frostiful/tags/banner_pattern`.

| Tag ID                               | Description                                                  | Default values (summarized) |
|--------------------------------------|--------------------------------------------------------------|-----------------------------|
| `#frostiful:pattern_item/icicle`     | Can be chosen in the loom with the icicle banner pattern     | Icicle                      |
| `#frostiful:pattern_item/snowflake`  | Can be chosen in the loom with the snowflake banner pattern  | Snowflake                   |
| `#frostiful:pattern_item/frostology` | Can be chosen in the loom with the frostology banner pattern | Frostology                  |

## Blocks

Location: `data/frostiful/tags/block`.

| Tag ID                                         | Description                                                                                                       | Default values (summarized)                            |
|------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------|
| `#frostiful:icicle_growable`                   | The root of an icicle will not fall from these blocks once placed, allowing for infinite growth                   | Ice                                                    |
| `#frostiful:icicle_replaceable_blocks`         | Blocks that an icicle can replace when growing                                                                    | Ice                                                    |
| `#frostiful:is_open_flame`                     | Fire blocks that will be replaced with air when blown out by [Freezing Wind](./Weather)                           | Fire, Soul Fire                                        |
| `#frostiful:has_open_flame`                    | Light blocks with a boolean `lit` property that will be set to false when blown out by [Freezing Wind](./Weather) | Campfires, Candles, Candle Cakes                       |
| `#frostiful:frostologer_cannot_freeze`         | Blocks that the Frostologer cannot freeze with their destroy heat sources spell                                   | Portals, Beacons, Vaults, Spawners                     |
| `#frostiful:hot_floor`                         | Provide heat when stood on                                                                                        | Magma blocks                                           |
| `#frostiful:frozen_torches`                    | The frozen torch blocks. Exists for purely technical reasons                                                      | Frozen Torch and Frozen Wall Torch                     |
| `#frostiful:covered_rocks_cannot_replace`      | Blocks that covered rocks cant replace with cobble stone during generation                                        | Logs, leaves, and other blocks features cannot replace |
| `#frostiful:covered_rock_covering_replaceable` | Blocks that the sun lichen generated on covered rocks can replace                                                 | Sun lichen, glow lichen, snow                          |

## Biomes

Location: `data/frostiful/tags/worldgen/biome`.

| Tag ID                                           | Description                                                             | Default values (summarized)                                                                                                     |
|--------------------------------------------------|-------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------|
| `#frostiful:has_feature/sun_lichen_covered_rock` | Biomes that sun lichen covered rocks generate in                        | Snowy, icy, and taiga biomes                                                                                                    |
| `#frostiful:has_feature/icicle_cluster`          | Biomes that icicle clusters generate in                                 | Snowy and icy biomes                                                                                                            |
| `#frostiful:has_feature/brittle_ice`             | Biomes that brittle ice sheets generate in                              | Frozen ocean                                                                                                                    |
| `#frostiful:has_structure/chillager_outpost`     | Biomes that the [Chillager Outpost](./chillager-outpost) generates in   | Snowy plains, ice spikes                                                                                                        |
| `#frostiful:has_structure/frostologer_castle`    | Biomes that the [Frostologer Castle](./frostologer-castle) generates in | Snowy plains, ice spikes                                                                                                        |
| `#frostiful:dry_biomes`                          | Biomes that are cold at night                                           | Desert, savanna, badlands                                                                                                       |
| :star: `#frostiful:freezing_blacklist_biomes`    | Biomes that can never be cold                                           | Nether and End biomes                                                                                                           |
| `#frostiful:freezing_wind_always_spawns`         | Biomes where freezing wind will always appear on the ground             | Mountains and windswept biomes                                                                                                  |
| `#frostiful:freezing_wind_spawns_in_storms`      | Biomes where freezing wind will appear on the ground in storms          | Snowy and hilly biomes                                                                                                          |
| `#frostiful:freezing_wind_never_spawns`          | Biomes that freezing wind will never spawn in                           | Nether and End biomes                                                                                                           |
| Seasonal temperature tags                        | Categorizes biome temperature based on season                           | See the [Temperature System page](https://github.com/TheDeathlyCow/frostiful/wiki/Temperature-System#seasonal-temperature-tags) |

## Damage Types

Location: `data/frostiful/tags/worldgen/damage_type`.

| Tag ID                           | Description                                                           | Default values (summarized) |
|----------------------------------|-----------------------------------------------------------------------|-----------------------------|
| `#frostiful:does_not_break_root` | Damage types that do not breaking the freezing root of the Frost Wand | Freezing damage, broken ice |
| `#frostiful:is_icicle`           | Damage types from being impaled by/falling on an icicle               | Icicle, falling icicle      |

## Enchantments

Location: `data/frostiful/tags/enchantment`.

| Tag ID                                | Description                                                                                          | Default values (summarized)       |
|---------------------------------------|------------------------------------------------------------------------------------------------------|-----------------------------------|
| `#frostiful:exclusive_set/heat_drain` | Heat-drain like enchantments that can't both be put on the same item at the same time.               | Enervation, Curse of Frozen Touch |
| `#frostiful:is_frosty`                | If the player's boots are enchanted with this enchantment, then they gain no heat from magma blocks. | Frost Walker                      |

## Entity Types

Location: `data/frostiful/tags/entity_type`.

| Tag ID                                     | Description                                                                         | Default values (summarized)                                                              |
|--------------------------------------------|-------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------|
| `#frostiful:benefits_from_cold`            | Mobs that benefit from freezing, like the Frostologer.                              | Frostologer, Snow Golem                                                                  |
| `#frostiful:heavy_entity_types`            | Mobs that pack snow when walking on it.                                             | Iron Golem, Ravager, Warden                                                              |
| `#frostiful:root_immune`                   | Mobs that are immune to the freezing root effect of the [Frost Wand](./Frost-Wand). | Cold Immune, Frostologer                                                                 |
| `#frostiful:is_brushable`                  | Animals that can be brushed for [fur tufts](./Fur-Tufts)                            | All entities in the brushing tags below                                                  |
| `#frostiful:advancement/creepers`          | Which mobs count for the [Stop Right There advancement](./Advancements)             | Creepers + Conventional creepers                                                         |
| `#frostiful:brushing/drops_ocelot_fur`     | Which animals drop Ocelot Fur Tufts when brushed                                    | Ocelot + Conventional ocelots                                                            |
| `#frostiful:brushing/drops_wolf_fur`       | Which animals drop Wolf Fur Tufts when brushed                                      | Wolf + Conventional wolves                                                               |
| `#frostiful:brushing/drops_polar_bear_fur` | Which animals drop Polar Bear Fur Tufts when brushed                                | Polar Bear + Conventional polar bears                                                    |
| `#thermoo:benefits_from_cold`              | Mobs that benefit from freezing, like the Frostologer.                              | Entities in the benefits from cold tag above                                             |
| :star: `#thermoo:cold_immune`              | Entites that are immune to freezing in all forms                                    | Bosses, vanilla freeze immune entities, [Biters](./Biter), and [Chillagers](./Chillager) |

## Items

Location: `data/frostiful/tags/entity_type`.

| Tag ID                              | Description                                                                                | Default values (summarized)                                       |
|-------------------------------------|--------------------------------------------------------------------------------------------|-------------------------------------------------------------------|
| `#frostiful:frostology_cloaks`      | Items that make the [Frostology Cloak](./frostology-cloak) appear when worn                | Cloak of Frostology, Inert Cloak of Frostology                    |
| `#frostiful:fur_armor`              | Items that trigger the craft fur armor [advancement](./advancement)                        | All fur armour items                                              |
| `#frostiful:fur_boots`              | Items that are [fur boots](./fur-armor)                                                    | Fur boots items                                                   |
| `#frostiful:ice_skates`             | Items that allow you to skate on ice                                                       | Ice Skates, Armored Ice Skates                                    |
| `#frostiful:icicles`                | Items that are [Icicles](./icicle) for crafting purposes                                   | Icicles and Icicles from Immersive Weathering                     |
| `#frostiful:powder_snow_walkable`   | Items that will let you walk on powder snow when worn in the feet or body slot             | Leather, fur, fur padded chainmail boots and leather horse armour |
| `#frostiful:sun_lichens`            | The [sun lichen](./sun-lichen) block items                                                 | All variants of sun lichen                                        |
| `#frostiful:supports_heat_drain`    | Items that support the [Enervation and Curse of Frozen Touch](./Enchantments) enchantments | Swords, axes, spears, and the Frost Wand                          |
| :star: `#frostiful:warming_foods`   | Items that provide the Warmth status effect when consumed                                  | Various modded drinks, like teas, coffees, and hot chocolate      |
| `#frostiful:enchantable/frost_wand` | Items that can receive the Frost Wand's enchantments in the Enchanting Table               | Frost Wand                                                        |

## Trim Patterns

Location: `data/frostiful/tags/trim_pattern`.

| Tag ID                       | Description                                          | Default values (summarized) |
|------------------------------|------------------------------------------------------|-----------------------------|
| `#frostiful:custom_patterns` | The custom patterns of Frostiful, used for rendering | Glacial, snow man, frosty   |

## Removed Tags