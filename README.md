# Mineplex Map Parser
#### A project by Plexverse Studio for the Mineplex Studio Engine

![Datapoint Menu Example](https://i.imgur.com/j7RDTI4.png)

Mineplex Map Parser is a tool for creating datapoints. It allows you to build a map, place down armor stands and then parse the map in the format Mineplex Studio accepts, with the appropriate datapoint format.

## Output
This plugin outputs a zip file with the format `GAME_NAME-WORLD_NAME.zip`, this is a minecraft world with the junk files ripped out.
This zip file includes a `dataPoints.json` file: information on this file structure can be [found here](https://studio.mineplex.com/docs/sdk/modules/world/datapoints).

This also generates a `mapMeta.json` file, with the following structure:
```json
{
  "author": "efa1e2d8-871d-4ede-93e5-3c82102cbd42,a44aece0-abb2-438a-a352-11a9829b33ae",
  "gameType": "SKYWARS",
  "mapName": "CookieBilly Land",
  "legacy": false
}
```
This is useful for figuring out who built a map at runtime, or marking it as an old Map in-game. You can encase the Mineplex world object with extra details on the SDK.

This output zip file should be placed in `assets/world-templates` in your Mineplex project.

---

# User instructions

## Placing datapoints

### /mapsettings
In order to place datapoints, you first must use the `/mapsettings` command and give your map the following details:
- A name for your map (a-z, 1-9, nothing else is allowed)
- If the map is a legacy map (was on previous Mineplex)
- Author (this is UUIDs of the authors, seperated by commas: e.g. `efa1e2d8-871d-4ede-93e5-3c82102cbd42,a44aece0-abb2-438a-a352-11a9829b33ae`)

### Placing an armorstand
- In order to get started, place an armor stand on your map, and right click it.
- Select a datapoint type (for example, a spawnpoint).
- Choose the team (if applicable): 
  - You can choose between pre-defined teams: Aqua, Yellow, Red, Green
  - You can define your own team using the black bed, e.g. Magenta 

To speed things up, you can click the Piston to quickly clone datapoints.

### Parsing the world
Once you've met all of the requirements of a game, use the `/parse [size]` command.
This will output a zip file, as mentioned in the output section.

---

# Developer instructions

## Adding new games and datapoints
To add a new game, edit `GameType.java`, add your game and modify the requirements, display name, and eligible datapoints. 

To add new datapoint types, go to `DataPointType.java` and add an extra enum value
Example:
```java
//menuName
//hasTeam
//changeYawPitch
//material
NEW_DATAPOINT("Display Name", true, false, Material.END_PORTAL_FRAME)
```

### Datapoint requirements:
- These are the number of datapoints required in order to `/parse` the world.

### Data point type list:
- These are the datapoints relevant to the game, this will show up on the armorstand menu

## Building the plugin

### Set up codeartifact
This plugin runs on the old Maven Mineplex structure. You must set-up the `Generate AWS codeartifact Credentials for Maven` [plugin on Intellij](https://plugins.jetbrains.com/plugin/16777-aws-codeartifact--maven).

Set up your AWS credentials, this can be done by following the guide pinned in the `#announcements` channel in the Mineplex Studio Discord. [Message Link](https://discord.com/channels/1122000908671270994/1122010968222867526/1141520390775132210)

Generate the key using the plugin:
Ensure you point `settings.xml` to the settings.xml within this project.

![Codeartifact Setup](https://i.imgur.com/Jj68cX1.png)

Next, point Maven at the correct place for your settings file.
- Open Maven Settings (click shift twice & type in Maven Settings)
- `User settings file` should be pointed at the `settings.xml` within this project

### Build
Pretty simple, `mvn install`. Drag and drop the output jar (`target/MapParser-1.0-SNAPSHOT`) to your build server's plugins' folder, and enjoy yourself!

## Contributing
This plugin has a few light utils, but it needs a lot more work, the code is a mess as it was built as a quick internal tool.  If you improve this plugin in any way, we ask that you contribute back to the main repository to help others using the tool.