package lycanitestweaks.proxy;

import lycanitestweaks.client.ClientEventListener;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        MinecraftForge.EVENT_BUS.register(ClientEventListener.class);
    }
}