package slimeknights.tconstruct.shared.block;

import slimeknights.tconstruct.library.*;
import net.minecraft.creativetab.*;
import net.minecraft.item.*;
import net.minecraft.block.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.properties.*;
import javax.annotation.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import slimeknights.mantle.block.*;
import java.util.*;

public class BlockSlime extends net.minecraft.block.BlockSlime
{
    public static final PropertyEnum<SlimeType> TYPE;
    
    public BlockSlime() {
        this.func_149647_a((CreativeTabs)TinkerRegistry.tabWorld);
        this.func_149649_H();
        this.func_149672_a(SoundType.field_185859_l);
    }
    
    @SideOnly(Side.CLIENT)
    public void func_149666_a(final CreativeTabs tab, final NonNullList<ItemStack> list) {
        for (final SlimeType type : SlimeType.values()) {
            list.add((Object)new ItemStack((Block)this, 1, type.meta));
        }
    }
    
    @Nonnull
    protected BlockStateContainer func_180661_e() {
        return new BlockStateContainer((Block)this, new IProperty[] { (IProperty)BlockSlime.TYPE });
    }
    
    @Nonnull
    public IBlockState func_176203_a(final int meta) {
        return this.func_176223_P().func_177226_a((IProperty)BlockSlime.TYPE, (Comparable)SlimeType.fromMeta(meta));
    }
    
    public int func_176201_c(final IBlockState state) {
        return ((SlimeType)state.func_177229_b((IProperty)BlockSlime.TYPE)).meta;
    }
    
    public int func_180651_a(final IBlockState state) {
        return this.func_176201_c(state);
    }
    
    public boolean isStickyBlock(final IBlockState state) {
        return true;
    }
    
    static {
        TYPE = PropertyEnum.func_177709_a("type", (Class)SlimeType.class);
    }
    
    public enum SlimeType implements IStringSerializable, EnumBlock.IEnumMeta
    {
        GREEN(118094, 6929502), 
        BLUE(117709, 7652808), 
        PURPLE(11488502, 13396223), 
        BLOOD(11862273, 12058624), 
        MAGMA(16750349, 16755529);
        
        public final int meta;
        private final int color;
        private final int ballColor;
        
        private SlimeType(final int color, final int ballColor) {
            this.meta = this.ordinal();
            this.color = color;
            this.ballColor = ballColor;
        }
        
        public static SlimeType fromMeta(int meta) {
            if (meta < 0 || meta >= values().length) {
                meta = 0;
            }
            return values()[meta];
        }
        
        public int getMeta() {
            return this.meta;
        }
        
        public String func_176610_l() {
            return this.toString().toLowerCase(Locale.US);
        }
        
        public int getColor() {
            return this.color;
        }
        
        public int getBallColor() {
            return this.ballColor;
        }
    }
}
