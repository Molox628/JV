package slimeknights.tconstruct.plugin.waila;

import mcp.mobius.waila.api.*;
import net.minecraft.item.*;
import java.util.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class CastingDataProvider implements IWailaDataProvider
{
    public ItemStack getWailaStack(final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return null;
    }
    
    public List<String> getWailaHead(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return currenttip;
    }
    
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        if (config.getConfig(WailaRegistrar.CONFIG_CASTING) && accessor.getTileEntity() instanceof TileCasting) {
            final TileCasting te = (TileCasting)accessor.getTileEntity();
            final ItemStack output = te.getCurrentResult();
            if (output != null) {
                currenttip.add(Util.translateFormatted("gui.waila.casting.recipe", output.func_82833_r()));
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
