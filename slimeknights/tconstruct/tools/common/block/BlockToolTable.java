package slimeknights.tconstruct.tools.common.block;

import slimeknights.tconstruct.shared.block.*;
import com.google.common.collect.*;
import net.minecraft.block.material.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.*;
import slimeknights.mantle.inventory.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import net.minecraft.item.*;
import net.minecraftforge.items.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraftforge.oredict.*;
import slimeknights.tconstruct.common.config.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraftforge.common.property.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import java.util.*;

public class BlockToolTable extends BlockTable implements ITinkerStationBlock
{
    public static final PropertyEnum<TableTypes> TABLES;
    private static ImmutableList<AxisAlignedBB> BOUNDS_Chest;
    
    public BlockToolTable() {
        super(Material.field_151575_d);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
        this.func_149672_a(SoundType.field_185848_a);
        this.func_149752_b(5.0f);
        this.func_149711_c(1.0f);
        this.setHarvestLevel("axe", 0);
    }
    
    @Nonnull
    @Override
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        switch (TableTypes.fromMeta(meta)) {
            case CraftingStation: {
                return (TileEntity)new TileCraftingStation();
            }
            case StencilTable: {
                return (TileEntity)new TileStencilTable();
            }
            case PartBuilder: {
                return (TileEntity)new TilePartBuilder();
            }
            case ToolStation: {
                return (TileEntity)new TileToolStation();
            }
            case PatternChest: {
                return (TileEntity)new TilePatternChest();
            }
            case PartChest: {
                return (TileEntity)new TilePartChest();
            }
            default: {
                return super.func_149915_a(worldIn, meta);
            }
        }
    }
    
    @Override
    public boolean openGui(final EntityPlayer player, final World world, final BlockPos pos) {
        if (!world.field_72995_K) {
            player.openGui((Object)TConstruct.instance, 0, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
            if (player.field_71070_bA instanceof BaseContainer) {
                ((BaseContainer)player.field_71070_bA).syncOnOpen((EntityPlayerMP)player);
            }
        }
        return true;
    }
    
    public boolean func_180639_a(final World world, final BlockPos pos, final IBlockState state, final EntityPlayer player, final EnumHand hand, final EnumFacing side, final float clickX, final float clickY, final float clickZ) {
        final TileEntity te = world.func_175625_s(pos);
        final ItemStack heldItem = player.field_71071_by.func_70448_g();
        if (!heldItem.func_190926_b() && te instanceof TileTinkerChest) {
            final IItemHandlerModifiable itemHandler = ((TileTinkerChest)te).getItemHandler();
            final ItemStack rest = ItemHandlerHelper.insertItem((IItemHandler)itemHandler, heldItem, false);
            if (rest.func_190926_b() || rest.func_190916_E() < heldItem.func_190916_E()) {
                player.field_71071_by.field_70462_a.set(player.field_71071_by.field_70461_c, (Object)rest);
                return true;
            }
        }
        return super.func_180639_a(world, pos, state, player, hand, side, clickX, clickY, clickZ);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        list.add((Object)new ItemStack((Block)this, 1, TableTypes.CraftingStation.meta));
        this.addBlocksFromOredict("plankWood", TableTypes.StencilTable.meta, list);
        list.add((Object)new ItemStack((Block)this, 1, TableTypes.PatternChest.meta));
        this.addBlocksFromOredict("logWood", TableTypes.PartBuilder.meta, list);
        list.add((Object)new ItemStack((Block)this, 1, TableTypes.PartChest.meta));
        list.add((Object)new ItemStack((Block)this, 1, TableTypes.ToolStation.meta));
    }
    
    private void addBlocksFromOredict(final String oredict, final int meta, final NonNullList<ItemStack> list) {
        for (final ItemStack stack : OreDictionary.getOres(oredict)) {
            final Block block = func_149634_a(stack.func_77973_b());
            final int blockMeta = stack.func_77952_i();
            if (blockMeta == 32767) {
                final NonNullList<ItemStack> subBlocks = (NonNullList<ItemStack>)NonNullList.func_191196_a();
                block.func_149666_a((CreativeTabs)null, (NonNullList)subBlocks);
                for (final ItemStack subBlock : subBlocks) {
                    list.add((Object)BlockTable.createItemstack(this, meta, func_149634_a(subBlock.func_77973_b()), subBlock.func_77952_i()));
                    if (!Config.listAllTables) {
                        return;
                    }
                }
            }
            else {
                list.add((Object)BlockTable.createItemstack(this, meta, block, blockMeta));
                if (!Config.listAllTables) {
                    return;
                }
                continue;
            }
        }
    }
    
    @Override
    protected boolean keepInventory(final IBlockState state) {
        return Config.chestsKeepInventory && (state.func_177229_b((IProperty)BlockToolTable.TABLES) == TableTypes.PatternChest || state.func_177229_b((IProperty)BlockToolTable.TABLES) == TableTypes.PartChest);
    }
    
    @Nonnull
    @Override
    protected BlockStateContainer func_180661_e() {
        return (BlockStateContainer)new ExtendedBlockState((Block)this, new IProperty[] { (IProperty)BlockToolTable.TABLES }, new IUnlistedProperty[] { (IUnlistedProperty)BlockToolTable.TEXTURE, (IUnlistedProperty)BlockToolTable.INVENTORY, (IUnlistedProperty)BlockToolTable.FACING });
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockToolTable.TABLES, (Comparable)TableTypes.fromMeta(meta));
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((TableTypes)state.func_177229_b((IProperty)BlockToolTable.TABLES)).meta;
    }
    
    @Override
    public RayTraceResult func_180636_a(final IBlockState blockState, @Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final Vec3d start, @Nonnull final Vec3d end) {
        if (((TableTypes)blockState.func_177229_b((IProperty)BlockToolTable.TABLES)).isChest) {
            return BlockTable.raytraceMultiAABB((List<AxisAlignedBB>)BlockToolTable.BOUNDS_Chest, pos, start, end);
        }
        return super.func_180636_a(blockState, worldIn, pos, start, end);
    }
    
    @Override
    public int getGuiNumber(final IBlockState state) {
        switch ((TableTypes)state.func_177229_b((IProperty)BlockToolTable.TABLES)) {
            case StencilTable: {
                return 10;
            }
            case PatternChest: {
                return 15;
            }
            case PartChest: {
                return 16;
            }
            case PartBuilder: {
                return 20;
            }
            case ToolStation: {
                return 25;
            }
            case CraftingStation: {
                return 50;
            }
            default: {
                return 0;
            }
        }
    }
    
    static {
        TABLES = PropertyEnum.func_177709_a("type", (Class)TableTypes.class);
        BlockToolTable.BOUNDS_Chest = (ImmutableList<AxisAlignedBB>)ImmutableList.of((Object)new AxisAlignedBB(0.0, 0.9375, 0.0, 1.0, 1.0, 1.0), (Object)new AxisAlignedBB(0.0625, 0.1875, 0.0625, 0.9375, 1.0, 0.9375), (Object)new AxisAlignedBB(0.03125, 0.0, 0.03125, 0.15625, 0.75, 0.15625), (Object)new AxisAlignedBB(0.84375, 0.0, 0.03125, 0.96875, 0.75, 0.15625), (Object)new AxisAlignedBB(0.84375, 0.0, 0.84375, 0.96875, 0.75, 0.96875), (Object)new AxisAlignedBB(0.03125, 0.0, 0.84375, 0.15625, 0.75, 0.96875));
    }
    
    public enum TableTypes implements IStringSerializable
    {
        CraftingStation, 
        StencilTable, 
        PartBuilder, 
        ToolStation, 
        PatternChest(true), 
        PartChest(true);
        
        public final int meta;
        public final boolean isChest;
        
        private TableTypes() {
            this.meta = this.ordinal();
            this.isChest = false;
        }
        
        private TableTypes(final boolean chest) {
            this.meta = this.ordinal();
            this.isChest = chest;
        }
        
        public static TableTypes fromMeta(int meta) {
            if (meta < 0 || meta >= values().length) {
                meta = 0;
            }
            return values()[meta];
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
