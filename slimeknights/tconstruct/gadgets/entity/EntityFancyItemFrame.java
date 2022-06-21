package slimeknights.tconstruct.gadgets.entity;

import net.minecraft.entity.item.*;
import net.minecraftforge.fml.common.registry.*;
import net.minecraft.world.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.item.*;
import javax.annotation.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;

public class EntityFancyItemFrame extends EntityItemFrame implements IEntityAdditionalSpawnData
{
    private FrameType type;
    
    public EntityFancyItemFrame(final World worldIn, final BlockPos p_i45852_2_, final EnumFacing p_i45852_3_, final int meta) {
        this(worldIn, p_i45852_2_, p_i45852_3_, FrameType.fromMeta(meta));
    }
    
    public EntityFancyItemFrame(final World worldIn, final BlockPos p_i45852_2_, final EnumFacing p_i45852_3_, final FrameType type) {
        super(worldIn, p_i45852_2_, p_i45852_3_);
        this.type = type;
    }
    
    public EntityFancyItemFrame(final World worldIn) {
        super(worldIn);
    }
    
    public void func_146065_b(final Entity entity, final boolean dropFrame) {
        if (this.func_130014_f_().func_82736_K().func_82766_b("doEntityDrops")) {
            ItemStack itemstack = this.func_82335_i();
            if (entity instanceof EntityPlayer) {
                final EntityPlayer entityplayer = (EntityPlayer)entity;
                if (entityplayer.field_71075_bZ.field_75098_d) {
                    this.func_110131_b(itemstack);
                    return;
                }
            }
            if (dropFrame) {
                this.func_70099_a(new ItemStack((Item)TinkerGadgets.fancyFrame, 1, this.type.ordinal()), 0.0f);
            }
            if (!itemstack.func_190926_b()) {
                itemstack = itemstack.func_77946_l();
                this.func_110131_b(itemstack);
                this.func_70099_a(itemstack, 0.0f);
            }
        }
    }
    
    @Nonnull
    public String func_70005_c_() {
        if (this.func_145818_k_()) {
            return this.func_95999_t();
        }
        final ItemStack foo = new ItemStack((Item)TinkerGadgets.fancyFrame, 1, this.type.ordinal());
        return foo.func_82833_r();
    }
    
    public void func_70014_b(final NBTTagCompound tagCompound) {
        final int nr = (this.type != null) ? this.type.ordinal() : 0;
        tagCompound.func_74768_a("frame", nr);
        super.func_70014_b(tagCompound);
    }
    
    public void func_70037_a(@Nonnull final NBTTagCompound tagCompund) {
        final int nr = tagCompund.func_74762_e("frame");
        this.type = FrameType.values()[nr % FrameType.values().length];
        super.func_70037_a(tagCompund);
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        assert this.field_174860_b != null;
        buffer.writeShort(this.field_174860_b.func_176736_b());
        buffer.writeShort((this.type != null) ? this.type.ordinal() : 0);
    }
    
    public void readSpawnData(final ByteBuf additionalData) {
        final EnumFacing facing = EnumFacing.func_176731_b((int)additionalData.readShort());
        this.func_174859_a(facing);
        this.type = FrameType.values()[additionalData.readShort()];
    }
    
    public FrameType getType() {
        if (this.type == null) {
            return FrameType.JEWEL;
        }
        return this.type;
    }
    
    public enum FrameType
    {
        JEWEL, 
        ALUBRASS, 
        COBALT, 
        ARDITE, 
        MANYULLYN, 
        GOLD, 
        CLEAR;
        
        public static FrameType fromMeta(final int meta) {
            return values()[meta % values().length];
        }
    }
}
