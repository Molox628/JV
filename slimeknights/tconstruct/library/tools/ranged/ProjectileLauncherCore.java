package slimeknights.tconstruct.library.tools.ranged;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tools.*;

public abstract class ProjectileLauncherCore extends TinkerToolCore
{
    public ProjectileLauncherCore(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
    }
    
    protected ProjectileLauncherNBT getData(final ItemStack stack) {
        return ProjectileLauncherNBT.from(stack);
    }
    
    public abstract ProjectileLauncherNBT buildTagData(final List<Material> p0);
}
