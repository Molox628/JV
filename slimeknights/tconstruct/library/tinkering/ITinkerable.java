package slimeknights.tconstruct.library.tinkering;

import net.minecraft.item.*;
import java.util.*;

public interface ITinkerable
{
    void getTooltip(final ItemStack p0, final List<String> p1);
    
    void getTooltipDetailed(final ItemStack p0, final List<String> p1);
    
    void getTooltipComponents(final ItemStack p0, final List<String> p1);
}
