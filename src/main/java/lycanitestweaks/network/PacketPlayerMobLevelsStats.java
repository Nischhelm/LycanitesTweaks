package lycanitestweaks.network;

import io.netty.buffer.ByteBuf;
import lycanitestweaks.LycanitesTweaks;
import lycanitestweaks.capability.IPlayerMobLevelCapability;
import lycanitestweaks.capability.PlayerMobLevelCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class PacketPlayerMobLevelsStats implements IMessage {

    private int[] nonMainLevels = new int[5];
    private Queue<Integer> mainHandLevels = new LinkedList<>();
    private Map<Integer, Integer> petEntryLevels = new HashMap<>();


    // ==================================================
    //                    Constructors
    // ==================================================
    public PacketPlayerMobLevelsStats() {}
    public PacketPlayerMobLevelsStats(PlayerMobLevelCapability pml) {
        this.nonMainLevels = pml.nonMainLevels;
        this.mainHandLevels = pml.mainHandLevels;
        this.petEntryLevels = pml.petEntryLevels;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer packet = new PacketBuffer(buf);

        int nonMainLevelsSize = packet.readInt();
        if(nonMainLevelsSize == 5){
            for(int i = 0; i < 5; i++){
                this.nonMainLevels[i] = packet.readInt();
            }
        }

        int mainHandLevelsSize = packet.readInt();
        if(mainHandLevelsSize <= PlayerMobLevelCapability.MAINHAND_CHECK_SIZE){
            for(int i = 0; i < mainHandLevelsSize; i++){
                this.mainHandLevels.add(packet.readInt());
            }
        }

        int petEntryLevelsSize = packet.readInt();
        for(int i = 0; i < petEntryLevelsSize; i++){
            this.petEntryLevels.put(packet.readInt(), packet.readInt());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer packet = new PacketBuffer(buf);

        packet.writeInt(nonMainLevels.length);
        for(int level : nonMainLevels){
            packet.writeInt(level);
        }

        packet.writeInt(mainHandLevels.size());
        for(int level : mainHandLevels){
            packet.writeInt(level);
        }

        packet.writeInt(petEntryLevels.size());
        petEntryLevels.forEach((Integer level, Integer count) -> {
            packet.writeInt(level);
            packet.writeInt(count);
        });
    }


    @SideOnly(Side.CLIENT)
    public static class ClientHandler implements IMessageHandler<PacketPlayerMobLevelsStats, IMessage> {

        @Override
        public IMessage onMessage(PacketPlayerMobLevelsStats message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                IPlayerMobLevelCapability pml = PlayerMobLevelCapability.getForPlayer(Minecraft.getMinecraft().player);
                if (pml instanceof PlayerMobLevelCapability) {
                    if(message.mainHandLevels.isEmpty()) message.mainHandLevels.add(0);

                    ((PlayerMobLevelCapability) pml).nonMainLevels = message.nonMainLevels;
                    ((PlayerMobLevelCapability) pml).mainHandLevels = message.mainHandLevels;
                    ((PlayerMobLevelCapability) pml).petEntryLevels = message.petEntryLevels;

                    LycanitesTweaks.LOGGER.log(Level.INFO, "nonMainLevels {}", message.nonMainLevels);
                    LycanitesTweaks.LOGGER.log(Level.INFO, "mainHandLevels {}", message.mainHandLevels);
                    LycanitesTweaks.LOGGER.log(Level.INFO, "petEntryLevels {}", message.petEntryLevels);
                }
            });
            return null;
        }
    }
}
