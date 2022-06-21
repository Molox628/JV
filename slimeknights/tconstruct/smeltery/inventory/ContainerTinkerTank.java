package slimeknights.tconstruct.smeltery.inventory;

import slimeknights.mantle.inventory.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.tileentity.*;

public class ContainerTinkerTank extends BaseContainer<TileTinkerTank>
{
    public ContainerTinkerTank(final TileTinkerTank tile) {
        super((TileEntity)tile);
    }
}
