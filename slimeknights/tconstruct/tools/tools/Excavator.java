package slimeknights.tconstruct.tools.tools;

import slimeknights.tconstruct.library.tinkering.*;
import slimeknights.tconstruct.tools.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;

public class Excavator extends Shovel
{
    public static final float DURABILITY_MODIFIER = 1.75f;
    
    public Excavator() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toughToolRod), PartMaterialType.head(TinkerTools.excavatorHead), PartMaterialType.head(TinkerTools.largePlate), PartMaterialType.extra(TinkerTools.toughBinding) });
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, new Material[0]);
            this.addInfiTool((List<ItemStack>)subItems, "InfiDigger");
        }
    }
    
    @Override
    public float miningSpeedModifier() {
        return 0.28f;
    }
    
    @Override
    public float damagePotential() {
        return 1.25f;
    }
    
    @Override
    public double attackSpeed() {
        return 0.699999988079071;
    }
    
    @Override
    public ImmutableList<BlockPos> getAOEBlocks(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos origin) {
        return ToolHelper.calcAOEBlocks(stack, world, player, origin, 3, 3, 1);
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 1, 2 };
    }
    
    @Override
    public float getRepairModifierForPart(final int index) {
        return (index == 1) ? 1.75f : 1.3125f;
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        final HandleMaterialStats handle = materials.get(0).getStatsOrUnknown("handle");
        final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
        final HeadMaterialStats plate = materials.get(2).getStatsOrUnknown("head");
        final ExtraMaterialStats binding = materials.get(3).getStatsOrUnknown("extra");
        final ToolNBT data = new ToolNBT();
        data.head(head, plate);
        data.extra(binding);
        data.handle(handle);
        final ToolNBT toolNBT = data;
        toolNBT.durability *= (int)1.75f;
        return data;
    }
}
