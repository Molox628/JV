package slimeknights.tconstruct.library.events;

import slimeknights.tconstruct.library.entity.*;
import javax.annotation.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.util.math.*;
import net.minecraft.block.state.*;

public class ProjectileEvent extends TinkerEvent
{
    public final Entity projectileEntity;
    @Nullable
    public final EntityProjectileBase projectile;
    
    public ProjectileEvent(final Entity projectile) {
        this.projectileEntity = projectile;
        if (projectile instanceof EntityProjectileBase) {
            this.projectile = (EntityProjectileBase)projectile;
        }
        else {
            this.projectile = null;
        }
    }
    
    @Cancelable
    public static class OnLaunch extends ProjectileEvent
    {
        @Nullable
        public final ItemStack launcher;
        @Nullable
        public final EntityLivingBase shooter;
        
        public OnLaunch(final Entity projectile, final ItemStack launcher, final EntityLivingBase shooter) {
            super(projectile);
            this.launcher = launcher;
            this.shooter = shooter;
        }
        
        public static boolean fireEvent(final Entity projectile, final ItemStack launcher, final EntityLivingBase shooter) {
            return !MinecraftForge.EVENT_BUS.post((Event)new OnLaunch(projectile, launcher, shooter));
        }
    }
    
    public static class OnHitBlock extends ProjectileEvent
    {
        public final float speed;
        public final BlockPos pos;
        public final IBlockState blockState;
        
        public OnHitBlock(final EntityProjectileBase projectile, final float speed, final BlockPos pos, final IBlockState blockState) {
            super((Entity)projectile);
            this.speed = speed;
            this.pos = pos;
            this.blockState = blockState;
        }
        
        public static void fireEvent(final EntityProjectileBase projectile, final float speed, final BlockPos pos, final IBlockState blockState) {
            MinecraftForge.EVENT_BUS.post((Event)new OnHitBlock(projectile, speed, pos, blockState));
        }
    }
}
