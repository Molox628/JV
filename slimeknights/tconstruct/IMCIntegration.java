package slimeknights.tconstruct;

import org.apache.logging.log4j.*;
import net.minecraftforge.fml.common.event.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraftforge.fluids.*;
import net.minecraft.nbt.*;
import slimeknights.tconstruct.smeltery.*;
import net.minecraftforge.oredict.*;
import java.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;

public abstract class IMCIntegration
{
    static final Logger log;
    
    private IMCIntegration() {
    }
    
    public static void integrateSmeltery(final FMLInterModComms.IMCMessage message) {
        if (!message.isNBTMessage()) {
            return;
        }
        final NBTTagCompound tag = message.getNBTValue();
        final String fluidName = tag.func_74779_i("fluid");
        final String ore = tag.func_74779_i("ore");
        final boolean toolforge = tag.func_74767_n("toolforge");
        final Fluid fluid = FluidRegistry.getFluid(fluidName);
        if (fluid != null && !ore.isEmpty()) {
            boolean isNew = true;
            for (final MaterialIntegration mi : TinkerRegistry.getMaterialIntegrations()) {
                if (mi.fluid != null && mi.fluid.getName().equals(fluidName)) {
                    isNew = false;
                }
            }
            if (isNew) {
                final MaterialIntegration materialIntegration = new MaterialIntegration(null, fluid, ore);
                if (toolforge) {
                    materialIntegration.toolforge();
                }
                TinkerRegistry.integrate(materialIntegration);
                materialIntegration.preInit();
                IMCIntegration.log.debug("Added integration smelting for " + ore + " from " + message.getSender());
            }
        }
        if (tag.func_74764_b("alloy")) {
            alloy(tag.func_150295_c("alloy", 10));
        }
    }
    
    public static void alloy(final FMLInterModComms.IMCMessage message) {
        if (!message.isNBTMessage()) {
            return;
        }
        alloy(message.getNBTValue().func_150295_c("alloy", 10));
    }
    
    private static void alloy(final NBTTagList tagList) {
        TinkerIntegration.addAlloyNBTTag(tagList);
    }
    
    public static void blacklistMelting(final FMLInterModComms.IMCMessage message) {
        if (!message.isStringMessage() && !message.isItemStackMessage()) {
            return;
        }
        if (message.getMessageType() == String.class) {
            TinkerSmeltery.meltingBlacklist.addAll((Collection<? extends ItemStack>)OreDictionary.getOres(message.getStringValue(), false));
            IMCIntegration.log.debug("Blacklisted oredictionary entry " + message.getStringValue() + " from melting");
        }
        else {
            TinkerSmeltery.meltingBlacklist.add(message.getItemStackValue());
            IMCIntegration.log.debug("Blacklisted " + message.getItemStackValue().func_77977_a() + " from melting");
        }
    }
    
    public static void addDryingRecipe(final FMLInterModComms.IMCMessage message) {
        if (!message.isNBTMessage()) {
            return;
        }
        final NBTTagCompound tag = message.getNBTValue();
        final ItemStack input = new ItemStack(tag.func_74775_l("input"));
        final ItemStack output = new ItemStack(tag.func_74775_l("output"));
        final int time = tag.func_74762_e("time") * 20;
        if (!input.func_190926_b() && !output.func_190926_b() && time > 0) {
            TinkerRegistry.registerDryingRecipe(input, output, time);
            IMCIntegration.log.debug("Added drying rack recipe from " + input.func_77977_a() + " to " + output.func_77977_a());
        }
        else if (input.func_190926_b()) {
            final String ore = tag.func_74779_i("input");
            if (!ore.isEmpty()) {
                TinkerRegistry.registerDryingRecipe(ore, output, time);
                IMCIntegration.log.debug("Added drying rack recipe from oredictionary " + ore + " to " + output.func_77977_a());
            }
        }
    }
    
    static {
        log = Util.getLogger("IMC");
    }
}
