package slimeknights.tconstruct.tools.common.block;

import slimeknights.tconstruct.shared.block.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.*;
import slimeknights.mantle.inventory.*;
import net.minecraft.entity.player.*;
import javax.annotation.*;
import net.minecraft.tileentity.*;
import slimeknights.tconstruct.tools.common.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.oredict.*;
import slimeknights.tconstruct.common.config.*;
import java.util.*;
import net.minecraft.block.properties.*;
import net.minecraftforge.common.property.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;

public class BlockToolForge extends BlockTable implements ITinkerStationBlock
{
    public final Set<String> baseBlocks;
    
    public BlockToolForge() {
        super(Material.field_151573_f);
        this.baseBlocks = (Set<String>)Sets.newLinkedHashSet();
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabGeneral);
        this.func_149672_a(SoundType.field_185852_e);
        this.func_149752_b(10.0f);
        this.func_149711_c(2.0f);
        this.setHarvestLevel("pickaxe", 0);
    }
    
    @Override
    public boolean openGui(final EntityPlayer player, final World world, final BlockPos pos) {
        player.openGui((Object)TConstruct.instance, 0, world, pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p());
        if (player.field_71070_bA instanceof BaseContainer) {
            ((BaseContainer)player.field_71070_bA).syncOnOpen((EntityPlayerMP)player);
        }
        return true;
    }
    
    @Nonnull
    @Override
    public TileEntity func_149915_a(@Nonnull final World worldIn, final int meta) {
        return (TileEntity)new TileToolForge();
    }
    
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final String oredict : this.baseBlocks) {
            final List<ItemStack> ores = (List<ItemStack>)OreDictionary.getOres(oredict, false);
            if (ores.size() > 0) {
                list.add((Object)BlockTable.createItemstack(this, 0, func_149634_a(ores.get(0).func_77973_b()), ores.get(0).func_77952_i()));
                if (!Config.listAllTables) {
                    return;
                }
                continue;
            }
        }
    }
    
    @Nonnull
    @Override
    protected BlockStateContainer func_180661_e() {
        return (BlockStateContainer)new ExtendedBlockState((Block)this, new IProperty[0], new IUnlistedProperty[] { (IUnlistedProperty)BlockToolForge.TEXTURE, (IUnlistedProperty)BlockToolForge.INVENTORY, (IUnlistedProperty)BlockToolForge.FACING });
    }
    
    @Override
    public int getGuiNumber(final IBlockState state) {
        return 25;
    }
}
