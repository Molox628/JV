package slimeknights.tconstruct.library.tinkering;

import net.minecraft.item.*;
import net.minecraft.util.*;
import javax.annotation.*;

public interface IRepairable
{
    @Nonnull
    ItemStack repair(final ItemStack p0, final NonNullList<ItemStack> p1);
}
