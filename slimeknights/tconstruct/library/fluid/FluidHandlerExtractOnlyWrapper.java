package slimeknights.tconstruct.library.fluid;

import java.lang.ref.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.capability.templates.*;
import net.minecraftforge.fluids.*;

public class FluidHandlerExtractOnlyWrapper extends FluidHandlerConcatenate
{
    private final WeakReference<IFluidHandler> parent;
    
    public FluidHandlerExtractOnlyWrapper(final IFluidHandler parent) {
        super(new IFluidHandler[] { parent });
        this.parent = new WeakReference<IFluidHandler>(parent);
    }
    
    public boolean hasParent() {
        return this.parent.get() != null;
    }
    
    public IFluidTankProperties[] getTankProperties() {
        if (this.hasParent()) {
            final IFluidHandler iFluidHandler = this.parent.get();
            assert iFluidHandler != null;
            final IFluidTankProperties[] iFluidTankPropertiesArray = iFluidHandler.getTankProperties();
            if (iFluidTankPropertiesArray.length > 0) {
                final IFluidTankProperties fluidTankProperties = iFluidHandler.getTankProperties()[0];
                return new IFluidTankProperties[] { (IFluidTankProperties)new FluidTankProperties(fluidTankProperties.getContents(), fluidTankProperties.getCapacity(), true, false) };
            }
        }
        return EmptyFluidHandler.EMPTY_TANK_PROPERTIES_ARRAY;
    }
    
    public FluidStack drain(final int maxDrain, final boolean doDrain) {
        return null;
    }
    
    public FluidStack drain(final FluidStack resource, final boolean doDrain) {
        return null;
    }
}
