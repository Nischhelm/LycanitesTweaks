package lycanitestweaks.proxy;

import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.network.PacketHandler;

public class CommonProxy {

    public void preInit() {
        PacketHandler.registerMessages(LycanitesTweaks.MODID);
    }

    public void init(){

    }

    public void postInit() {

    }
}