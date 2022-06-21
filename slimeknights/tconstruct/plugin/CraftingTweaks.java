package slimeknights.tconstruct.plugin;

import slimeknights.mantle.pulsar.pulse.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.tools.common.inventory.*;
import net.minecraftforge.fml.common.event.*;
import com.google.common.eventbus.*;

@Pulse(id = "craftingtweaksIntegration", modsRequired = "craftingtweaks", defaultEnable = true)
public class CraftingTweaks
{
    public static final String modid = "craftingtweaks";
    public static final String PulseId = "craftingtweaksIntegration";
    
    @Subscribe
    public void init(final FMLInitializationEvent event) {
        final NBTTagCompound tagCompound = new NBTTagCompound();
        tagCompound.func_74778_a("ContainerClass", ContainerCraftingStation.class.getName());
        tagCompound.func_74778_a("AlignToGrid", "left");
        FMLInterModComms.sendMessage("craftingtweaks", "RegisterProvider", tagCompound);
    }
}
