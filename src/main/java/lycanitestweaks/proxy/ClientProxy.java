package lycanitestweaks.proxy;

import lycanitestweaks.client.ClientEventListener;
import lycanitestweaks.client.renderer.entity.RenderBossSummonCrystal;
import lycanitestweaks.entity.item.EntityBossSummonCrystal;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
        RenderingRegistry.registerEntityRenderingHandler(EntityBossSummonCrystal.class, RenderBossSummonCrystal::new);
        MinecraftForge.EVENT_BUS.register(ClientEventListener.class);
    }

    @Override
    public void init(){
    }

    @Override
    public void postInit() {

    }
}