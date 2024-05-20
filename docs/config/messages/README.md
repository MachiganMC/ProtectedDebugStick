# :thought_balloon: Messages file
You want to configure the messages of the plugin ? It's normal !<br>
We'll not see each field of the files because the way to configure them is always the same (*and it would be very very long*).<br>
We'll see how to configure them ! How you can customize it as much as you want !

## :art: Colors and styles
The basis in a configuration of a message, it's to colorise it.

### :art: Base colors
The old way and faster to colorise a message is via the base colors. In addition to the colors you can also add some style like
**bold**, *italic*, <span style="text-decoration: underline">underline</span>, etc...<br>
You'll find all codes [here](https://www.digminecraft.com/lists/color_list_pc.php) (*check 'Chat Code' column*). Replace just the ``§`` char with the `&` char.
````yml
MyMessage: "&6Hello I am gold &oand italic !"
````

### :hash: Hex colors 
In ``1.16``, Minecraft adds the chance to use every possible color using the hex code and the plugin allows you to, great !<br>
To colorise the text in hex color you'll have to follow this format : ``#[hex_code]``. You can find a color picker
[here](https://rgbcolorcode.com/).
````yml
MyMessage: "#FF4019I am in 'orange-red' color #D5FFCCand me 'tea green'."
````

### :rainbow: Gradient
To finish with color, you can generate a gradient from a color to another color to make a cool transition with the char of a message.<br>
To generate a gradient the format ``<s:[hex_color_from]>[you_message]<e:[hex_color_to]>`` can be complex but easy with an example.

````yml
MyMessage: "<s:000000>Gradient from black to white<e:FFFFFF><s:FF9580>and from 'light salmon' to 'ufo green'<e:33FF77>"
````

You can stop the generation of a gradient temporally by using a base color and take it back by using the reset color code.
````yml
MyMessage: "<s:FFFFFF>Gradient from white to &6gold color now !&rtaking back the gradient<e:000000>"
````

## :page_facing_up: Content
A message isn't just a text ! You can let the player interact with it.

### :eyes: Hover
You can add another message when the player will hover the message with its mouse.<br>
You'll achieve that by simply adding a new field with key ``[message_field]Hover``.
````yml
MyMessage: "Can you pass you mouse on me ? ^^"
MyMessageHover: "Thank you !"
````

### :computer_mouse: Click
You can also trigger action when the player will click on your message. For logical reason, you'll have to chose which action you want to
trigger because only one can be performed.

#### :runner: Run
When the player clicks on the message, it will run very fast ... I mean ...<br>
The player will execute a command ! Better ..?<br>
To achieve that, add a field with the following format ``[message_field]Run`` and as value the command with a `/` before.
````yml
MyMessage: "Click on me to go to spawn !"
MyMessageRun: "/spawn"
````

#### :thinking: Suggest
If the player clicks on the message, another message will be written in its command bar to suggest it a command or a message.<br>
The format is ``[message_field]Suggest``.
````yml
MyMessage: "Home not found ... Click to have your primary home"
MyMessageSuggest: "/home maison"
````

### :bar_chart: Actionbar
To finish with all types of message, you can also send a message to the actionbar of the player in addition to the base message by adding the field
``[message_field]Hotbar``.
````yml
MyMessage: "A message in the tchat"
MyMessageHotbar: "A message in the action bar"
````
!> We'll see later, that some message isn't necessary. For these messages, you can only configure the actionbar message and not the base message.

## :no_entry: Unnecessary message
We are later !<br>
All messages aren't necessary, it means that you don't have to assign them a value. But how you can know which message is necessary and which message isn't ?<br>
It's always marked !
````yml
# This message is necessary
MyMessage: "You have to configure me !"

# This message isn't necessary
MySecondMessage: 
````

!> If you do not configure a necessary message, it will have the default value. So the value that the message had when you've downloaded the plugin.

## :memo: Placeholder
To configure the message even more, it's important to have the context where the message appears. <br>
For example, if a player edits a block, and you want to send a message about
the new value of the property, you'll have to know the property. But this value can change depending on the context.<br>

The placeholders exist for this, you configure a message, and instead of the value of a variable you write the variable. The plugin will replace it by the value for you !

But how to know when you can use a variable, which one and what it represents ? It's always written !
````yml
# Placeholders :
# {value} : the new value of the property 
Message: "The new value of the property is {value}"

# One time the message will be :
# 	The new value of the property is south
# 	The new value of the property is north
````

You have to know that there are complete placeholders, for the moment two. These placeholder represent an object so you can access to its properties by its placeholders.

### :mag: Property
Represent a property (*e.g. [orientable](/property/orientable)*). The placeholders marked in message description is ``{property}`` and it let you access
to these placeholders :
- ``{property_name}`` : the name of the property.
- ``{property_durability}`` : the durability cost of the property.
- ``{property_perm}`` : the permission required to edit the property.

````yml
# Placeholders :
# {property} : the edited property
Message: "You edited the property {property_name} for a cost of {property_durability} durability."
# One time the message will be :
# 	You edited the property orientable for a cost of 10 durability.
# 	You edited the property bisected for a cost of 5 durability.
````

### :package: Block
Represents a block in the world. The placeholders marked in message description is ``{block}`` and it let you access to these placeholders :
- ``{block_material}`` : the material of the block.
- ``{block_loc_x}`` : the location `x` of the block.
- ``{block_loc_y}`` : the location `y` of the block.
- ``{block_loc_z}`` : the location `z` of the block.
- ``{block_loc_world}`` : the name of the world where the block is.

````yml
# Placeholders :
# {block} : the edited block
Message: "You edited a {block_material} located at {block_loc_x}, {block_loc_y}, {block_loc_z} in the world {block_loc_world}."
# One time the message will be :
# 	You edited a grass block located at 10, 60, 12 in the world spawn.
# 	You edited a spruce stairs located at 10, 60, 12 in the world world_the_end.
````

[//]: # (### ![]&#40;https://camo.githubusercontent.com/4ad47d972a82e3d28d61e54dae16c29d0542eeaf396a036e01f667ce23e5ca80/68747470733a2f2f77696b692e706c616365686f6c6465726170692e636f6d2f6173736574732f696d672f706170692d6c6f676f2e706e67 ':size=10%'&#41; PlaceholderAPI)
### <img src="https://camo.githubusercontent.com/4ad47d972a82e3d28d61e54dae16c29d0542eeaf396a036e01f667ce23e5ca80/68747470733a2f2f77696b692e706c616365686f6c6465726170692e636f6d2f6173736574732f696d672f706170692d6c6f676f2e706e67" class="emoji"> PlaceholderAPI
We saw that [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) is supported (*and not necessary !*). It's totally usable in the message configuration.<br>
But where to use the placeholders of this plugin ? Anywhere ! But not all ...

In [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/), there is two types of placeholders :
1. Those are linked to a player, e.g. ``%player_name%``.
2. And those who aren't linked to a player, e.g. ``%server_name%``.

The second one is usable anywhere !<br>
The first one will depend on the context, and it's normal there is not always a user implicated in a message. But when it's the case, it's always written.

!> In a case of you don't see it, for all messages in the section ``OnUse``. The placeholders linked to a player are always usable.

## :receipt: Personal variables
To finish, the plugin lets you configure your own variables.<br>
Imagine that you have a word or a sentence that you write very often using the copy-paste can be a bad idea. If you want to change this word or sentence, you have to
change everywhere you used these.

**Protected DebugStick** has an answer to this issue, the *personal variables* !<br>
Let's take, for example, a prefix. You want to add a prefix for each message of the plugin. How to use the personal variable to have your prefix ?

1. Let's configure first the personal variable :
````yml
PersonalVariable:
	prefix: "#3399FF[<s:667db6>Debug<e:0082c8>#3399FF-<s:0082c8>Stick<e:667db6>#3399FF]&r"
````
2. And we can use it in any message of the plugin by inserting with this format ``{name_of_the_variable}`` :
````yml
# will be replaced by : "#3399FF[<s:667db6>Debug<e:0082c8>#3399FF-<s:0082c8>Stick<e:667db6>#3399FF]&r &aHello message"
MyMessage: "{prefix} &aHello message"
````
