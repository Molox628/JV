package slimeknights.tconstruct.plugin.theoneprobe;

import slimeknights.tconstruct.library.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import mcjty.theoneprobe.api.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;
import net.minecraft.item.*;

public class CastingInfoProvider implements IProbeInfoProvider
{
    public String getID() {
        return Util.getResource("casting").toString();
    }
    
    public void addProbeInfo(final ProbeMode mode, final IProbeInfo probeInfo, final EntityPlayer player, final World world, final IBlockState blockState, final IProbeHitData data) {
        final TileEntity te = world.func_175625_s(data.getPos());
        if (te instanceof TileCasting) {
            final TileCasting casting = (TileCasting)te;
            final ItemStack output = casting.getCurrentResult();
            if (output != null) {
                probeInfo.horizontal().text(Util.translateFormatted("gui.waila.casting.recipe", output.func_82833_r()));
            }
        }
    }
}
