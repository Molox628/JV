package slimeknights.tconstruct.world.entity;

import net.minecraft.entity.monster.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import javax.annotation.*;
import net.minecraft.entity.item.*;
import slimeknights.tconstruct.shared.*;
import net.minecraft.item.*;
import net.minecraft.world.storage.loot.*;
import slimeknights.tconstruct.shared.block.*;
import slimeknights.tconstruct.world.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.library.*;

public class EntityBlueSlime extends EntitySlime
{
    public static final ResourceLocation LOOT_TABLE;
    
    public EntityBlueSlime(final World worldIn) {
        super(worldIn);
    }
    
    public EntityItem func_145778_a(@Nonnull final Item itemIn, final int size, final float offsetY) {
        final ItemStack stack = TinkerCommons.matSlimeBallBlue.func_77946_l();
        stack.func_190920_e(size);
        return this.func_70099_a(stack, offsetY);
    }
    
    protected ResourceLocation func_184647_J() {
        return (this.func_70809_q() == 1) ? EntityBlueSlime.LOOT_TABLE : LootTableList.field_186419_a;
    }
    
    @Nonnull
    protected EntitySlime func_70802_j() {
        return new EntityBlueSlime(this.func_130014_f_());
    }
    
    public boolean func_70601_bi() {
        return this.func_130014_f_().func_180495_p(this.func_180425_c()).func_177230_c() instanceof BlockLiquidSlime || this.func_130014_f_().func_180495_p(this.func_180425_c().func_177977_b()).func_177230_c() == TinkerWorld.slimeGrass;
    }
    
    protected boolean spawnCustomParticles() {
        if (this.func_130014_f_().field_72995_K) {
            for (int i = this.func_70809_q(), j = 0; j < i * 8; ++j) {
                final float f = this.field_70146_Z.nextFloat() * 3.1415927f * 2.0f;
                final float f2 = this.field_70146_Z.nextFloat() * 0.5f + 0.5f;
                final float f3 = MathHelper.func_76126_a(f) * i * 0.5f * f2;
                final float f4 = MathHelper.func_76134_b(f) * i * 0.5f * f2;
                final double d0 = this.field_70165_t + f3;
                final double d2 = this.field_70161_v + f4;
                final double d3 = this.func_174813_aQ().field_72338_b;
                TinkerWorld.proxy.spawnSlimeParticle(this.func_130014_f_(), d0, d3, d2);
            }
        }
        return true;
    }
    
    static {
        LOOT_TABLE = Util.getResource("entities/blueslime");
    }
}
