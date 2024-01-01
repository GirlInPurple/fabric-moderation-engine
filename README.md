# Fabric Moderation Engine

## Why?
There aren't any good anti-cheats or moderation tools for Fabric servers, so I decided to create one myself.\
This mod is meant to work on barely modded and heavily modded server just about the same, although same features may be restricted to one or the other.\
Its also meant to be compatible with as many mods as **_reasonably_** possible, as this is meant to be put on open servers running a modpack that the owner of the server wants to keep safe.

## Tutorials

### Install and Setup
It should be noted this mod only works on dedicated servers. Do not install this on your client.
- Download the mod from the [releases](https://github.com/GirlInPurple/fabric-moderation-engine/releases) page, in the future it you can download it from Modrinth.
- Download a copy of [LuckPerms](https://luckperms.net/) as well, as it is a required dependency.
- Copy the files to the server's `mods` folder, then restart the server if needed.
- TODO: Finish this section

### Building
Just clone to project into whatever empty directory you like and run `./gradlew build`\
Requires at minimum Java 17

### White, Gray, Blacklisting and Vantalisting

First, you need to know the difference between these four things:

- Whitelisting is where you can do anything, and it won't be logged
- Graylisting is where you can still do anything, but it will be logged  <!-- consider performance factors! -->
- Blacklisting is where you can't do anything, and it will be logged if you try to  <!-- definitely consider factors, someone may spam the log! -->
- Vantalisting is where you can't do anything at all, and it won't be logged if you try to (Named after the color [Vantablack](https://en.wikipedia.org/wiki/Vantablack)).

## Roadmap
**We are no longer taking feature requests, this is already alot to program considering we are a 2-person team. If you suggest features they will not be added until all of the features below have been added first.**
- [ ] Finish README.md, CONTRIBUTING.md, and DOCS.md
- [ ] Post to Modrinth
  - [ ] Add a Modrinth update notification on startup
- [ ] Compatibility with other mods
  - [ ] [Bluemap](https://modrinth.com/plugin/bluemap)/[Dynmap](https://modrinth.com/plugin/dynmap)/[Squaremap](https://modrinth.com/plugin/squaremap) for showing whitelisted/blacklisted areas
  - [ ] [FLan](https://modrinth.com/mod/flan) for easier and more configs for certain areas
  - [ ] Any mod that adds a liquid ([Create](https://modrinth.com/mod/create)'s Honey and [Chocolate](https://www.curseforge.com/minecraft/mc-mods/create-confectionery) come to mind) will follow the same rules as water and alava entering a blacklisted area
  - [ ] [LuckPerms](https://luckperms.net/) support/integration for commands and ability for certain groups to place blocks inside blacklisted areas
- [ ] Add commands
  - [ ] `warn`; Warns the player, can be set up to run automatically on certain events. An optional timer value for temporary warns
    - [ ] `warn list`; Lists out the player who have been warned on the servere, and if given a player it return a description of the warns
    - [ ] `warn pardon`; Unwarns a player
  - [ ] `tempban`; An alternate `ban` that is on a timer, allowing for temporary bans
  - [ ] `tempban-ip`; An alternate `ban-ip` that is on a timer, allowing for temporary bans
    - [ ] It should be noted that `pardon` and `pardon-ip` work on these commands as well
    - [ ] The "method" argument on `ban`, `tempban`, and their IP equivilants is for if you want to hide that fact that you're banning a player. This allows you the option of either crashing their game or kicking them from the server through an error instead of a "You're Banned!" message.
  - [ ] `whois`; gives the UUID, IP, online/offline status, when the account was created, other playerdata, etc. To be used to filter out unwanted players.
    - [ ] `whois history`; gives historical data about the player, like IP, last played, first joined, etc.
  - [ ] `jail`; Teleports the player to a set place, restricting certain actions (e.g. build, opening chests, etc.)
  - [ ] `freeze`; Completely freezes the player (movements, inventory interactions, etc.), good for trolling flyhackers :)
- [ ] Anti-Grief
  - [ ] Options to completely disable explosives and Lava Buckets
  - [ ] Add a whitelist system for the items above to be used in certain places (TNT based farms, beds for netherite mining, respawn anchors in the nether, etc.)
  - [ ] Liquids won't flow or interact with other things within certain areas
- [ ] Anti-Cheat
  - [ ] Better "Flying is not enabled on this server." checks
  - [ ] Simple packet exploit patches
  - [ ] Strict caps for entity speed, and forces boats to fall to the floor
  - [ ] Mining speed and Kill Aura detection
  - [ ] Disables Auto-Totem
- [ ] Add highly customizable configs for everything mentioned above.
- [ ] Datapack support for certain events, like if a player is warned or if a hack was detected

### Future Features
**Newly suggested features will go here**

## Contributing

See CONTRIBUTING.md (TODO)

## Contributors

Thank you to the following:
- [LuckPerms](https://luckperms.net/) for their Permissions API
- [The EssentialsX Team](https://essentialsx.net/) for some code (GPLv3) and ideas, you can think of this mod as a partial port of their suite
- [falseresync](https://modrinth.com/user/falseresync) for creating [Ban Trigger](https://modrinth.com/mod/ban-trigger)
- [flemmli97](https://modrinth.com/user/flemmli97) for creating [FLan](https://modrinth.com/mod/flan)
- [DogLoverPink's Minecraft Error Ban](https://github.com/DogLoverPink/Minecraft-Error-Ban) plugin and [Goteusz Maszyk's Client Crasher](https://github.com/goteusz-maszyk/ClientCrasher-Fabric) mod, as `ban` and `tempban`'s "method" argument is based off these two projects
