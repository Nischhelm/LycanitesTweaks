package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.handlers.config.server.AltarsConfig;
import lycanitestweaks.handlers.config.server.EnchantedSoulkeyConfig;
import lycanitestweaks.handlers.config.server.LootConfig;
import lycanitestweaks.handlers.config.server.PotionEffectsConfig;
import net.minecraftforge.common.config.Config;

public class ServerConfig {

    @Config.Name("Additional Altars")
    @MixinConfig.SubInstance
    public final AltarsConfig altarsConfig = new AltarsConfig();

    @Config.Comment("LycanitesTweaks adds two new Potion Effects (Voided and Consumed) that are only intended for the main boss fights. \n" +
            "Voided is used by Rahovart and Asmodeus, while Consumed is used by Amalgalich\n" +
            "Both effects are fully customisable and intended to make the boss fights harder\n" +
            "By default, Voided stops the player from getting new buffs and slightly decreases their health (-10%).\n" +
            "While Consumed not only stops buffs from being added, but also removes all current buffs, decreases the players health to 5% and blocks item use (rightclick).\n" +
            "Additionally, Voided will turn all environmental damages to piercing damage, while Consumed does that with any damage")
    @Config.Name("Additional Effects")
    public final PotionEffectsConfig effectsConfig = new PotionEffectsConfig();

    @Config.Comment("Here you could explain how the enchanted soulkey is supposed to work. \n" +
            "Like that it has charges, that it can be used in the infuser and the station\n" +
            "what doing that does (i guess infuser for lvl and station for how often you can use it?)\n" +
            "and that it can increase the summoned bosses lvl")
    @Config.Name("Enchanted Soulkey")
    @MixinConfig.SubInstance
    public final EnchantedSoulkeyConfig enchSoulkeyConfig = new EnchantedSoulkeyConfig();

    @Config.Comment("Here you could explain that you added vanilla loot tables (accessible via resourcepacks or loottweaker) for all lyca mobs\n" +
            "and what kinda loot tables you added for what kinda mobs")
    @Config.Name("Additional Loot")
    @MixinConfig.SubInstance
    public final LootConfig lootConfig = new LootConfig();

    @Config.Comment("Whether Lycanites Block Protection protects against any Living Entity, not just players")
    @Config.Name("Block Protection Living Event")
    public boolean blockProtectionLivingEvent = true;
}