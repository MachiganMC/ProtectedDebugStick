# :lock: Permissions
Like every plugin, **Protected DebugStick** proposes you a list of permissions

<!-- tabs:start -->
### **:information_source: Explanations**

## :door: General permission
Want to have access to all content about the plugin ? the permission is ``pds.*``.

## :arrows_counterclockwise: Version
The plugin has a system to notify when a new version of the plugin is available. If you have the permission ``pds.version``, you'll receive
a message when you'll connect and there is a new version.

## :keyboard: Commands
To access to all arguments of the command, give you the permission ``pds.commands.*``.

!> Remember to check the command's page to see details of each command, because this page it's not the place to explain them.

### :zap: Use 
The permission ``pds.command.use`` is a base permission to use the command `/pds`. In addition, to see the argument in the tab completion, this
permission is needed.

### :bricks: Chunk
To have access to all argument of the ``/pds chunk`` argument you can use the permission `pds.command.chunk.*`. Alternatively, if you want to target a specific argument :
- ``/pds chunk info`` : `pds.command.chunk.info`
- ``/pds chunk clear`` : `pds.command.chunk.clear`

### :heavy_plus_sign: Other argument
|Command|Permission|
|---|---|
|`/pds give`|`pds.command.use`|
|`/pds reload-config`|`pds.command.reload-config`|
|`/pds load`|`pds.command.load`|

## :package: Items
For each item you'll see there are always two more permissions : ``.see`` is the left click use of the item and `.edit` right click.<br>
If you want to have access to all uses of all items, you'll have to have the permission ``pds.items.*``. And if you want to give access to all uses of a specific 
item, give the permission ``pds.<item>.*``.
- ``pds.item.basic.`` : by default, all players have access.
- ``pds.item.infinite.``
- ``pds.item.inspector.``

## :construction: Bypass
You should know that there are some restrictions like the blacklists or the territories. They are useful but as an administrator of a server you don't want to be
restricted by these functionalities. These permissions are made for you !<br>
If you want to bypass all restrictions, give you the permission ``pds.bypass.*``.

### :no_entry: Blacklists
If you want to bypass all blacklist of the plugin, give you the permission ``pds.bypass.blacklist.*``.<br>
For specific blacklist :
- Materials : ``pds.bypass.blacklist.material``
- Worlds : ``pds.bypass.blacklist.world``

### :world_map: Territory block
To bypass the constraint by territory plugin use the permission ``pds.bypass.plugin-block``.

## :mag: Properties
I will not describe all property permissions. If you want to know a permission for a specific property, check the page of the property.

### **:triangular_ruler: Schema**
A little diagram to explain the nodes of permission and better handling access to a specific node.
```
Plugin → pds.*
├── Version → pds.version
├── Command → pds.command.*
│   ├── /pds → pds.command.use
│   ├── /pds give → pds.command.give
│   ├── /pds reload-config → pds.command.reload-config
│   ├── /pds chunk [info|clear] → pds.command.chunk.*
│   │   ├── /pds chunk info → pds.command.chunk.info
│   │   └── /pds chunk clear → pds.command.chunk.clear
│   └── /pds load → pds.command.load
├── Item → pds.item.*
│   ├── Basic DebugStick → pds.item.basic.*
│   │   ├── See → pds.item.basic.see
│   │   └── Edit → pds.item.basic.edit
│   ├── Infinite DebugStick → pds.item.infinite.*
│   │   ├── See → pds.item.infinite.see
│   │   └── Edit → pds.item.infinite.edit
│   └── Inspector → pds.item.inspector.*
│       ├── See → pds.item.inspector.see
│       └── Edit → pds.item.inspector.edit
├── Bypass → pds.bypass.*
│   ├── Blacklists → pds.bypass.blacklist.*
│   │   ├── Materials → pds.bypass.blacklist.material
│   │   └── Worlds → pds.bypass.blacklist.world
│   └── Territory → pds.bypass.plugin-block
└── Properties → pds.property.*
    ├── Orientable → pds.property.orientable
    ├── Directional → pds.property.directional
    └── ...
```
<!-- tabs:end -->

