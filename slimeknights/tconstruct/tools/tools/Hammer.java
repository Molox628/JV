package slimeknights.tconstruct.tools.tools;

import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.*;
import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;

public class Hammer extends Pickaxe
{
    public static final float DURABILITY_MODIFIER = 2.5f;
    
    public Hammer() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toughToolRod), PartMaterialType.head(TinkerTools.hammerHead), PartMaterialType.head(TinkerTools.largePlate), PartMaterialType.head(TinkerTools.largePlate) });
        this.addCategory(Category.WEAPON);
    }
    
    @Override
    public void func_150895_a(final CreativeTabs tab, final NonNullList<ItemStack> subItems) {
        if (this.func_194125_a(tab)) {
            this.addDefaultSubItems((List<ItemStack>)subItems, new Material[0]);
            this.addInfiTool((List<ItemStack>)subItems, "InfiMiner");
        }
    }
    
    @Override
    public float miningSpeedModifier() {
        return 0.4f;
    }
    
    @Override
    public float damagePotential() {
        return 1.2f;
    }
    
    @Override
    public double attackSpeed() {
        return 0.800000011920929;
    }
    
    @Override
    public boolean dealDamage(final ItemStack stack, final EntityLivingBase player, final Entity entity, float damage) {
        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).func_70668_bt() == EnumCreatureAttribute.UNDEAD) {
            damage += 3 + TConstruct.random.nextInt(4);
        }
        final boolean hit = super.dealDamage(stack, player, entity, damage);
        if (hit && this.readyForSpecialAttack(player)) {
            TinkerTools.proxy.spawnAttackParticle(Particles.HAMMER_ATTACK, (Entity)player, 0.8);
        }
        return hit;
    }
    
    @Override
    public ImmutableList<BlockPos> getAOEBlocks(final ItemStack stack, final World world, final EntityPlayer player, final BlockPos origin) {
        return ToolHelper.calcAOEBlocks(stack, world, player, origin, 3, 3, 1);
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 1, 2, 3 };
    }
    
    @Override
    public float getRepairModifierForPart(final int index) {
        return (index == 1) ? 2.5f : 1.5f;
    }
    
    public ToolNBT buildTagData(final List<Material> materials) {
        final HandleMaterialStats handle = materials.get(0).getStatsOrUnknown("handle");
        final HeadMaterialStats head = materials.get(1).getStatsOrUnknown("head");
        final HeadMaterialStats plate1 = materials.get(2).getStatsOrUnknown("head");
        final HeadMaterialStats plate2 = materials.get(3).getStatsOrUnknown("head");
        final ToolNBT data = new ToolNBT();
        data.head(head, head, plate1, plate2);
        data.handle(handle);
        data.harvestLevel = head.harvestLevel;
        final ToolNBT toolNBT = data;
        toolNBT.durability *= (int)2.5f;
        return data;
    }
}
