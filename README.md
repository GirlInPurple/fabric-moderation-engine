# Fabric Moderation Engine

## Why?
There aren't any good anti-cheats or moderation tools for Fabric servers, so I decided to create one myself.\
This mod is meant to work on barely modded and heavily modded server just about the same, although same features may be restricted to one or the other.\
Its also meant to be compatible with as many mods as **_reasonably_** possible, as this is meant to be put on open servers running a modpack that the owner of the server wants to keep safe.

## Tutorial

### Installing
TODO

### White, Gray, and Blacklisting

First, you need to know the difference between these three:

- Whitelisting is where you can do the action, and it won't be logged
- Graylisting is where you can do the action, but it will be logged
- Blacklisting is where you can't do the action, and it will be logged if you try to
- Vantalisting is where you can't do the action, but it won't be logged if you try to (Named after the color [Vantablack](https://en.wikipedia.org/wiki/Vantablack))

## Roadmap
- [ ] Write the Tutorial in the README
- [ ] Post to Modrinth
  - [ ] Add a Modrinth update notification on startup
- [ ] Compatibility with other mods
  - [ ] [Bluemap](https://modrinth.com/plugin/bluemap)/[Dynmap](https://modrinth.com/plugin/dynmap)/[Squaremap](https://modrinth.com/plugin/squaremap) for showing whitelisted/blacklisted areas
  - [ ] [FLan](https://modrinth.com/mod/flan) for easier and more configs for certain areas
  - [ ] Any mod that adds a liquid ([Create](https://modrinth.com/mod/create)'s Honey and [Chocolate](https://www.curseforge.com/minecraft/mc-mods/create-confectionery) come to mind) will follow the same rules as water and alava entering a blacklisted area
  - [ ] [Luckperms](https://luckperms.net/) support for commands and ability for certain groups to place blocks inside blacklisted areas
- [ ] Add commands
  - [ ] `warn`; Warns the player, can be set up to run automatically on certain events. An optional timer value for temporary warns.
  - [ ] `unwarn`; Un-warns the player
  - [ ] `tempban`; An alternate `ban` that is on a timer, allowing for temporary bans
  - [ ] `tempban-ip`; An alternate `ban-ip` that is on a timer, allowing for temporary bans
  - [ ] `whois`; gives the uuid, ip, online/offline status, when the account was created, etc. To be used to filter out unwanted players.
- [ ] Some kind of anti-grief system
  - [ ] Option to completely disable TNT, TNT Minecarts, End Crystals, Respawn Anchors, Beds, and Lava Buckets
  - [ ] Add a whitelist system for the items above to be used in certain places (TNT based farms, beds for netherite mining, respawn anchors in the nether, etc)
  - [ ] Liquids won't flow or create blocks when touching another liquid within certain areas
- [ ] Add highly customizable configs for everything here

## Contributing

There are no specific guidelines

## Contributors

Thank you to the following:
- [Luckperms](https://luckperms.net/) for their Permissions API
- [EssentialsX](https://essentialsx.net/) for some code (GPLv3) and ideas, you can think of this mod as a partial port of their suite
- falseresync for creating [Ban Trigger](https://modrinth.com/mod/ban-trigger)
- flemmli97 for creating [FLan](https://modrinth.com/mod/flan)
- 
