package slimeknights.tconstruct.library.modifiers;

import javax.annotation.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import java.util.*;
import net.minecraft.enchantment.*;

public interface IToolMod
{
    @Nonnull
    String getIdentifier();
    
    String getLocalizedName();
    
    String getLocalizedDesc();
    
    List<String> getExtraInfo(final ItemStack p0, final NBTTagCompound p1);
    
    boolean isHidden();
    
    boolean canApplyTogether(final IToolMod p0);
    
    boolean canApplyTogether(final Enchantment p0);
}
