package slimeknights.tconstruct.tools.traits;

import slimeknights.tconstruct.library.traits.*;
import net.minecraft.entity.monster.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import java.lang.reflect.*;

public class TraitSlimey extends AbstractTrait
{
    private static float chance;
    protected final Class<? extends EntitySlime> slime;
    
    public TraitSlimey(final String suffix, final Class<? extends EntitySlime> slime) {
        super("slimey_" + suffix, TextFormatting.GREEN);
        this.slime = slime;
    }
    
    @Override
    public String getLocalizedName() {
        return Util.translate(String.format("modifier.%s.name", "slimey"), new Object[0]);
    }
    
    @Override
    public String getLocalizedDesc() {
        return Util.translate(String.format("modifier.%s.desc", "slimey"), new Object[0]);
    }
    
    @Override
    public void afterBlockBreak(final ItemStack tool, final World world, final IBlockState state, final BlockPos pos, final EntityLivingBase player, final boolean wasEffective) {
        if (wasEffective && !world.field_72995_K && TraitSlimey.random.nextFloat() < TraitSlimey.chance) {
            this.spawnSlime(player, pos.func_177958_n() + 0.5, pos.func_177956_o(), pos.func_177952_p() + 0.5, world);
        }
    }
    
    @Override
    public void afterHit(final ItemStack tool, final EntityLivingBase player, final EntityLivingBase target, final float damageDealt, final boolean wasCritical, final boolean wasHit) {
        if (!target.func_70089_S() && !target.func_130014_f_().field_72995_K && TraitSlimey.random.nextFloat() < TraitSlimey.chance) {
            this.spawnSlime(player, target.field_70165_t, target.field_70163_u, target.field_70161_v, target.func_130014_f_());
        }
    }
    
    protected void spawnSlime(final EntityLivingBase player, final double x, final double y, final double z, final World world) {
        try {
            final EntitySlime entity = (EntitySlime)this.slime.getConstructor(World.class).newInstance(world);
            entity.func_70799_a(1, true);
            entity.func_70107_b(x, y, z);
            world.func_72838_d((Entity)entity);
            entity.func_130011_c((Entity)player);
            entity.func_70642_aH();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e2) {
            e2.printStackTrace();
        }
        catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
        catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        }
    }
    
    static {
        TraitSlimey.chance = 0.0033f;
    }
}
