## :file_folder: Config file
You installed the plugin ? Great !<br>
And because you're brilliant, you read this section before using it in your production server !<br>
We'll see all configurations field and explain it.

When you've finished to edit the config, don't forget to apply the changements on your server with the command
``/pds reload-config``.

<!-- tabs:start -->

### **:globe_with_meridians: Lang**
### :globe_with_meridians: Lang
Represents the message's file used by the plugin to display messages.<br>
The possible values are : 
- ``en`` : for english language,
- `fr` : for french language,
- `zh-cn` : for chinese language.
If you select `en`, the file used will be [`lang/messages_en.yml`](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/plugin/src/main/resources/lang/messages_en.yml).
````yml
Lang: 'en'
````

### **:pick: Items**
### :pick: Items
In this section, you'll configure the looks of the three items of the plugin.<br>
The fields are the same for the three items `BasicDebugStick`, `InfinityDebugStick` and `Inspector`. So I'll explain it just one time.

<!-- tabs:start -->
#### **:package: Material**
### :package: Material
Configure which material is used for the item.
Possible values can be found [here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html).
````yml
Items:
  BasicDebugStick:
    Material: "STICK"
````

#### **:label: Name**
#### :label: Name
The name of the item. You can apply the same color code anf format as the ``messages.yml`` files.
````yml
Items:
  BasicDebugStick:
    Name: "#33FFBB&lBASIC DEBUG STICK"
````

#### **:scroll: Lore**
#### :scroll: Lore
The description of the items (*the lines after the name*). Like the name, the color code and format are the same as the ``messages.yml`` files.
Each item of the list represents a line in the description.
````yml
Items:
  BasicDebugStick:
    Lore:
    - "&aMy cool DebugStick !"
    - "&o ...but how to use it ?" 
````
#### **:sparkles: Enchants**
#### :sparkles: Enchants
You want to show some enchants on the items ? It's item of the list represents an enchantment in level 1.<br>
The list of possible values can be found [here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/enchantments/Enchantment.html).
````yml
Items:
  BasicDebugStick:
    Enchants:
    - "ARROW_DAMAGE"
    - "DURABILITY"
      	
````
!> Remember, it's a pure cosmetic ! <br>
Currently, there is no enchanting feature for the DebugStick, but it can be arrived, so don't love too much this field. 

#### **:tophat: HideEnchants**
#### :tophat: HideEnchants
You put some enchants, but it was only for the effect of the item and you don't want a list of enchantments to waste the look of the item.<br>
This field is for you !
````yml
Items:
  BasicDebugStick:
    HideEnchants: true		
````

#### **:bar_chart:HideAttributes**
#### :bar_chart: HideAttributes
The attribute are on some material, not all. If you want to hide lines like *+ 2 damages*, set to ``true``. 
````yml
Items:
  BasicDebugStick:
    HideAttributes: true		
````

#### **:alembic: HidePotionEffets**
#### :alembic: HidePotionEffets
If you DebugStick is a potion (???) or a banner pattern (yes it counts like a potion effect) and you don't want the line that says "*Resistance III (00:20)*"
set to ``true``.
````yml
Items:
  BasicDebugStick:
    HidePotionEffets: true		
````

#### **:art: HideDye**
#### :art: HideDye
Idem but if you use a colored armor and you don't want the line that says that's colored.
````yml
Items:
  BasicDebugStick:
    HideDye: true		
````

#### **:pushpin: HidePlacedOn**
#### :pushpin: HidePlacedOn
Again idem but if in the specific case where you add a tag on it to only place it, and you don't want to have the line on the item (*very specific*).
````yml
Items:
  BasicDebugStick:
    HidePlacedOn: true		
````
#### **:shield: IsUnbreakable**
#### :shield: IsUnbreakable
If you want to add the line "*unbreakable*" on the item. For example, for the **Infinite DebugStick**.
````yml
Items:
  BasicDebugStick:
    IsUnbreakable: true		
````
!> Again, pure cosmetic !
#### **:dress: CustomModelData**
#### :dress: CustomModelData
If your server handle resources pack, you can set a custom textures on the 3 items of the plugin via the ``CustomModelData`` non-necessary field.
````yml
Items:
  BasicDebugStick:
    CustomModelData: 100
````

<!-- tabs:end -->

### **:gear: Settings**
### :gear: Settings
Some miscellaneous settings of the plugin.

<!-- tabs:start -->

#### **:hammer_and_wrench: Durability<span style="opacity: 0">1</span>**
#### :hammer_and_wrench: Durability
We saw that the **Basic DebugStick** loses durability when you use it on a block. But how much durability per use ?<br>
It's your job to define how many ! Put the name of the property (*in uppercase !!!*).

````yml
Settings:
  Durability:
    ORIENTABLE: 1
