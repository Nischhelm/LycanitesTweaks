package lycanitestweaks.handlers.config;

import fermiumbooter.annotations.MixinConfig;
import lycanitestweaks.handlers.config.server.AltarsConfig;
import lycanitestweaks.handlers.config.server.ItemConfig;
import lycanitestweaks.handlers.config.server.LootConfig;
import lycanitestweaks.handlers.config.server.PotionEffectsConfig;
import net.minecraftforge.common.config.Config;

public class ServerConfig {

    @Config.Name("Additional Altars")
    @MixinConfig.SubInstance
    public final AltarsConfig altarsConfig = new AltarsConfig();

    @Config.Name("Additional Effects")
    public final PotionEffectsConfig effectsConfig = new PotionEffectsConfig();

    @Config.Name("Additional Items")
    @MixinConfig.SubInstance
    public final ItemConfig itemConfig = new ItemConfig();

    @Config.Name("Additional Loot")
    @MixinConfig.SubInstance
    public final LootConfig lootConfig = new LootConfig();

    @Config.Comment("Whether Lycanites Block Protection protects against any Living Entity, not just players")
    @Config.Name("Block Protection Living Event")
    public boolean blockProtectionLivingEvent = true;
}