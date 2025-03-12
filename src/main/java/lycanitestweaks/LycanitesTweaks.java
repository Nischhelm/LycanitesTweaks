package lycanitestweaks;

import lycanitestweaks.handlers.ForgeConfigHandler;
import lycanitestweaks.handlers.features.boss.DamageLimitCalcHandler;
import lycanitestweaks.handlers.features.equipment.ItemEquipmentRLCombatSweepHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lycanitestweaks.handlers.ModRegistry;
import lycanitestweaks.proxy.CommonProxy;

@Mod(modid = LycanitesTweaks.MODID, version = LycanitesTweaks.VERSION, name = LycanitesTweaks.NAME, dependencies = "required-after:fermiumbooter")
public class LycanitesTweaks {
    public static final String MODID = "lycanitestweaks";
    public static final String VERSION = "1.0.0";
    public static final String NAME = "LycanitesTweaks";
    public static final Logger LOGGER = LogManager.getLogger();
	
    @SidedProxy(clientSide = "lycanitestweaks.proxy.ClientProxy", serverSide = "lycanitestweaks.proxy.CommonProxy")
    public static CommonProxy PROXY;
	
	@Instance(MODID)
	public static LycanitesTweaks instance;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRegistry.init();
        LycanitesTweaks.PROXY.preInit();

        if(ForgeConfigHandler.mixinConfig.bossDPSLimitRecalc) MinecraftForge.EVENT_BUS.register(DamageLimitCalcHandler.class);
        if(ForgeConfigHandler.mixinConfig.craftedEquipmentRLCombatSweep) MinecraftForge.EVENT_BUS.register(ItemEquipmentRLCombatSweepHandler.class);
    }
}