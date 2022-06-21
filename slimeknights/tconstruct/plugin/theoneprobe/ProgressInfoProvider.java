package slimeknights.tconstruct.plugin.theoneprobe;

import slimeknights.tconstruct.library.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import mcjty.theoneprobe.api.*;
import slimeknights.tconstruct.library.tileentity.*;
import net.minecraft.tileentity.*;

public class ProgressInfoProvider implements IProbeInfoProvider
{
    public String getID() {
        return Util.getResource("progress").toString();
    }
    
    public void addProbeInfo(final ProbeMode mode, final IProbeInfo probeInfo, final EntityPlayer player, final World world, final IBlockState blockState, final IProbeHitData data) {
        final TileEntity te = world.func_175625_s(data.getPos());
        if (te instanceof IProgress) {
            final IProgress progressTe = (IProgress)te;
            final float progress = progressTe.getProgress();
            if (progress > 0.0f) {
                probeInfo.horizontal().progress((int)(progress * 100.0f), 100, probeInfo.defaultProgressStyle().suffix("%"));
            }
        }
    }
}
