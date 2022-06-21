package slimeknights.tconstruct.tools.ranged.item;

import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.entity.*;
import slimeknights.tconstruct.tools.common.entity.*;
import slimeknights.tconstruct.library.tools.*;

public class Arrow extends ProjectileCore
{
    public Arrow() {
        super(new PartMaterialType[] { PartMaterialType.arrowShaft(TinkerTools.arrowShaft), PartMaterialType.arrowHead(TinkerTools.arrowHead), PartMaterialType.fletching(TinkerTools.fletching) });
        this.addCategory(Category.NO_MELEE, Category.PROJECTILE);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, TinkerMaterials.wood, null, TinkerMaterials.feather);
        }
    }
    
    @Override
    public float damagePotential() {
        return 1.0f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.0;
    }
    
    @Override
    public ProjectileNBT buildTagData(final List<Material> materials) {
        final ProjectileNBT data = new ProjectileNBT();
        final ArrowShaftMaterialStats shaft = materials.get(0).getStatsOrUnknown("shaft");
        final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
        final FletchingMaterialStats fletching = materials.get(2).getStatsOrUnknown("fletching");
        data.head(head);
        data.fletchings(fletching);
        data.shafts(this, shaft);
        final ProjectileNBT projectileNBT = data;
        projectileNBT.attack += 2.0f;
        return data;
    }
    
    @Override
    public EntityProjectileBase getProjectile(final ItemStack stack, final ItemStack bow, final World world, final EntityPlayer player, final float speed, float inaccuracy, final float power, final boolean usedAmmo) {
        inaccuracy -= (1.0f - 1.0f / ProjectileNBT.from(stack).accuracy) * speed / 2.0f;
        return new EntityArrow(world, player, speed, inaccuracy, power, this.getProjectileStack(stack, world, player, usedAmmo), bow);
    }
}