````

##### :wrench: Recommended Config
````yml
Settings:
  Durability:
    ORIENTABLE: 1
    DIRECTIONAL: 1
    ROTATABLE: 1
    SLAB: 1
    BISECTED: 1
    SHAPE_STAIRS: 1
    SHAPE_RAIL: 1
    PERSISTENT: 3
    MULTIPLE_FACING: 1
    LIGHTABLE: 5
    REDSTONE_WIRE: 2
    WATER_LOGGED: 3
    ANALOGUE_POWERABLE: 10
    POWERABLE: 10
    AGEABLE: 20
    STAGE: 10
    HONEY_LEVEL: 5
    WALL: 1
    SNOWABLE: 3
    DISTANCE: 1
    LAYERS: 1
    BAMBOO_LEAVES: 2
    TILT: 5
    EGGS: 50
    SIGNAL_FIRE: 5
    ATTACHABLE: 5
    EXTENDABLE: 10
    HANGABLE: 5
    CAKE: 5
    BERRY: 10
    CONDITIONAL: 10
    OPENABLE: 1
    END_PORTAL_FRAME: 5
    FACE_ATTACHABLE: 3
    FARMLAND: 3
    GATE: 3
    LEVELLED: 10
    THICKNESS: 5
    VERTICAL_DIRECTION: 1
    DELAY: 1
    LOCKED: 2
    CHARGES: 5
    BLOOM: 10
    PHASE: 3
    SUMMON: 10
    SHRIEKING: 1
    PICKLES: 10
    DISARMED: 3
    OCCUPIED_SLOT: 5
    BRUSHABLE: 3
    HATCH: 20
    PETALS: 20
````

#### **:memo: HideNoPermProperty**
#### :memo: HideNoPermProperty
We saw that you can see the list of editable properties of a block with ``shift + left-click`` and we'll see later that you can define which player can edit which
property.<br>
So it can be normal that you don't want the player to see the property that they can edit. Set this field to ``true``, 
````yml
Settings:
  Durability:
    HideNoPermProperty: true
````

<div style="display: flex">
	<span style="flex: 1"><b>Set to true :</b></span>
	<span style="flex: 1"><b>Set to false:</b></span>
</div>

<div style="display: flex">
<span style="flex: 1">

![Example of the option HideNoPermProperty set to true](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/docs/assets/hide-no-perm-property-true.PNG?raw=true 
':size=90%')

</span>
<span style="flex: 1">

![Example of the option HideNoPermProperty set to false](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/docs/assets/hide-no-perm-property-false.PNG?raw=true
':size=90%')

</span>
</div>

#### ** :no_entry_sign:BlackList**
#### :no_entry_sign: Blacklist
You don't want the player to use the DebugStick on certain blocks or in certain worlds ? Add it into the black list.

<!-- tabs:start -->
##### **:package: Material**
##### :package: Material
Like for the **Material** in the **Items** section, put the type of item.
Again a list can be found [here](https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html)<br>
Each elements of the list, represents a material to exclude.
```yml
Settings:
  BlackList:
    Material:
      - "CHEST"
      - "COMPOSTER" 
```

