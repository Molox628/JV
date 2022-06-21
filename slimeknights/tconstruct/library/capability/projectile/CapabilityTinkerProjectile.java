package slimeknights.tconstruct.library.capability.projectile;

import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.*;
import java.util.concurrent.*;
import net.minecraft.nbt.*;

public class CapabilityTinkerProjectile implements Capability.IStorage<ITinkerProjectile>
{
    @CapabilityInject(ITinkerProjectile.class)
    public static Capability<ITinkerProjectile> PROJECTILE_CAPABILITY;
    private static final CapabilityTinkerProjectile INSTANCE;
    
    private CapabilityTinkerProjectile() {
    }
    
    public static Optional<ITinkerProjectile> getTinkerProjectile(final DamageSource source) {
        if (source.func_76352_a()) {
            return getTinkerProjectile(source.func_76364_f());
        }
        return Optional.empty();
    }
    
    public static Optional<ITinkerProjectile> getTinkerProjectile(final Entity entity) {
        ITinkerProjectile capability = null;
        if (entity != null && entity.hasCapability((Capability)CapabilityTinkerProjectile.PROJECTILE_CAPABILITY, (EnumFacing)null)) {
            capability = (ITinkerProjectile)entity.getCapability((Capability)CapabilityTinkerProjectile.PROJECTILE_CAPABILITY, (EnumFacing)null);
        }
        return Optional.ofNullable(capability);
    }
    
    public static void register() {
        CapabilityManager.INSTANCE.register((Class)ITinkerProjectile.class, (Capability.IStorage)CapabilityTinkerProjectile.INSTANCE, (Callable)TinkerProjectileHandler::new);
    }
    
    public NBTBase writeNBT(final Capability<ITinkerProjectile> capability, final ITinkerProjectile instance, final EnumFacing side) {
        return null;
    }
    
    public void readNBT(final Capability<ITinkerProjectile> capability, final ITinkerProjectile instance, final EnumFacing side, final NBTBase nbt) {
    }
    
    static {
        CapabilityTinkerProjectile.PROJECTILE_CAPABILITY = null;
        INSTANCE = new CapabilityTinkerProjectile();
    }
}
