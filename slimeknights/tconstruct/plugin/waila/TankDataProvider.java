package slimeknights.tconstruct.plugin.waila;

import mcp.mobius.waila.api.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.tileentity.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.*;
import net.minecraft.entity.player.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class TankDataProvider implements IWailaDataProvider
{
    public ItemStack getWailaStack(final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return null;
    }
    
    public List<String> getWailaHead(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return currenttip;
    }
    
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        if (config.getConfig(WailaRegistrar.CONFIG_TANK)) {
            final TileEntity te = accessor.getTileEntity();
            if (te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, (EnumFacing)null)) {
                final IFluidHandler fluidHandler = (IFluidHandler)te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, (EnumFacing)null);
                assert fluidHandler != null;
                final IFluidTankProperties[] tankProperties;
                final IFluidTankProperties[] fluidHandlerTankProperties = tankProperties = fluidHandler.getTankProperties();
                for (final IFluidTankProperties fluidTankProperties : tankProperties) {
                    final FluidStack fluidStack = fluidTankProperties.getContents();
                    if (fluidStack != null) {
                        currenttip.add(Util.translateFormatted("gui.waila.tank.fluid", fluidStack.getLocalizedName()));
                        currenttip.add(Util.translateFormatted("gui.waila.tank.amount", fluidStack.amount, fluidTankProperties.getCapacity()));
                    }
                    else {
                        currenttip.add(Util.translate("gui.waila.tank.empty", new Object[0]));
                    }
                }
            }
        }
        return currenttip;
    }
    
    public List<String> getWailaTail(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return currenttip;
    }
    
    public NBTTagCompound getNBTData(final EntityPlayerMP player, final TileEntity te, final NBTTagCompound tag, final World world, final BlockPos pos) {
        return tag;
    }
}