###### :wrench: Recommander config
Thanks to [@Blaiziuss](https://github.com/Blaiziuss) for the list !
```yml
Settings:
  BlackList:
    Material:
    - "COMPOSTER"
    - "CACTUS"
    - "KELP"
    - "SUGAR_CANE"
    - "GRASS_BLOCK"
    - "ACACIA_LEAVES"
    - "AZALEA_LEAVES"
    - "BIRCH_LEAVES"
    - "DARK_OAK_LEAVES"
    - "FLOWERING_AZALEA_LEAVES"
    - "JUNGLE_LEAVES"
    - "MANGROVE_LEAVES"
    - "OAK_LEAVES"
    - "SPRUCE_LEAVES"
    - "BIG_DRIPLEAF"
    - "BIG_DRIPLEAF_STEM"
    - "CHORUS_FLOWER"
    - "CHORUS_FRUIT"
    - "CHORUS_PLANT"
    - "SEA_PICKLE"
    - "RESPAWN_ANCHOR"
    - "ACACIA_TRAPDOOR"
    - "AZALEA_TRAPDOOR"
    - "BIRCH_TRAPDOOR"
    - "DARK_OAK_TRAPDOOR"
    - "FLOWERING_AZALEA_TRAPDOOR"
    - "JUNGLE_TRAPDOOR"
    - "MANGROVE_TRAPDOOR"
    - "OAK_TRAPDOOR"
    - "SPRUCE_TRAPDOOR"
    - "WARPED_TRAPDOOR"
    - "CRIMSON_TRAPDOOR"
    - "TRIPWIRE_HOOK"
    - "TURTLE_EGG"
    - "BEE_NEST"
    - "BEEHIVE"
    - "CAKE"
    - "SWEET_BERRY_BUSH"
    - "BEETROOT_SEEDS"
    - "MELON_SEEDS"
    - "PUMPKIN_SEEDS"
    - "CHEST"
    - "END_PORTAL_FRAME"
    - "END_PORTAL"
    - "PISTON"
    - "PISTON_HEAD"
    - "SNOW"
```

##### **:earth_africa: Worlds**
##### :earth_africa: Worlds
Put the name of the world's name. Each line represents a world.
```yml
Settings:
  BlackList:
    World:
      - "world_the_end"
```
<!-- tabs:end -->

### **:warning: WarnPlayerWhenBreaking**
### :warning: WarnPlayerWhenBreaking
We see that the **Basic DebugStick** has durability. It could be cool that the player is warned when its tools will break.<br> 
It's possible with this part of the config !
<!-- tabs:start -->
#### **:heavy_check_mark: Enable**
#### :heavy_check_mark: Enable
This allows you to enable or disable this feature.<br>
````yml
Settings:
  WarnPlayerWhenBreaking:
    Enable: true
````
!> If you set to `true` don't forget to configure the message in the messages' files at `OnUse.WarnBreakMessage`.

#### **:postbox: SendOnce**
#### :postbox: SendOnce
When the DebugStick's durability is lower than the limit of warning, should the message be sent just once or each time the tool is used.
```yml
Settings:
  WarnPlayerWhenBreaking:
    SendOnce: true
```

#### **:hammer_and_wrench: Durability**
#### :hammer_and_wrench: Durability
On which durability, the messages to warn the player are sent.
```yml
Settings:
  WarnPlayerWhenBreaking:
    Durability: 5
```
<!-- tabs:end -->
<!-- tabs:end -->

## **:memo: Log**
## :memo: Log
The plugin offers you the possibility to log each time a player uses a DebugStick. 

<!-- tabs:start -->
### **:page_with_curl: Format**
### :page_with_curl: Format
Set the looks of the log, you can customize it with these options :
- `{property_name}`: the name of the property that has been edited (*e.g. Orientable*)
- `{property_perm}`: the name of the permission of the property that has been edited (*e.g. orientable*)
- `{property_durability}`: the cost in durability of the property that has been edited (*e.g. 1*)
- `{value}`: the new value of the property (*e.g. x*)
- `{old_value}`: the old value of the property, before the modification (*e.g. y*)
- `{block_loc_x}`: the coordinate x of the block (*e.g. 1*)
- `{block_loc_y}`: the coordinate y of the block (*e.g. 2*)
- `{block_loc_z}`: the coordinate z of the block (*e.g. 3*)
- `{block_loc_world}`: the name of the world where the block has been edited (*e.g. world_the_end*)
- `{year}`: the year when the block has been edited (*e.g. 2024*)
- `{month}`: the month when the block has been edited (numerical) (*e.g. 2*)
- `{day}`: the day of the month when the block has been edited (*e.g. 24*)
- `{hour}`: the hour when the block has been edited (*e.g. 22*)
- `{minute}`: the minute when the block has been edited (*e.g. 24*)
- `{second}`: the second when the block has been edited (*e.g. 20*)
- `{player}`: the name of the player who edited the block (*e.g. Machigan*)

You can also use the placeholders of [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/), for example `%player_name%`.
```yml
Log:
  Format: "{player} edited the property {property_name} from {old_value} to {value} of the block at {block_loc_x} {block_loc_y} {block_loc_z} in {block_loc_world}"
```
<!-- tabs:end -->

## **:scroll: Recipe**
## :scroll: Recipe

We've seen that it's possible to configure recipes for the three items of the plugin ... but how ?<br>
To begin, we'll take a craft table and numerate each slot from 1 to 9 :
![Crafting table numerate from 1 to 9](https://github.com/MachiganMC/ProtectedDebugStick/blob/master/docs/assets/recipe.PNG?raw=true ':size=90%')

When you configure a recipe, you'll configure the required slots with the number above with the name of the required material.<br>
1. Start by giving a name to the recipe; the name doesn't matter. 
2. Define the item you want to for the result of the recipe in the list :
	- BASIC
    - INFINITY
    - INSPECTOR
3. To finish, add the slots as a key and the material as value to define the recipe itself.

!> If the item is "**BASIC**", you have to add the field ``Durability`` to define the durability that the **DebugStick** will have when crafting.

```yml
Recipes:
  basic:
    Item: "BASIC"
    Durability: 1000
    Craft:
      2: "DIAMOND"
      5: "BLAZE_ROD"
      8: "BLAZE_ROD"
```
<!-- tabs:end -->
