package slimeknights.tconstruct.smeltery.block;

import slimeknights.mantle.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.smeltery.tileentity.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import slimeknights.mantle.multiblock.*;

public class BlockEnumSmeltery<T extends Enum> extends EnumBlock<T> implements ITileEntityProvider
{
    public BlockEnumSmeltery(final PropertyEnum<T> prop, final Class<T> clazz) {
        this(Material.field_151576_e, prop, clazz);
    }
    
    public BlockEnumSmeltery(final Material material, final PropertyEnum<T> prop, final Class<T> clazz) {
        super(material, (PropertyEnum)prop, (Class)clazz);
        this.func_149711_c(3.0f);
        this.func_149752_b(20.0f);
        this.func_149672_a(SoundType.field_185852_e);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabSmeltery);
        this.field_149758_A = true;
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileSmelteryComponent();
    }
    
    public void func_180663_b(@Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final IBlockState state) {
        final TileEntity te = worldIn.func_175625_s(pos);
        if (te instanceof TileSmelteryComponent) {
            ((TileSmelteryComponent)te).notifyMasterOfChange();
        }
        super.func_180663_b(worldIn, pos, state);
        worldIn.func_175713_t(pos);
    }
    
    public void func_180633_a(final World worldIn, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        for (final EnumFacing dir : EnumFacing.values()) {
            final TileEntity te = worldIn.func_175625_s(pos.func_177972_a(dir));
            if (te instanceof IMasterLogic) {
                final TileEntity servant = worldIn.func_175625_s(pos);
                if (servant instanceof IServantLogic) {
                    ((IMasterLogic)te).notifyChange((IServantLogic)servant, pos);
                    break;
                }
            }
            else if (te instanceof TileSmelteryComponent) {
                final TileSmelteryComponent component = (TileSmelteryComponent)te;
                if (component.hasValidMaster()) {
                    component.notifyMasterOfChange();
                    break;
                }
            }
        }
    }
    
    @Deprecated
    public boolean func_189539_a(final IBlockState state, final World worldIn, final BlockPos pos, final int id, final int param) {
        super.func_189539_a(state, worldIn, pos, id, param);
        final TileEntity tileentity = worldIn.func_175625_s(pos);
        return tileentity != null && tileentity.func_145842_c(id, param);
    }
}
