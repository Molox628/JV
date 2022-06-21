package slimeknights.tconstruct.library.smeltery;

import java.util.*;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.*;

public interface ISmelteryTankHandler
{
    void onTankChanged(final List<FluidStack> p0, final FluidStack p1);
    
    SmelteryTank getTank();
    
    @SideOnly(Side.CLIENT)
    void updateFluidsFromPacket(final List<FluidStack> p0);
}
