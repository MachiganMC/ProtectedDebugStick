![icon](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/src/main/resources/icon.png?raw=true)

## Welcome :
Welcome to the official GitHub of the plugin **ProtectedDebugStick** !

Here you'll find a little presentation of the plugin, the source code and some additional
files.
***

#
## Presentation :
**Protected Debug Stick** (that now we'll call **DBS**) is a plugin that has the
goal to transform the vanilla **Debug Stick** legit for a survival use.
To do that, the plugin add 3 new object (*totally customizable*) :
* The Basic Debug Stick (*/pds give \[\<player>] basic \[\<durability>]*)
* The Infinity Debug Stick (*/pds give \[\<player>] infinity*)
* The Inspector (*/pds give \[\<player>] inspector*)

To perform that, the plugin use claim from
[GriefPrevention](https://www.spigotmc.org/resources/griefprevention.1884/)
plugin to protect the constructions of others players. So yes,
[GriefPrevention](https://www.spigotmc.org/resources/griefprevention.1884/)
is needed to use **PDS** plugin.
***

#
## How the plugin transform the Debug Stick ?

To begin, the plugin divise each possible properties of the minecraft blocks (*e.g. :the 
facing or the water log*) into 4 categories :
* Rotation
* Reversed
* Special
* Creatives

You can find all the properties sorted in the categories with their descriptions to easily
configure the plugin and permissions,
[here](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/src/main/resources/properties_en.yml)
(*for French people 
[here](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/src/main/resources/properties_fr.yml)*).



But it's not all ! Like we saw before, the plugin add 3 items. To change a properties of a
block you have to use the **Basic Debug Stick** or the **Infinity Debug Stick**.
The only difference of this 2 items is that the **Basic Debug Stick** has a durability
that you define when you obtain the item.
Each time you'll use the **Basic Debug Stick**, he'll take some durability damages
(*depending on the property and how you have configured it*).


Each property has a permission to modify it (*and a permission group*) and a durability,
that the **Basic Debug Stick** will take, to set.


And to finish, the plugin check if the player has the claim permission to edit the property
of the block (*build permission in the claim needed*). 
***

*N.B. : the inspector is used to know the values of the properties of a block*