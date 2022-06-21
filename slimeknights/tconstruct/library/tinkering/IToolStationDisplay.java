package slimeknights.tconstruct.library.tinkering;

import net.minecraft.item.*;
import java.util.*;

public interface IToolStationDisplay
{
    @Deprecated
    String getLocalizedToolName();
    
    default String getLocalizedName() {
        return this.getLocalizedToolName();
    }
    
    List<String> getInformation(final ItemStack p0);
}
