package slimeknights.tconstruct.gadgets.entity;

import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.fml.relauncher.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import com.google.common.collect.*;
import net.minecraft.block.*;

public class ExplosionEFLN extends Explosion
{
    protected ImmutableSet<BlockPos> affectedBlockPositionsInternal;
    
    @SideOnly(Side.CLIENT)
    public ExplosionEFLN(final World worldIn, final Entity entityIn, final double x, final double y, final double z, final float size, final List<BlockPos> affectedPositions) {
        super(worldIn, entityIn, x, y, z, size, (List)affectedPositions);
    }
    
    @SideOnly(Side.CLIENT)
    public ExplosionEFLN(final World worldIn, final Entity entityIn, final double x, final double y, final double z, final float size, final boolean flaming, final boolean smoking, final List<BlockPos> affectedPositions) {
        super(worldIn, entityIn, x, y, z, size, flaming, smoking, (List)affectedPositions);
    }
    
    public ExplosionEFLN(final World worldIn, final Entity entityIn, final double x, final double y, final double z, final float size, final boolean flaming, final boolean smoking) {
        super(worldIn, entityIn, x, y, z, size, flaming, smoking);
    }
    
    public void func_77278_a() {
        final ImmutableSet.Builder<BlockPos> builder = (ImmutableSet.Builder<BlockPos>)ImmutableSet.builder();
        final float r = this.field_77280_f * this.field_77280_f;
        for (int i = (int)r + 1, j = -i; j < i; ++j) {
            for (int k = -i; k < i; ++k) {
                for (int l = -i; l < i; ++l) {
                    final int d = j * j + k * k + l * l;
                    if (d <= r) {
                        final BlockPos blockpos = new BlockPos(j, k, l).func_177963_a(this.field_77284_b, this.field_77285_c, this.field_77282_d);
                        if (!this.field_77287_j.func_175623_d(blockpos)) {
                            float f = this.field_77280_f * (1.0f - d / r);
                            final IBlockState iblockstate = this.field_77287_j.func_180495_p(blockpos);
                            final float f2 = (this.field_77283_e != null) ? this.field_77283_e.func_180428_a((Explosion)this, this.field_77287_j, blockpos, iblockstate) : iblockstate.func_177230_c().getExplosionResistance(this.field_77287_j, blockpos, (Entity)null, (Explosion)this);
                            f -= (f2 + 0.3f) * 0.3f;
                            if (f > 0.0f && (this.field_77283_e == null || this.field_77283_e.func_174816_a((Explosion)this, this.field_77287_j, blockpos, iblockstate, f))) {
                                builder.add((Object)blockpos);
                            }
                        }
                    }
                }
            }
        }
        this.affectedBlockPositionsInternal = (ImmutableSet<BlockPos>)builder.build();
    }
    
    public void func_77279_a(final boolean spawnParticles) {
        this.field_77287_j.func_184148_a((EntityPlayer)null, this.field_77284_b, this.field_77285_c, this.field_77282_d, SoundEvents.field_187539_bB, SoundCategory.BLOCKS, 4.0f, (1.0f + (this.field_77287_j.field_73012_v.nextFloat() - this.field_77287_j.field_73012_v.nextFloat()) * 0.2f) * 0.7f);
        this.field_77287_j.func_175688_a(EnumParticleTypes.EXPLOSION_LARGE, this.field_77284_b, this.field_77285_c, this.field_77282_d, 1.0, 0.0, 0.0, new int[0]);
        for (final BlockPos blockpos : this.affectedBlockPositionsInternal) {
            final IBlockState iblockstate = this.field_77287_j.func_180495_p(blockpos);
            final Block block = iblockstate.func_177230_c();
            if (iblockstate.func_185904_a() != Material.field_151579_a) {
                if (block.func_149659_a((Explosion)this)) {
                    block.func_180653_a(this.field_77287_j, blockpos, this.field_77287_j.func_180495_p(blockpos), 1.0f, 0);
                }
                block.onBlockExploded(this.field_77287_j, blockpos, (Explosion)this);
            }
        }
    }
    
    public void addAffectedBlock(final BlockPos blockPos) {
        this.field_77281_g.add(blockPos);
    }
}
