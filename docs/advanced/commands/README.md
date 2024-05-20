# :keyboard: Commands
In fact, there is only one command ... But with a lot of arguments !
The command is ``/pds`` or if you have time to spend to write a long command `/protected-debug-stick`.

!> We'll not see the permissions required to use these arguments, check the [Permissions](/advanced/permission/)'s page to get some informations.
But you have to know that by default, all arguments are accessible only by op.

<!-- tabs:start -->

## **:gift: Give**

### :keyboard: Command 
``/pds give <player> [basic|infinite|inspector] <durability>``

### :information_source: Explanation
This command allows you to give one of the three items of the plugin to another player.

### :speech_balloon: Arguments
- **Player** : This argument defines which player will receive the item.
- **Durability** : This argument is necessary when the item is the "*basic*", it defines the base durability the Basic DebugStick will have.

## **:arrows_counterclockwise: Reload-config**

### :keyboard: Command
``/pds reload-config``

### :information_source: Explanation
This command allows you to reload the config after you edit it. It reloads the base config and the message config.

## **:file_folder: Load**

### :keyboard: Command
``/pds load <file>``

### :information_source: Explanation
Imagine that you have deleted by accident a file of the plugin ... This command is made for you !<br>
It give you the opportunity to load in the plugin folder any file of the plugin.

### :speech_balloon: Arguments
The tabcompleter will help you the list of the files that can be loaded, but you can found the list 
[here](https://github.com/MachiganMC/ProtectedDebugStick/tree/master/plugin/src/main/resources) (*just ignore the `.png` files*).

## **:bricks: Chunk**

### :keyboard: Command
``/pds chunk <action>``

### :information_source: Explanation
Before explaining the command, you have to know a little internal thing about the plugin ...

You know that the plugin prevents changed block from being edited by natural changements (*e.g. the crops from growing*). But how the plugin does that ?

It stores the list of unmodifiable blocks in the chunk data, and when there is modification that could appear, the plugin will check if the location of the block is
present in the current chunk data (*in fact, it's more complexe, there is a cache system, but we'll keep it simple !*).

Now we know that what is the purpose of the command ?<br>
To know and edit information about the unmodifiable blocks in the current chunk. 

### :speech_balloon: Arguments
#### :information_source: Info
If you execute the command ``/pds chunk info``, you'll have the list of all unmodifiable block presents in the chunk.

### :wastebasket: Clear
The command ``/pds chunk clear`` remove all unmodifiable blocks from the list of chunk. So, all blocks will be like before the use of the **DebugStick**.

<!-- tabs:end -->

<div id="preventToc"></div>