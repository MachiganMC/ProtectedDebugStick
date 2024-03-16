![icon](https://raw.githubusercontent.com/MachiganMC/ProtectedDebugStick/master/plugin/src/main/resources/icon.png)

[![Spiget Downloads](https://img.shields.io/spiget/downloads/102630?color=A87D17&style=for-the-badge)](https://www.spigotmc.org/resources/protected-debug-stick.102630/)
[![Spiget Download Size](https://img.shields.io/spiget/download-size/102630?color=a8&style=for-the-badge)](https://www.spigotmc.org/resources/protected-debug-stick.102630/)
[![Spiget Version](https://img.shields.io/spiget/version/102630?label=Current%20Version&style=for-the-badge)](https://www.spigotmc.org/resources/protected-debug-stick.102630/updates)
[![Spiget tested server versions](https://img.shields.io/spiget/tested-versions/102630?color=FF3333&label=Minecraft%20Version&style=for-the-badge)](https://www.spigotmc.org/resources/protected-debug-stick.102630/)


## Welcome :
Welcome to the official GitHub of the plugin **ProtectedDebugStick** !

Here you'll find a little presentation of the plugin, the source code and some additional
files.

#
## Presentation :
**Protected Debug Stick** (that now we'll call **PDS**) is a plugin that has the
goal to transform the vanilla **Debug Stick** legit for a survival use.
To do that, the plugin adds 3 new object (*totally customizable*) :
* The Basic Debug Stick (*/pds give \<player> basic \<durability>*)
* The Infinity Debug Stick (*/pds give \<player> infinity*)
* The Inspector (*/pds give \<player> inspector*)

#
## How the plugin transforms the Debug Stick ?

First, it will check if another plugin permits the player to use the debugstick at the location he wants to edit
(plugins like GriefPrevention or WorldGuard).
For example, if you have a claim plugin on your server (like GriefPrevention). The player, to use the debugstick, 
should have the permission to build in the claim to use it (*or have the permission `pds.bypass.plugin-block`*)


The plugin adds a system of durability (for the basic debugstick) to force the survival side. Each property has it own
durability (configurable, of course).

You can find all the properties with an explication, example of value, example of block and the permission to edit it 
[here](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/src/main/resources/properties_en.MD)
(*for French people 
[here](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/src/main/resources/properties_fr.MD)*).



But it's not all ! Like we saw before, the plugin adds 3 items. To change a properties of a
block you have to use the **Basic Debug Stick** or the **Infinity Debug Stick**.
The only difference of this 2 items is that the **Basic Debug Stick** has a durability
that you define when you obtain the item.
Each time you'll use the **Basic Debug Stick**, he'll take some durability damages
(*depending on the property and how you have configured it*).


If you don't want to execute a be.machigan.protecteddebugstick.command to give a debugstick to a player each time (because it isn't very survival),
you can create your own recipes for each item of the plugin. And, of course, if you want to create 2 recipes for
2 durability for the basic debugstick, you can !


*N.B. : the inspector is used to know the values of the properties of a block*