package slimeknights.tconstruct.world.block;

import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.world.*;
import net.minecraftforge.common.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import java.util.*;
import net.minecraftforge.event.terraingen.*;
import slimeknights.tconstruct.shared.block.*;
import slimeknights.tconstruct.shared.*;
import slimeknights.tconstruct.world.worldgen.*;

public class BlockSlimeSapling extends BlockSapling
{
    public static PropertyEnum<BlockSlimeGrass.FoliageType> FOLIAGE;
    
    public BlockSlimeSapling() {
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
        this.func_180632_j(this.field_176227_L.func_177621_b());
        this.func_149672_a(SoundType.field_185850_c);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final BlockSlimeGrass.FoliageType type : BlockSlimeGrass.FoliageType.values()) {
            list.add((Object)new ItemStack((Block)this, 1, this.func_176201_c(this.func_176223_P().func_177226_a((IProperty)BlockSlimeSapling.FOLIAGE, (Comparable)type))));
        }
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockSlimeSapling.FOLIAGE, (IProperty)BlockSlimeSapling.field_176479_b, (IProperty)BlockSlimeSapling.field_176480_a });
    }
    
    @Nonnull
    public IBlockState func_176203_a(int meta) {
        if (meta < 0 || meta >= BlockSlimeGrass.FoliageType.values().length) {
            meta = 0;
        }
        final BlockSlimeGrass.FoliageType grass = BlockSlimeGrass.FoliageType.values()[meta];
        return this.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, (Comparable)grass);
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((BlockSlimeGrass.FoliageType)state.func_177229_b((IProperty)BlockSlimeGrass.FOLIAGE)).ordinal();
    }
    
    public int func_180651_a(final IBlockState state) {
        return this.func_176201_c(state);
    }
    
    public boolean func_176200_f(final IBlockAccess worldIn, @Nonnull final BlockPos pos) {
        return false;
    }
    
    public boolean func_176196_c(final World worldIn, final BlockPos pos) {
        final Block ground = worldIn.func_180495_p(pos.func_177977_b()).func_177230_c();
        return ground == TinkerWorld.slimeGrass || ground == TinkerWorld.slimeDirt;
    }
    
    @Nonnull
    public EnumPlantType getPlantType(final IBlockAccess world, final BlockPos pos) {
        return TinkerWorld.slimePlantType;
    }
    
    @Nonnull
    public ItemStack getPickBlock(@Nonnull final IBlockState state, final RayTraceResult target, @Nonnull final World world, @Nonnull final BlockPos pos, final EntityPlayer player) {
        final IBlockState iblockstate = world.func_180495_p(pos);
        final int meta = iblockstate.func_177230_c().func_176201_c(iblockstate);
        return new ItemStack(Item.func_150898_a((Block)this), 1, meta);
    }
    
    public void func_176476_e(@Nonnull final World worldIn, @Nonnull final BlockPos pos, @Nonnull final IBlockState state, @Nonnull final Random rand) {
        if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) {
            return;
        }
        BlockSlime.SlimeType slimeType = BlockSlime.SlimeType.GREEN;
        if (state.func_177229_b((IProperty)BlockSlimeSapling.FOLIAGE) == BlockSlimeGrass.FoliageType.ORANGE) {
            slimeType = BlockSlime.SlimeType.MAGMA;
        }
        final IBlockState slimeGreen = TinkerCommons.blockSlimeCongealed.func_176223_P().func_177226_a((IProperty)BlockSlime.TYPE, (Comparable)slimeType);
        final IBlockState leaves = TinkerWorld.slimeLeaves.func_176223_P().func_177226_a((IProperty)BlockSlimeGrass.FOLIAGE, state.func_177229_b((IProperty)BlockSlimeSapling.FOLIAGE));
        final SlimeTreeGenerator gen = new SlimeTreeGenerator(5, 4, slimeGreen, leaves, null);
        worldIn.func_175698_g(pos);
        gen.generateTree(rand, worldIn, pos);
        if (worldIn.func_175623_d(pos)) {
            worldIn.func_180501_a(pos, state, 4);
        }
    }
    
    static {
        BlockSlimeSapling.FOLIAGE = BlockSlimeGrass.FOLIAGE;
    }
}
