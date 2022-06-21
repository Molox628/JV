package slimeknights.tconstruct.tools.tools;

import net.minecraft.block.material.*;
import slimeknights.tconstruct.tools.*;
import slimeknights.tconstruct.library.tinkering.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.library.events.*;
import com.google.common.collect.*;
import javax.annotation.*;
import net.minecraft.init.*;
import net.minecraft.entity.*;
import java.util.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.util.text.*;
import slimeknights.tconstruct.library.tools.*;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.utils.*;
import net.minecraft.nbt.*;

public class Mattock extends AoeToolCore
{
    public static final ImmutableSet<Material> effective_materials_axe;
    public static final ImmutableSet<Material> effective_materials_shovel;
    
    public Mattock() {
        super(new PartMaterialType[] { PartMaterialType.handle(TinkerTools.toolRod), PartMaterialType.head(TinkerTools.axeHead), PartMaterialType.head(TinkerTools.shovelHead) });
        this.addCategory(Category.HARVEST);
        this.setHarvestLevel("mattock", 0);
    }
    
    @Override
    public int getHarvestLevel(final ItemStack stack, final String toolClass, @Nullable final EntityPlayer player, @Nullable final IBlockState blockState) {
        if (StringUtils.func_151246_b(toolClass)) {
            return -1;
        }
        if (toolClass.equals("axe")) {
            return this.getAxeLevel(stack);
        }
        if (toolClass.equals("shovel")) {
            return this.getShovelLevel(stack);
        }
        return super.getHarvestLevel(stack, toolClass, player, blockState);
    }
    
    @Override
    public boolean isEffective(final IBlockState state) {
        return Mattock.effective_materials_axe.contains((Object)state.func_185904_a()) || Mattock.effective_materials_shovel.contains((Object)state.func_185904_a());
    }
    
    @Override
    public float miningSpeedModifier() {
        return 0.95f;
    }
    
    @Override
    public float damagePotential() {
        return 0.9f;
    }
    
    @Override
    public float knockback() {
        return 1.1f;
    }
    
    @Override
    public double attackSpeed() {
        return 0.8999999761581421;
    }
    
    @Override
    public int[] getRepairParts() {
        return new int[] { 1, 2 };
    }
    
    @Nonnull
    public EnumActionResult func_180614_a(final EntityPlayer player, final World worldIn, final BlockPos pos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        final ItemStack stack = player.func_184586_b(hand);
        if (ToolHelper.isBroken(stack)) {
            return EnumActionResult.FAIL;
        }
        EnumActionResult ret = this.useHoe(stack, player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        for (final BlockPos blockPos : this.getAOEBlocks(stack, worldIn, player, pos)) {
            if (ToolHelper.isBroken(stack)) {
                break;
            }
            final EnumActionResult ret2 = this.useHoe(stack, player, worldIn, blockPos, hand, facing, hitX, hitY, hitZ);
            if (ret == EnumActionResult.SUCCESS) {
                continue;
            }
            ret = ret2;
        }
        if (ret == EnumActionResult.SUCCESS) {
            TinkerToolEvent.OnMattockHoe.fireEvent(stack, player, worldIn, pos);
        }
        return ret;
    }
    
    private EnumActionResult useHoe(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos blockPos, final EnumHand hand, final EnumFacing facing, final float hitX, final float hitY, final float hitZ) {
        final int damage = stack.func_77952_i();
        final EnumActionResult ret = Items.field_151012_L.func_180614_a(playerIn, worldIn, blockPos, hand, facing, hitX, hitY, hitZ);
        stack.func_77964_b(damage);
        if (!worldIn.field_72995_K && ret == EnumActionResult.SUCCESS) {
            ToolHelper.damageTool(stack, 1, (EntityLivingBase)playerIn);
        }
        return ret;
    }
    
    @Override
    public boolean isAoeHarvestTool() {
        return false;
    }
    
    @Override
    public List<String> getInformation(final ItemStack stack, final boolean detailed) {
        final TooltipBuilder info = new TooltipBuilder(stack);
        info.addDurability(!detailed);
        String text = Util.translate("stat.mattock.axelevel.name", new Object[0]);
        info.add(String.format("%s: %s", text, HarvestLevels.getHarvestLevelName(this.getAxeLevel(stack))) + TextFormatting.RESET);
        text = Util.translate("stat.mattock.shovellevel.name", new Object[0]);
        info.add(String.format("%s: %s", text, HarvestLevels.getHarvestLevelName(this.getShovelLevel(stack))) + TextFormatting.RESET);
        info.addMiningSpeed();
        info.addAttack();
        if (ToolHelper.getFreeModifiers(stack) > 0) {
            info.addFreeModifiers();
        }
        if (detailed) {
            info.addModifierInfo();
        }
        return info.getTooltip();
    }
    
    public ToolNBT buildTagData(final List<slimeknights.tconstruct.library.materials.Material> materials) {
        final HandleMaterialStats handle = materials.get(0).getStatsOrUnknown("handle");
        final HeadMaterialStats axe = materials.get(1).getStatsOrUnknown("head");
        final HeadMaterialStats shovel = materials.get(2).getStatsOrUnknown("head");
        final MattockToolNBT data = new MattockToolNBT();
        data.head(axe, shovel);
        data.handle(handle);
        data.axeLevel = axe.harvestLevel;
        data.shovelLevel = shovel.harvestLevel;
        final MattockToolNBT mattockToolNBT = data;
        mattockToolNBT.attack += 3.0f;
        return data;
    }
    
    protected int getAxeLevel(final ItemStack stack) {
        return new MattockToolNBT(TagUtil.getToolTag(stack)).axeLevel;
    }
    
    protected int getShovelLevel(final ItemStack stack) {
        return new MattockToolNBT(TagUtil.getToolTag(stack)).shovelLevel;
    }
    
    static {
        effective_materials_axe = ImmutableSet.of((Object)Material.field_151575_d, (Object)Material.field_151570_A, (Object)Material.field_151585_k, (Object)Material.field_151582_l, (Object)Material.field_151572_C);
        effective_materials_shovel = ImmutableSet.of((Object)Material.field_151577_b, (Object)Material.field_151578_c, (Object)Material.field_151571_B);
    }
    
    public static class MattockToolNBT extends ToolNBT
    {
        private static final String TAG_AxeLevel = "HarvestLevelAxe";
        private static final String TAG_ShovelLevel = "HarvestLevelShovel";
        public int axeLevel;
        public int shovelLevel;
        
        public MattockToolNBT() {
        }
        
        public MattockToolNBT(final NBTTagCompound tag) {
            super(tag);
        }
        
        @Override
        public void read(final NBTTagCompound tag) {
            super.read(tag);
            this.axeLevel = tag.func_74762_e("HarvestLevelAxe");
            this.shovelLevel = tag.func_74762_e("HarvestLevelShovel");
        }
        
        @Override
        public void write(final NBTTagCompound tag) {
            super.write(tag);
            tag.func_74768_a("HarvestLevelAxe", this.axeLevel);
            tag.func_74768_a("HarvestLevelShovel", this.shovelLevel);
        }
    }
}
