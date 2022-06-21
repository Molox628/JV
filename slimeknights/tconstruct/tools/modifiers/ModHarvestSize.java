package slimeknights.tconstruct.tools.modifiers;

import java.util.*;
import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.nbt.*;

public class ModHarvestSize extends ToolModifier
{
    public ModHarvestSize(final String name) {
        super("harvest" + name.toLowerCase(Locale.US), 13301410);
        this.addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect((T)this), ModifierAspect.aoeOnly, ModifierAspect.freeModifier);
    }
    
    public void applyEffect(final NBTTagCompound rootCompound, final NBTTagCompound modifierTag) {
    }
}
