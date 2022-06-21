package slimeknights.tconstruct.library.client.model;

import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.entity.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.item.*;

@SideOnly(Side.CLIENT)
public class BakedToolModelOverride
{
    public final ImmutableMap<ResourceLocation, Float> predicates;
    public final BakedToolModel bakedToolModel;
    
    public BakedToolModelOverride(final ImmutableMap<ResourceLocation, Float> predicates, final BakedToolModel bakedToolModel) {
        this.predicates = predicates;
        this.bakedToolModel = bakedToolModel;
    }
    
    public boolean matches(final ItemStack stack, @Nullable final World worldIn, @Nullable final EntityLivingBase entityIn) {
        final Item item = stack.func_77973_b();
        for (final Map.Entry<ResourceLocation, Float> entry : this.predicates.entrySet()) {
            final IItemPropertyGetter iitempropertygetter = item.func_185045_a((ResourceLocation)entry.getKey());
            if (iitempropertygetter == null || iitempropertygetter.func_185085_a(stack, worldIn, entityIn) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }
}
