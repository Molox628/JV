package slimeknights.tconstruct.common;

import net.minecraftforge.fml.common.*;
import slimeknights.tconstruct.*;
import slimeknights.mantle.network.*;
import slimeknights.tconstruct.library.client.particle.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;
import slimeknights.tconstruct.shared.client.*;
import net.minecraftforge.fml.common.network.*;
import slimeknights.tconstruct.common.network.*;
import net.minecraftforge.fluids.*;
import net.minecraft.item.*;
import net.minecraft.world.*;
import net.minecraftforge.event.*;
import net.minecraft.util.*;

public class CommonProxy
{
    public void preInit() {
        if (!Loader.instance().isInState(LoaderState.PREINITIALIZATION)) {
            TConstruct.log.error("Proxy.preInit has to be called during Pre-Initialisation.");
        }
    }
    
    public void init() {
        if (!Loader.instance().isInState(LoaderState.INITIALIZATION)) {
            TConstruct.log.error("Proxy.init has to be called during Initialisation.");
        }
    }
    
    public void postInit() {
        if (!Loader.instance().isInState(LoaderState.POSTINITIALIZATION)) {
            TConstruct.log.error("Proxy.postInit has to be called during Post-Initialisation.");
        }
    }
    
    public void registerModels() {
        if (Loader.instance().hasReachedState(LoaderState.INITIALIZATION)) {
            TConstruct.log.error("Proxy.registerModels has to be called during preInit. Otherwise the models wont be found on first load.");
        }
    }
    
    public void sendPacketToServerOnly(final AbstractPacket packet) {
    }
    
    public void spawnAttackParticle(final Particles particleType, final Entity entity, final double height) {
        float distance = 0.017453292f;
        double xd = -MathHelper.func_76126_a(entity.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(entity.field_70125_A / 180.0f * 3.1415927f);
        double zd = MathHelper.func_76134_b(entity.field_70177_z / 180.0f * 3.1415927f) * MathHelper.func_76134_b(entity.field_70125_A / 180.0f * 3.1415927f);
        double yd = -MathHelper.func_76126_a(entity.field_70125_A / 180.0f * 3.1415927f);
        distance = 1.0f;
        xd *= distance;
        yd *= distance;
        zd *= distance;
        this.spawnParticle(particleType, entity.func_130014_f_(), entity.field_70165_t + xd, entity.field_70163_u + entity.field_70131_O * height, entity.field_70161_v + zd, xd, yd, zd, new int[0]);
    }
    
    public void spawnEffectParticle(final ParticleEffect.Type type, final Entity entity, final int count) {
        this.spawnParticle(Particles.EFFECT, entity.func_130014_f_(), entity.field_70165_t, entity.field_70163_u + entity.field_70131_O * 0.5f, entity.field_70161_v, 0.0, 1.0, 0.0, count, type.ordinal());
    }
    
    public void spawnEffectParticle(final ParticleEffect.Type type, final World world, final double x, final double y, final double z, final int count) {
        this.spawnParticle(Particles.EFFECT, world, x, y, z, 0.0, -1.0, 0.0, count, type.ordinal());
    }
    
    public void spawnParticle(final Particles particleType, final World world, final double x, final double y, final double z, final int... data) {
        this.spawnParticle(particleType, world, x, y, z, 0.0, 0.0, 0.0, data);
    }
    
    public void spawnParticle(final Particles particleType, final World world, final double x, final double y, final double z, final double xSpeed, final double ySpeed, final double zSpeed, final int... data) {
        final NetworkRegistry.TargetPoint point = new NetworkRegistry.TargetPoint(world.field_73011_w.getDimension(), x, y, z, 32.0);
        final AbstractPacket packet = (AbstractPacket)new SpawnParticlePacket(particleType, x, y, z, xSpeed, ySpeed, zSpeed, data);
        TinkerNetwork.sendToAllAround(packet, point);
    }
    
    public void spawnSlimeParticle(final World world, final double x, final double y, final double z) {
    }
    
    public void registerFluidModels(final Fluid fluid) {
    }
    
    public void preventPlayerSlowdown(final Entity player, final float originalSpeed, final Item item) {
    }
    
    public void customExplosion(final World world, final Explosion explosion) {
        if (ForgeEventFactory.onExplosionStart(world, explosion)) {
            return;
        }
        explosion.func_77278_a();
        explosion.func_77279_a(false);
        if (!explosion.field_82755_b) {
            explosion.func_180342_d();
        }
    }
    
    public void updateEquippedItemForRendering(final EnumHand hand) {
    }
}
