---
title: 📦 Component Types
---

# Component Types

This page documents the various component types added by Frostiful.

## Item Components

This is a new component type added for items. For the full format in Vanilla,
see: [https://minecraft.wiki/w/Data_component_format](https://minecraft.wiki/w/Data_component_format)

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