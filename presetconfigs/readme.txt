noscalingaddon - Config for an experience without "Player Mob Levels" scaling
This is meant to be a partial file replacement ready to use with a diff compare. Can be used as a full file replacement.
Relies on InControl to help allow common mobs to drop charges.

* "Random Charge Loot Minimum Mob Level" 0 -> 5 to make rare to see early game until InControl nighttime/spawner
* Disabled "Transform Into Boss NBT Flag" as SpawnedAsBoss tag can be applied through InControl
* Disabled additional altars as they were primarily there to populate the Beastiary Tab
* Disabled "Size Change Foods" and "Soulkeys Set Variant" as no need for min/max without high level threats
* Disabled "Stat Bonus Receivers - Soulbounded Pets" as no need for min/max without high level threats
* Disabled "player mob levels bonus" category
* Removed debuff effects limit from "tweak creature stats" category to match vanilla Lycanites
* Enabled "Override Dungeon Boss Config Level" for laziness and it will log and tell you to set Vanilla Lycanites configs

patchesonly - Config for patches only and a few LycanitesTweaks features
This is meant to be a full file replacement, therefore should be drag n drop or copied pasted.
Anything noted here is outside the "toggle patches" category

* Enabled "0. Modify Beastiary Information" to access the Keybound Soulbound feature
* Enabled "Vehicle Anti Cheese" for NoClip and Mounted entities
* Disabled "Variant/NBT Stat Bonus Receivers", therefore soulbounds will not be given variant boosts with "Fix Properties Set After Stat Calculation" patch
* Enabled "Boss DPS Limit Recalc"
* Enabled "Boss Projectile Modify Tracking Range"
* Enabled "Fix Golems Attacking Tamed Mobs (Vanilla)"
* Enabled "Fix Withers Attacking Tremors (Vanilla)"