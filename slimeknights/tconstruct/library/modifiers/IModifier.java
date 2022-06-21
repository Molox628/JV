package slimeknights.tconstruct.library.modifiers;

import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;
import slimeknights.mantle.util.*;
import net.minecraft.nbt.*;
import net.minecraftforge.fml.relauncher.*;

public interface IModifier extends IToolMod
{
    Optional<RecipeMatch.Match> matches(final NonNullList<ItemStack> p0);
    
    boolean canApply(final ItemStack p0, final ItemStack p1) throws TinkerGuiException;
    
    void apply(final ItemStack p0);
    
    void apply(final NBTTagCompound p0);
    
    void updateNBT(final NBTTagCompound p0);
    
    void applyEffect(final NBTTagCompound p0, final NBTTagCompound p1);
    
    String getTooltip(final NBTTagCompound p0, final boolean p1);
    
    @SideOnly(Side.CLIENT)
    boolean hasTexturePerMaterial();
    
    boolean equalModifier(final NBTTagCompound p0, final NBTTagCompound p1);
    
    default boolean hasItemsToApplyWith() {
        return true;
    }
}
