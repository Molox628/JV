package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import slimeknights.tconstruct.library.potion.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.*;
import javax.annotation.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.math.*;
import javax.vecmath.*;
import net.minecraft.potion.*;
import java.util.*;

public class TraitMagnetic extends AbstractTraitLeveled
{
    public static TinkerPotion Magnetic;
    
    public TraitMagnetic(final int levels) {
        super("magnetic", 14540253, 3, levels);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        final ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, this.name));
        TraitMagnetic.Magnetic.apply(player, 30, data.level);
    }
    
    @Override
    public void onHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damage, final boolean isCritical) {
        final ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, this.name));
        TraitMagnetic.Magnetic.apply(player, 30, data.level);
    }
    
    static {
        TraitMagnetic.Magnetic = new MagneticPotion();
    }
    
    private static class MagneticPotion extends TinkerPotion
    {
        public MagneticPotion() {
            super(Util.getResource("magnetic"), false, false);
        }
        
        public boolean func_76397_a(final int duration, final int strength) {
            return (duration & 0x1) == 0x0;
        }
        
        public void func_76394_a(@Nonnull final EntityLivingBase entity, final int id) {
            final double x = entity.field_70165_t;
            final double y = entity.field_70163_u;
            final double z = entity.field_70161_v;
            double range = 1.8;
            final PotionEffect activePotionEffect = entity.func_70660_b((Potion)this);
            if (activePotionEffect != null) {
                range += activePotionEffect.func_76458_c() * 0.3f;
            }
            final List<EntityItem> items = (List<EntityItem>)entity.func_130014_f_().func_72872_a((Class)EntityItem.class, new AxisAlignedBB(x - range, y - range, z - range, x + range, y + range, z + range));
            int pulled = 0;
            for (final EntityItem item : items) {
                if (!item.func_92059_d().func_190926_b()) {
                    if (item.field_70128_L) {
                        continue;
                    }
                    if (pulled > 200) {
                        break;
                    }
                    final float strength = 0.07f;
                    final Vector3d vec = new Vector3d(x, y, z);
                    vec.sub((Tuple3d)new Vector3d(item.field_70165_t, item.field_70163_u, item.field_70161_v));
                    vec.normalize();
                    vec.scale((double)strength);
                    final EntityItem entityItem = item;
                    entityItem.field_70159_w += vec.x;
                    final EntityItem entityItem2 = item;
                    entityItem2.field_70181_x += vec.y;
                    final EntityItem entityItem3 = item;
                    entityItem3.field_70179_y += vec.z;
                    ++pulled;
                }
            }
        }
    }
}
