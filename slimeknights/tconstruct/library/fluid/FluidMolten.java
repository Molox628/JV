package slimeknights.tconstruct.library.fluid;

import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.*;

public class FluidMolten extends FluidColored
{
    public static ResourceLocation ICON_MetalStill;
    public static ResourceLocation ICON_MetalFlowing;
    
    public FluidMolten(final String fluidName, final int color) {
        this(fluidName, color, FluidMolten.ICON_MetalStill, FluidMolten.ICON_MetalFlowing);
    }
    
    public FluidMolten(final String fluidName, final int color, final ResourceLocation still, final ResourceLocation flow) {
        super(fluidName, color, still, flow);
        this.setDensity(2000);
        this.setViscosity(10000);
        this.setTemperature(1000);
        this.setLuminosity(10);
        this.setRarity(EnumRarity.UNCOMMON);
    }
    
    static {
        FluidMolten.ICON_MetalStill = Util.getResource("blocks/fluids/molten_metal");
        FluidMolten.ICON_MetalFlowing = Util.getResource("blocks/fluids/molten_metal_flow");
    }
}
