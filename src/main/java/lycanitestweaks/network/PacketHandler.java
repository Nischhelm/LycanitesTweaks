package lycanitestweaks.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketHandler {

    public static SimpleNetworkWrapper instance = null;

    public static void registerMessages(String channelName) {
        instance = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);
        registerMessages();
    }

    public static void registerMessages() {
        instance.registerMessage(PacketPlayerMobLevelsStats.ServerHandler.class, PacketPlayerMobLevelsStats.class, 1, Side.SERVER);
        instance.registerMessage(PacketExtendedPlayerSelectedCreature.ServerHandler.class, PacketExtendedPlayerSelectedCreature.class, 2, Side.SERVER);
        instance.registerMessage(PacketKeybindsSync.ServerHandler.class, PacketKeybindsSync.class, 3, Side.SERVER);
        instance.registerMessage(PacketKeybindSoulgazerAutoNext.ServerHandler.class, PacketKeybindSoulgazerAutoNext.class, 4, Side.SERVER);
        instance.registerMessage(PacketKeybindSoulgazerManualNext.ServerHandler.class, PacketKeybindSoulgazerManualNext.class, 5, Side.SERVER);
    }

    @SideOnly(Side.CLIENT)
    public static void registerClientMessages() {
        instance.registerMessage(PacketPlayerMobLevelsStats.ClientHandler.class, PacketPlayerMobLevelsStats.class, 1, Side.CLIENT);
        instance.registerMessage(PacketKeybindsSync.ClientHandler.class, PacketKeybindsSync.class, 3, Side.CLIENT);
    }
}
