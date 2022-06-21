package slimeknights.tconstruct.tools.ranged.item;

import slimeknights.tconstruct.library.tools.ranged.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.tools.*;
import java.util.*;
import net.minecraft.item.*;
import slimeknights.tconstruct.tools.ranged.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.library.client.crosshair.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.tools.*;

public class ShortBow extends BowCore implements ICustomCrosshairUser
{
    public ShortBow() {
        this(new PartMaterialType[] { PartMaterialType.bow(TinkerTools.bowLimb), PartMaterialType.bow(TinkerTools.bowLimb), PartMaterialType.bowstring(TinkerTools.bowString) });
    }
    
    public ShortBow(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
        this.func_185043_a(ShortBow.PROPERTY_PULL_PROGRESS, this.pullProgressPropertyGetter);
        this.func_185043_a(ShortBow.PROPERTY_IS_PULLING, this.isPullingPropertyGetter);
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 0, 1 };
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, null, null, TinkerMaterials.string);
        }
    }
    
    @Override
    public float baseProjectileDamage() {
        return 0.0f;
    }
    
    @Override
    public float damagePotential() {
        return 0.7f;
    }
    
    @Override
    public double attackSpeed() {
        return 1.5;
    }
    
    @Override
    protected float baseInaccuracy() {
        return 1.0f;
    }
    
    @Override
    public float projectileDamageModifier() {
        return 0.8f;
    }
    
    @Override
    public int getDrawTime() {
        return 12;
    }
    
    @Override
    protected List<Item> getAmmoItems() {
        return TinkerRangedWeapons.getDiscoveredArrows();
    }
    
    @Override
    public void func_77663_a(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) {
        this.preventSlowDown(entityIn, 0.5f);
        super.func_77663_a(stack, worldIn, entityIn, itemSlot, isSelected);
    }
    
    @Override
    public ProjectileLauncherNBT buildTagData(final List<Material> materials) {
        final ProjectileLauncherNBT data = new ProjectileLauncherNBT();
        final HeadMaterialStats head1 = materials.get(0).getStatsOrUnknown("head");
        final HeadMaterialStats head2 = materials.get(1).getStatsOrUnknown("head");
        final BowMaterialStats limb1 = materials.get(0).getStatsOrUnknown("bow");
        final BowMaterialStats limb2 = materials.get(1).getStatsOrUnknown("bow");
        final BowStringMaterialStats bowstring = materials.get(2).getStatsOrUnknown("bowstring");
        data.head(head1, head2);
        data.limb(limb1, limb2);
        data.bowstring(bowstring);
        return data;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public ICrosshair getCrosshair(final ItemStack itemStack, final EntityPlayer player) {
        return Crosshairs.SQUARE;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public float getCrosshairState(final ItemStack itemStack, final EntityPlayer player) {
        return this.getDrawbackProgress(itemStack, (EntityLivingBase)player);
    }
}
