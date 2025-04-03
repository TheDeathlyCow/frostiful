---
title: 📦 Component Types
---

# Component Types

This page documents the various component types added by Frostiful.

## Item Components

This is a new component type added for items. For the full format in Vanilla,
see: [https://minecraft.wiki/w/Data_component_format](https://minecraft.wiki/w/Data_component_format)

### Cape
> Available 1.21.4+

This component can be used to render capes when worn in the Chest or [Cape Trinket slot](https://www.modrinth.com/mod/trinkets).

- `{}` **minecraft:components**: Parent tag.
    - `{}` **frostiful:cape**: A compound component.
        - `"` **cape_texture**: A Identifier/Resource Location of the cape texture to use.
        - `"` **override_account_cape**: An optional boolean indicating whether this should render over any capes on the player's account. Defaults to `true`.

!!! tip
    The Cloak of Frostology texture path is `frostiful:textures/entity/frostology_cloak.png`. Other Cape textures use a hashed value for their ID by default, and are not available unless you have seen a player wearing that cape during your session. If you want to use a specific cape texture, add a dedicated file to a resource pack first.

### Ice Like 
> Available 1.21.4+

When present on an item, causes that item to have the effects of the [Cloak of Frostology](https://modded.wiki/w/Frostiful:Cloak_of_Frostology).

- `{}` **minecraft:components**: Parent tag.
    - `{}` **frostiful:ice_like**: A compound component.
        - `"` **block_damage_types**: An optional damage-type tag ID of what damage types this item should fully block when worn. Defaults to `minecraft:is_freezing`.  

### Frost Resistance

This component adds Frost Resistance and Environment Frost Resistance attribute modifiers to items. This does not modify
the underlying `minecraft:attribute_modifiers` component, it only applies modifiers when the item is equipped or
displayed in a tooltip.

The attributes are documented on the [Thermoo Wiki](https://thermoo.thedeathlycow.com/entity_attributes/).

- `{}` **components**: Parent tag.
    - `{}` **frostiful:frost_resistance**: A compound component.
        - `D` **frost_resistance_multiplier**: Multiplies the base frost resistance of the armor type to get the final
          modifier value.
        - `D` **environment_frost_resistance_multiplier**: Multiplies the base environment frost resistance of the armor
          type to get the final modifier value.

The final attribute modifiers are applied as an `add_value` modifier for the `thermoo:generic.frost_resistance`
and `thermoo:generic.environment_frost_resistance` attributes with modifier IDs
of `frostiful:base_frost_resistance/${slot_id}` and `frostiful:base_environment_frost_resistance/${slot_id}`,
respectively.

The base values for the different armor types are as follows:

| Armor Type | Base Frost Resistance | Base Environment Frost Resistance |
|------------|-----------------------|-----------------------------------|
| Helmet     | 1.5                   | 0.25                              |
| Chestplate | 2.0                   | 0.5                               |
| Leggings   | 1.0                   | 0.125                             |
| Boots      | 0.5                   | 0.125                             |
| Body       | 4.0                   | 1.0                               | 

!!! tip
    These base values are not currently configurable. If you wish to have more fine-grained control over the item attribute modifier values, you will need to use another mod like [Default Components](https://modrinth.com/mod/default-components) to set the default multiplier value to `0` in this component, and then add a regular attribute modifier to the underlying `minecraft:attribute_modifiers` component using a mod like [CIA](https://modrinth.com/mod/cia) or Default Components.