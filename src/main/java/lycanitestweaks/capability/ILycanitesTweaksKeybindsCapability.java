package lycanitestweaks.capability;

import net.minecraft.entity.player.EntityPlayer;

public interface ILycanitesTweaksKeybindsCapability {

    EntityPlayer getPlayer();
    void setPlayer(EntityPlayer player);

    void sync();

    byte getSoulgazerAutoToggle();
    void setSoulgazerAutoToggle(byte id);
    void nextSoulgazerAutoToggle();

    boolean getSoulgazerManualToggle();
    void setSoulgazerManualToggle(boolean toggle);
    void nextSoulgazerManualToggle();
}
