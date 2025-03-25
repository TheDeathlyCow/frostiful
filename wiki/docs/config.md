---
title: ⚙️ Config File
---
# Config File
Frostiful is highly configurable. However, not all of the values in the config are properly bounded and so adjusting them to weird values may result in undefined behaviour. Adjust the config at your own risk!

!!! tip
    Items that are most relevant to mod pack authors have been marked with :star:

!!! warning
    This page reflects the latest version of the config for Minecraft 1.21.1. Items documented here may not necessarily reflect what appears in an older version of Frostiful. Starting from Frostiful 2.2, removed options will be documented in [Removed Config Options](#removed-config-options).

This documents the various global config options found in `[minecraft]/config/frostiful.json`.

## [IMPORTANT] Update Config

The update config controls the automatic updating of config values.

* :star: Enable config auto updates `enableConfigUpdates` Enables/disables automatic config updates. **If you are tinkering with the config, this should probably be turned off**.
* `currentConfigVersion`: The current config version. Does not show in the GUI and should be left alone.

## Client Config

These are a set of client-side settings that contain options for things like accessibility, rendering, and the HUD. Changing these on a dedicated server will do nothing.

* Frost overlay start percent (0-1) `frostOverlayStart`: Double between 0 and 1 that determines at what negative temperature scale the vanilla powder snow border overlay should start rendering at
* Do cold heart overlay `doColdHeartOverlay`: Whether to render the cold heart overlay or not
* Render water drip particles when wet `renderDripParticles`: Whether to render water dripping particles on wet players (does nothing if Scorchful is installed)
* Disable frost overlay when wearing Cloak of Frostology `disableFrostOverlayWhenWearingFrostologyCloak`: Whether to disable the frost overlay (powder snow border) from showing when wearing the Cloak of Frostology
* :star: Shake hand when shivering `shakeHandWhenShivering`: Whether to shake the player's hand in first person when [shivering](./Temperature-System).
* Hand shake intensity `handShakeIntensity`: How intense the hand shaking should be when shivering.

## Combat config

This config contains anything related to combat.

* :star: Do chillager patrols `doChillagerPatrols`: Whether Chillagers should replace Pillagers in patrols in snowy biomes
* Strays carry frost arrows `straysCarryFrostArrows`: Whether Strays will carry Glacial Arrows.
* Max frost spell distance `maxFrostSpellDistance`: The maximum distance the spell cast by the [Frost Wand](./Frost-Wand) should be able to go before exploding.
* Frost Wand cool down (ticks) `frostWandCooldown`: The cooldown time (in ticks) of the Frost Wand after casting a spell.
* Frost Wand root time (ticks) `frostWandRootTime`: The time (in ticks) that the Frozen Rooted effect of the Frost Wand should last.
* Frostologer heat drain per tick `frostologerHeatDrainPerTick`: How much heat the [Frostologer](./Frostologer) should drain from nearby targets per tick when using its freezing attack.
* Packed snowball freeze amount `packedSnowballFreezeAmount`: The amount of temperature that the [Packed Snowball](./Packed-Snow) should remove when thrown at an entity.
* Packed snowball damage `packedSnowballDamage`: The amount of Damage that the [Packed Snowball](./Packed-Snow) should apply when thrown at an entity.
* Packed snowball vulnerable types damage `packedSnowballVulnerableTypesDamage`: The amount of Damage that the [Packed Snowball](./Packed-Snow) should apply when thrown at an entity that belongs to the tag `#minecraft:freeze_hurts_entity_types`.
* Max Frost Bite Max Amplifier (inclusive) `biterFrostBiteMaxAmplifier`: The maximum level of Frost Bite to apply to players frozen by the Frost Wand when attacked by a Biter (inclusive)
* Chillager fire damage multiplier `chillagerFireDamageMultiplier`: Increases damage Chillagers take from fire
* Frostologer fire damage multiplier `frostologerFireDamageMultiplier`: Increases damage Frostologers take from fire
* Ice Skate upgrade generate chance in Igloos (0-1) `skateUpgradeTemplateIglooGenerateChance`: Chance of an Ice Skate upgrade template appearing in an Igloo chest

## Freezing Config

This config contains all of the values associated with freezing.

* :star: Do passive freezing `doPassiveFreezing`: Global toggle on whether passive freezing should be enabled. Both this config option and the gamerule `frostiful.doPassiveFreezing` must be true for passive freezing to take effect!
* :star: Do wind spawning `doWindSpawning`: Whether [Freezing Wind](./Weather) should spawn
* :star: Wind spawn strategy `windSpawnStrategy`: The strategy to be used when spawning freezing wind. One of `NONE`, `POINT`, or `ENTITY`. Defaults to `ENTITY`. See the [alternate strategies](./Weather) page on the Weather page for more information.
* Do wind spawning in air `spawnWindInAir`: Whether [Freezing Wind](./Weather) should spawn in the air
* :star: Wind destroys torches `windDestroysTorches`: Whether [Freezing Wind](./Weather) can destroy open flames.
* Do snow packing from heavy entities `doSnowPacking`: Whether heavy entities should turn snow layers they step on into packed snow.
* :star: Passive freezing tick interval `passiveFreezingTickInterval`: How many ticks occur between applying passive freezing. This is intended to be used to allow configuring passive freezing to work slower than it does by default. Setting less then or equal to 1 makes passive freezing occur every tick.
* Wind spawn cap per second `windSpawnCapPerSecond`: The maximum number of Freezing Wind events allowed to spawn per second
* Wind spawn rarity `windSpawnRarity`: Controls the chance of wind spawning when snowing. Higher values mean less wind.
* Wind spawn rarity `windSpawnRarityThunder`: Controls the chance of wind spawning in a thunder storm. Higher values mean less wind.
* :star: Max passive freezing percent (0-1) `maxPassiveFreezingPercent`: The minimum temperature scale at which passive freezing should apply (higher values = you can freeze more)
* Soak percent from splash water bottle (0-1) `soakPercentFromWaterPotion`: A 0-1 percentage of how wet splash water bottles should make a player.
* Sun Lichen heat per level `sunLichenHeatPerLevel`: The amount of temperature that Sun Lichen adds to entities per level of warmth (The levels are cold=0, cool=1, warm=2, hot=3).
* Sun Lichen burn time `sunLichenBurnTime`: How long Sun Lichen should set entities on fire when overheating, in ticks.
* Campfire warmth search size `campfireWarmthSearchRadius`: How far away Campfires can warm players when a log is added.
* Campfire warmth time `campfireWarmthTime`: The amount of time in ticks that Warmth granted by campfires should last
* Freezing wind frost (per 5 ticks) `freezingWindFrost`: The amount of temperature to remove from players when colliding with wind
* Conduit Power warmth per tick `conduitPowerWarmthPerTick`: The amount of temperature gained from having the Conduit Power status effect whilst submerged in water.
* Heat from hot floor (magma block, campfire) `heatFromHotFloor`: How much heat to apply per tick to entites standing on hot floor blocks, such as magma blocks
* Start shivering when below temp `shiverBelow`: The temperature scale below which to have entities start shivering. Scale from -1 to +1.
* :star: Shivering warmth per tick `shiverWarmth`: How much warmth shivering should apply per tick.
* Stop shivering warmth below food level `stopShiverWarmingBelowFoodLevel`: The food level below which shivering should not apply heat.
* Warming food and drink Warmth duration (ticks) `warmFoodWarmthTime`: How many ticks Warmth should be applied for when consuming Warming foods. That is, food with the item tag `#frostiful:warm_foods`. This tag is mainly used for integration with warming food and drinks from mods like Farmer's Delight.

## Icicle Config

This config contains values associated with [icicles](./Icicle).

* :star: Icicles form in weather `iciclesFormInWeather`: Whether Icicles should form during weather events.
* Become unstable chance `becomeUnstableChance`: The chance of an icicle block attempting to become unstable on random tick.
* Grow chance `growChance`: The chance of an icicle block attempting to grow on random tick.
* Grow chance during rain `growChanceDuringRain`: The chance of an icicle block attempting to grow on random tick when its raining.
* Grow chance during thunder `growChanceDuringThunder`: The chance of an icicle block attempting to grow on random tick when its thundering.
* Frost arrow freeze amount `frostArrowFreezeAmount`: The amount of temperature to remove from victims of a [Glacial Arrow](https://github.com/TheDeathlyCow/frostiful/wiki/Glacial-Arrow).
* Thrown icicle freeze amount `thrownIcicleFreezeAmount`: The amount of temperature to remove to from entities when they have an icicle thrown at them.
* Icicle collision freeze amount `icicleCollisionFreezeAmount`: The amount of temperature to remove from entities that hurt themselves on an icicle.
* Maximum light level to form (exclusive) `maxLightLevelToForm`: The maximum (block) light level that icicles are allowed to form at, exclusive upper bound.
* Minimum sky light level to form (inclusive) `minSkylightLevelToForm`: The minimum amount of sky light that icicles require to form.
* Thrown icicle damage `thrownIcicleDamage`: The amount of damage to apply to victims when they have an icicle thrown at them.
* Thrown icicle hurts extra damage `thrownIcicleExtraDamage`: The amount of damage to apply to entities belonging to the tag `#minecraft:freeze_hurts_extra_types`. Note: this is not additive with `thrownIcicleDamage`!
* Thrown icicle cooldown `thrownIcicleCooldown`: The cooldown in ticks between icicle throws.


## Environment Config

This config contains all of the values associated with environmental temperature changes and effects.

* :star: Max temperature for cold (in °C) `maxTemperatureForColdC` [^1]: Cutoff temperature for freezing in Celsius. Biomes at or below this temperature will apply environment freezing to players. May not exceed 15°C.
* :star: Degrees per temperature level decrease (in °C/°K) `degreesCPerTemperatureDecrease` [^1]: Specifies the number of Celsius/Kelvin degrees the temperature must fall below the maximum cold threshold for each one-point decrease in player temperature per tick. Must be positive and non-zero.
* :star: Environment temperature multiplier `environmentTemperatureMultiplier`[^1]: Multiplies the final temperature point change of an environment temperature change.
* Apply Environment Frost Resistance penalty when wet `applyEnvironmentPenaltyWhenWet`: When true, entities will have their Environment Frost Resistance attribute set to 0 when wet.
* Rain wetness increase per tick `rainWetnessIncrease`: How many points to increase wetness by each tick when in the rain.
* Touching water wetness increase per tick `touchingWaterWetnessIncrease`: How many points to increase wetness by each tick when touching, but not submerged in, water.
* On fire dry rate `onFireDryDate`: How many points of wetness to remove each tick when on fire.
* On fire warm rate `onFireWarmRate`: The amount of temperature per tick that should be added from entities that are on fire.
* Powder snow freeze rate `powderSnowFreezeRate`: The per-tick temperature reduction of entities submerged in Powder Snow.
* Warmth per light level `warmthPerLightLevel`: How much temperature per tick is added to entities in an area per light level.
* Minimum block light level for warmth `minLightForWarmth`: The minimum light level needed to an area to be warm.
* Ultrawarm dimension warm rate `ultrawarmWarmRate`: The amount of warmth to apply in Ultrawarm dimensions like The Nether (does nothing if Scorchful is installed).
* Maximum snow accumulation ticks `maxSnowAccumulationTicks`: Controls how much many ticks of snow can be accumulated on entities that will melt off them when they go inside or enter a warm area. Set to 0 to disable this feature.

## Removed Config Options

These reflect the removed config options from Frostiful, and their replacements (starting from Frostiful 2.2).

* Passive freezing wetness scale multiplier `passiveFreezingWetnessScaleMultiplier`: Increases passive freezing when wet
    - Removed in 2.2
    - Replaced with `environmentConfig/environmentFreezingSoakedMultiplier`
* Very protective Frost Resistance multiplier `veryProtectiveFrostResistanceMultiplier`: Multiplies the base frost resistance of very protective armour materials
    - Removed in 2.2
    - Replaced with the [Frost Resistance Item Component](./components.md#frost-resistance) 
* Protective Frost Resistance multiplier `protectiveFrostResistanceMultiplier`: Multiplies the base frost resistance of protective armour materials
    - Removed in 2.2
    - Replaced with the [Frost Resistance Item Component](./components.md#frost-resistance) 
* Do dry biome night freezing `doDryBiomeNightFreezing`: Whether dry biomes, like deserts, should be cold at night.
    - Removed in 2.2
    - Feature is now defined by [Scorchful](https://github.com/TheDeathlyCow/scorchful)'s `scorchful:set_humidity/arid_climate` [environment provider](https://thermoo.thedeathlycow.com/datapacks/environment_provider_definition/)
* Cold biome base temperature change `coldBiomeTemperatureChange`: The base ambient temperature change per tick of snowy biomes that are not freezing. See [Temperature System](https://github.com/TheDeathlyCow/frostiful/wiki/Temperature-System).
    - Removed in 2.2
    - Replaced by the `frostiful:cold_climate` [environment](https://thermoo.thedeathlycow.com/datapacks/environment_definition/).
* Freezing biome base temperature change `freezingBiomeTemperatureChange`: The base ambient temperature change per tick of freezing biomes. See [Temperature System](https://github.com/TheDeathlyCow/frostiful/wiki/Temperature-System).
    - Removed in 2.2
    - Replaced by the `frostiful:freezing_climate` [environment](https://thermoo.thedeathlycow.com/datapacks/environment_definition/).
* Night time temperature shift `nightTemperatureShift`: Ambient temperature per tick shift in dark areas of cold or cool biomes. *This will also apply in dark places during the day.*
    - Removed in 2.2
    - Replaced by the `frostiful:modifier/sun_light` [environment provider](https://thermoo.thedeathlycow.com/datapacks/environment_provider_definition/).
* Dry rate `dryRate`: How many points of wetness to remove each tick when not touching water in any way.
    - Removed in 2.2
    - No replacement, hardcoded to be handled by Thermoo directly.
* Enable seasons integration `enableSeasonsIntegration`: Allows the passive temperature of biomes to change depending on the Season. Requires a Seasons mod like Fabric Seasons or Serene Seasons and [Thermoo Patches](https://modrinth.com/mod/thermoo-patches) to have any effect. If disabled, then the Season will always be treated as being like Spring.
    - Removed in 2.2
    - Replaced by the `thermoo:seasonal/temperate` [environment provider types](https://thermoo.thedeathlycow.com/datapacks/environment_provider_definition/#temperate-seasonal), and used in the `frostiful:temperate_climate`, `frostiful:cool_climate`, `frostiful:cold_climate`, and `frostiful:freezing_climate` [environments](https://thermoo.thedeathlycow.com/datapacks/environment_definition/).

[^1]: The final value of the environment temperature change is calculated using the formula below. You can find a graphical representation on [Desmos](https://www.desmos.com/calculator/01nd0aidxh).
    ??? Formula
        $$
        B(T) = \frac{T - maxTemp - degreesPerTempDec}{degreesPerTempDec} \\
        \\[12pt]
        TemperatureChange(T) = max(0, ceil(multiplier * B(T))
        $$
