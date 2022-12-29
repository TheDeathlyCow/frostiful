# Download Pages

CurseForge: https://www.curseforge.com/minecraft/mc-mods/frostiful

Modrinth: https://modrinth.com/mod/frostiful

The above download pages are the ONLY source of this mod, DO NOT TRUST ANY OTHER SOURCE!

# Wiki

Basically everything about this mod is documented extensively on the [wiki](https://github.com/TheDeathlyCow/frostiful/wiki/).

# Compatibility

Known compatibility issues and potential workarounds/fixes will be listed here as they become known. If you find a compatibility issue, feel free to raise an [issue](https://github.com/TheDeathlyCow/frostiful/issues) or tell me about it on my [Discord](https://discord.gg/aqASuWebRU) and I will add it here.

| Mod Name             | Problem                                               | Versions              | Fix or Workaround                                                                                                                      |
|----------------------|-------------------------------------------------------|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------|
| Lithium              | Mixin error                                           | Frostiful 0.1.0-0.2.1 | Add the line `mixin.entity.fast_powder_snow_check=false` to Lithium's config in `lithium.properties`.                                  |
| Origins              | Merling Origin freezes to death very quickly in water | Frostiful 0.1.0-0.2.1 | Frostiful 0.2.2+ now natively works with the Merling Origin appropriately                                                              |
| EnvironmentZ         | Both mods add a temperatue bar                        | All                   | The mods don't seem to immediately crash when used together, however you should turn off passive freezing for Frostiful in the config. |
| Immersive Weathering | Freezing Water is applied twice                       | All                   | In Immersive Weathering's Common Config, set `water_increment` to 0.                                                                   |
| Health Overlay       | Cold hearts not rendering                             | Frostiful 0.1.0-0.2.6 | Frostiful 0.2.7+ how has native compatibility with Health Overlay                                                                      | 

