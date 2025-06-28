package lycanitestweaks.capability;

import lycanitestweaks.network.PacketHandler;
import lycanitestweaks.network.PacketKeybindsSync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class LycanitesTweaksPlayerCapability implements ILycanitesTweaksPlayerCapability {

    private EntityPlayer player;
    private SOULGAZER_AUTO_ID soulgazerAuto = SOULGAZER_AUTO_ID.NONE;
    private boolean soulgazerManual = true;

    public enum SOULGAZER_AUTO_ID {
        NONE((byte)1), DAMAGE((byte)2), KILL((byte)3);
        public final byte id;
        SOULGAZER_AUTO_ID(byte i) { id = i; }
        public static SOULGAZER_AUTO_ID get(byte id) {
            return Arrays.stream(SOULGAZER_AUTO_ID.values())
                    .filter(toggleId -> toggleId.id == id)
                    .findFirst().orElse(NONE);
        }
    }

    LycanitesTweaksPlayerCapability(){}

    LycanitesTweaksPlayerCapability(@Nonnull EntityPlayer player){
        this.player = player;
    }

    public static ILycanitesTweaksPlayerCapability getForPlayer(EntityPlayer player) {
        if (player == null) {
            return null;
        }
        ILycanitesTweaksPlayerCapability playerKeybinds = player.getCapability(LycanitesTweaksPlayerCapabilityHandler.LT_PLAYER, null);
        if (playerKeybinds != null && playerKeybinds.getPlayer() != player) {
            playerKeybinds.setPlayer(player);
        }
        return playerKeybinds;
    }

    @Override
    public EntityPlayer getPlayer() {
        return this.player;
    }

    @Override
    public void setPlayer(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void sync() {
        if(!this.player.world.isRemote) {
            PacketKeybindsSync packet = new PacketKeybindsSync(this);
            EntityPlayerMP playerMP = (EntityPlayerMP) this.player;
            PacketHandler.instance.sendTo(packet, playerMP);
        }
    }

    @Override
    public byte getSoulgazerAutoToggle() {
        return this.soulgazerAuto.id;
    }

    @Override
    public void setSoulgazerAutoToggle(byte id) {
        this.soulgazerAuto = SOULGAZER_AUTO_ID.get(id);
    }

    @Override
    public void nextSoulgazerAutoToggle() {
        this.setSoulgazerAutoToggle((byte) (this.soulgazerAuto.id + 1));
        if(!this.player.getEntityWorld().isRemote) {
            this.player.sendStatusMessage(new TextComponentTranslation("item.soulgazer.description.keybind.auto." + this.soulgazerAuto.id), true);
            this.sync();
        }
    }

    @Override
    public boolean getSoulgazerManualToggle() {
        return this.soulgazerManual;
    }

    @Override
    public void setSoulgazerManualToggle(boolean toggle) {
        this.soulgazerManual = toggle;
    }

    @Override
    public void nextSoulgazerManualToggle() {
        this.setSoulgazerManualToggle(!this.soulgazerManual);
        if(!this.player.getEntityWorld().isRemote) {
            int manualID = this.getSoulgazerManualToggle() ? 1 : 2;
            this.player.sendStatusMessage(new TextComponentTranslation("item.soulgazer.description.keybind.manual." + manualID), true);
            this.sync();
        }
    }
}
