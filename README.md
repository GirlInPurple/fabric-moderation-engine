# Fabric Moderation Engine

## Why?
There aren't any good anti-cheats or moderation tools for Fabric servers, so I decided to create one myself.\
This mod is meant to work on barely modded and heavily modded server just about the same, although same features may be restricted to one or the other.\
Its also meant to be compatible with as many mods as **_reasonably_** possible, as this is meant to be put on open servers running a modpack that the owner of the server wants to keep safe.

## Tutorial

### Installing (is this good?)
- Download the mod from the [releases](https://github.com/GirlInPurple/fabric-moderation-engine/releases) page.
- Copy the file to the server's mod folder, then restart the server if needed.

### Building
TODO

### White, Gray, Blacklisting and Vantalisting

First, you need to know the difference between these four things:

- Whitelisting is where you can do anything, and it won't be logged
- Graylisting is where you can still do anything, but it will be logged  <!-- consider performance factors! -->
- Blacklisting is where you can't do anything, and it will be logged if you try to  <!-- definitely consider factors, someone may spam the log! -->
- Vantalisting is where you can't do anything at all, and it won't be logged if you try to (Named after the color [Vantablack](https://en.wikipedia.org/wiki/Vantablack)).

## Roadmap
- [ ] Write the Tutorials in the README
- [ ] Post to Modrinth
  - [ ] Add a Modrinth update notification on startup
- [ ] Compatibility with other mods
  - [ ] [Bluemap](https://modrinth.com/plugin/bluemap)/[Dynmap](https://modrinth.com/plugin/dynmap)/[Squaremap](https://modrinth.com/plugin/squaremap) for showing whitelisted/blacklisted areas
  - [ ] [FLan](https://modrinth.com/mod/flan) for easier and more configs for certain areas
  - [ ] Any mod that adds a liquid ([Create](https://modrinth.com/mod/create)'s Honey and [Chocolate](https://www.curseforge.com/minecraft/mc-mods/create-confectionery) come to mind) will follow the same rules as water and alava entering a blacklisted area
  - [ ] [LuckPerms](https://luckperms.net/) support/integration for commands and ability for certain groups to place blocks inside blacklisted areas
- [ ] Add commands
  - [ ] `warn`; Warns the player, can be set up to run automatically on certain events. An optional timer value for temporary warns
    <!-- This section needs approval
    - Each warn produces a (UU)ID for tracing back and removal
    - Can be set up to execute commands/actions when the player reaches a certain amount of warnings
    - Usage: <todo>
    -->
  <!-- - [ ] `unwarn`; Un-warns the player -->
  - [ ] `tempban`; An alternate `ban` that is on a timer, allowing for temporary bans
  - [ ] `tempban-ip`; An alternate `ban-ip` that is on a timer, allowing for temporary bans
  <!-- Maybe use this format: "6w5d4h3m2s1t"? -->
    - [ ] Allow specific IP address ranges (e.g. "13.37.*.*")?
  - [ ] `whois`; gives the UUID, IP, online/offline status, when the account was created, other playerdata, etc. To be used to filter out unwanted players.
  - [ ] `jail`; \<please expand\> Teleports the player to a set place, restricting certain actions (e.g. build, opening chests, etc.) <!-- imo this should not block movement -->
  - [ ] `freeze`; \<please expand\> Completely freezes the player (movements, inventory interactions, etc.), good for trolling flyhackers :)
- [ ] Some kind of anti-grief system
  - [ ] Options to completely disable explosives and Lava Buckets
  - [ ] Add a whitelist system for the items above to be used in certain places (TNT based farms, beds for netherite mining, respawn anchors in the nether, etc.)
  - [ ] Liquids won't flow or interact with other things within certain areas
- [ ] Add highly customizable configs for everything above

## Contributing

There are no specific guidelines  <!-- How about "See CONTRIBUTING.md"? -->

## Contributors

Thank you to the following:
- [LuckPerms](https://luckperms.net/) for their Permissions API
- [The EssentialsX Team](https://essentialsx.net/) for some code (GPLv3) and ideas, you can think of this mod as a partial port of their suite
- [falseresync](https://modrinth.com/user/falseresync) for creating [Ban Trigger](https://modrinth.com/mod/ban-trigger)
- [flemmli97](https://modrinth.com/user/flemmli97) for creating [FLan](https://modrinth.com/mod/flan)
<!--
- [DogLoverPink](https://github.com/DogLoverPink) for the (funny) [Minecraft-Error-Ban](https://github.com/DogLoverPink/Minecraft-Error-Ban) plugin
-->
