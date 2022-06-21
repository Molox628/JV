package slimeknights.tconstruct.library.capability.piggyback;

import net.minecraftforge.common.capabilities.*;
import java.util.concurrent.*;
import net.minecraft.util.*;
import net.minecraft.nbt.*;

public class CapabilityTinkerPiggyback implements Capability.IStorage<ITinkerPiggyback>
{
    @CapabilityInject(ITinkerPiggyback.class)
    public static Capability<ITinkerPiggyback> PIGGYBACK;
    private static final CapabilityTinkerPiggyback INSTANCE;
    
    private CapabilityTinkerPiggyback() {
    }
    
    public static void register() {
        CapabilityManager.INSTANCE.register((Class)ITinkerPiggyback.class, (Capability.IStorage)CapabilityTinkerPiggyback.INSTANCE, (Callable)new Callable<ITinkerPiggyback>() {
            @Override
            public ITinkerPiggyback call() throws Exception {
                return new TinkerPiggybackHandler();
            }
        });
    }
    
    public NBTBase writeNBT(final Capability<ITinkerPiggyback> capability, final ITinkerPiggyback instance, final EnumFacing side) {
        return null;
    }
    
    public void readNBT(final Capability<ITinkerPiggyback> capability, final ITinkerPiggyback instance, final EnumFacing side, final NBTBase nbt) {
    }
    
    static {
        CapabilityTinkerPiggyback.PIGGYBACK = null;
        INSTANCE = new CapabilityTinkerPiggyback();
    }
}
