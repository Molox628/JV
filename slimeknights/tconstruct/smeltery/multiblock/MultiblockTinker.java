package slimeknights.tconstruct.smeltery.multiblock;

import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.mantle.multiblock.*;
import net.minecraft.tileentity.*;

public abstract class MultiblockTinker extends MultiblockCuboid
{
    public final TileMultiblock<?> tile;
    
    public MultiblockTinker(final TileMultiblock<?> tile, final boolean hasFloor, final boolean hasFrame, final boolean hasCeiling) {
        super(hasFloor, hasFrame, hasCeiling);
        this.tile = tile;
    }
    
    protected boolean isValidSlave(final World world, final BlockPos pos) {
        if (!world.func_175667_e(pos)) {
            return false;
        }
        final TileEntity te = world.func_175625_s(pos);
        if (te instanceof MultiServantLogic) {
            final MultiServantLogic slave = (MultiServantLogic)te;
            if (slave.hasValidMaster() && !this.tile.func_174877_v().equals((Object)slave.getMasterPosition())) {
                return false;
            }
        }
        return true;
    }
}
