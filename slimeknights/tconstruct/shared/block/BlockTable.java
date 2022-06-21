package slimeknights.tconstruct.shared.block;

import slimeknights.mantle.block.*;
import slimeknights.mantle.property.*;
import net.minecraft.block.material.*;
import javax.annotation.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.shared.tileentity.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraftforge.common.property.*;
import net.minecraft.entity.*;
import slimeknights.tconstruct.library.utils.*;
import slimeknights.mantle.tileentity.*;
import net.minecraft.nbt.*;
import net.minecraftforge.event.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraft.util.math.*;
import com.google.common.collect.*;
import com.google.common.base.*;

public class BlockTable extends BlockInventory implements ITileEntityProvider
{
    public static final PropertyString TEXTURE;
    public static final PropertyTableItem INVENTORY;
    public static final PropertyUnlistedDirection FACING;
    private static ImmutableList<AxisAlignedBB> BOUNDS_Table;
    
    public BlockTable(final Material materialIn) {
        super(materialIn);
    }
    
    public boolean func_149662_c(final IBlockState state) {
        return false;
    }
    
    public boolean func_149686_d(final IBlockState state) {
        return false;
    }
    
    @Nonnull
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT;
    }
    
    public boolean func_176225_a(final IBlockState blockState, @Nonnull final IBlockAccess blockAccess, @Nonnull final BlockPos pos, final EnumFacing side) {
        return true;
    }
    
    public boolean hasTileEntity(final IBlockState state) {
        return true;
    }
    
    @Nonnull
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileTable("tile.table", 0, 0);
    }
    
    public boolean openGui(final EntityPlayer player, final World world, final BlockPos pos) {
        return false;
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return (BlockStateContainer)new ExtendedBlockState((Block)this, new IProperty[0], new IUnlistedProperty[] { (IUnlistedProperty)BlockTable.TEXTURE, (IUnlistedProperty)BlockTable.INVENTORY, (IUnlistedProperty)BlockTable.FACING });
    }
    
    @Nonnull
    public IBlockState getExtendedState(@Nonnull final IBlockState state, final IBlockAccess world, final BlockPos pos) {
        final IExtendedBlockState extendedState = (IExtendedBlockState)state;
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileTable) {
            final TileTable table = (TileTable)te;
            return (IBlockState)table.writeExtendedBlockState(extendedState);
        }
        return super.getExtendedState(state, world, pos);
    }
    
    public void func_180633_a(final World world, final BlockPos pos, final IBlockState state, final EntityLivingBase placer, final ItemStack stack) {
        super.func_180633_a(world, pos, state, placer, stack);
        final NBTTagCompound tag = TagUtil.getTagSafe(stack);
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileTable) {
            final TileTable table = (TileTable)te;
            NBTTagCompound feetTag = tag.func_74775_l("textureBlock");
            if (feetTag == null) {
                feetTag = new NBTTagCompound();
            }
            table.updateTextureBlock(feetTag);
            table.setFacing(placer.func_174811_aO().func_176734_d());
            if (tag.func_74764_b("inventory")) {
                table.readInventoryFromNBT(tag.func_74775_l("inventory"));
            }
            if (stack.func_82837_s()) {
                table.setCustomName(stack.func_82833_r());
            }
        }
    }
    
    public boolean removedByPlayer(@Nonnull final IBlockState state, @Nonnull final World world, @Nonnull final BlockPos pos, @Nonnull final EntityPlayer player, final boolean willHarvest) {
        this.func_176206_d(world, pos, state);
        if (willHarvest) {
            this.func_180657_a(world, player, pos, state, world.func_175625_s(pos), player.func_184614_ca());
        }
        if (this.keepInventory(state)) {
            final TileEntity te = world.func_175625_s(pos);
            if (te instanceof TileInventory) {
                ((TileInventory)te).func_174888_l();
            }
        }
        world.func_175698_g(pos);
        return false;
    }
    
    protected boolean keepInventory(final IBlockState state) {
        return false;
    }
    
    private void writeDataOntoItemstack(@Nonnull final ItemStack item, @Nonnull final IBlockAccess world, @Nonnull final BlockPos pos, @Nonnull final IBlockState state, final boolean inventorySave) {
        final TileEntity te = world.func_175625_s(pos);
        if (te != null && te instanceof TileTable) {
            final TileTable table = (TileTable)te;
            final NBTTagCompound tag = TagUtil.getTagSafe(item);
            final NBTTagCompound data = table.getTextureBlock();
            if (!data.func_82582_d()) {
                tag.func_74782_a("textureBlock", (NBTBase)data);
            }
            if (inventorySave && this.keepInventory(state) && !table.isInventoryEmpty()) {
                final NBTTagCompound inventoryTag = new NBTTagCompound();
                table.writeInventoryToNBT(inventoryTag);
                tag.func_74782_a("inventory", (NBTBase)inventoryTag);
                table.func_174888_l();
            }
            if (!tag.func_82582_d()) {
                item.func_77982_d(tag);
            }
        }
    }
    
    public void func_180653_a(@Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final IBlockState state, float chance, final int fortune) {
        if (!worldIn.field_72995_K && !worldIn.restoringBlockSnapshots) {
            final List<ItemStack> items = (List<ItemStack>)this.getDrops((IBlockAccess)worldIn, pos, state, fortune);
            chance = ForgeEventFactory.fireBlockHarvesting((List)items, worldIn, pos, state, fortune, chance, false, (EntityPlayer)this.harvesters.get());
            for (final ItemStack item : items) {
                if (item.func_77973_b() == Item.func_150898_a((Block)this)) {
                    this.writeDataOntoItemstack(item, (IBlockAccess)worldIn, pos, state, chance >= 1.0f);
                }
            }
            final TileEntity te = worldIn.func_175625_s(pos);
            if (te instanceof TileInventory) {
                final TileInventory tileInventory = (TileInventory)te;
                for (int i = 0; i < tileInventory.func_70302_i_(); ++i) {
                    final ItemStack itemStack = tileInventory.func_70301_a(i);
                    if (!itemStack.func_190926_b()) {
                        items.add(itemStack);
                    }
                }
                tileInventory.func_174888_l();
                if (tileInventory.func_145818_k_()) {
                    for (final ItemStack item2 : items) {
                        if (item2.func_77973_b() == Item.func_150898_a((Block)this)) {
                            item2.func_151001_c(tileInventory.func_70005_c_());
                            item2.func_82841_c(0);
                        }
                    }
                }
            }
            for (final ItemStack item3 : items) {
                if (worldIn.field_73012_v.nextFloat() <= chance) {
                    func_180635_a(worldIn, pos, item3);
                }
            }
        }
    }
    
    @Nonnull
    public ItemStack getPickBlock(@Nonnull final IBlockState state, final RayTraceResult target, @Nonnull final World world, @Nonnull final BlockPos pos, final EntityPlayer player) {
        final List<ItemStack> drops = (List<ItemStack>)this.getDrops((IBlockAccess)world, pos, world.func_180495_p(pos), 0);
        if (drops.size() > 0) {
            final ItemStack stack = drops.get(0);
            this.writeDataOntoItemstack(stack, (IBlockAccess)world, pos, state, false);
            return stack;
        }
        return super.getPickBlock(state, target, world, pos, player);
    }
    
    public static ItemStack createItemstack(final BlockTable table, final int tableMeta, final Block block, final int blockMeta) {
        final ItemStack stack = new ItemStack((Block)table, 1, tableMeta);
        if (block != null) {
            final ItemStack blockStack = new ItemStack(block, 1, blockMeta);
            final NBTTagCompound tag = new NBTTagCompound();
            final NBTTagCompound subTag = new NBTTagCompound();
            blockStack.func_77955_b(subTag);
            tag.func_74782_a("textureBlock", (NBTBase)subTag);
            stack.func_77982_d(tag);
        }
        return stack;
    }
    
    public boolean isSideSolid(@Nonnull final IBlockState base_state, @Nonnull final IBlockAccess world, @Nonnull final BlockPos pos, @Nonnull final EnumFacing side) {
        return side == EnumFacing.UP || super.isSideSolid(base_state, world, pos, side);
    }
    
    public RayTraceResult func_180636_a(final IBlockState blockState, @Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final Vec3d start, @Nonnull final Vec3d end) {
        return raytraceMultiAABB((List<AxisAlignedBB>)BlockTable.BOUNDS_Table, pos, start, end);
    }
    
    public static RayTraceResult raytraceMultiAABB(final List<AxisAlignedBB> aabbs, final BlockPos pos, final Vec3d start, final Vec3d end) {
        final List<RayTraceResult> list = (List<RayTraceResult>)Lists.newArrayList();
        for (final AxisAlignedBB axisalignedbb : aabbs) {
            list.add(rayTrace2(pos, start, end, axisalignedbb));
        }
        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0;
        for (final RayTraceResult raytraceresult2 : list) {
            if (raytraceresult2 != null) {
                final double d2 = raytraceresult2.field_72307_f.func_72436_e(end);
                if (d2 <= d1) {
                    continue;
                }
                raytraceresult1 = raytraceresult2;
                d1 = d2;
            }
        }
        return raytraceresult1;
    }
    
    private static RayTraceResult rayTrace2(final BlockPos pos, final Vec3d start, final Vec3d end, final AxisAlignedBB boundingBox) {
        final Vec3d vec3d = start.func_178786_a((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
        final Vec3d vec3d2 = end.func_178786_a((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p());
        final RayTraceResult raytraceresult = boundingBox.func_72327_a(vec3d, vec3d2);
        return (raytraceresult == null) ? null : new RayTraceResult(raytraceresult.field_72307_f.func_72441_c((double)pos.func_177958_n(), (double)pos.func_177956_o(), (double)pos.func_177952_p()), raytraceresult.field_178784_b, pos);
    }
    
    public boolean rotateBlock(final World world, final BlockPos pos, final EnumFacing axis) {
        final TileEntity te = world.func_175625_s(pos);
        if (te instanceof TileTable) {
            final TileTable table = (TileTable)te;
            table.setFacing(table.getFacing().func_176746_e());
            final IBlockState state = world.func_180495_p(pos);
            world.func_184138_a(pos, state, state, 0);
            return true;
        }
        return false;
    }
    
    static {
        TEXTURE = new PropertyString("texture");
        INVENTORY = new PropertyTableItem();
        FACING = new PropertyUnlistedDirection("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
        BlockTable.BOUNDS_Table = (ImmutableList<AxisAlignedBB>)ImmutableList.of((Object)new AxisAlignedBB(0.0, 0.75, 0.0, 1.0, 1.0, 1.0), (Object)new AxisAlignedBB(0.0, 0.0, 0.0, 0.25, 0.75, 0.25), (Object)new AxisAlignedBB(0.75, 0.0, 0.0, 1.0, 0.75, 0.25), (Object)new AxisAlignedBB(0.75, 0.0, 0.75, 1.0, 0.75, 1.0), (Object)new AxisAlignedBB(0.0, 0.0, 0.75, 0.25, 0.75, 1.0));
    }
}
