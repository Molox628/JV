package slimeknights.tconstruct.library.modifiers;

import java.util.*;
import net.minecraft.item.*;

public interface IModifierDisplay
{
    int getColor();
    
    List<List<ItemStack>> getItems();
}
