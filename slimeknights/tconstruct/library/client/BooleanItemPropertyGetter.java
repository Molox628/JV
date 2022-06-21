package slimeknights.tconstruct.library.client;

import net.minecraft.item.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.relauncher.*;

public abstract class BooleanItemPropertyGetter implements IItemPropertyGetter
{
    @SideOnly(Side.CLIENT)
    public final float func_185085_a(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
        return this.applyIf(stack, worldIn, entityIn) ? 1.0f : 0.0f;
    }
    
    @SideOnly(Side.CLIENT)
    public abstract boolean applyIf(final ItemStack p0, @Nullable final World p1, @Nullable final EntityLivingBase p2);
}
