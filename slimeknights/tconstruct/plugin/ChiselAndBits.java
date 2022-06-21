package slimeknights.tconstruct.plugin;

import slimeknights.mantle.pulsar.pulse.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.world.*;
import com.google.common.eventbus.*;
import net.minecraftforge.fml.common.event.*;

@Pulse(id = "chiselsandbitsIntegration", modsRequired = "chiselsandbits", defaultEnable = true)
public class ChiselAndBits
{
    public static final String modid = "chiselsandbits";
    public static final String PulseId = "chiselsandbitsIntegration";
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        this.imc((Block)TinkerSmeltery.searedBlock);
        this.imc(TinkerCommons.blockClearGlass);
        this.imc((Block)TinkerCommons.blockClearStainedGlass);
        this.imc((Block)TinkerCommons.blockSlime);
        this.imc(TinkerCommons.blockSlimeCongealed);
        this.imc((Block)TinkerWorld.slimeLeaves);
    }
    
    protected void imc(final Block block) {
        if (block != null) {
            FMLInterModComms.sendMessage("chiselsandbits", "ignoreblocklogic", block.getRegistryName());
        }
    }
}
