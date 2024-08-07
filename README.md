# Fabric Moderation Engine

<a href="https://discord.gg/aNjm3b6eYJ"><img src="https://img.shields.io/discord/969376256640569474?logo=discord&label=development%20server"></a>
<a href="https://github.com/GirlInPurple/fabric-moderation-engine/actions"><img src="https://img.shields.io/github/actions/workflow/status/GirlInPurple/fabric-moderation-engine/build.yml"></a>


## Why?
There aren't any good anti-cheats or moderation tools for Fabric servers, so I decided to create one myself.\
This mod is meant to work on barely modded and heavily modded server just about the same, although same features may be restricted to one or the other.\
Its also meant to be compatible with as many mods as **_reasonably_** possible, as this is meant to be put on open servers running a modpack that the owner of the server wants to keep safe.

## Tutorials

### Install and Setup
It should be noted this mod only works on dedicated servers. This is **not** a client mod, do not install this on your client.
- Download the mod from the [releases](https://github.com/GirlInPurple/fabric-moderation-engine/releases) page, in the future it you can download it from Modrinth.
- Download a copy of [LuckPerms](https://luckperms.net/) as well, as it is a required dependency.
- Copy the files to the server's `mods` folder, then restart the server if needed.
- TODO: Finish this section

### Building
Just clone the project with the clone button on top (and extract the zip) into whatever empty directory you like and run `./gradlew build` if you're on Linux. For Windows systems, open Command Prompt (CD into the directory if necessary) and type `gradlew.bat build`. <br>
**Java 17** or later is required for the building process.

### White, Gray, Blacklisting and Vantalisting

First, you need to know the difference between these four things:

- Whitelisting is where you can do anything, and it won't be logged
- Graylisting is where you can still do anything, but it will be logged  <!-- consider performance factors! -->
- Blacklisting is where you can't do anything, and it will be logged if you try to  <!-- definitely consider factors, someone may spam the log! -->
- Vantalisting is where you can't do anything at all, and it won't be logged if you try to (Named after the color [Vantablack](https://en.wikipedia.org/wiki/Vantablack)).

## Roadmap
**We are no longer taking feature requests, this is already alot to program considering we are a 2-person team. You can suggest features [here](https://github.com/GirlInPurple/fabric-moderation-engine/issues/new?template=feature_request.yml), although they will not be added until all of the features below have been added first.**
- [ ] Finish README.md, [CONTRIBUTING.md](./.github/CONTRIBUTING.md), and [DOCS.md](./DOCS.md)
- [ ] Post to Modrinth  <!-- You can now download the mod [here](https://modrinth.com/mod/fme) -->
  - [ ] Update checker (adds a Modrinth update notification on startup)
- [ ] Incorporation with other mods
  - [ ] Using [BlueMap](https://modrinth.com/plugin/bluemap)/[Dynmap®](https://modrinth.com/plugin/dynmap)/[squaremap](https://modrinth.com/plugin/squaremap) to show whitelisted/blacklisted areas
  - [ ] [FLan](https://modrinth.com/mod/flan) for easier and more configs for certain areas
  - [ ] Any mod that adds a liquid ([Create](https://modrinth.com/mod/create)'s Honey and [Chocolate](https://www.curseforge.com/minecraft/mc-mods/create-confectionery) come to mind) will follow the same rules as water and lava entering a blacklisted area
  - [ ] [LuckPerms](https://luckperms.net/) support/integration for commands and ability for certain groups to place blocks inside blacklisted areas
- [ ] Add commands
  - [ ] `warn`; Warns the player, can be set up to run automatically on certain events. An optional timer value for temporary warns
    - [ ] `warn list`; Lists out the player who have been warned on the servere, and if given a player it return a description of the warns
    - [ ] `warn pardon`; Unwarns a player
  - [ ] `tempban`; An alternate `ban` that is on a timer, allowing for temporary bans
  - [ ] `tempban-ip`; An alternate `ban-ip` that is on a timer, allowing for temporary bans
    - [ ] It should be noted that `pardon` and `pardon-ip` work on these commands as well
    - [ ] The "method" argument on `ban`, `tempban`, and their IP equivilants is for if you want to hide that fact that you're banning a player. This allows you the option of either crashing their game or kicking them from the server through a fake error instead of a "Banned by an operator" message.
  - [ ] `whois`; gives information about a player such as UUID, IP, online/offline status, when the account was created, other playerdata, etc. To be used to filter out unwanted players.
    - [ ] `whois history`; gives historical data about the player, like IP, last played, first joined, etc.
  - [ ] `jail`; Teleports the player to a set place, restricting certain actions (e.g. build, opening chests, etc.)
  - [ ] `freeze`; Completely freezes the player (movements, inventory interactions, etc.), good for trolling flyhackers :)
  - [x] `admincall`; Allows any player to call anyone with a specific Luckerpms group, by default a group called `Admin`
- [ ] Anti-Grief
  - [ ] Options to completely disable explosives and Lava Buckets
  - [ ] Add a whitelist system for the items above to be used in certain places (TNT based farms, beds for netherite mining, respawn anchors in the nether, etc.)
  - [ ] Liquids won't flow or interact with other things within certain areas
- [ ] Anti-Cheat (complex)
  - [ ] Better vanilla fly-prevention checks
  - [ ] Simple packet exploit patches (packet limiter, crash packet blocker, etc.)
  - [ ] Strict caps for entity speed (including velocity/acceleration), and forces boats to fall to the floor
  - [ ] Mining speed and Kill Aura detection
  - [ ] (An attempt at) Preventing Auto-Totem
- [ ] Add highly customizable configs for everything mentioned above.
- [ ] Datapack support for certain events, like if a player is warned or if a cheat was detected

### Future Features
**Newly suggested features will go here. ~~Probably not in the foreseeable future :).~~**

## Contributing

See [CONTRIBUTING.md](/.github/CONTRIBUTING.md) (TODO)

## Credits and Attributions

Thank you to the following:
- [LuckPerms](https://luckperms.net/) for their Permissions API
- [The EssentialsX Team](https://essentialsx.net/) for some code (GPLv3) and ideas, you can think of this mod as a partial port of their suite
- [falseresync](https://modrinth.com/user/falseresync) for creating [Ban Trigger](https://modrinth.com/mod/ban-trigger)
- [flemmli97](https://modrinth.com/user/flemmli97) for creating [FLan](https://modrinth.com/mod/flan)
<!-- - [QuendoDev](https://github.com/QuendoDev) for creating [QStaffMode](https://github.com/QuendoDev/QStaffMode) -->
- [DogLoverPink's Minecraft Error Ban](https://github.com/DogLoverPink/Minecraft-Error-Ban) plugin and [Goteusz Maszyk's Client Crasher](https://github.com/goteusz-maszyk/ClientCrasher-Fabric) mod, as `ban` and `tempban`'s "method" argument is based off these two projects
