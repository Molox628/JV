package slimeknights.tconstruct.plugin.waila;

import slimeknights.mantle.pulsar.pulse.*;
import net.minecraftforge.fml.common.event.*;
import com.google.common.eventbus.*;

@Pulse(id = "wailaIntegration", modsRequired = "waila", defaultEnable = true)
public class Waila
{
    public static final String modid = "waila";
    public static final String PulseId = "wailaIntegration";
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        FMLInterModComms.sendMessage("waila", "register", "slimeknights.tconstruct.plugin.waila.WailaRegistrar.wailaCallback");
    }
}
