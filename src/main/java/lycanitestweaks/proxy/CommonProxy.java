package lycanitestweaks.proxy;

import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.handlers.LycanitesTweaksRegistry;
import lycanitestweaks.network.PacketHandler;

public class CommonProxy {

    public void preInit() {
        LycanitesTweaksRegistry.init();
        PacketHandler.registerMessages(LycanitesTweaks.MODID);
    }

    public void init(){

    }

    public void postInit() {

    }

    public int getMount3rdPersonView() {
        return 0;
    }

    public void setMount3rdPersonView(int view) {
    }
}