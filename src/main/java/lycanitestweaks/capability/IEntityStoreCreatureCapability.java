package lycanitestweaks.capability;

import lycanitestweaks.storedcreatureentity.StoredCreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;

public interface IEntityStoreCreatureCapability {

    Entity getHost();
    void setHost(Entity entity);

    StoredCreatureEntity getStoredCreatureEntity();
    void setStoredCreatureEntity(StoredCreatureEntity storedCreatureEntity);

    public void readNBT(NBTTagCompound nbtTagCompound);
    public void writeNBT(NBTTagCompound nbtTagCompound);

    }
