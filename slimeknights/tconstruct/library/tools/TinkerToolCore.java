package slimeknights.tconstruct.library.tools;

import slimeknights.tconstruct.library.tinkering.*;
import java.util.*;
import slimeknights.tconstruct.library.materials.*;
import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import javax.annotation.*;
import slimeknights.tconstruct.library.utils.*;

public abstract class TinkerToolCore extends ToolCore
{
    public TinkerToolCore(final PartMaterialType... requiredComponents) {
        super(requiredComponents);
    }
    
    @Override
    public final NBTTagCompound buildTag(final List<Material> materials) {
        return this.buildTagData(materials).get();
    }
    
    protected abstract ToolNBT buildTagData(final List<Material> p0);
    
    @Override
    public boolean onBlockStartBreak(final ItemStack itemstack, final BlockPos pos, final EntityPlayer player) {
        if (DualToolHarvestUtils.shouldUseOffhand((EntityLivingBase)player, pos, itemstack)) {
            final ItemStack offhand = player.func_184592_cb();
            return offhand.func_77973_b().onBlockStartBreak(offhand, pos, player);
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }
    
    @Override
    public boolean func_179218_a(final ItemStack stack, final World worldIn, final IBlockState state, final BlockPos pos, final EntityLivingBase player) {
        if (DualToolHarvestUtils.shouldUseOffhand(player, pos, stack)) {
            final ItemStack offhand = player.func_184592_cb();
            return offhand.func_77973_b().func_179218_a(offhand, worldIn, state, pos, player);
        }
        return super.func_179218_a(stack, worldIn, state, pos, player);
    }
    
    @Override
    public int getHarvestLevel(final ItemStack stack, final String toolClass, @Nullable final EntityPlayer player, @Nullable final IBlockState blockState) {
        if (ToolHelper.isBroken(stack)) {
            return -1;
        }
        if (player != null && DualToolHarvestUtils.shouldUseOffhand((EntityLivingBase)player, blockState, stack)) {
            final ItemStack offhand = player.func_184592_cb();
            return offhand.func_77973_b().getHarvestLevel(offhand, toolClass, player, blockState);
        }
        return super.getHarvestLevel(stack, toolClass, player, blockState);
    }
}
