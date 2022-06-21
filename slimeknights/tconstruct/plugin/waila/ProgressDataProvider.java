package slimeknights.tconstruct.plugin.waila;

import mcp.mobius.waila.api.*;
import net.minecraft.item.*;
import java.util.*;
import slimeknights.tconstruct.library.tileentity.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.entity.player.*;
import net.minecraft.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;

public class ProgressDataProvider implements IWailaDataProvider
{
    public ItemStack getWailaStack(final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return null;
    }
    
    public List<String> getWailaHead(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        return currenttip;
    }
    
    public List<String> getWailaBody(final ItemStack itemStack, final List<String> currenttip, final IWailaDataAccessor accessor, final IWailaConfigHandler config) {
        if (config.getConfig(WailaRegistrar.CONFIG_PROGRESS) && accessor.getTileEntity() instanceof IProgress) {
            final IProgress te = (IProgress)accessor.getTileEntity();
            final float progress = te.getProgress();
            if (progress > 0.0f) {
                currenttip.add(Util.translateFormatted("gui.waila.casting.progress", Util.dfPercent.format(progress)));
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
