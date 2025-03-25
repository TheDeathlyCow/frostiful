---
title: 🏠 Homepage
---
# Frostiful Config Wiki

Welcome to the Frostiful Config Wiki! This is a technical wiki specifically meant for addon and modpack developers who wish to configure Frostiful. For information about the content of Frostiful (blocks, items, mobs, etc) see: [https://github.com/TheDeathlyCow/frostiful/wiki](https://github.com/TheDeathlyCow/frostiful/wiki).

!!! warning
    Some links to content within Frostiful on this wiki are currently broken. These links will be fixed when Frostiful gets a new content wiki in the near future.

## 🌡️🐮 Thermoo Documentation

Frostiful uses [Thermoo](https://github.com/TheDeathlyCow/thermoo) for its internal temperature and environment system. Mod pack authors may find its documentation useful. You can find the Thermoo Developer Wiki here: [https://thermoo.thedeathlycow.com/](https://thermoo.thedeathlycow.com/). This wiki is written with the assumption of general understanding of Thermoo.

## 🧭 Navigation

- [Config File](./config.md)
- [Datapack Tags](./tags.md)
- [Component Types](./components.md)

## 🖥️ Mod Integrations

* [Tips](https://modrinth.com/mod/tips): Added some Frostiful-specific tips
* [Enchantment Descriptions](https://modrinth.com/mod/enchantment-descriptions): Descriptions are provided for Frostiful's enchantments
* [Trinkets](https://modrinth.com/mod/trinkets): The Cloak of Frostology can be equipped in the Cape slot

Some modded warm foods will provide [Warmth](./Status-Effects). This can be configured with the [item tag](./Tags#items) `#frostiful:warm_foods`.

* [Farmer's Delight](https://modrinth.com/mod/farmers-delight-fabric): Hot Cocoa provides the Warmth effect
* [Farmer's Respite](https://www.curseforge.com/minecraft/mc-mods/farmers-respite): Teas and warm foods provide the Warmth effect
* [Festive Delight](https://www.curseforge.com/minecraft/mc-mods/festive-delight): Christmas Tea provides the Warmth effect
* [Frozen Up](https://www.curseforge.com/minecraft/mc-mods/frozen-up): Truffle Hot Chocolate provides the Warmth effect
* [Spectrum](https://modrinth.com/mod/spectrum): Hot Chocolate, Demon Tea, Restoration Tea, and Glistering Jelly Tea provide the Warmth effect

# Compatibility

While this mod is designed to be as compatible as possible with other mods, it is inevitable that issues will arise. Specific incompatibilities will be documented here as they become known.

* [Hephaestus / Tinker's Construct](https://github.com/Alpha-s-Stuff/TinkersConstruct): Temperature bar not showing up. **FIX:** set `extraHeartRenderer` to `false` in the `mantle-client.toml` config.
* [Iris Shaders](https://github.com/IrisShaders/Iris): Frostiful Armor Trim Patterns not showing up consistently. Currently no fix. **HELP WANTED**

## Other Temperature Mods

Most other temperature mods do not work with Frostiful's temperature system and likely never will. Running Frostiful with these mods is redundant as they also add their own temperature system in addition to Frostiful. These mods may technically run together, however if you want to have a warm temperature system with Frostiful I would recommend using [Scorchful](https://modrinth.com/mod/scorchful) instead, as it was made to work together with Frostiful.

All of the following temperature mods should not be used with Frostiful (note that this list is not exhaustive, these are just the ones I've heard of):

* PyroFrost
* EnvironmentZ
* Thermite
* Tough as Nails
* Cold Sweat
* Homeostatic
* Metabolism

For the mods that add thirst - [Dehydration](https://modrinth.com/mod/dehydration) works with Frostiful and does not add a temperature system.
