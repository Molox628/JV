package slimeknights.tconstruct.library.capability.piggyback;

import net.minecraft.entity.player.*;
import javax.annotation.*;
import net.minecraftforge.common.capabilities.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.nbt.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.storage.*;
import java.util.*;

public class TinkerPiggybackSerializer implements ICapabilitySerializable<NBTTagCompound>
{
    private final EntityPlayer player;
    private final ITinkerPiggyback piggyback;
    
    public TinkerPiggybackSerializer(@Nonnull final EntityPlayer player) {
        this.player = player;
        (this.piggyback = new TinkerPiggybackHandler()).setRiddenPlayer(player);
    }
    
    public boolean hasCapability(final Capability<?> capability, final EnumFacing facing) {
        return capability == CapabilityTinkerPiggyback.PIGGYBACK;
    }
    
    public <T> T getCapability(final Capability<T> capability, final EnumFacing facing) {
        if (capability == CapabilityTinkerPiggyback.PIGGYBACK) {
            return (T)this.piggyback;
        }
        return null;
    }
    
    public NBTTagCompound serializeNBT() {
        final NBTTagCompound tagCompound = new NBTTagCompound();
        final NBTTagList riderList = new NBTTagList();
        for (final Entity entity : this.player.func_184182_bu()) {
            final String id = EntityList.func_75621_b(entity);
            if (id != null && !"".equals(id)) {
                final NBTTagCompound entityTag = new NBTTagCompound();
                final NBTTagCompound entityDataTag = new NBTTagCompound();
                entity.func_189511_e(entityDataTag);
                entityDataTag.func_74778_a("id", EntityList.func_75621_b(entity));
                entityTag.func_186854_a("Attach", entity.func_184187_bx().func_110124_au());
                entityTag.func_74782_a("Entity", (NBTBase)entityDataTag);
                riderList.func_74742_a((NBTBase)entityTag);
            }
        }
        tagCompound.func_74782_a("riders", (NBTBase)riderList);
        if (riderList.func_82582_d()) {
            return new NBTTagCompound();
        }
        return tagCompound;
    }
    
    public void deserializeNBT(final NBTTagCompound nbt) {
        final NBTTagList riderList = nbt.func_150295_c("riders", 10);
        final Map<UUID, Entity> attachedTo = (Map<UUID, Entity>)Maps.newHashMap();
        for (int i = 0; i < riderList.func_74745_c(); ++i) {
            final NBTTagCompound entityTag = riderList.func_150305_b(i);
            final Entity entity = AnvilChunkLoader.func_186051_a(entityTag.func_74775_l("Entity"), this.player.func_130014_f_(), true);
            if (entity != null) {
                final UUID uuid = entityTag.func_186857_a("Attach");
                attachedTo.put(uuid, entity);
            }
        }
    }
}
