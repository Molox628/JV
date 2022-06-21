package slimeknights.tconstruct.plugin.theoneprobe;

import slimeknights.mantle.pulsar.pulse.*;
import net.minecraftforge.fml.common.event.*;
import com.google.common.eventbus.*;

@Pulse(id = "theoneprobeIntegration", modsRequired = "theoneprobe", defaultEnable = true)
public class TheOneProbe
{
    public static final String modid = "theoneprobe";
    public static final String PulseId = "theoneprobeIntegration";
    
    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "slimeknights.tconstruct.plugin.theoneprobe.GetTheOneProbe");
    }
}
