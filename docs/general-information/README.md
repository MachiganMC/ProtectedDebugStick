## :feather: General information
<br>

<!-- tabs:start -->
#### **Before use it**

You never installed the plugin ? Here are some information you should know before downloading it.

### The goal ?
**Protected DebugStick** is a plugin that has the goal to re-create the vanilla [DebugStick](https://minecraft.wiki/w/Debug_Stick).
#### Why re-create the DebugStick if it already exists ?
The vanilla DebugStick has been designed for players that play in creative mode, so there are some functionalities that don't exist that should be useful/necessary for 
survival servers. Some example why the vanilla DebugStick shouldn't be used for survival servers :
- If you have access to the DebugStick, you have access to all the DebugStick : Some properties of some blocks that the DebugStick can edit shouldn't be accessible in
survival. Some properties allow player to literally duplicate some block without any cost.
- The DebugStick is only accessible via command : You want a DebugStick ? Ask an admin to give it to you via `/give youre_cool_pseudo minecraft:debug_stick`.
- You have access to the DebugStick ? You can use it anywhere : Now every survival server that isn't an anarchic server has to have a descent territory plugin that prevents
a new player from breaking everything on the server and lets the players protect their builds.
- To finish, the DebugStick isn't usable in survival ...

#### How the plugin tries to make it usable ?
By fixing these presents issues ! But how ?
- With the **Protected DebugStick**, you can configure which player has access to which property. Each property has it own permissions like `pds.property.<property>`
  (*e.g. `pds.property.orientable` to edit the* [*Orientable*](/property/orientable) *property*). 
- In survival servers, it's frequent to have many worlds. One for the Overwold, Nether, End, Resources etc... It could happen that you don't want that players can use 
the **Protected DebugStick** in every world.<br>
With the plugin, you can define a list of black listed worlds where the items if the plugin cannot be used.
- Now, you have the same issue but with some block, you want to give access to the player to a property but not to a certain block. For example, toe property to rotate
the chest could result in some graphics issues.<br> 
It's possible, like for worlds, to define a list of blocks that the player cannot edit.
- I mentioned that in vanilla, the only way to have the DebugStick it's to use the commande `/give youre_very_cool_pseudo minecraft:debug_stick`.<br>
In the plugin, you can define a recipe for each item of the plugin.
- One of the aspects with the tools of survival is that the tools cannot be used infinitely. The plugin adds a system of durability.  
- To finish, the **Protected DebugStick** is compatible with any territory plugin like [WorldGuard](https://dev.bukkit.org/projects/worldguard),
[Lands](https://www.spigotmc.org/resources/lands-%E2%AD%95-land-claim-plugin-%E2%9C%85-grief-prevention-protection-gui-management-nations-wars-1-20-support.53313/), etc...<br>
If the player cannot build in the territory, it cannot use the **Protected DebugStick**.


### How to install it ?
You are convinced to use it ? So you want to download it.
Currently, the only where to download the plugin is on Spigot [here](https://www.spigotmc.org/resources/protected-debug-stick.102630/).
Check if the version of you're server is handled by the plugin and put it in the ``/plugins`` folder of your server.

!> Remember to check the properties that players could use depending on your server version.

#### Dependencies
Currently, there is no dependency !<br>
But, if you want to customize more the messages of the plugin, you can install [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/), the plugin
is compatible with it.


#### **Player's side**
You'll now have all information about the features of the plugin that could be used by a player of your server.

### The three items
The plugin embarks on three items :

- **Basic DebugStick** : like I have mentioned before, the plugin adds a system of durability. The **Basic DebugStick** is de DebugStick is a Debug Stick
with durability. Each time a player uses the tools on a block, depending on the property, it will lose some durability. When the durability goes to 0, it breaks :cold_sweat:
- **Infinite DebugStick** : like the Basic DebugStick, but you can use it as much as you want :sunglasses:
- **Inspector** : this item is special ... I used it to debug the plugin (debugging the plugin of DebugStick :exploding_head:) but I found funny to let it usable by 
the players and not keeping for me.<br>
It has two utilities. The first one, when you ``left-click`` with it on a block show all properties of a block and their current values.<br>
The second, it's when you ``right-click``, you can see all *implemented BlockData's interfaces* of the block.


### How to use the DebugStick ?
The DebugStick has four utilities assigned to a click type on a block. The two first are the same as the vanilla DebugStick, but I add more uses.
- `left-click` : to change the current property on the DebugStick.
![Demo of the left click with DebugStick](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/docs/assets/left-click-demo.gif?raw=true ':size=60%')
- `right-click` : to edit the clicked block with the selected property on the DebugStick.
![Demo of the right click with DebugStick](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/docs/assets/right-click-demo.gif?raw=true ':size=60%')
- `shift + left-click` : to see all the editable properties and their values.
![Demo of the right click with DebugStick](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/docs/assets/shift-left-click-demo.gif?raw=true ':size=60%')
- `shift + right-click` : to clear all permanents values of the block.

### Clear permanents values ???
Yes !<br>
I didn't talk about all features of the plugin :eyes:<br>
One of the weaknesses of the vanilla DebugStick is that when you use the DebugStick on a block, the modification can be canceled if the block is refreshed ...<br>
But the plugin, prevents the block the property of a block to be refreshed :<br> 
<div style="display: flex">
<span style="flex: 1"><b>In vanilla :</b></span>
<span style="flex: 1"><b>With the plugin :</b></span>
</div>

<div style="display: flex">
<span style="flex: 1">

![Demo of the left click with DebugStick](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/docs/assets/lamp-vanilla-demo.gif?raw=true ':size=90%')

</span>
<span style="flex: 1">

![Demo of the left click with DebugStick](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/docs/assets/lamp-plugin-demo.gif?raw=true ':size=90%')

</span>
</div>

But it can happen that you want a block to be updated. By using, the `shift + right-click` on a block, you remove the force of property's value on the block. 
<!-- tabs:end -->
