package slimeknights.tconstruct.gadgets.entity;

import net.minecraft.entity.projectile.*;
import net.minecraftforge.fml.common.registry.*;
import slimeknights.tconstruct.gadgets.item.*;
import javax.annotation.*;
import net.minecraft.util.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.*;
import net.minecraftforge.event.*;
import net.minecraft.world.*;
import slimeknights.tconstruct.gadgets.*;
import net.minecraft.nbt.*;
import io.netty.buffer.*;

public class EntityThrowball extends EntityThrowable implements IEntityAdditionalSpawnData
{
    public ItemThrowball.ThrowballType type;
    
    public EntityThrowball(final World worldIn) {
        super(worldIn);
    }
    
    public EntityThrowball(final World worldIn, final double x, final double y, final double z) {
        super(worldIn, x, y, z);
    }
    
    public EntityThrowball(final World worldIn, final EntityLivingBase throwerIn, final ItemThrowball.ThrowballType type) {
        super(worldIn, throwerIn);
        this.type = type;
    }
    
    protected void func_70184_a(@Nonnull final RayTraceResult result) {
        if (this.type != null) {
            switch (this.type) {
                case GLOW: {
                    this.placeGlow(result);
                    break;
                }
                case EFLN: {
                    this.explode(6.0f);
                    break;
                }
            }
        }
        if (!this.func_130014_f_().field_72995_K) {
            this.func_70106_y();
        }
    }
    
    private void placeGlow(final RayTraceResult result) {
        if (!this.func_130014_f_().field_72995_K) {
            BlockPos pos = result.func_178782_a();
            if (pos == null && result.field_72308_g != null) {
                pos = result.field_72308_g.func_180425_c();
            }
            EnumFacing facing = EnumFacing.DOWN;
            if (result.field_72313_a == RayTraceResult.Type.BLOCK) {
                pos = pos.func_177972_a(result.field_178784_b);
                facing = result.field_178784_b.func_176734_d();
            }
            TinkerCommons.blockGlow.addGlow(this.func_130014_f_(), pos, facing);
        }
    }
    
    protected void explode(final float strength) {
        if (!this.func_130014_f_().field_72995_K) {
            final ExplosionEFLN explosion = new ExplosionEFLN(this.func_130014_f_(), (Entity)this, this.field_70165_t, this.field_70163_u, this.field_70161_v, strength, false, false);
            if (!ForgeEventFactory.onExplosionStart(this.field_70170_p, (Explosion)explosion)) {
                Exploder.startExplosion(this.func_130014_f_(), explosion, (Entity)this, new BlockPos(this.field_70165_t, this.field_70163_u, this.field_70161_v), strength, strength);
            }
        }
    }
    
    public void func_70014_b(final NBTTagCompound compound) {
        super.func_70014_b(compound);
        this.ensureType();
        compound.func_74768_a("type", this.type.ordinal());
    }
    
    public void func_70037_a(final NBTTagCompound compound) {
        super.func_70037_a(compound);
        this.type = ItemThrowball.ThrowballType.values()[compound.func_74762_e("type")];
        this.ensureType();
    }
    
    public void writeSpawnData(final ByteBuf buffer) {
        this.ensureType();
        buffer.writeInt(this.type.ordinal());
    }
    
    public void readSpawnData(final ByteBuf additionalData) {
        this.type = ItemThrowball.ThrowballType.values()[additionalData.readInt()];
        this.ensureType();
    }
    
    private void ensureType() {
        if (this.type == null) {
            this.type = ItemThrowball.ThrowballType.GLOW;
        }
    }
}
