# Cobblemon: Team Preview

[![License: MPL-2.0](https://img.shields.io/badge/License-MPL_2.0-brightgreen.svg?style=flat-square)](https://opensource.org/licenses/MPL-2.0)
[![modrinth-badge](https://img.shields.io/modrinth/dt/cobblemon-team-preview?label=Modrinth&logo=Modrinth&style=flat-square)](https://modrinth.com/mod/cobblemon-team-preview/versions)

![cobblemon-team-preview-icon](/common/src/main/resources/assets/battle_showdown/icon.png)

Cobblemon: Team Preview is a Cobblemon addon that adds **team preview formats** to PvP battles: before the battle starts, both players see each other's team and choose which Pokémon they bring and in which order.

## Features

- **Singles Preview**: you bring your whole team and choose its order. The first Pokémon you pick leads.
- **Doubles Preview** (VGC style): bring 6, pick 4. The first 2 you pick lead, the other 2 stay in the back.
- **Ordered selection**: click your Pokémon in the order you want them and confirm to lock the selection.
- **Timers**: 60 seconds to choose. If the time runs out, the missing picks are filled in with your team order.
- **Hidden information**: the rival only sees species, level, HP and status.

Both formats show up in Cobblemon's challenge screen, right after the normal Singles and Doubles. The normal Cobblemon battles are untouched and start without a preview.

## Requirements

The mod must be installed on **both** the server and the clients.

| | Version |
|---|---|
| Minecraft | 1.21.1 |
| Cobblemon | 1.8.2+ (below 1.9.0) |
| Fabric Loader | 0.17.2+ with Fabric API and Fabric Language Kotlin |
| NeoForge | 21.1.182+ with Kotlin for Forge |

## Downloads

Get the jar for your loader from [Modrinth](https://modrinth.com/mod/cobblemon-team-preview/versions) and place it in your `mods` folder.

What changed in the current version is listed in [CHANGELOG.md](/CHANGELOG.md); older versions are kept in [changelogs](/changelogs).

## Building

```bash
./gradlew build
```

The Fabric and NeoForge jars end up in `build/libs`.

## License

Licensed under [MPL-2.0](https://mozilla.org/MPL/2.0/)
