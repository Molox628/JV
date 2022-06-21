package slimeknights.tconstruct.world.block;

import net.minecraft.block.material.*;
import net.minecraft.block.properties.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import slimeknights.tconstruct.world.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.item.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraftforge.common.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockSlimeGrass extends Block implements IGrowable
{
    public static PropertyEnum<DirtType> TYPE;
    public static PropertyEnum<FoliageType> FOLIAGE;
    public static final PropertyBool SNOWY;
    
    public BlockSlimeGrass() {
        super(Material.field_151577_b);
        this.func_180632_j(this.field_176227_L.func_177621_b().func_177226_a((IProperty)BlockSlimeGrass.SNOWY, (Comparable)Boolean.FALSE));
        this.func_149675_a(true);
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
        this.func_149711_c(0.65f);
        this.func_149672_a(SoundType.field_185850_c);
        this.field_149765_K += 0.05f;
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final FoliageType grass : FoliageType.values()) {
            for (final DirtType type : DirtType.values()) {
                list.add((Object)new ItemStack((Block)this, 1, this.func_176201_c(this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.TYPE, (Comparable)type).func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)grass))));
            }
        }
    }
    
    public boolean func_176473_a(final World worldIn, final BlockPos pos, final IBlockState state, final boolean isClient) {
        return true;
    }
    
    public boolean func_180670_a(final World worldIn, final Random rand, final BlockPos pos, final IBlockState state) {
        return true;
    }
    
    public void func_176474_b(@Nonnull final World worldIn, @Nonnull final Random rand, @Nonnull final BlockPos pos, @Nonnull final IBlockState state) {
        final BlockPos blockpos1 = pos.func_177984_a();
        int i = 0;
    Label_0009:
        while (i < 128) {
            BlockPos blockpos2 = blockpos1;
            while (true) {
                for (int j = 0; j < i / 16; ++j) {
                    blockpos2 = blockpos2.func_177982_a(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                    if (worldIn.func_180495_p(blockpos2.func_177977_b()).func_177230_c() != this || worldIn.func_180495_p(blockpos2).func_177230_c().func_149721_r(state)) {
                        ++i;
                        continue Label_0009;
                    }
                }
                if (!worldIn.func_175623_d(blockpos2)) {
                    continue;
                }
                IBlockState plantState;
                if (rand.nextInt(8) == 0) {
                    plantState = TinkerWorld.slimeGrassTall.func_176223_P().func_177226_a((IProperty)BlockTallSlimeGrass.TYPE, (Comparable)BlockTallSlimeGrass.SlimePlantType.FERN);
                }
                else {
                    plantState = TinkerWorld.slimeGrassTall.func_176223_P().func_177226_a((IProperty)BlockTallSlimeGrass.TYPE, (Comparable)BlockTallSlimeGrass.SlimePlantType.TALL_GRASS);
                }
                plantState = plantState.func_177226_a((IProperty)BlockTallSlimeGrass.FOLIAGE, state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE));
                if (TinkerWorld.slimeGrassTall.func_180671_f(worldIn, blockpos2, plantState)) {
                    worldIn.func_180501_a(blockpos2, plantState, 3);
                }
                continue;
            }
        }
    }
    
    public void func_180650_b(final World worldIn, @Nonnull final BlockPos pos, final IBlockState state, @Nonnull final Random rand) {
        if (worldIn.field_72995_K) {
            return;
        }
        if (worldIn.func_175671_l(pos.func_177984_a()) >= 9) {
            for (int i = 0; i < 4; ++i) {
                final BlockPos blockpos = pos.func_177982_a(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
                if (blockpos.func_177956_o() >= 0 && blockpos.func_177956_o() < 256 && !worldIn.func_175667_e(blockpos)) {
                    return;
                }
                final IBlockState iblockstate = worldIn.func_180495_p(blockpos.func_177984_a());
                final IBlockState iblockstate2 = worldIn.func_180495_p(blockpos);
                if (worldIn.func_175671_l(blockpos.func_177984_a()) >= 4 && iblockstate.getLightOpacity((IBlockAccess)worldIn, pos.func_177984_a()) <= 2) {
                    this.convert(worldIn, blockpos, iblockstate2, (FoliageType)state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE));
                }
            }
        }
    }
    
    public void convert(final World world, final BlockPos pos, final IBlockState state, final FoliageType foliageType) {
        final IBlockState newState = this.getStateFromDirt(state);
        if (newState != null) {
            world.func_175656_a(pos, newState.func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)foliageType));
        }
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockSlimeGrass.TYPE, (IProperty)BlockSlimeGrass.FOLIAGE, (IProperty)BlockGrass.field_176498_a });
    }
    
    @Nonnull
    public IBlockState func_176203_a(int meta) {
        if (meta > 14) {
            meta = 0;
        }
        return this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.TYPE, (Comparable)DirtType.values()[meta % 5]).func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)FoliageType.values()[meta / 5]);
    }
    
    public int func_176201_c(final IBlockState state) {
        final DirtType type = (DirtType)state.func_177229_b((IProperty)BlockSlimeGrass.TYPE);
        final FoliageType grass = (FoliageType)state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE);
        return type.ordinal() + grass.ordinal() * 5;
    }
    
    public int func_180651_a(final IBlockState state) {
        final DirtType type = (DirtType)state.func_177229_b((IProperty)BlockSlimeGrass.TYPE);
        if (type == DirtType.VANILLA) {
            return 0;
        }
        return ((BlockSlimeDirt.DirtType)this.getDirtState(state).func_177229_b((IProperty)BlockSlimeDirt.TYPE)).getMeta();
    }
    
    public Item func_180660_a(final IBlockState state, @Nonnull final Random rand, final int fortune) {
        return Item.func_150898_a(this.getDirtState(state).func_177230_c());
    }
    
    @Nonnull
    public ItemStack getPickBlock(@Nonnull final IBlockState state, final RayTraceResult target, @Nonnull final World world, @Nonnull final BlockPos pos, final EntityPlayer player) {
        return this.func_180643_i(world.func_180495_p(pos));
    }
    
    public IBlockState getDirtState(final IBlockState grassState) {
        final DirtType type = (DirtType)grassState.func_177229_b((IProperty)BlockSlimeGrass.TYPE);
        switch (type) {
            case VANILLA: {
                return Blocks.field_150346_d.func_176223_P();
            }
            case GREEN: {
                return TinkerWorld.slimeDirt.func_176203_a(BlockSlimeDirt.DirtType.GREEN.getMeta());
            }
            case BLUE: {
                return TinkerWorld.slimeDirt.func_176203_a(BlockSlimeDirt.DirtType.BLUE.getMeta());
            }
            case PURPLE: {
                return TinkerWorld.slimeDirt.func_176203_a(BlockSlimeDirt.DirtType.PURPLE.getMeta());
            }
            case MAGMA: {
                return TinkerWorld.slimeDirt.func_176203_a(BlockSlimeDirt.DirtType.MAGMA.getMeta());
            }
            default: {
                return TinkerWorld.slimeDirt.func_176203_a(BlockSlimeDirt.DirtType.GREEN.getMeta());
            }
        }
    }
    
    public IBlockState getStateFromDirt(final IBlockState dirtState) {
        if (dirtState.func_177230_c() == Blocks.field_150346_d && dirtState.func_177229_b((IProperty)BlockDirt.field_176386_a) == BlockDirt.DirtType.DIRT) {
            return this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.TYPE, (Comparable)DirtType.VANILLA);
        }
        if (dirtState.func_177230_c() == TinkerWorld.slimeDirt) {
            if (dirtState.func_177229_b((IProperty)BlockSlimeDirt.TYPE) == BlockSlimeDirt.DirtType.GREEN) {
                return this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.TYPE, (Comparable)DirtType.GREEN);
            }
            if (dirtState.func_177229_b((IProperty)BlockSlimeDirt.TYPE) == BlockSlimeDirt.DirtType.BLUE) {
                return this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.TYPE, (Comparable)DirtType.BLUE);
            }
            if (dirtState.func_177229_b((IProperty)BlockSlimeDirt.TYPE) == BlockSlimeDirt.DirtType.PURPLE) {
                return this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.TYPE, (Comparable)DirtType.PURPLE);
            }
            if (dirtState.func_177229_b((IProperty)BlockSlimeDirt.TYPE) == BlockSlimeDirt.DirtType.MAGMA) {
                return this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.TYPE, (Comparable)DirtType.MAGMA);
            }
        }
        return null;
    }
    
    public boolean canSustainPlant(@Nonnull final IBlockState state, @Nonnull final IBlockAccess world, final BlockPos pos, @Nonnull final EnumFacing direction, final IPlantable plantable) {
        return plantable.getPlantType(world, pos) == TinkerWorld.slimePlantType || plantable.getPlantType(world, pos) == EnumPlantType.Plains;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer func_180664_k() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }
    
    static {
        BlockSlimeGrass.TYPE = (PropertyEnum<DirtType>)PropertyEnum.func_177709_a("type", (Class)DirtType.class);
        BlockSlimeGrass.FOLIAGE = (PropertyEnum<FoliageType>)PropertyEnum.func_177709_a("foliage", (Class)FoliageType.class);
        SNOWY = PropertyBool.func_177716_a("snowy");
    }
    
    public enum FoliageType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        BLUE, 
        PURPLE, 
        ORANGE;
        
        public static FoliageType getValFromMeta(int meta) {
            if (meta < 0 || meta >= values().length) {
                meta = 0;
            }
            return values()[meta];
        }
        
        public int getMeta() {
            return this.ordinal();
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
    }
    
    public enum DirtType implements IStringSerializable
    {
        VANILLA, 
        GREEN, 
        BLUE, 
        PURPLE, 
        MAGMA;
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
    }
}
