package slimeknights.tconstruct.tools.ranged.item;

import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.entity.*;
import javax.annotation.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.tools.common.entity.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tools.*;

public class Shuriken extends ProjectileCore
{
    private static PartMaterialType shurikenPMT;
    
    public Shuriken() {
        super(new PartMaterialType[] { Shuriken.shurikenPMT, Shuriken.shurikenPMT, Shuriken.shurikenPMT, Shuriken.shurikenPMT });
        this.addCategory(Category.NO_MELEE, Category.PROJECTILE);
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 0, 1, 2, 3 };
    }
    
    @Override
    public float damagePotential() {
        return 0.7f;
    }
    
    @Nonnull
    public ActionResult<ItemStack> func_77659_a(final World worldIn, final EntityPlayer playerIn, final EnumHand hand) {
        final ItemStack itemStackIn = playerIn.func_184586_b(hand);
        if (ToolHelper.isBroken(itemStackIn)) {
            return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.FAIL, (Object)itemStackIn);
        }
        playerIn.func_184811_cZ().func_185145_a(itemStackIn.func_77973_b(), 4);
        if (!worldIn.field_72995_K) {
            final boolean usedAmmo = this.useAmmo(itemStackIn, (EntityLivingBase)playerIn);
            final EntityProjectileBase projectile = this.getProjectile(itemStackIn, itemStackIn, worldIn, playerIn, 2.1f, 0.0f, 1.0f, usedAmmo);
            worldIn.func_72838_d((Entity)projectile);
        }
        return (ActionResult<ItemStack>)ActionResult.newResult(EnumActionResult.SUCCESS, (Object)itemStackIn);
    }
    
    @Override
    public ProjectileNBT buildTagData(final List<Material> materials) {
        final ProjectileNBT data = new ProjectileNBT();
        data.head(materials.get(0).getStatsOrUnknown("head"), materials.get(1).getStatsOrUnknown("head"), materials.get(2).getStatsOrUnknown("head"), materials.get(3).getStatsOrUnknown("head"));
        data.extra(materials.get(0).getStatsOrUnknown("extra"), materials.get(1).getStatsOrUnknown("extra"), materials.get(2).getStatsOrUnknown("extra"), materials.get(3).getStatsOrUnknown("extra"));
        final ProjectileNBT projectileNBT = data;
        ++projectileNBT.attack;
        data.accuracy = 1.0f;
        return data;
    }
    
    @Override
    public EntityProjectileBase getProjectile(final ItemStack stack, final ItemStack launcher, final World world, final EntityPlayer player, final float speed, float inaccuracy, final float progress, final boolean usedAmmo) {
        inaccuracy *= ProjectileNBT.from(stack).accuracy;
        return new EntityShuriken(world, player, speed, inaccuracy, this.getProjectileStack(stack, world, player, usedAmmo), launcher);
    }
    
    static {
        Shuriken.shurikenPMT = new PartMaterialType(TinkerTools.knifeBlade, new String[] { "head", "extra", "projectile" });
    }
}
