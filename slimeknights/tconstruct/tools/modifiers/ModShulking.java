package slimeknights.tconstruct.tools.modifiers;

import slimeknights.tconstruct.library.modifiers.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import com.google.common.collect.*;

public class ModShulking extends ModifierTrait
{
    public ModShulking() {
        super("shulking", 11193599, 1, 50);
    }
    
    private int getDuration(final ItemStack tool) {
        return this.getData(tool).current / 2 + 10;
    }
    
    @Override
    public void onHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final boolean isCritical) {
        final int duration = this.getDuration(tool);
        target.func_70690_d(new PotionEffect(MobEffects.field_188424_y, duration, 0));
    }
    
    @Override
    public List<String> getExtraInfo(final ItemStack tool, final NBTTagCompound modifierTag) {
        final String loc = String.format("modifier.%s.extra", this.getIdentifier());
        float duration = (float)this.getDuration(tool);
        duration /= 20.0f;
        return (List<String>)ImmutableList.of((Object)Util.translateFormatted(loc, Util.df.format(duration)));
    }
}
