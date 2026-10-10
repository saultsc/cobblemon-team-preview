# Changelog
## [1.1.0 (October 9th, 2026)](#1-1-0)

### Additions
- Ported the team preview from the Fabric-only version to Fabric + NeoForge.
- Added the Singles Preview and Doubles Preview formats to Cobblemon's challenge screen.
- Added a Cobblemon styled confirm button; the selection is locked only after confirming.
- Pokémon are now picked in order: each pick shows its position and can be dropped again.
- Doubles Preview is bring 6, pick 4, with the first 2 picks as leads.
- Added Spanish translation.

### Changes
- Updated to Cobblemon 1.8.1, Kotlin 2.2.20, Fabric Loader 0.17.2 and NeoForge 21.1.182.
- Switched to Mojang mappings and the cobblemon-contenders build setup.
- The preview only opens for the preview formats; normal battles start right away as in Cobblemon.
- Selection time is now 60 seconds; when it runs out the selection is completed with the team order instead of cancelling.
- Timers now run on the server tick instead of a background thread.
- Battles are started through Cobblemon's battle builder (respects level-adjusted formats and validations).

### Fixes
- The rival only receives the data shown in the preview (no moves, IVs, EVs, nature, ability or held item).
- Previews are cancelled when a player disconnects or dies, and players are looked up by UUID when the battle starts.
- Teams without any usable Pokémon skip the preview so Cobblemon reports the error right away.
- Only the left mouse button selects a Pokémon.
- The selection survives resizing the window.
