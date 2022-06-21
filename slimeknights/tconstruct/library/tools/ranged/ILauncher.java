package slimeknights.tconstruct.library.tools.ranged;

import com.google.common.collect.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.item.*;
import javax.annotation.*;

public interface ILauncher
{
    void modifyProjectileAttributes(final Multimap<String, AttributeModifier> p0, @Nullable final ItemStack p1, final ItemStack p2, final float p3);
}
