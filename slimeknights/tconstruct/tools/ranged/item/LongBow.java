package slimeknights.tconstruct.tools.ranged.item;

import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.tools.*;

public class LongBow extends ShortBow
{
    public static final float DURABILITY_MODIFIER = 1.4f;
    
    public LongBow() {
        super(new PartMaterialType[] { PartMaterialType.bow(TinkerTools.bowLimb), PartMaterialType.bow(TinkerTools.bowLimb), PartMaterialType.extra(TinkerTools.largePlate), PartMaterialType.bowstring(TinkerTools.bowString) });
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, null, null, null, TinkerMaterials.string);
        }
    }
    
    @Override
    public double attackSpeed() {
        return 1.3;
    }
    
    @Override
    public float baseProjectileDamage() {
        return 2.5f;
    }
    
    @Override
    protected float baseProjectileSpeed() {
        return 5.5f;
    }
    
    @Override
    protected float baseInaccuracy() {
        return 1.2f;
    }
    
    @Override
    public float projectileDamageModifier() {
        return 1.25f;
    }
    
    @Override
    public int getDrawTime() {
        return 30;
    }
    
    @Override
    public void func_77663_a(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        this.onUpdateTraits(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    @Override
    public ProjectileLauncherNBT buildTagData(final List<Material> materials) {
        final ProjectileLauncherNBT data = new ProjectileLauncherNBT();
        final HeadMaterialStats head1 = materials.get(0).getStatsOrUnknown("head");
        final HeadMaterialStats head2 = materials.get(1).getStatsOrUnknown("head");
        final BowMaterialStats limb1 = materials.get(0).getStatsOrUnknown("bow");
        final BowMaterialStats limb2 = materials.get(1).getStatsOrUnknown("bow");
        final ExtraMaterialStats grip = materials.get(2).getStatsOrUnknown("extra");
        final BowStringMaterialStats bowstring = materials.get(3).getStatsOrUnknown("bowstring");
        data.head(head1, head2);
        data.limb(limb1, limb2);
        data.extra(grip);
        data.bowstring(bowstring);
        final ProjectileLauncherNBT projectileLauncherNBT = data;
        projectileLauncherNBT.durability *= (int)1.4f;
        return data;
    }
}
