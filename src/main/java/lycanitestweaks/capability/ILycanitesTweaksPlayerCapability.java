package lycanitestweaks.capability;

import com.lycanitesmobs.core.entity.BaseCreatureEntity;
import net.minecraft.entity.player.EntityPlayer;

public interface ILycanitesTweaksPlayerCapability {

    EntityPlayer getPlayer();
    void setPlayer(EntityPlayer player);

    void sync();

    float getPMLModifierForCreature(BaseCreatureEntity creature);
    void setPMLModifierForCreature(BaseCreatureEntity creature, float modifier);
    void setPMLModifierForAll(float modifier);

    byte getSoulgazerAutoToggle();
    void setSoulgazerAutoToggle(byte id);
    void nextSoulgazerAutoToggle();

    boolean getSoulgazerManualToggle();
    void setSoulgazerManualToggle(boolean toggle);
    void nextSoulgazerManualToggle();
}
